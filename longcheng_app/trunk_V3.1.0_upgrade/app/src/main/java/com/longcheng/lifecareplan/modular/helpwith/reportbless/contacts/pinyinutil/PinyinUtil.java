package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

public class PinyinUtil {

    public static String getPingYin(String inputString) {
        try {
            if (TextUtils.isEmpty(Pinyin.toPinyin(inputString, ""))) {
                return "#";
            }else{
                return Pinyin.toPinyin(inputString, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "#";
    }
}
