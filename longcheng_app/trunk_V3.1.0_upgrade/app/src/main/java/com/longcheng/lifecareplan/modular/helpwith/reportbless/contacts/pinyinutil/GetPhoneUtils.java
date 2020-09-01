package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil;


import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.utils.LogUtils;

import java.util.ArrayList;
/**
 * Created by gerry on 2018/9/11.
 * 获取通讯录公共方法
 */

public class GetPhoneUtils {

    private ArrayList<PhoneBean> list;

    public GetPhoneUtils() {

    }
    public ArrayList<PhoneBean> getPhoneNumberFromMobile(Context context,boolean isadd ,boolean isshow) {
        // TODO Auto-generated constructor stub
        list = new ArrayList<PhoneBean>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { "display_name", "sort_key", "contact_id",
                        "data1" }, null, null, null);
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            int Id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            String Sortkey = getSortkey(cursor.getString(1));
            PhoneBean phoneInfo = new PhoneBean(Id,name, number,Sortkey,isadd,isshow);
            LogUtils.v("TAG","联系人："+name+"电话："+number);
            list.add(phoneInfo);
        }
        cursor.close();
        return list;
    }

    private static String getSortkey(String sortKeyString) {
        String key =sortKeyString.substring(0,1).toUpperCase();
        if (key.matches("[A-Z]")){
            return key;
        }else
            return "#";
    }
}