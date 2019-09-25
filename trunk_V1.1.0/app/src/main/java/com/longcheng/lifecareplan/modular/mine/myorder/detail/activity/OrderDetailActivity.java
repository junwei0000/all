package com.longcheng.lifecareplan.modular.mine.myorder.detail.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity.LifeStyleApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressAddActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.activity.TrankActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity.ThanksActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 *
 */
public class OrderDetailActivity extends BaseActivityMVP<DetailContract.View, DetailPresenterImp<DetailContract.View>> implements DetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.detail_iv_statusimg)
    ImageView detailIvStatusimg;
    @BindView(R.id.detail_tv_status)
    TextView detailTvStatus;
    @BindView(R.id.detail_tv_name)
    TextView detailTvName;
    @BindView(R.id.detail_tv_address)
    TextView detailTvAddress;
    @BindView(R.id.tv_goodtype)
    TextView tvGoodtype;
    @BindView(R.id.layout_type)
    LinearLayout layoutType;
    @BindView(R.id.item_iv_goodthumb)
    ImageView itemIvGoodthumb;
    @BindView(R.id.item_tv_goodname)
    TextView itemTvGoodname;
    @BindView(R.id.item_tv_guigename)
    TextView item_tv_guigename;
    @BindView(R.id.item_iv_goodtypeimg)
    ImageView itemIvGoodtypeimg;
    @BindView(R.id.item_tv_goodnum)
    TextView itemTvGoodnum;
    @BindView(R.id.layout_good)
    LinearLayout layoutGood;
    @BindView(R.id.detail_tv_ordernum)
    TextView detailTvOrdernum;
    @BindView(R.id.detail_tv_time)
    TextView detailTvTime;
    @BindView(R.id.detail_tv_left)
    TextView detailTvLeft;
    @BindView(R.id.detail_tv_center)
    TextView detailTvCenter;
    @BindView(R.id.detail_tv_right)
    TextView detailTvRight;
    @BindView(R.id.layout_bootom)
    LinearLayout layout_bootom;
    @BindView(R.id.tv_leftimg)
    TextView tv_leftimg;
    @BindView(R.id.tv_rightimg)
    TextView tv_rightimg;
    @BindView(R.id.layout_yajin)
    LinearLayout layout_yajin;
    @BindView(R.id.detail_tv_yajin)
    TextView detail_tv_yajin;

    private String user_id, order_id;
    /**
     * //订单类型	1商城订单 2 生命能量订单 3 生活方式互祝订单 4 康农
     */
    private int type;
    private String goods_info_url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.detail_tv_left:
                setLeftClickSkip(mOrderItemBean);
                break;
            case R.id.detail_tv_center:
                setCenterClickSkip(mOrderItemBean);
                break;
            case R.id.detail_tv_right:
                setRightClickSkip(mOrderItemBean);
                break;
            case R.id.layout_type:
            case R.id.layout_good:
                int Bottom_status = mOrderItemBean.getBottom_status();
                if (type == 2) {//生命能量详情
                    if (Bottom_status != 0 && Bottom_status != 8 && Bottom_status != -1) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("msg_id", mOrderItemBean.getMsg_id());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }
                } else if (type == 4) {//康农工程互祝详情
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + mOrderItemBean.getKnp_info_url());
                    startActivity(intent);
                } else {//商品详情
                    if (type == 3) {
                        if (Bottom_status != 0 && Bottom_status != 8 && Bottom_status != -1) {
                            Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("help_goods_id", mOrderItemBean.getHelp_goods_id());
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        }
                    } else {
                        RedeemAgain();
                    }
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_order_detail;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("订单详情");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layoutGood.setOnClickListener(this);
        layoutType.setOnClickListener(this);
        user_id = UserUtils.getUserId(mContext);
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        mPresent.getDetail(user_id, order_id);
    }

    @Override
    public void initDataAfter() {

    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn)
            mPresent.getDetail(user_id, order_id);
    }

    @Override
    protected DetailPresenterImp<DetailContract.View> createPresent() {
        return new DetailPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    DetailAfterBean mOrderItemBean = new DetailAfterBean();

    @Override
    public void ListSuccess(DetailDataBean responseBean) {
        firstComIn = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            mOrderItemBean = responseBean.getData();
            if (mOrderItemBean != null) {
                goods_info_url = mOrderItemBean.getGoods_info_url();
                showGoodView(mOrderItemBean);
                showStatusView(mOrderItemBean);
                showBottomBtn(mOrderItemBean);
                if (detailTvLeft.getVisibility() != View.VISIBLE &&
                        detailTvCenter.getVisibility() != View.VISIBLE &&
                        detailTvRight.getVisibility() != View.VISIBLE) {
                    layout_bootom.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ActionItemBean mBean = responseBean.getData();
            if (mBean != null) {
                //状态 0：没有要做的任务 ;1， 有要去互祝的任务; 2，有未读的任务
                String apply_status = mBean.getApply_status();
                String need_help_number = mBean.getRemain_number();
                String redirectMsgId = mBean.getMsg_id();
                if (!apply_status.equals("0")) {
                    if (!need_help_number.equals("0")) {
                        //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                        Intent intent;
                        if (redirectMsgId.equals("0")) {
                            intent = new Intent(mContext, HelpWithEnergyActivity.class);
                            intent.putExtra("skiptype", "Order");
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } else {
                            intent = new Intent(mContext, DetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("msg_id", redirectMsgId);
                            startActivity(intent);
                        }
                    }
                }
            }

        }
    }

    private int is_read = 1;//是否已读： 1 已读
    private int need_help_number = 1;//数量
    /**
     * 任务ID
     */
    String redirectMsgId;

    @Override
    public void getLifeStyleNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeNeedItemBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                need_help_number = mPeopleAfterBean.getNeedhelpGoodsnumber();
                is_read = mPeopleAfterBean.getIs_read();
                if (need_help_number > 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getAppointHelpGoods();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//互祝help_goods_id
                    }
                } else if (is_read == 0 && need_help_number == 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getApplySuccess();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//自己help_goods_id
                    }
                }
                Intent intent;
                if (need_help_number > 0) {
                    //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                    if (redirectMsgId.equals("0")) {
                        intent = new Intent(mContext, LifeStyleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("skiptype", "LifeApplyHelp");
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    } else {
                        intent = new Intent(mContext, LifeStyleDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("help_goods_id", "" + redirectMsgId);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }
                } else {
                    intent = new Intent(mContext, LifeStyleDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", "" + redirectMsgId);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    doFinish();
                }

            }

        }
    }

    @Override
    public void editSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            sendBroadcastsRefreshList();
            ToastUtils.showToast(responseBean.getMsg());
            mPresent.getDetail(user_id, order_id);
        }
    }

    @Override
    public void careReceiveOrderSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            sendBroadcastsRefreshList();
            ToastUtils.showToast(responseBean.getMsg());
            mPresent.getDetail(user_id, order_id);
        } else if (status.equals("410")) {

            if (mCertDialogUtils == null) {
                mCertDialogUtils = new CertDialogUtils(mActivity);
            }
            mCertDialogUtils.showCertificatDialog();
        }
    }

    CertDialogUtils mCertDialogUtils;

    private void sendBroadcastsRefreshList() {
        Intent intent = new Intent();
        intent.setAction(ConstantManager.BroadcastReceiver_ORDER_ACTION);
        intent.putExtra("type", "EDIT");
        sendBroadcast(intent);//发送普通广播
    }

    /**
     * 显示btn状态
     *
     * @param mOrderItemBean
     */
    private void showBottomBtn(DetailAfterBean mOrderItemBean) {
        detailTvLeft.setOnClickListener(this);
        detailTvCenter.setOnClickListener(this);
        detailTvRight.setOnClickListener(this);
        detailTvLeft.setVisibility(View.GONE);
        detailTvCenter.setVisibility(View.GONE);
        detailTvRight.setVisibility(View.GONE);
        tv_leftimg.setVisibility(View.GONE);
        tv_rightimg.setVisibility(View.GONE);
        layout_yajin.setVisibility(View.GONE);
        detailTvLeft.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        detailTvLeft.setTextColor(mContext.getResources().getColor(R.color.text_contents_color));
        detailTvCenter.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        detailTvCenter.setTextColor(mContext.getResources().getColor(R.color.text_contents_color));
        detailTvRight.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        detailTvRight.setTextColor(mContext.getResources().getColor(R.color.text_contents_color));
        /**
         * 是否显示提现  展示提现的时候其他按钮 全部隐藏 只展示提现
         */
        int is_show_care = mOrderItemBean.getIs_show_care();
        if (is_show_care == 0) {
            int bottom_status = mOrderItemBean.getBottom_status();
            int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
            int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
            int is_show_help_info = mOrderItemBean.getIs_show_help_info();
            int is_show_pre_delivery = mOrderItemBean.getIs_pre_delivery();
            if (is_show_pre_delivery == 1) {
                layout_yajin.setVisibility(View.VISIBLE);
                detail_tv_yajin.setText(mOrderItemBean.getPre_delivery_deposit() + "元");
            }
            if (bottom_status == 0) {//申请状态 （显示取消行动 和 送祝福）
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                detailTvCenter.setText("取消行动");
                detailTvRight.setText("送出祝福");
                detailTvRight.setTextColor(getResources().getColor(R.color.white));
                detailTvRight.setBackgroundColor(getResources().getColor(R.color.blue));
            } else if (bottom_status == 1) {//1非商城订单已发货 （显示感恩录  查看物流 和 确认收货）
                detailTvLeft.setVisibility(View.VISIBLE);
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                tv_leftimg.setVisibility(View.VISIBLE);
                tv_rightimg.setVisibility(View.VISIBLE);
                detailTvLeft.setText("感恩录");
                detailTvCenter.setText("查看物流");
                detailTvRight.setText("确认收货");
            } else if (bottom_status == 2) {//商城订单已发货 （显示查看物流 和 确认收货）
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                detailTvCenter.setText("查看物流");
                detailTvRight.setText("确认收货");
                detailTvRight.setTextColor(getResources().getColor(R.color.white));
                detailTvRight.setBackgroundColor(getResources().getColor(R.color.blue));
            } else if (bottom_status == 3) {//
                if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
                    if (type != 1) {
                        detailTvLeft.setText("感恩录");
                        detailTvLeft.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (is_show_help_info == 1) {
                        detailTvLeft.setText("送祝福");
                        detailTvLeft.setVisibility(View.VISIBLE);
                        tv_leftimg.setVisibility(View.VISIBLE);
                    }
                    if (is_show_perfect_info == 1) {
                        detailTvCenter.setText("完善信息");
                        detailTvCenter.setVisibility(View.VISIBLE);
                        tv_rightimg.setVisibility(View.VISIBLE);
                    }
                    if (is_show_consignee_info == 1) {
                        detailTvRight.setText("收货地址");
                        detailTvRight.setVisibility(View.VISIBLE);
                    }
                }
            } else if (bottom_status == 4) {//商城已完成 （显示再次兑换  goods_id <= 10000 点击再次兑换 跳至商城首页 否则 详情 ）
                detailTvRight.setVisibility(View.VISIBLE);
                detailTvRight.setText("再次兑换");
            } else if (bottom_status == 5) {//生命能量已完成 （显示智能互祝  再次申请  感恩录）
                detailTvLeft.setVisibility(View.VISIBLE);
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                tv_leftimg.setVisibility(View.VISIBLE);
                tv_rightimg.setVisibility(View.VISIBLE);
                detailTvLeft.setText("智能互祝");
                detailTvCenter.setText("感恩录");
                detailTvRight.setText("再次申请");
            } else if (bottom_status == 6) {//生活方式已完成 （显示再次申请  感恩录）
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                detailTvCenter.setText("再次申请");
                detailTvRight.setText("感恩录");
                detailTvRight.setTextColor(getResources().getColor(R.color.white));
                detailTvRight.setBackgroundColor(getResources().getColor(R.color.blue));
            } else if (bottom_status == 7) {//进行中
                if (is_show_help_info == 1) {
                    detailTvLeft.setText("送祝福");
                    detailTvLeft.setVisibility(View.VISIBLE);
                    tv_leftimg.setVisibility(View.VISIBLE);
                }
                if (is_show_perfect_info == 1) {
                    detailTvCenter.setText("完善信息");
                    detailTvCenter.setVisibility(View.VISIBLE);
                    tv_rightimg.setVisibility(View.VISIBLE);
                }
                if (is_show_consignee_info == 1) {
                    detailTvRight.setText("收货地址");
                    detailTvRight.setVisibility(View.VISIBLE);
                }
                //type 4康农工程不显示
                if (is_show_pre_delivery == 1 && type != 4) {
                    detailTvRight.setText("支付押金");
                    detailTvRight.setVisibility(View.VISIBLE);
                }
            } else if (bottom_status == 8) {//已取消订单或退订  或驳回 （不显示按钮）
            } else if (bottom_status == -1) {
            }
            /**
             * ***********************新增押金***********************
             */
            else if (bottom_status == 9) {//预发货单未发货行动未完成  （显示已付押金）
            } else if (bottom_status == 10) {//预发货单未发货行动已完成 （显示感恩录，已付押金）
                detailTvRight.setVisibility(View.VISIBLE);
                detailTvRight.setText("感恩录");
            } else if (bottom_status == 11) {//预发货单已发货行动未完成 （显示查看物流  ）
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvCenter.setText("查看物流");
            } else if (bottom_status == 12) {//预发货单已发货行动已完成 （显示查看物流  ，确认收货，感恩录）
                detailTvLeft.setVisibility(View.VISIBLE);
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                tv_leftimg.setVisibility(View.VISIBLE);
                tv_rightimg.setVisibility(View.VISIBLE);
                detailTvLeft.setText("感恩录");
                detailTvCenter.setText("查看物流");
                detailTvRight.setText("确认收货");
            } else if (bottom_status == 13) {//预发货单已收货行动未完成

            } else if (bottom_status == 14) {//预发货单已收货行动已完成 （显示智能互祝  ，感恩录,再次申请）
                detailTvLeft.setVisibility(View.VISIBLE);
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                tv_leftimg.setVisibility(View.VISIBLE);
                tv_rightimg.setVisibility(View.VISIBLE);
                detailTvLeft.setText("智能互祝");
                detailTvCenter.setText("感恩录");
                detailTvRight.setText("再次申请");
            } else if (bottom_status == 15) {//康农工程订单已完成（不展示按钮）

            } else if (bottom_status == 16) {//康农工程订单未发货（不展示按钮）

            } else if (bottom_status == 17) {//康农工程订单已发货（显示查看物流  ，确认收货）
                detailTvCenter.setVisibility(View.VISIBLE);
                detailTvRight.setVisibility(View.VISIBLE);
                tv_rightimg.setVisibility(View.VISIBLE);
                detailTvCenter.setText("查看物流");
                detailTvRight.setText("确认收货");
            }
            /**
             * ***********************end*********************
             */
        } else {
            detailTvLeft.setText("提现");
            detailTvLeft.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 订单类型	1商城订单 2 生命能量订单 3 生活方式互祝订单
     *
     * @param mOrderItemBean
     */
    private void showGoodView(DetailAfterBean mOrderItemBean) {
        type = mOrderItemBean.getType();
        String price = mOrderItemBean.getPrice();
        tvGoodtype.setText(mOrderItemBean.getType_name());
        itemTvGoodname.setText(mOrderItemBean.getGoods_x_name());
        String guigeName = mOrderItemBean.getShop_goods_price_name();
        item_tv_guigename.setText(guigeName);
        if (!TextUtils.isEmpty(guigeName)) {
            item_tv_guigename.setVisibility(View.VISIBLE);
        } else {
            item_tv_guigename.setVisibility(View.GONE);
        }
        if (type == 2 || type == 4) {
            GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, mOrderItemBean.getImage(), itemIvGoodthumb, ConstantManager.image_angle);
            int width = DensityUtil.dip2px(mContext, 80);
            int height = DensityUtil.dip2px(mContext, 50);
            itemIvGoodthumb.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            itemIvGoodtypeimg.setBackgroundResource(R.mipmap.activat_icon);
            itemTvGoodnum.setText("生命能量：" + price);
            itemTvGoodnum.setTextColor(mContext.getResources().getColor(R.color.mediumseagreen));
        } else {
            GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(mContext, mOrderItemBean.getImage(), itemIvGoodthumb, ConstantManager.image_angle);
            int width = DensityUtil.dip2px(mContext, 65);
            itemIvGoodthumb.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            itemIvGoodtypeimg.setBackgroundResource(R.mipmap.activat_skb_icon);
            itemTvGoodnum.setText("寿康宝:" + price);
            itemTvGoodnum.setTextColor(mContext.getResources().getColor(R.color.cyanblue));
        }
    }

    @Override
    public void ListError() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }

    /**
     * //0无 1进行中 2已发货 3已完成4驳回
     *
     * @param mOrderItemBean
     */
    private void showStatusView(DetailAfterBean mOrderItemBean) {
        int top_status = mOrderItemBean.getTop_status();
        detailTvStatus.setText(mOrderItemBean.getTop_title());
        if (top_status == 1 || top_status == 5 || top_status == 6) {
            detailIvStatusimg.setBackgroundResource(R.mipmap.my_theorder_for);
        } else if (top_status == 2 || top_status == 7) {
            detailIvStatusimg.setBackgroundResource(R.mipmap.my_theorder_thedelivery);
        } else if (top_status == 3) {
            detailIvStatusimg.setBackgroundResource(R.mipmap.my_theorder_complete);
        } else if (top_status == 4) {
            detailIvStatusimg.setBackgroundResource(R.mipmap.my_theorder_rejected);
        } else {
            detailIvStatusimg.setVisibility(View.INVISIBLE);
            detailTvStatus.setVisibility(View.INVISIBLE);
        }
        if (type == 1 && (top_status == 1 || top_status == 2)) {//商城
            detailIvStatusimg.setVisibility(View.INVISIBLE);
            detailTvStatus.setVisibility(View.INVISIBLE);
        }
        String phone = mOrderItemBean.getMobile();
        if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
            phone = phone.substring(0, 3) + "****" + phone.substring(7);
        }
        detailTvName.setText(mOrderItemBean.getConsignee() + "     " + phone);
        String addre = mOrderItemBean.getAddress();
        if (TextUtils.isEmpty(addre)) {
            detailTvName.setVisibility(View.GONE);
            detailTvAddress.setText("暂无地址");
        } else {
            detailTvName.setVisibility(View.VISIBLE);
            detailTvAddress.setText(addre);
        }

        detailTvOrdernum.setText(mOrderItemBean.getOrder_sn());
        detailTvTime.setText(mOrderItemBean.getDate());
    }

    private void setLeftClickSkip(DetailAfterBean mOrderItemBean) {
        int is_show_care = mOrderItemBean.getIs_show_care();
        if (is_show_care == 0) {
            int bottom_status = mOrderItemBean.getBottom_status();
            int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
            int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
            int is_show_help_info = mOrderItemBean.getIs_show_help_info();
            if (bottom_status == 1) {
                Thanksgiving(order_id, mOrderItemBean.getType());
            } else if (bottom_status == 3) {
                if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
                    Thanksgiving(order_id, mOrderItemBean.getType());
                } else {
                    if (is_show_help_info == 1) {
                        SendBlessing();
                    }
                }
            } else if (bottom_status == 7) {
                if (is_show_help_info == 1) {
                    SendBlessing();
                }
            } else if (bottom_status == 5) {
                mutualWish();
            } else if (bottom_status == 12) {
                Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
            } else if (bottom_status == 14) {
                mutualWish();
            }
        } else {
            //提现
            mPresent.careReceiveOrder(user_id, order_id);
        }
    }

    private void setCenterClickSkip(DetailAfterBean mOrderItemBean) {
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        if (bottom_status == 0) {
            cancelAction();
        } else if (bottom_status == 1 || bottom_status == 2) {
            lookLogistics(order_id);
        } else if (bottom_status == 3) {
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
            } else {
                if (is_show_perfect_info == 1) {
                    PerfectInformation();
                }
            }
        } else if (bottom_status == 5) {
            Thanksgiving(order_id, mOrderItemBean.getType());
        } else if (bottom_status == 6) {
            ApplyAgain();
        } else if (bottom_status == 7) {
            if (is_show_perfect_info == 1) {
                PerfectInformation();
            }
        } else if (bottom_status == 11) {
            lookLogistics(mOrderItemBean.getOrder_id());
        } else if (bottom_status == 12) {
            lookLogistics(mOrderItemBean.getOrder_id());
        } else if (bottom_status == 14) {
            Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
        } else if (bottom_status == 17) {
            lookLogistics(mOrderItemBean.getOrder_id());
        }
    }

    private void setRightClickSkip(DetailAfterBean mOrderItemBean) {
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        if (bottom_status == 0) {
            SendBlessing();
        } else if (bottom_status == 1 || bottom_status == 2) {
            ConfirmReceipt(order_id);
        } else if (bottom_status == 3) {
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
            } else {
                if (is_show_consignee_info == 1) {
                    addAddress();
                }
            }
        } else if (bottom_status == 4) {//商城已完成
            RedeemAgain();
        } else if (bottom_status == 5) {
            ApplyAgain();
        } else if (bottom_status == 6) {
            Thanksgiving(order_id, mOrderItemBean.getType());
        } else if (bottom_status == 7) {
            if (is_show_consignee_info == 1) {
                addAddress();
            }
            if (mOrderItemBean.getIs_pre_delivery() == 1 && mOrderItemBean.getType() != 4) {
                jioaYaJin();
            }
        } else if (bottom_status == 10) {
            Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
        } else if (bottom_status == 12) {
            ConfirmReceipt(order_id);
        } else if (bottom_status == 14) {
            ApplyAgain();
        } else if (bottom_status == 17) {
            ConfirmReceipt(order_id);
        }
    }

    /**
     * 交押金
     */
    private void jioaYaJin() {
        Intent intent = new Intent(mContext, YaJinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }

    /**
     * 智能互祝");//跳转智能互祝页面（。。。。返回逻辑待定）
     */
    private void mutualWish() {
        Intent intent = new Intent(mContext, AutoHelpH5Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
        Log.e("btnClick", "智能互祝");
    }

    /**
     * 再次兑换");//（goods_id <= 10000 跳至商城首页 否则 商品详情 ）
     */
    private void RedeemAgain() {
        if (xiajia()) {
            return;
        }
        if (type == 1) {
            Log.e("btnClick", "再次兑换");
            int goodsid = mOrderItemBean.getGoods_id();
            if (goodsid >= 10000) {
                Intent intent = new Intent(mContext, MallDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("shop_goods_id", "" + goodsid);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            } else {
                //老商品
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                intents.putExtra("solar_terms_id", 0);
                intents.putExtra("solar_terms_name", "");
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
                doFinish();
            }
        } else if (type == 3) {
            Intent intent = new Intent(mContext, LifeStyleApplyHelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("goods_id", "" + mOrderItemBean.getGoods_id());
            intent.putExtra("shop_goods_price_id", "" + mOrderItemBean.getShop_goods_price_id());
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        }
    }

    /**
     * 是否下架
     *
     * @return
     */
    private boolean xiajia() {
        boolean xiajiaStatus = false;
        int action_status = mOrderItemBean.getAction_status();
        if (action_status == 0) {
            xiajiaStatus = true;
            Intent intent = new Intent(mContext, XiaJiaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", type);
            mContext.startActivity(intent);
        }
        return xiajiaStatus;
    }

    /**
     * 感恩录,跳转新页面 接口
     */
    private void Thanksgiving(String order_id, int type) {
        Log.e("btnClick", "感恩录");
        Intent intent = new Intent(mContext, ThanksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        intent.putExtra("type", type);
        mContext.startActivity(intent);
    }


    /**
     * 送祝福,调取接口根据msgid跳转生命能量列表或详情
     */
    private void SendBlessing() {
        Log.e("btnClick", "送祝福");
        sendBroadcastsRefreshList();
        if (type == 2 || type == 4) {
            mPresent.getNeedHelpNumberTask(user_id);
        } else {
            mPresent.getLifeNeedHelpNumberTaskSuccess(user_id, mOrderItemBean.getGoods_id() + "");
        }
    }


    /**
     * 查看物流,跳转新页面 接口
     */
    private void lookLogistics(String order_id) {
        Log.e("btnClick", "查看物流");
        Intent intent = new Intent(mContext, TrankActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        mContext.startActivity(intent);
    }


    /**
     * 完善信息，用户信息页面
     */
    private void PerfectInformation() {
        Log.e("btnClick", "完善信息");
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

    /**
     * 再次申请， 生命能量跳转申请互祝页面
     */
    private void ApplyAgain() {
        Log.e("btnClick", "再次申请");
        if (type == 2 || type == 4) {
            int action_status = mOrderItemBean.getAction_status();
            if (action_status == 0) {
                xiajia();
                return;
            }
            int action_goods_id = mOrderItemBean.getAction_id();
            SkipHelpUtils.getInstance().skipIntent(mActivity, action_goods_id);
        } else {
            RedeemAgain();
        }
    }

    /**
     * 取消行动， 调取接口刷新数据
     */
    private void cancelAction() {
        Log.e("btnClick", "取消行动");
        setDelDialog();
    }

    /**
     * 确认收货，调取接口改变状态
     */
    private void ConfirmReceipt(String order_id) {
        Log.e("btnClick", "确认收货");
        mPresent.confirmReceipt(user_id, order_id);
    }

    /**
     * 收货地址");//跳转添加地址页面（。。。。返回逻辑待定）
     */
    private void addAddress() {
        Log.e("btnClick", "收货地址");
        Intent intent = new Intent(mContext, AddressAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", order_id);
        intent.putExtra("receive_user_id", UserUtils.getUserId(mContext));
        mContext.startActivity(intent);
    }

    MyDialog selectDialog;

    public void setDelDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_delfamily);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_title = (TextView) selectDialog.findViewById(R.id.tv_title);
            TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
            TextView myexit_text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
            tv_title.setText("取消行动后将彻底删除此订单");
            myexit_text_off.setText("再想想");
            myexit_text_sure.setText("确认取消");
            myexit_text_off.setTextColor(getResources().getColor(R.color.text_contents_color));
            myexit_text_sure.setTextColor(getResources().getColor(R.color.white));
            myexit_text_sure.setBackgroundResource(R.drawable.corners_bg_loginrigthbottom);
            myexit_text_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            myexit_text_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    mPresent.cancelAction(user_id, order_id);
                }
            });
        } else {
            selectDialog.show();
        }
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
