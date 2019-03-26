package com.longcheng.lifecareplan.modular.mine.myorder.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity.LifeStyleApplyHelpActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressAddActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.XiaJiaActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.YaJinActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.activity.TrankActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity.ThanksActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class OrderListAdapter extends BaseAdapterHelper<OrderItemBean> {
    ViewHolder mHolder = null;
    Handler mHandler;
    Activity context;
    List<OrderItemBean> list;

    public OrderListAdapter(Activity context, List<OrderItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
    }

    public List<OrderItemBean> getList() {
        return list;
    }

    public void setList(List<OrderItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<OrderItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_order_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        OrderItemBean mOrderItemBean = list.get(position);
        mOrderItemBean.setPosition(position);
        showStatusView(mOrderItemBean);
        showGoodView(mOrderItemBean);
        showBottomBtn(mOrderItemBean);
        return convertView;
    }

    /**
     * //0无 1进行中 2已发货 3已完成4驳回
     *
     * @param mOrderItemBean
     */
    private void showStatusView(OrderItemBean mOrderItemBean) {
        int top_status = mOrderItemBean.getTop_status();
        mHolder.tv_status.setText("");
        if (top_status == 3) {
            mHolder.item_iv_status.setVisibility(View.VISIBLE);
            mHolder.item_iv_status.setBackgroundResource(R.mipmap.my_theorder_theseal1);
        } else if (top_status == 4) {
            mHolder.item_iv_status.setVisibility(View.VISIBLE);
            mHolder.item_iv_status.setBackgroundResource(R.mipmap.my_theorder_theseal2);
        } else {
            mHolder.item_iv_status.setVisibility(View.GONE);
            int type = mOrderItemBean.getType();
            if (type == 1) {
                mHolder.tv_status.setText("");
            } else {
                String showcon = mOrderItemBean.getTop_title();
                if (top_status == 1) {//进行中加进度显示
                    showcon = mOrderItemBean.getTop_title() + " " + mOrderItemBean.getProgress() + "%";
                }
                mHolder.tv_status.setText(showcon);
            }
        }
    }

    /**
     * 显示btn状态
     *
     * @param mOrderItemBean
     */
    private void showBottomBtn(OrderItemBean mOrderItemBean) {
        mHolder.item_tv_left.setTag(mOrderItemBean);
        mHolder.item_tv_center.setTag(mOrderItemBean);
        mHolder.item_tv_right.setTag(mOrderItemBean);
        mHolder.item_tv_left.setOnClickListener(mClickListener);
        mHolder.item_tv_center.setOnClickListener(mClickListener);
        mHolder.item_tv_right.setOnClickListener(mClickListener);
        mHolder.item_tv_left.setVisibility(View.GONE);
        mHolder.item_tv_center.setVisibility(View.GONE);
        mHolder.item_tv_right.setVisibility(View.GONE);
        mHolder.item_tv_left.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        mHolder.item_tv_left.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        mHolder.item_tv_center.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        mHolder.item_tv_center.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        int is_show_pre_delivery = mOrderItemBean.getIs_show_pre_delivery();
        int type = mOrderItemBean.getType();
        if (bottom_status == 0) {//申请状态 （显示取消行动 和 送祝福）
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setText("送出祝福");
            mHolder.item_tv_right.setText("取消行动");
        } else if (bottom_status == 1) {//1非商城订单已发货 （显示感恩录  查看物流 和 确认收货）
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("感恩录");
            mHolder.item_tv_center.setText("查看物流");
            mHolder.item_tv_right.setText("确认收货");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 2) {//商城订单已发货 （显示查看物流 和 确认收货）
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setText("查看物流");
            mHolder.item_tv_right.setText("确认收货");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 3) {//
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
                mHolder.item_tv_left.setText("感恩录");
                mHolder.item_tv_left.setVisibility(View.VISIBLE);
            } else {
                if (is_show_help_info == 1) {
                    mHolder.item_tv_left.setText("送祝福");
                    mHolder.item_tv_left.setVisibility(View.VISIBLE);
                }
                if (is_show_perfect_info == 1) {
                    mHolder.item_tv_center.setText("完善信息");
                    mHolder.item_tv_center.setVisibility(View.VISIBLE);
                }
                if (is_show_consignee_info == 1) {
                    mHolder.item_tv_right.setText("收货地址");
                    mHolder.item_tv_right.setVisibility(View.VISIBLE);
                }
            }
        } else if (bottom_status == 4) {//商城已完成 （显示再次兑换  goods_id <= 10000 点击再次兑换 跳至商城首页 否则 详情 ）
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setText("再次兑换");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 5) {//生命能量已完成 （显示智能互祝  再次申请  感恩录）
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("智能互祝");
            mHolder.item_tv_center.setText("感恩录");
            mHolder.item_tv_right.setText("再次申请");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 6) {//生活方式已完成 （显示再次申请  感恩录）
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("感恩录");
            mHolder.item_tv_center.setText("再次申请");
            mHolder.item_tv_center.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_center.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 7) {//进行中
            if (is_show_help_info == 1) {
                mHolder.item_tv_left.setText("送祝福");
                mHolder.item_tv_left.setVisibility(View.VISIBLE);
            }
            if (is_show_perfect_info == 1) {
                mHolder.item_tv_center.setText("完善信息");
                mHolder.item_tv_center.setVisibility(View.VISIBLE);
            }
            if (is_show_consignee_info == 1) {
                mHolder.item_tv_right.setText("收货地址");
                mHolder.item_tv_right.setVisibility(View.VISIBLE);
            }
            //type 4康农工程不显示
            if (is_show_pre_delivery == 1 && type != 4) {
                mHolder.item_tv_right.setText("支付押金");
                mHolder.item_tv_right.setVisibility(View.VISIBLE);
            }
        } else if (bottom_status == 8) {//已取消订单或退订  或驳回 （不显示按钮）
        } else if (bottom_status == -1) {
        }
        /**
         * ***********************新增押金***********************
         */
        else if (bottom_status == 9) {//预发货单未发货行动未完成  （显示已付押金）
            mHolder.item_tv_left.setText("已付押金");
            mHolder.item_tv_left.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setBackgroundResource(R.drawable.corners_bg_write);
        } else if (bottom_status == 10) {//预发货单未发货行动已完成 （显示感恩录，已付押金）
            mHolder.item_tv_left.setText("已付押金");
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setBackgroundResource(R.drawable.corners_bg_write);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setText("感恩录");
        } else if (bottom_status == 11) {//预发货单已发货行动未完成 （显示查看物流  ）
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setText("查看物流");
        } else if (bottom_status == 12) {//预发货单已发货行动已完成 （显示查看物流  ，确认收货，感恩录）
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("感恩录");
            mHolder.item_tv_center.setText("查看物流");
            mHolder.item_tv_right.setText("确认收货");
        } else if (bottom_status == 13) {//预发货单已收货行动未完成

        } else if (bottom_status == 14) {//预发货单已收货行动已完成 （显示智能互祝  ，感恩录,再次申请）
            mHolder.item_tv_left.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_left.setText("智能互祝");
            mHolder.item_tv_center.setText("感恩录");
            mHolder.item_tv_right.setText("再次申请");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        } else if (bottom_status == 15) {//康农工程订单已完成（不展示按钮）

        } else if (bottom_status == 16) {//康农工程订单未发货（不展示按钮）

        } else if (bottom_status == 17) {//康农工程订单已发货（显示查看物流  ，确认收货）
            mHolder.item_tv_center.setVisibility(View.VISIBLE);
            mHolder.item_tv_right.setVisibility(View.VISIBLE);
            mHolder.item_tv_center.setText("查看物流");
            mHolder.item_tv_right.setText("确认收货");
            mHolder.item_tv_right.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_right.setTextColor(context.getResources().getColor(R.color.blue));
        }
        /**
         * ***********************end*********************
         */
    }

    /**
     * 订单类型	1商城订单 2 生命能量订单 3 生活方式互祝订单
     *
     * @param mOrderItemBean
     */
    private void showGoodView(OrderItemBean mOrderItemBean) {
        int type = mOrderItemBean.getType();
        String price = mOrderItemBean.getPrice();
        mHolder.tv_goodtype.setText(mOrderItemBean.getType_name());
        mHolder.item_tv_goodname.setText(mOrderItemBean.getGoods_x_name());
        mHolder.item_tv_date.setText(mOrderItemBean.getDate());
        if (type == 2 || type == 4) {
            GlideDownLoadImage.getInstance().loadCircleImageRole(context, mOrderItemBean.getImage(), mHolder.item_iv_goodthumb, ConstantManager.image_angle);
            int width = DensityUtil.dip2px(context, 80);
            int height = DensityUtil.dip2px(context, 50);
            mHolder.item_iv_goodthumb.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            mHolder.item_iv_goodtypeimg.setBackgroundResource(R.mipmap.activat_icon);
            mHolder.item_tv_goodnum.setText("生命能量:" + price);
            mHolder.item_tv_goodnum.setTextColor(context.getResources().getColor(R.color.mediumseagreen));
        } else {
            GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, mOrderItemBean.getImage(), mHolder.item_iv_goodthumb, ConstantManager.image_angle);
            int width = DensityUtil.dip2px(context, 65);
            mHolder.item_iv_goodthumb.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            mHolder.item_iv_goodtypeimg.setBackgroundResource(R.mipmap.activat_skb_icon);
            mHolder.item_tv_goodnum.setText("寿康宝:" + price);
            mHolder.item_tv_goodnum.setTextColor(context.getResources().getColor(R.color.cyanblue));
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderItemBean mOrderItemBean = (OrderItemBean) v.getTag();
            int position = mOrderItemBean.getPosition();
            switch (v.getId()) {
                case R.id.item_tv_left:
                    setLeftClickSkip(mOrderItemBean);
                    break;
                case R.id.item_tv_center:
                    setCenterClickSkip(mOrderItemBean);
                    break;
                case R.id.item_tv_right:
                    setRightClickSkip(mOrderItemBean);
                    break;
            }
        }
    };

    private void setLeftClickSkip(OrderItemBean mOrderItemBean) {
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        String order_id = mOrderItemBean.getOrder_id();
        if (bottom_status == 1 || bottom_status == 6) {
            Thanksgiving(order_id, mOrderItemBean.getType());
        } else if (bottom_status == 3) {
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
                Thanksgiving(order_id, mOrderItemBean.getType());
            } else {
                if (is_show_help_info == 1) {
                    SendBlessing(mOrderItemBean.getType(), mOrderItemBean.getGoods_id());
                }
            }
        } else if (bottom_status == 7) {
            if (is_show_help_info == 1) {
                SendBlessing(mOrderItemBean.getType(), mOrderItemBean.getGoods_id());
            }
        } else if (bottom_status == 5) {
            mutualWish();
        } else if (bottom_status == 12) {
            Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
        } else if (bottom_status == 14) {
            mutualWish();
        }
    }

    private void setCenterClickSkip(OrderItemBean mOrderItemBean) {
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        if (bottom_status == 0) {
            SendBlessing(mOrderItemBean.getType(), mOrderItemBean.getGoods_id());
        } else if (bottom_status == 1 || bottom_status == 2) {
            lookLogistics(mOrderItemBean.getOrder_id());
        } else if (bottom_status == 3) {
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
            } else {
                if (is_show_perfect_info == 1) {
                    PerfectInformation();
                }
            }
        } else if (bottom_status == 5) {
            Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
        } else if (bottom_status == 6) {
            ApplyAgain(mOrderItemBean.getAction_status(), mOrderItemBean.getType(), mOrderItemBean.getAction_id(), mOrderItemBean.getGoods_id(), mOrderItemBean.getShop_goods_price_id());
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

    private void setRightClickSkip(OrderItemBean mOrderItemBean) {
        int bottom_status = mOrderItemBean.getBottom_status();
        int is_show_perfect_info = mOrderItemBean.getIs_show_perfect_info();
        int is_show_consignee_info = mOrderItemBean.getIs_show_consignee_info();
        int is_show_help_info = mOrderItemBean.getIs_show_help_info();
        String order_id = mOrderItemBean.getOrder_id();
        if (bottom_status == 0) {
            cancelAction(order_id);
        } else if (bottom_status == 1 || bottom_status == 2) {
            ConfirmReceipt(order_id);
        } else if (bottom_status == 3) {
            if (is_show_perfect_info == 0 && is_show_consignee_info == 0 && is_show_help_info == 0) {
            } else {
                if (is_show_consignee_info == 1) {
                    addAddress();
                }
            }
        } else if (bottom_status == 4) {
            RedeemAgain(mOrderItemBean.getAction_status(), mOrderItemBean.getType(), mOrderItemBean.getGoods_id(), mOrderItemBean.getShop_goods_price_id());
        } else if (bottom_status == 5) {
            ApplyAgain(mOrderItemBean.getAction_status(), mOrderItemBean.getType(), mOrderItemBean.getAction_id(), mOrderItemBean.getGoods_id(), mOrderItemBean.getShop_goods_price_id());
        } else if (bottom_status == 7) {
            if (is_show_consignee_info == 1) {
                addAddress();
            }
            if (mOrderItemBean.getIs_show_pre_delivery() == 1 && mOrderItemBean.getType() != 4) {
                jioaYaJin(order_id);
            }
        } else if (bottom_status == 10) {
            Thanksgiving(mOrderItemBean.getOrder_id(), mOrderItemBean.getType());
        } else if (bottom_status == 12) {
            ConfirmReceipt(order_id);
        } else if (bottom_status == 14) {
            ApplyAgain(mOrderItemBean.getAction_status(), mOrderItemBean.getType(), mOrderItemBean.getAction_id(), mOrderItemBean.getGoods_id(), mOrderItemBean.getShop_goods_price_id());
        } else if (bottom_status == 17) {
            ConfirmReceipt(order_id);
        }
    }

    /**
     * 交押金
     */
    private void jioaYaJin(String order_id) {
        Intent intent = new Intent(context, YaJinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        context.startActivity(intent);
    }

    /**
     * 智能互祝");//跳转智能互祝页面（。。。。返回逻辑待定）
     */
    private void mutualWish() {
        Intent intent = new Intent(context, AutoHelpH5Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
        Log.e("btnClick", "智能互祝");
    }

    /**
     * 再次兑换");//（goods_id <= 10000 跳至商城首页 否则 商品详情 ）
     */
    private void RedeemAgain(int action_status, int type, int goodsid, String shop_goods_price_id) {
        if (xiajia(action_status, type)) {
            return;
        }
        if (type == 1) {
            Log.e("btnClick", "再次兑换");
            if (goodsid >= 10000) {
                Intent intent = new Intent(context, MallDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("shop_goods_id", "" + goodsid);
                context.startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, context);
            } else {
                //老商品
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                intents.putExtra("solar_terms_id", 0);
                intents.putExtra("solar_terms_name", "");
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
            }
        } else if (type == 3) {
            Intent intent = new Intent(context, LifeStyleApplyHelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("goods_id", "" + goodsid);
            intent.putExtra("shop_goods_price_id", "" + shop_goods_price_id);
            context.startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, context);
        }
    }

    /**
     * 感恩录,跳转新页面 接口
     */
    private void Thanksgiving(String order_id, int type) {
        Log.e("btnClick", "感恩录");
        Intent intent = new Intent(context, ThanksActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    /**
     * 送祝福,调取接口根据msgid跳转生命能量列表或详情
     */
    private void SendBlessing(int type, int goods_id) {
        Log.e("btnClick", "送祝福");
        if (type == 2 || type == 4) {
            mHandler.sendEmptyMessage(ConstantManager.ORDER_HANDLE_SendBlessing);
        } else {
            Message message = new Message();
            message.what = ConstantManager.ORDER_HANDLE_SendBlessingLifeStyle;
            message.obj = goods_id + "";
            mHandler.sendMessage(message);
            message = null;
        }
    }


    /**
     * 查看物流,跳转新页面 接口
     */
    private void lookLogistics(String order_id) {
        Log.e("btnClick", "查看物流");
        Intent intent = new Intent(context, TrankActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("order_id", "" + order_id);
        context.startActivity(intent);
    }


    /**
     * 完善信息，用户信息页面
     */
    private void PerfectInformation() {
        Log.e("btnClick", "完善信息");
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * 再次申请， 生命能量跳转申请互祝页面
     */
    private void ApplyAgain(int action_status, int type, int action_goods_id, int goodsid, String shop_goods_price_id) {
        if (type == 2 || type == 4) {
            if (action_status == 0) {
                xiajia(action_status, type);
                return;
            }
            SkipHelpUtils.getInstance().skipIntent(context, action_goods_id);
        } else {
            RedeemAgain(action_status, type, goodsid, shop_goods_price_id);
        }
    }

    /**
     * 是否下架
     *
     * @return
     */
    private boolean xiajia(int action_status, int type) {
        boolean xiajiaStatus = false;
        if (action_status == 0) {
            xiajiaStatus = true;
            Intent intent = new Intent(context, XiaJiaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", type);
            context.startActivity(intent);
        }
        return xiajiaStatus;
    }

    /**
     * 取消行动， 调取接口刷新数据
     */
    private void cancelAction(String order_id) {
        Log.e("btnClick", "取消行动");
        setDelDialog(order_id);
    }

    /**
     * 确认收货，调取接口改变状态
     */
    private void ConfirmReceipt(String order_id) {
        Log.e("btnClick", "确认收货");
        Message message = new Message();
        message.what = ConstantManager.ORDER_HANDLE_ConfirmReceipt;
        message.obj = order_id;
        mHandler.sendMessage(message);
        message = null;
    }

    /**
     * 收货地址");//跳转添加地址页面（。。。。返回逻辑待定）
     */
    private void addAddress() {
        Log.e("btnClick", "收货地址");
        Intent intent = new Intent(context, AddressAddActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("receive_user_id", UserUtils.getUserId(context));
        context.startActivity(intent);
    }

    MyDialog selectDialog;

    public void setDelDialog(String order_id) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_delfamily);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
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
            myexit_text_off.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            myexit_text_sure.setTextColor(context.getResources().getColor(R.color.white));
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
                    Message message = new Message();
                    message.what = ConstantManager.ORDER_HANDLE_cancelAction;
                    message.obj = order_id;
                    mHandler.sendMessage(message);
                    message = null;
                }
            });
        } else {
            selectDialog.show();
        }
    }

    private class ViewHolder {
        private TextView tv_goodtype;
        private TextView tv_status;
        private ImageView item_iv_goodthumb;
        private TextView item_tv_goodname;
        private ImageView item_iv_goodtypeimg;
        private TextView item_tv_goodnum;
        private TextView item_tv_date;
        private TextView item_tv_left;
        private TextView item_tv_right;
        private TextView item_tv_center;
        private ImageView item_iv_status;

        public ViewHolder(View view) {
            tv_goodtype = (TextView) view.findViewById(R.id.tv_goodtype);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            item_iv_status = (ImageView) view.findViewById(R.id.item_iv_status);
            item_iv_goodthumb = (ImageView) view.findViewById(R.id.item_iv_goodthumb);
            item_tv_goodname = (TextView) view.findViewById(R.id.item_tv_goodname);
            item_iv_goodtypeimg = (ImageView) view.findViewById(R.id.item_iv_goodtypeimg);
            item_tv_goodnum = (TextView) view.findViewById(R.id.item_tv_goodnum);
            item_tv_date = (TextView) view.findViewById(R.id.item_tv_date);
            item_tv_left = (TextView) view.findViewById(R.id.item_tv_left);
            item_tv_center = (TextView) view.findViewById(R.id.item_tv_center);
            item_tv_right = (TextView) view.findViewById(R.id.item_tv_right);
        }
    }
}
