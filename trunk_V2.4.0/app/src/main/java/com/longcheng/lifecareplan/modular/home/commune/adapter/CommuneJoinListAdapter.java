package com.longcheng.lifecareplan.modular.home.commune.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneJoinListActivity;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneJoinTeamListActivity;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.like.GoodImgView;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;

import java.util.List;


public class CommuneJoinListAdapter extends BaseAdapterHelper<CommuneItemBean> {
    private final GoodImgView mGoodView;
    ViewHolder mHolder = null;
    Context context;
    int isLeader;
    Handler mHandler;
    int mHandlerID;

    /**
     * 是否点击刷新数据，显示动画
     */
    boolean updateClickLikeStatus = false;
    int clickPo = -1;

    public CommuneJoinListAdapter(Context context, List<CommuneItemBean> list, int isLeader, Handler mHandler, int mHandlerID) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
        this.isLeader = isLeader;
        mGoodView = new GoodImgView(context);
    }

    public boolean isUpdateClickLikeStatus() {
        return updateClickLikeStatus;
    }

    public void setUpdateClickLikeStatus(boolean updateClickLikeStatus) {
        this.updateClickLikeStatus = updateClickLikeStatus;
    }

    public int getClickPo() {
        return clickPo;
    }

    public void setClickPo(int clickPo) {
        this.clickPo = clickPo;
    }

    /**
     * 点赞效果动画
     *
     * @param view
     */
    public void ClickLikeShowAn(View view) {
        clickPo = -1;
        updateClickLikeStatus = false;
        mGoodView.setImage(context.getResources().getDrawable(R.mipmap.good_checked));
        mGoodView.show(view);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CommuneItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.commune_joinlist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CommuneItemBean mHelpItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, mHelpItemBean.getAvatar(), mHolder.item_iv_communethumb);
        String num = mHelpItemBean.getLikes_number();
        mHolder.item_tv_likenum.setText(num);
        if (!TextUtils.isEmpty(num) && num.equals("0")) {
            mHolder.item_tv_likenum.setText("赞");
        }
        mHolder.item_tv_communename.setText(mHelpItemBean.getGroup_name());
        String name = mHelpItemBean.getUser_name();
        if (TextUtils.isEmpty(name)) {
            name = "暂无";
        }
        mHolder.item_tv_communename2.setText("主任  " + name);
        String count = mHelpItemBean.getCount();
        mHolder.item_tv_num.setText(count);
        mHolder.item_tv_engrynum.setText(mHelpItemBean.getAbility());
        if (updateClickLikeStatus && clickPo == position) {
            ClickLikeShowAn(mHolder.item_layout_top);
        }
        mHolder.item_layout_likenum.setTag(position);
        mHolder.item_layout_likenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                CommuneItemBean mHelpItemBean = list.get(position);
                Message message = new Message();
                message.what = mHandlerID;
                message.arg1 = mHelpItemBean.getGroup_id();
                message.arg2 = position;
                mHandler.sendMessage(message);
                message = null;
            }
        });
        mHolder.item_tv_joincommune.setTag(mHelpItemBean.getGroup_id());
        mHolder.item_tv_joincommune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeader == 1) {
                    ToastUtils.showToast("您在公社有任职，不可更改噢~");
                } else {
                    int group_id = (int) v.getTag();
                    Intent intent = new Intent(context, CommuneJoinTeamListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("group_id", group_id);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout item_layout_top;
        private ImageView item_iv_communethumb;
        private LinearLayout item_layout_likenum;
        private TextView item_tv_likenum;

        private TextView item_tv_communename;

        private TextView item_tv_communename2;

        private TextView item_tv_num;
        private TextView item_tv_engrynum;

        private TextView item_tv_joincommune;

        public ViewHolder(View view) {
            item_layout_top = view.findViewById(R.id.item_layout_top);
            item_iv_communethumb = view.findViewById(R.id.item_iv_communethumb);
            item_layout_likenum = view.findViewById(R.id.item_layout_likenum);
            item_tv_likenum = view.findViewById(R.id.item_tv_likenum);
            item_tv_communename = view.findViewById(R.id.item_tv_communename);
            item_tv_communename2 = view.findViewById(R.id.item_tv_communename2);

            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_tv_engrynum = view.findViewById(R.id.item_tv_engrynum);

            item_tv_joincommune = view.findViewById(R.id.item_tv_joincommune);
        }
    }
}
