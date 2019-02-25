package com.longcheng.lifecareplan.modular.mine.myaddress.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.AddressPickerView;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.YwpAddressBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/22 17:55
 * 意图：
 */

public class AddressSelectUtils {

    Activity mContext;
    Handler mHandler;
    int mHandlerID;

    public AddressSelectUtils(Activity mContext, Handler mHandler, int mHandlerID) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
    }

    MyDialog selectDialog;

    public void onSelectShiQu() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_city);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout city_layout_cancel = selectDialog.findViewById(R.id.city_layout_cancel);
            city_layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            AddressPickerView addressView = selectDialog.findViewById(R.id.apvAddress);
            addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
                @Override
                public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                    selectDialog.dismiss();
                    Message message = new Message();
                    message.what = mHandlerID;
                    Bundle bundle = new Bundle();

                    bundle.putString("pid", provinceCode);
                    bundle.putString("cid", cityCode);
                    bundle.putString("aid", districtCode);
                    bundle.putString("area", address);

                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                }
            });
        } else {
            selectDialog.show();
        }
    }

    static String area;

    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    public static String initData(Context mContext, String province, String city, String district) {
        StringBuilder jsonSB = new StringBuilder();
        try {
            BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(mContext.getAssets().open("address.json")));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonSB.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将数据转换为对象
        YwpAddressBean mYwpAddressBean = new Gson().fromJson(jsonSB.toString(), YwpAddressBean.class);
        if (mYwpAddressBean != null) {
            List<YwpAddressBean.AddressItemBean> p = mYwpAddressBean.getProvince();
            List<YwpAddressBean.AddressItemBean> c = mYwpAddressBean.getCity();
            List<YwpAddressBean.AddressItemBean> d = mYwpAddressBean.getDistrict();

            for (YwpAddressBean.AddressItemBean b : p) {
                if (b.getI().equals(province)) {
                    area = b.getN();
                    break;
                }
            }
            for (YwpAddressBean.AddressItemBean b : c) {
                if (b.getI().equals(city)) {
                    area = area + " " + b.getN();
                    break;
                }
            }
            for (YwpAddressBean.AddressItemBean b : d) {
                if (b.getI().equals(district)) {
                    area = area + " " + b.getN();
                    break;
                }
            }
        }
        return area;
    }
}
