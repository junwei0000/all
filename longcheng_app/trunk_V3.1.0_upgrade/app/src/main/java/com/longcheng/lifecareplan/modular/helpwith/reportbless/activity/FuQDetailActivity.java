package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.DetailJieQiAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQDetailAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.like.GoodImgView;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 福券详情
 */
public class FuQDetailActivity extends BaseListActivity<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.layout_engry)
    LinearLayout layoutEngry;
    @BindView(R.id.tv_noble)
    TextView tvNoble;
    @BindView(R.id.tv_fushen)
    TextView tv_fushen;
    @BindView(R.id.detail_lv_rank)
    MyListView detailLvRank;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.item_iv_img)
    ImageView itemIvImg;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieqi)
    TextView itemTvJieqi;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.gv_jieqi)
    MyGridView gvJieqi;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_sendtitle)
    TextView tv_sendtitle;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_typetitle)
    TextView tv_typetitle;
    @BindView(R.id.tv_typebtn)
    TextView tv_typebtn;
    @BindView(R.id.tv_typetitle3)
    TextView tv_typetitle3;


    @BindView(R.id.relat_info)
    FrameLayout relat_info;
    @BindView(R.id.relat_hua)
    RelativeLayout relat_hua;
    @BindView(R.id.relat_huawai)
    RelativeLayout relat_huawai;

    List<DetailAfterBean.DetailItemBean> response = new ArrayList<>();
    List<DetailAfterBean.DetailItemBean> recive = new ArrayList<>();
    String think_user_id;
    int widhua;
    String bless_id;
    List<DetailAfterBean.DetailItemBean> list;
    int index;
    boolean destory = false;
    boolean guiren = true;
    private GoodImgView mGoodView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_noble:
                guiren = true;
                destory = true;
                mHandler.sendEmptyMessageDelayed(0, 200);
                break;
            case R.id.tv_fushen:
                guiren = false;
                destory = true;
                mHandler.sendEmptyMessageDelayed(0, 200);
                break;
            case R.id.tv_typebtn:
                if (!TextUtils.isEmpty(think_user_id))
                    mPresent.ThinkEnterQueue(bless_id, think_user_id, 2);
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_detail;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("福券详情");
        pagetopLayoutLeft.setOnClickListener(this);
        tvNoble.setOnClickListener(this);
        tv_fushen.setOnClickListener(this);
        tv_typebtn.setOnClickListener(this);
        pagetopLayoutLeft.setFocusable(true);
        pagetopLayoutLeft.setFocusableInTouchMode(true);
        pagetopLayoutLeft.requestFocus();
        widhua = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 60);
        relat_huawai.setLayoutParams(new LinearLayout.LayoutParams(widhua, widhua));
    }

    @Override
    public void initDataAfter() {
        initLo();
        bless_id = getIntent().getStringExtra("bless_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList() {
        mPresent.getFuQDetailList(bless_id);
    }

    @Override
    protected ReportPresenterImp<ReportContract.View> createPresent() {
        return new ReportPresenterImp<>(mContext, this);
    }

    boolean refreshStatus = false;

    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void ListSuccess(ReportDataBean responseBean, int backPage) {

    }

    @Override
    public void DetailSuccess(FQDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DetailAfterBean mDataBean = responseBean.getData();
            String complete_money = mDataBean.getBless_user_statistics().getComplete_money();
            response = mDataBean.getSponsorDatas();
            recive = mDataBean.getWaitFillDatas();
            showUserView(mDataBean);
            showJieQiView(mDataBean.getLack_user_solar_terms());
            showNum(complete_money);
            mHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {

    }

    @Override
    public void Error() {
    }


    private void showUserView(DetailAfterBean mDataBean) {
        DetailAfterBean.DetailItemBean chatuser = mDataBean.getChatuser();
        GlideDownLoadImage.getInstance().loadCircleImage(chatuser.getAvatar(), itemIvImg);
        itemTvName.setText(CommonUtil.setName(chatuser.getUser_name()));
        itemTvJieqi.setText(chatuser.getJieqi_name());
        tvDate.setText(mDataBean.getBless().getCreate_date());
        List<String> identity_img = mDataBean.getCurrentUserIdentitys();
        itemLayoutShenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                ImageView imageView = new ImageView(mContext);
                int dit = DensityUtil.dip2px(mContext, 16);
                int jian = DensityUtil.dip2px(mContext, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, url, imageView);
                linearLayout.addView(imageView);
                itemLayoutShenfen.addView(linearLayout);
            }
        }

    }

    private void showJieQiView(ArrayList<DetailAfterBean.DetailItemBean> lack_user_solar_terms) {
        DetailJieQiAdapter mDetailJieQiAdapter = new DetailJieQiAdapter(mContext, lack_user_solar_terms);
        gvJieqi.setAdapter(mDetailJieQiAdapter);
    }

    private void showNum(String complete_money) {
        Log.e("onItemClick", "complete_money=" + complete_money);
        if (!TextUtils.isEmpty(complete_money)) {
            layoutEngry.removeAllViews();
            for (int i = 0; i < complete_money.length(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.fuqrep_loveresult_topnum, null);
                TextView tv_num = view.findViewById(R.id.tv_num);
                tv_num.setText("" + complete_money.charAt(i));
                layoutEngry.addView(view);
            }
        }
    }


    private void changeTab() {
        if (guiren) {
            shoeList(recive);
            tvNoble.setTextColor(getResources().getColor(R.color.white));
            tvNoble.setBackgroundResource(R.drawable.corners_bg_yellow25);
            tv_fushen.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tv_fushen.setBackgroundResource(R.color.transparent);
            tv_sendtitle.setText("24节气祝福");
            tv_typetitle3.setText("已收获");
            tv_typetitle.setVisibility(View.GONE);
            tv_typebtn.setVisibility(View.VISIBLE);
        } else {
            shoeList(response);
            tvNoble.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tvNoble.setBackgroundResource(R.color.transparent);
            tv_fushen.setTextColor(getResources().getColor(R.color.white));
            tv_fushen.setBackgroundResource(R.drawable.corners_bg_yellow25);
            tv_sendtitle.setText("18节气祝福");
            tv_typetitle3.setText("已送出");
            tv_typetitle.setVisibility(View.VISIBLE);
            tv_typebtn.setVisibility(View.GONE);
        }
    }


    private void shoeList(List<DetailAfterBean.DetailItemBean> list) {
        FuQDetailAdapter mLoveResultAdapter = new FuQDetailAdapter(mContext, list, guiren);
        detailLvRank.setAdapter(mLoveResultAdapter);
        if (mGoodView == null) {
            mGoodView = new GoodImgView(this);
        }
        tv_type.setText("0");
        relat_hua.removeAllViews();
        relat_info.removeAllViews();
        if (list != null && list.size() > 0) {
            layoutNotdate.setVisibility(View.GONE);
            detailLvRank.setVisibility(View.VISIBLE);
            this.list = list;
            index = 0;
            think_user_id = "";
            mHandler.sendEmptyMessageDelayed(1, 800);
        } else {
            destory = true;
            layoutNotdate.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destory = true;
    }

    private void showJieQiImgView(int index_) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fuqrep_detail_huaitem, null);
        ImageView iv_hua = view.findViewById(R.id.iv_hua);
        iv_hua.setLayoutParams(new LinearLayout.LayoutParams(widhua, widhua));
        String solar_terms_en = list.get(index_).getSolar_terms_en();
        if (solar_terms_en.equals("lichun")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_lichun_img);
        } else if (solar_terms_en.equals("yushui")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_yushui_img);
        } else if (solar_terms_en.equals("jingzhe")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_jingzhe_img);
        } else if (solar_terms_en.equals("chunfen")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_chunfen_img);
        } else if (solar_terms_en.equals("qingming")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_qingming_img);
        } else if (solar_terms_en.equals("guyu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_guyu_img);
        } else if (solar_terms_en.equals("lixia")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_lixia_img);
        } else if (solar_terms_en.equals("xiaoman")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_xiaoman_img);
        } else if (solar_terms_en.equals("mangzhong")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_mangzhong_img);
        } else if (solar_terms_en.equals("xiazhi")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_xiazhi_img);
        } else if (solar_terms_en.equals("xiaoshu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_xiaoshu_img);
        } else if (solar_terms_en.equals("dashu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_dashu_img);
        } else if (solar_terms_en.equals("liqiu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_liqiu_img);
        } else if (solar_terms_en.equals("chushu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_chushu_img);
        } else if (solar_terms_en.equals("bailu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_bailu_img);
        } else if (solar_terms_en.equals("qiufen")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_qiufen_img);
        } else if (solar_terms_en.equals("hanlu")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_hanlu_img);
        } else if (solar_terms_en.equals("shuangjiang")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_shuangjiang_img);
        } else if (solar_terms_en.equals("lidong")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_lidong_img);
        } else if (solar_terms_en.equals("xiaoxue")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_xiaoxue_img);
        } else if (solar_terms_en.equals("daxue")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_daxue_img);
        } else if (solar_terms_en.equals("dongzhi")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_dongzhi_img);
        } else if (solar_terms_en.equals("xiaohan")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_xiaohan_img);
        } else if (solar_terms_en.equals("dahan")) {
            iv_hua.setBackgroundResource(R.mipmap.fq_dahan_img);
        }
        relat_hua.addView(view);
        //
        if (index == 0) {
            think_user_id = list.get(index).getUser_id();
        } else {
            think_user_id = think_user_id + "," + list.get(index).getUser_id();
        }
        tv_type.setText("" + (index + 1));
        mGoodView.setImage(getResources().getDrawable(R.mipmap.fq_add_num));
        mGoodView.setDuration(800);
        mGoodView.show(tv_type);
        //
        if (index < list.size() - 1) {
            index++;
            mHandler.sendEmptyMessageDelayed(1, 800);
            mHandler.sendEmptyMessageDelayed(11, 800);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    destory = false;
                    changeTab();
                    break;
                case 1:
                    if (!destory)
                        showJieQiImgView(index);
                    break;
                case 11:
                    if (list != null && !destory) {
                        String solar_terms_en = list.get(index).getSolar_terms_en();

                        float fromX = mJieQMap.get(solar_terms_en).getFromX();
                        float fromY = mJieQMap.get(solar_terms_en).getFromY();
                        View view = LayoutInflater.from(mContext).inflate(R.layout.fuqrep_detail_additem, null);
                        ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
                        TextView tv_name = view.findViewById(R.id.tv_name);
                        String name = list.get(index).getUser_name();
                        if (!TextUtils.isEmpty(name) && name.length() > 3) {
                            name = name.substring(0, 3);
                        }
                        GlideDownLoadImage.getInstance().loadCircleImage(list.get(index).getAvatar(), iv_thumb);
                        tv_name.setText(CommonUtil.setName(name));
                        int[] loc = new int[2];
                        loc[0] = (int) fromX;
                        loc[1] = (int) fromY;
                        Log.e("playAnimation", "loc==" + loc[0] + "    " + loc[1]);
                        setAnimNew(view, fromX, fromY, index);
                    }
                    break;
            }
        }
    };


    HashMap<String, NFCDetailItemBean> mJieQMap = new HashMap();

    private void initLo() {
        int width = DensityUtil.screenWith(mContext) * 1 / 2;
        //左上
        mJieQMap.put("qiufen", new NFCDetailItemBean(-width * 6 / 6, -width * 0 / 6));

        mJieQMap.put("hanlu", new NFCDetailItemBean(-width * 5 / 6, -width * 1 / 6));
        mJieQMap.put("shuangjiang", new NFCDetailItemBean(-width * 4 / 6, -width * 2 / 6));
        mJieQMap.put("lidong", new NFCDetailItemBean(-width * 3 / 6, -width * 3 / 6));
        mJieQMap.put("xiaoxue", new NFCDetailItemBean(-width * 2 / 6, -width * 4 / 6));
        mJieQMap.put("daxue", new NFCDetailItemBean(-width * 1 / 6, -width * 5 / 6));
        //右上
        mJieQMap.put("dongzhi", new NFCDetailItemBean(width * 0 / 6, -width * 6 / 6));

        mJieQMap.put("xiaohan", new NFCDetailItemBean(width * 1 / 6, -width * 5 / 6));
        mJieQMap.put("dahan", new NFCDetailItemBean(width * 2 / 6, -width * 4 / 6));
        mJieQMap.put("lichun", new NFCDetailItemBean(width * 3 / 6, -width * 3 / 6));
        mJieQMap.put("yushui", new NFCDetailItemBean(width * 4 / 6, -width * 2 / 6));
        mJieQMap.put("jingzhe", new NFCDetailItemBean(width * 5 / 6, -width * 1 / 6));
        //右下
        mJieQMap.put("chunfen", new NFCDetailItemBean(width * 6 / 6, -width * 0 / 6));

        mJieQMap.put("qingming", new NFCDetailItemBean(width * 5 / 6, width * 1 / 6));
        mJieQMap.put("guyu", new NFCDetailItemBean(width * 4 / 6, width * 2 / 6));
        mJieQMap.put("lixia", new NFCDetailItemBean(width * 3 / 6, width * 3 / 6));
        mJieQMap.put("xiaoman", new NFCDetailItemBean(width * 2 / 6, width * 4 / 6));
        mJieQMap.put("mangzhong", new NFCDetailItemBean(width * 1 / 6, width * 5 / 6));
        //左下
        mJieQMap.put("xiazhi", new NFCDetailItemBean(width * 0 / 6, width * 6 / 6));

        mJieQMap.put("xiaoshu", new NFCDetailItemBean(-width * 1 / 6, width * 5 / 6));
        mJieQMap.put("dashu", new NFCDetailItemBean(-width * 2 / 6, width * 4 / 6));
        mJieQMap.put("liqiu", new NFCDetailItemBean(-width * 3 / 6, width * 3 / 6));
        mJieQMap.put("chushu", new NFCDetailItemBean(-width * 4 / 6, width * 2 / 6));
        mJieQMap.put("bailu", new NFCDetailItemBean(-width * 5 / 6, width * 1 / 6));
    }

    private void setAnimNew(View view, float fromX, float fromY, int i) {
        if (relat_info != null) {
            relat_info.addView(view);
        }
        //位移
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(view, "translationX", fromX, 0);
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(view, "translationY", fromY, 0);
        //缩放
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.1f);
        //透明
//        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.1f);
//        anim.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY); //设置动画
        animatorSet.setDuration(1800);  //设置动画时间
        animatorSet.start(); //启动
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }


}
