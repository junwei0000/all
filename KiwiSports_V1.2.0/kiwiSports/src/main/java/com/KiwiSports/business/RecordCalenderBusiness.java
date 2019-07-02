package com.KiwiSports.business;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.parser.RecordCalenderParser;
import com.KiwiSports.utils.parser.RecordListParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 */
public class RecordCalenderBusiness {

    public interface GetRecordListCallback {
        public void afterDataGet(String response);
    }

    HashMap<String, String> mhashmap;
    private GetRecordListCallback mGetDataCallback;
    Context mContext;

    public RecordCalenderBusiness(Context mContext,  HashMap<String, String> mhashmap,
                                  GetRecordListCallback mGetDataCallback) {
        this.mGetDataCallback = mGetDataCallback;
        this.mhashmap = mhashmap;
        this.mContext = mContext;
        getDate();
    }

    private void getDate() {
        //page_size=20&uid=20051&access_token=9_c0UZLZjgwus6s4fdiS1DgLHvYP46I955yh3vGA3p_z81gM3S2_zy8Zmlo2e63KtiM7O-dWzqfYZsaXk83XDQFXz1IVy81FwbYx5YDTeLc5k&page=1&token=aimei862348032510265
        String path = Constants.RECORDCALENDER;
        StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
                new Listener<String>() {
                    public void onResponse(String response) {
                        Log.e("TESTLOG", "------------response------------"
                                + response);
                        mGetDataCallback.afterDataGet(response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                mGetDataCallback.afterDataGet(null);
            }
        }) {
            @Override
            protected HashMap<String, String> getParams() {
                return mhashmap;
            }
        };
        RequestUtils.addRequest(mJsonObjectRequest, mContext);
    }

}
