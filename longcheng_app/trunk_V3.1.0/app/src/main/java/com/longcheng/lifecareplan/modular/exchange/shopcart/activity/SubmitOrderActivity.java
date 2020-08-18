package com.longcheng.lifecareplan.modular.exchange.shopcart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.adapter.OrderCartListAdapter;
import com.longcheng.lifecareplan.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalendarActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.myview.address.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品-提交订单
 */
public class SubmitOrderActivity extends BaseActivityMVP<ShopCartContract.View, ShopCartPresenterImp<ShopCartContract.View>> implements ShopCartContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.detail_tv_name)
    TextView detailTvName;
    @BindView(R.id.detail_tv_address)
    TextView detailTvAddress;
    @BindView(R.id.date_listview)
    MyListView dateListview;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.tv_skb)
    TextView tvSkb;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layout_address)
    LinearLayout layout_address;
    @BindView(R.id.iv_address)
    ImageView iv_address;
    @BindView(R.id.iv_addressimg)
    ImageView iv_addressimg;

    private Map<String, String> activityMap = new HashMap<>();
    private Map<String, DetailItemBean> ShoppingCartMap = new HashMap<>();
    ArrayList<DetailItemBean> mCartList = new ArrayList();
    private String user_id;
    private String address_id = "";
    private String total_skb_price = "0";
    private String total_super_ability = "0";
    private String orders_datas = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_address:
                Intent intent = new Intent(mContext, AddressListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("receive_user_id", user_id);
                intent.putExtra("skiptype", "SubmitOrderActivity");
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ADDRESS);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.tv_submit:
                if (showIdentity) {
                    showInfoDialog();
                } else {
                    createOrder();
                }
                break;
        }
    }

    private void createOrder() {
        Log.e("orders_datas", "" + orders_datas);
        mPresent.submitGoodsOrder(user_id, address_id, total_skb_price, total_super_ability, orders_datas, carving);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.exchange_submitorder;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("提交订单");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layout_address.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        total_skb_price = intent.getStringExtra("allskb_price");
        total_super_ability = intent.getStringExtra("allsuper_ability");
        user_id = UserUtils.getUserId(mContext);
        mPresent.getAddressList(user_id, user_id);
        getHashMapData();
        scrollView.smoothScrollBy(0, 0);
        int width = DensityUtil.screenWith(mContext);
        int height = (int) (width * 0.0111);
        iv_address.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        getHighestIdentity();
    }

    private void getHashMapData() {
        ShoppingCartMap.clear();
        ShoppingCartMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData(user_id, DetailItemBean.class));
        if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
            String cont = "";
            if (Integer.parseInt(total_super_ability) >= 0 && Integer.parseInt(total_skb_price) >= 0) {
                cont = CommonUtil.getHtmlContentBig("#c18f46", total_super_ability) + " 超能 + "
                        + CommonUtil.getHtmlContentBig("#c18f46", total_skb_price) + " 寿康宝";
            } else if (Integer.parseInt(total_super_ability) >= 0) {
                cont = CommonUtil.getHtmlContentBig("#c18f46", total_super_ability) + " 超能";
            } else if (Integer.parseInt(total_skb_price) >= 0) {
                cont = CommonUtil.getHtmlContentBig("#c18f46", total_skb_price) + " 寿康宝";
            }
            tvSkb.setText(Html.fromHtml(cont));
            for (String key : ShoppingCartMap.keySet()) {
                Log.e("key", " " + key);
                if (ShoppingCartMap.get(key).isCheck()) {
                    DetailItemBean itemBean = ShoppingCartMap.get(key);
                    activityMap.put(itemBean.getShop_goods_id(), itemBean.getShop_goods_id());
                    mCartList.add(itemBean);
                }
            }
            orders_datas = setRecordInfoArrayToJson();
            OrderCartListAdapter mShopCartListAdapter = new OrderCartListAdapter(mContext, mCartList);
            dateListview.setAdapter(mShopCartListAdapter);
        }
    }

    private String setRecordInfoArrayToJson() {
        JSONArray recordInfoArray = new JSONArray();
        for (int i = 0; i < mCartList.size(); i++) {
            JSONObject latObject = new JSONObject();
            try {
                DetailItemBean mDetailItemBean = mCartList.get(i);
                String skb_price = mDetailItemBean.getSkb_price();
                String super_ability = mDetailItemBean.getSuper_ability();
                int num = mDetailItemBean.getGoodsNum();
                latObject.put("shop_goods_id", mDetailItemBean.getShop_goods_id());
                latObject.put("skb_price", skb_price);
                latObject.put("super_ability", super_ability);
                latObject.put("number", "" + num);
                String itemtotal_skb_price = PriceUtils.getInstance().gteMultiplySumPrice(skb_price, ""
                        + num);
                String itemtotal_super_ability = PriceUtils.getInstance().gteMultiplySumPrice(super_ability, ""
                        + num);
                latObject.put("total_skb_price", ""
                        + itemtotal_skb_price);
                latObject.put("total_super_ability", ""
                        + itemtotal_super_ability);
                latObject.put("shop_goods_price_id", ""
                        + mCartList.get(i).getShop_goods_price_id());
                recordInfoArray.put(latObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return recordInfoArray.toString();
    }

    /**
     * 下單成功后，清楚選中的購物車商品
     */
    private void clearCheckCart() {
        if (mCartList != null && mCartList.size() > 0 && ShoppingCartMap != null) {
            for (int i = 0; i < mCartList.size(); i++) {
                String key = mCartList.get(i).getShop_goods_id() + "_" + mCartList.get(i).getShop_goods_price_id();
                ShoppingCartMap.remove(key);
            }
            SharedPreferencesUtil.getInstance().putHashMapData(user_id, ShoppingCartMap);
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

    }

    private void setAddressView(String address_name, String phone, String address) {
        if (!TextUtils.isEmpty(address_id)) {
            if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
                phone = phone.substring(0, 3) + "****" + phone.substring(7);
            }
            detailTvName.setText(address_name + "     " + phone);
            detailTvAddress.setText(address);
            tvSubmit.setBackgroundColor(getResources().getColor(R.color.blue));
            iv_addressimg.setVisibility(View.GONE);
            detailTvName.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            detailTvAddress.setTextColor(getResources().getColor(R.color.text_contents_color));
        }
    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AddressAfterBean mAddressAfterBean = responseBean.getData();
            if (mAddressAfterBean != null) {
                List<AddressItemBean> mList = mAddressAfterBean.getAddressList();
                if (mList != null && mList.size() > 0) {
                    int defalutindex = 0;
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getIs_default().equals("1")) {
                            defalutindex = i;
                            break;
                        }
                    }
                    AddressItemBean mAddressBean = mList.get(defalutindex);
                    String area = AddressSelectUtils.initData(mContext, mAddressBean.getProvince(), mAddressBean.getCity(), mAddressBean.getDistrict());
                    address_id = mAddressBean.getAddress_id();
                    String address_name = mAddressBean.getConsignee();
                    String phone = mAddressBean.getMobile();
                    String address_address = mAddressBean.getAddress();
                    setAddressView(address_name, phone, area + " " + address_address);
                }
            }
        }
    }

    @Override
    public void SubmitGoodsOrder(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            clearCheckCart();
            Intent intent = new Intent(mContext, SubmitSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.APPLYHELP_FORRESULT_ADDRESS) {
                address_id = data.getStringExtra("address_id");
                String address_name = data.getStringExtra("address_name");
                String phone = data.getStringExtra("address_mobile");
                String address_address = data.getStringExtra("address_address");
                setAddressView(address_name, phone, address_address);
            } else if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                tv_date.setText(birthday);
                getJieqiName();
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getJieqiName() {
        Observable<ShopCartDataBean> observable = Api.getInstance().service.getJieqiName(user_id, birthday,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ShopCartDataBean>() {
                    @Override
                    public void accept(ShopCartDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            jieqiBranchAllName = responseBean.getData().getJieqiBranchAllName();
                            tv_date.setText(birthday + "\n出生节气：" + jieqiBranchAllName);
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    /**
     * 是否显示定制弹层
     */
    boolean showIdentity = false;

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getHighestIdentity() {
        showIdentity = false;
        Observable<ShopCartDataBean> observable = Api.getInstance().service.getHighestIdentity(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ShopCartDataBean>() {
                    @Override
                    public void accept(ShopCartDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            highestIdentity = responseBean.getData().getHighestIdentity();
                            List<String> shopGoodsIds = responseBean.getData().getShopGoodsIds();
                            if (shopGoodsIds != null && shopGoodsIds.size() > 0) {
                                for (int i = 0; i < shopGoodsIds.size(); i++) {
                                    if (activityMap.containsKey(shopGoodsIds.get(i))) {
                                        showIdentity = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    String highestIdentity;
    String birthday;
    MyDialog mInfoDialog;
    TextView tv_date;
    String carving = "";// 如：李明 坐堂医 1987-00-00 戊戌·立春
    String jieqiBranchAllName = "";

    private void showInfoDialog() {
        if (mInfoDialog == null) {
            mInfoDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_phoneactivity);// 创建Dialog并设置样式主题
            mInfoDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mInfoDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            final EditText et = new EditText(mActivity);
            et.setHint("请输入");
            mInfoDialog.setView(et);//给对话框添加一个EditText输入文本框
            mInfoDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            mInfoDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mInfoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            mInfoDialog.getWindow().setAttributes(p); //设置生效

            EditText et_name = mInfoDialog.findViewById(R.id.et_name);
            LinearLayout layout_date = mInfoDialog.findViewById(R.id.layout_date);
            tv_date = mInfoDialog.findViewById(R.id.tv_date);
            TextView tv_identity = mInfoDialog.findViewById(R.id.tv_identity);
            TextView btn_ok = mInfoDialog.findViewById(R.id.btn_ok);
            tv_identity.setText(highestIdentity);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_name, 20);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = et_name.getText().toString();
                    if (TextUtils.isEmpty(name)) {
                        ToastUtils.showToast("请输入手机雕刻姓名");
                    } else if (TextUtils.isEmpty(birthday) || TextUtils.isEmpty(jieqiBranchAllName)) {
                        ToastUtils.showToast("请选择出生日期");
                    } else {
                        carving = name + " " + highestIdentity + " " + birthday + " " + jieqiBranchAllName;
                        createOrder();
                    }
                }
            });
            layout_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CalendarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("showDate", birthday);
                    startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_DATE);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            });
        } else {
            mInfoDialog.show();
        }
    }
}
