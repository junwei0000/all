package com.ricky.nfc.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ricky.nfc.R;
import com.ricky.nfc.api.Api;
import com.ricky.nfc.base.BaseNfcActivity;
import com.ricky.nfc.bean.OrderAfterBean;
import com.ricky.nfc.bean.OrderDataBean;
import com.ricky.nfc.bean.OrderItemBean;
import com.ricky.nfc.tools.GlideDownLoadImage;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WriteOpenAppActivity extends BaseNfcActivity {
    Toolbar toolbar;
    LinearLayout layoutLeft;
    TextView pageTop_tv_name;
    EditText et_cont;
    TextView button;

    RelativeLayout realt_info;
    ImageView item_iv_thumb;
    TextView item_tv_name;
    TextView item_tv_phone;
    TextView item_tv_orderid;

    TextView btn_bangding;

    String order_id;
    /**
     * 是否开始写入
     */
    boolean isWriter = false;
    boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_openapp);
        toolbar = findViewById(R.id.toolbar);
        layoutLeft = findViewById(R.id.pagetop_layout_left);
        pageTop_tv_name = findViewById(R.id.pageTop_tv_name);
        pageTop_tv_name.setText("NFC信息写入");
        et_cont = findViewById(R.id.et_cont);
        button = findViewById(R.id.button);

        realt_info = findViewById(R.id.realt_info);
        item_iv_thumb = findViewById(R.id.item_iv_thumb);
        item_tv_name = findViewById(R.id.item_tv_name);
        item_tv_phone = findViewById(R.id.item_tv_phone);
        item_tv_orderid = findViewById(R.id.item_tv_orderid);
        btn_bangding = findViewById(R.id.btn_bangding);

        setOrChangeTranslucentColor(toolbar, null);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!ifNFCUse()) {
            return;
        }
        setBtn();
        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_id = et_cont.getText().toString().trim();
                if (TextUtils.isEmpty(order_id)) {
                    Toast.makeText(WriteOpenAppActivity.this, "请输入要写入NFC的订单ID", Toast.LENGTH_LONG).show();
                } else {
                    closeSoftInput(WriteOpenAppActivity.this);
                    getOrderInfo();
                }
            }
        });
        btn_bangding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWriter = true;
                Toast.makeText(WriteOpenAppActivity.this, "请将NFC标签贴近手机背面进行绑定", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("CheckResult")
    public void getOrderInfo() {
        Observable<OrderDataBean> observable = Api.getInstance().service.getOrderInfo(order_id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderDataBean>() {
                    @Override
                    public void accept(OrderDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            OrderAfterBean mAfterBean = responseBean.getData();
                            OrderItemBean mItemBean = mAfterBean.getUser();
                            if (mItemBean != null) {
                                GlideDownLoadImage.getInstance().loadCircleImage(WriteOpenAppActivity.this, mItemBean.getAvatar(), item_iv_thumb);
                                item_tv_name.setText(mItemBean.getUser_name());
                                item_tv_phone.setText("手机号：" + mItemBean.getPhone());
                                item_tv_orderid.setText("订单ID：" + order_id);
                                isClick = true;
                                setBtn();
                            }
                        } else {
                            isClick = false;
                            setBtn();
                            Toast.makeText(WriteOpenAppActivity.this, responseBean.getMsg(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isClick = false;
                        setBtn();
                    }
                });

    }

    private void setBtn() {
        if (isClick) {
            realt_info.setVisibility(View.VISIBLE);
            btn_bangding.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            isWriter = false;
            realt_info.setVisibility(View.INVISIBLE);
            btn_bangding.setBackgroundColor(getResources().getColor(R.color.gray_bian));
        }

    }

    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    protected Boolean ifNFCUse() {
        if (mNfcAdapter == null) {
            Toast.makeText(this, "设备不支持NFC！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "请在系统设置中先启用NFC功能！", Toast.LENGTH_LONG).show();
            startActivity(new Intent("android.settings.NFC_SETTINGS"));
            return false;
        }
        return true;
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!ifNFCUse()) {
            return;
        }
        if (!isClick) {
            Toast.makeText(WriteOpenAppActivity.this, "请输入要写入NFC的订单ID", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isWriter) {
            Toast.makeText(this, "请确定绑定当前用户", Toast.LENGTH_LONG).show();
            return;
        }
        //1.获取Tag对象
        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        writeNFCTag(detectedTag);
    }

    /**
     * 往标签写数据的方法
     *
     * @param tag
     */
    public void writeNFCTag(Tag tag) {
        if (tag == null) {
            return;
        }
        String json = "{\"order_id\":" + order_id + "}";
        NdefMessage ndefMessage = new NdefMessage(
                new NdefRecord[]{NdefRecord.createExternal("com.longcheng.lifecareplan", "longchengnfc", json.getBytes())});
        //转换成字节获得大小
        int size = ndefMessage.toByteArray().length;
        Log.e("ndefMessage", ndefMessage.toString());
        try {
            //2.判断NFC标签的数据类型（通过Ndef.get方法）
            Ndef ndef = Ndef.get(tag);
            //判断是否为NDEF标签
            if (ndef != null) {
                ndef.connect();
                //判断是否支持可写
                if (!ndef.isWritable()) {
                    Toast.makeText(this, "写入失败，该NFC标签已被绑定", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断标签的容量是否够用
                if (ndef.getMaxSize() < size) {
                    return;
                }
                //3.写入数据
                ndef.writeNdefMessage(ndefMessage);
                setReadOnly(ndef);
            } else { //当我们买回来的NFC标签是没有格式化的，或者没有分区的执行此步
                //Ndef格式类
                NdefFormatable format = NdefFormatable.get(tag);
                //判断是否获得了NdefFormatable对象，有一些标签是只读的或者不允许格式化的
                if (format != null) {
                    //连接
                    format.connect();
                    //格式化并将信息写入标签
                    format.format(ndefMessage);
                    setReadOnly(ndef);
                } else {
                    Toast.makeText(this, "写入失败", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 设置只读
     *
     * @param ndef
     */
    private void setReadOnly(Ndef ndef) {
//        try {
//            ndef.makeReadOnly();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Toast.makeText(this, "绑定成功", Toast.LENGTH_SHORT).show();
        et_cont.setText("");
        isClick = false;
        setBtn();
    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public void closeSoftInput(Activity context) {
        if (context != null && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null)
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
