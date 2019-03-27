package com.longcheng.volunteer.modular.exchange.shopcart.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.volunteer.modular.exchange.shopcart.adapter.OrderCartListAdapter;
import com.longcheng.volunteer.modular.exchange.shopcart.bean.ShopCartDataBean;
import com.longcheng.volunteer.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.volunteer.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.PriceUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.MyListView;
import com.longcheng.volunteer.utils.myview.MyScrollView;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.volunteer.widget.dialog.LoadingDialogAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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


    private Map<String, DetailItemBean> ShoppingCartMap = new HashMap<>();
    ArrayList<DetailItemBean> mCartList = new ArrayList();
    private String user_id;
    private String address_id;
    private String total_skb_price;
    private String orders_datas;

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
                intent.putExtra("skipType", "SubmitOrderActivity");
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ADDRESS);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(address_id) && !TextUtils.isEmpty(total_skb_price)
                        && !TextUtils.isEmpty(orders_datas)) {
                    Log.e("orders_datas", "" + orders_datas);
                    mPresent.submitGoodsOrder(user_id, address_id, total_skb_price, orders_datas);
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
        user_id = UserUtils.getUserId(mContext);
        mPresent.getAddressList(user_id, user_id);
        getHashMapData();
        scrollView.smoothScrollBy(0, 0);
        int width = DensityUtil.screenWith(mContext);
        int height = (int) (width * 0.0111);
        iv_address.setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    private void getHashMapData() {
        ShoppingCartMap.clear();
        ShoppingCartMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData(user_id, DetailItemBean.class));
        if (ShoppingCartMap != null && ShoppingCartMap.size() > 0) {
            tvSkb.setText(total_skb_price);
            for (String key : ShoppingCartMap.keySet()) {
                Log.e("key", " " + key);
                if (ShoppingCartMap.get(key).isCheck())
                    mCartList.add(ShoppingCartMap.get(key));
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
                latObject.put("shop_goods_id", mDetailItemBean.getShop_goods_id());
                latObject.put("skb_price", mDetailItemBean.getSkb_price());
                latObject.put("number", ""
                        + mDetailItemBean.getGoodsNum());
                String itemtotal_skb_price = PriceUtils.getInstance().gteMultiplySumPrice(mDetailItemBean.getSkb_price(), ""
                        + mDetailItemBean.getGoodsNum());
                latObject.put("total_skb_price", ""
                        + itemtotal_skb_price);
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
                if (ShoppingCartMap.containsKey(key)) {
                    ShoppingCartMap.remove(key);
                }
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
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            clearCheckCart();
            Intent intent = new Intent(mContext, SubmitSuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
}
