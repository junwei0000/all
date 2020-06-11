package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleListProgressUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CSRecordAdapter extends BaseAdapterHelper<CSRecordList> {
    private Context context;
    private Activity activity;
    private List<CSRecordList> list;
    private int type = 3;
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    private IActivityUpData dd;

    public CSRecordAdapter(Context context, List<CSRecordList> list, Activity activity, int type) {
        super(context, list);
        this.context = context;
        this.activity = activity;
        this.list = list;
        this.type = type;
        this.dd = dd;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    public void setUpdateData(IActivityUpData dd) {
        this.dd = dd;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CSRecordList> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cs_record, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).getStatus() == 2) {
            mHolder.tv_have_money.setVisibility(View.GONE);
            mHolder.ll_no_money.setVisibility(View.VISIBLE);
        } else {
            mHolder.tv_have_money.setVisibility(View.VISIBLE);
            mHolder.ll_no_money.setVisibility(View.GONE);
        }
        mHolder.tv_jieqi.setText(list.get(position).getUser_branch_info());
        mHolder.tv_time.setText("完成时间: " + list.get(position).getOver_time());
        mHolder.tv_money.setText(list.get(position).getUser_money() + "元");
        mHolder.tv_card_name.setText(list.get(position).getCardholder_name());
        mHolder.tv_card_bank.setText(list.get(position).getBank_name());
        mHolder.tv_card_num.setText(list.get(position).getBank_no());
        mHolder.tv_card_bank_zhi.setText(list.get(position).getBank_full_name());
        asyncImageLoader.DisplayImage(list.get(position).getUser_avatar(), mHolder.iv_head);
        String name = list.get(position).getUser_name();
        if (!TextUtils.isEmpty(name) && name.length() > 9) {
            name = name.substring(0, 7) + "…";
        }
        mHolder.tv_name.setText(name);
        ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.tv_submit, R.color.red);
        mHolder.tv_submit.setTag(position);
        mHolder.tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                showHelpDialog(list.get(position).getHelp_need_id());

            }
        });
        mHolder.tv_copy.setTag(position);
        mHolder.tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                String content = list.get(position).getCardholder_name() + "\n"
                        + list.get(position).getUser_money() + "元\n"
                        + list.get(position).getBank_name() + "\n"
                        + list.get(position).getBank_no() + "\n"
                        + list.get(position).getBank_full_name();
                PriceUtils.getInstance().copy(activity, content);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_jieqi;
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_money;
        private TextView tv_card_name;
        private TextView tv_card_bank;
        private TextView tv_card_num;
        private TextView tv_submit;
        private TextView tv_copy;
        private TextView t1;
        private TextView t2;
        private TextView t3;
        private TextView t4;
        private TextView tv_have_money;
        private TextView tv_card_bank_zhi;
        private ImageView iv_head;
        private LinearLayout ll_no_money;


        public ViewHolder(View convertView) {
            tv_jieqi = convertView.findViewById(R.id.tv_jieqi);
            ll_no_money = convertView.findViewById(R.id.ll_no_money);
            tv_card_bank_zhi = convertView.findViewById(R.id.tv_card_bank_zhi);
            t1 = convertView.findViewById(R.id.t1);
            tv_have_money = convertView.findViewById(R.id.tv_have_money);
            t2 = convertView.findViewById(R.id.t2);
            t3 = convertView.findViewById(R.id.t3);
            t4 = convertView.findViewById(R.id.t4);
            tv_submit = convertView.findViewById(R.id.tv_submit);
            tv_time = convertView.findViewById(R.id.tv_time);
            tv_copy = convertView.findViewById(R.id.tv_copy);
            tv_card_name = convertView.findViewById(R.id.tv_card_name);
            tv_card_num = convertView.findViewById(R.id.tv_card_num);
            tv_card_bank = convertView.findViewById(R.id.tv_card_bank);
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_money = convertView.findViewById(R.id.tv_money);
            iv_head = convertView.findViewById(R.id.iv_head);

        }
    }

    public void submit(int help_need_id) {
        Observable<SubmitMoneyBean> observable = Api.getInstance().service.helpneed_confirmMakeMoney(
                UserUtils.getUserId(context), help_need_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SubmitMoneyBean>() {
                    @Override
                    public void accept(SubmitMoneyBean responseBean) throws Exception {
                        ToastUtils.showToast(responseBean.getMsg());
                        dd.upDataUi();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(throwable.getMessage());
                    }
                });
    }

    private MyDialog submitDialog;

    public void showHelpDialog(int id) {
        submitDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_submit);// 创建Dialog并设置样式主题
        submitDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = submitDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        submitDialog.show();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = submitDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
        submitDialog.getWindow().setAttributes(p); //设置生效
        TextView tv_ChangeCard = submitDialog.findViewById(R.id.tv_ChangeCard);
        TextView tv_sure = submitDialog.findViewById(R.id.tv_sure);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDialog.dismiss();
                submit(id);

            }
        });
        tv_ChangeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDialog.dismiss();
            }
        });


    }
}
