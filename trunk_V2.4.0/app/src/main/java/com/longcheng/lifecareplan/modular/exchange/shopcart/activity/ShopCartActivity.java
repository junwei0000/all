package com.longcheng.lifecareplan.modular.exchange.shopcart.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.adapter.GoodsListAdapter;
import com.longcheng.lifecareplan.modular.exchange.bean.GoodsItemBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.adapter.ShopCartListAdapter;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartAfterBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 商品-购物车
 */
public class ShopCartActivity extends BaseActivityMVP<ShopCartContract.View, ShopCartPresenterImp<ShopCartContract.View>> implements ShopCartContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.date_listview)
    MyListView dateListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tuijian_gv)
    MyGridView tuijianGv;
    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.tv_selectnum)
    TextView tvSelectnum;
    @BindView(R.id.tv_skb)
    TextView tvSkb;
    @BindView(R.id.layout_select)
    LinearLayout layoutSelect;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;

    @BindView(R.id.relat_cart)
    RelativeLayout relat_cart;

    private String user_id;
    private Map<String, DetailItemBean> ShoppingCartMap = new HashMap<>();
    ArrayList<DetailItemBean> mCartList = new ArrayList();
    List<GoodsItemBean> mGoodsList;
    boolean haveAllCheck = true;
    int allnum = 0;
    String allskb_price = "0";
    String allsuper_ability = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_select:
                if (haveAllCheck) {
                    haveAllCheck = false;
                } else {
                    haveAllCheck = true;
                }
                setAllCheckStatus();
                break;
            case R.id.not_date_cont:
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                intents.putExtra("solar_terms_id", 0);
                intents.putExtra("solar_terms_name", "");
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
                doFinish();
                break;
            case R.id.tv_buy:
                if (Integer.parseInt(allskb_price) > 0 || Integer.parseInt(allsuper_ability) > 0) {
                    Intent intent = new Intent(mContext, SubmitOrderActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("allskb_price", allskb_price);
                    intent.putExtra("allsuper_ability", allsuper_ability);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
        return R.layout.exchange_shopcart;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("购物车");
        //创建一个 SpannableString对象
        SpannableString msp = new SpannableString("空空如也，快去逛逛吧~");
        //设置字体前景色
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 6, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
        //设置下划线
        msp.setSpan(new UnderlineSpan(), 6, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        notDateCont.setText(msp);
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.mall_icon_empty);
        layoutNotdate.setBackgroundColor(getResources().getColor(R.color.page_bg));
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        notDateCont.setOnClickListener(this);
        layoutSelect.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        tuijianGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGoodsList != null && mGoodsList.size() > 0) {
                    Intent intent = new Intent(mContext, MallDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("shop_goods_id", mGoodsList.get(position).getShop_goods_id());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            }
        });
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mCartList != null && mCartList.size() > 0) {
                    Intent intent = new Intent(mContext, MallDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("shop_goods_id", mCartList.get(position).getShop_goods_id());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            }
        });
    }


    public static final int SELECT = 1;//选择
    public static final int DEL = 2;//删除
    public static final int ADD = 3;//加
    public static final int subtract = 4;//减
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String key = (String) msg.obj;
            switch (msg.what) {
                case SELECT:
                    if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                        if (ShoppingCartMap.containsKey(key)) {
                            DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                            boolean check = GoodsInfo_.isCheck();
                            if (check) {
                                GoodsInfo_.setCheck(false);
                            } else {
                                GoodsInfo_.setCheck(true);
                            }
                        }
                        refreshData();
                    }
                    break;
                case DEL:
                    delKey = key;
                    setDelDialog();
                    break;
                case ADD:
                    if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                        if (ShoppingCartMap.containsKey(key)) {
                            DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                            int num = GoodsInfo_.getGoodsNum();
                            GoodsInfo_.setGoodsNum(num + 1);
                        }
                        refreshData();
                    }
                    break;
                case subtract:
                    if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                        if (ShoppingCartMap.containsKey(key)) {
                            DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                            int num = GoodsInfo_.getGoodsNum();
                            if (num > 1) {
                                GoodsInfo_.setGoodsNum(num - 1);
                            }
                        }
                        refreshData();
                    }
                    break;
            }
        }
    };
    boolean firstConIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstConIn) {
            haveAllCheck = true;
            allnum = 0;
            allskb_price = "0";
            allsuper_ability = "0";
            getShoppingCartMap();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        haveAllCheck = true;
        allnum = 0;
        allskb_price = "0";
        allsuper_ability = "0";
        getShoppingCartMap();
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getTuiJianList(user_id);
        getShoppingCartMap();
    }

    /**
     * 获取购物车map
     */
    private void getShoppingCartMap() {
        getHashMapData();
        if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
            layoutBottom.setVisibility(View.VISIBLE);
            layoutNotdate.setVisibility(View.GONE);
            setAllCheckStatus();
        } else {
            layoutBottom.setVisibility(View.GONE);
            layoutNotdate.setVisibility(View.VISIBLE);
        }
        scrollView.smoothScrollBy(0, 0);
        firstConIn = false;
    }


    private void getHashMapData() {
        ShoppingCartMap.clear();
        ShoppingCartMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData(user_id, DetailItemBean.class));
    }

    /**
     * 全选按钮时或取消全选
     */
    private void setAllCheckStatus() {
        if (!haveAllCheck) {
            if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                for (String key : ShoppingCartMap.keySet()) {
                    DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                    GoodsInfo_.setCheck(false);
                }
            }
        } else {
            if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                for (String key : ShoppingCartMap.keySet()) {
                    DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                    GoodsInfo_.setCheck(true);
                }
            }
        }
        refreshData();
    }

    private void refreshData() {
        SharedPreferencesUtil.getInstance().putHashMapData(user_id, ShoppingCartMap);
        getHashMapData();
        allnum = 0;
        allskb_price = "0";
        allsuper_ability = "0";
        changeCartListView();
        calculateShopCartList();
    }

    ShopCartListAdapter mShopCartListAdapter;

    private void changeCartListView() {
        mCartList.clear();
        if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
            for (String key : ShoppingCartMap.keySet()) {
                Log.e("key", " " + key);
                mCartList.add(ShoppingCartMap.get(key));
            }
            if (mShopCartListAdapter == null) {
                mShopCartListAdapter = new ShopCartListAdapter(mContext, mCartList, mHandler);
                dateListview.setAdapter(mShopCartListAdapter);
            } else {
                mShopCartListAdapter.refreshListView(mCartList);
            }
        } else {
            layoutBottom.setVisibility(View.GONE);
            layoutNotdate.setVisibility(View.VISIBLE);
        }
    }

    /**
     * item 刷新时
     */
    private void calculateShopCartList() {
        if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
            int haveSelectNum = 0;
            for (String key : ShoppingCartMap.keySet()) {
                DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                boolean check = GoodsInfo_.isCheck();
                if (check) {
                    haveSelectNum++;
                    int num_ = GoodsInfo_.getGoodsNum();
                    String skb_price_ = GoodsInfo_.getSkb_price();
                    String super_ability_ = GoodsInfo_.getSuper_ability();
                    allnum += num_;
                    skb_price_ = PriceUtils.getInstance().gteMultiplySumPrice(skb_price_, "" + num_);
                    super_ability_ = PriceUtils.getInstance().gteMultiplySumPrice(super_ability_, "" + num_);
                    allskb_price = PriceUtils.getInstance().gteAddSumPrice(allskb_price, skb_price_);
                    allsuper_ability = PriceUtils.getInstance().gteAddSumPrice(allsuper_ability, super_ability_);
                    Log.e("ShopCartItemBean", "" + ShoppingCartMap.size() + "  num=" + num_);
                }
            }
            if (haveSelectNum > 0 && haveSelectNum < ShoppingCartMap.size()) {
                tvSelectnum.setText("已选(" + allnum + ")");
                if (Integer.parseInt(allsuper_ability) > 0 && Integer.parseInt(allskb_price) > 0) {
                    tvSkb.setText(allsuper_ability + "超能+" + allskb_price + "寿康宝");
                } else if (Integer.parseInt(allsuper_ability) > 0) {
                    tvSkb.setText(allsuper_ability + "超能");
                } else if (Integer.parseInt(allskb_price) > 0) {
                    tvSkb.setText(allskb_price + "寿康宝");
                }
                tvBuy.setBackgroundColor(getResources().getColor(R.color.blue));
                haveAllCheck = false;
                tvCheck.setBackgroundResource(R.mipmap.check_false);
            } else if (haveSelectNum == 0) {
                allnum = 0;
                allskb_price = "0";
                tvSelectnum.setText("全选");
                tvSkb.setText("0");
                haveAllCheck = false;
                tvCheck.setBackgroundResource(R.mipmap.check_false);
                tvBuy.setBackgroundColor(getResources().getColor(R.color.linebg));
            } else if (haveSelectNum == ShoppingCartMap.size()) {
                haveAllCheck = true;
                tvCheck.setBackgroundResource(R.mipmap.check_true_red);
                tvSelectnum.setText("已选(" + allnum + ")");
                if (Integer.parseInt(allsuper_ability) > 0 && Integer.parseInt(allskb_price) > 0) {
                    tvSkb.setText(allsuper_ability + "超能+" + allskb_price + "寿康宝");
                } else if (Integer.parseInt(allsuper_ability) > 0) {
                    tvSkb.setText(allsuper_ability + "超能");
                } else if (Integer.parseInt(allskb_price) > 0) {
                    tvSkb.setText(allskb_price + "寿康宝");
                }
                tvBuy.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            Log.e("ShopCartItemBean", "" + ShoppingCartMap.size() + "  allnum=" + allnum);
        }
    }

    @Override
    protected ShopCartPresenterImp<ShopCartContract.View> createPresent() {
        return new ShopCartPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mActivity);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mActivity);
    }

    @Override
    public void GetTuiJianListSuccess(ShopCartDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ShopCartAfterBean mDetailAfterBean = (ShopCartAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                mGoodsList = mDetailAfterBean.getHotGoodsList();
                if (mGoodsList != null && mGoodsList.size() > 0) {
                    GoodsListAdapter mAdapter = new GoodsListAdapter(mContext, mGoodsList);
                    tuijianGv.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {

    }

    @Override
    public void SubmitGoodsOrder(EditListDataBean responseBean) {

    }

    @Override
    public void ListError() {
    }


    MyDialog selectDialog;
    String delKey = "";

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
            tv_title.setText("确定要删除所选商品吗？");
            myexit_text_sure.setText("删除");
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
                    if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
                        if (ShoppingCartMap.containsKey(delKey)) {
                            ShoppingCartMap.remove(delKey);
                        }
                        refreshData();
                    }
                }
            });
        } else {
            selectDialog.show();
        }
    }

    //***************************************************************

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
