package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.app.Activity;
import android.content.Intent;

import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DatesUtils;

/**
 * 作者：jun on
 * 时间：2018/9/13 19:28
 * 意图：
 */

public class SkipHelpUtils {
    private static SkipHelpUtils instance;

    private SkipHelpUtils() {
    }

    public static synchronized SkipHelpUtils getInstance() {
        if (instance == null) {
            instance = new SkipHelpUtils();
        }
        return instance;
    }

    public void skipIntent(Activity mContext, int action_goods_id) {
        Intent intent;
        //能量配跳转行动列表
        if (action_goods_id == 27) {
            intent = new Intent(mContext, ActionActivity.class);
        } else {
            intent = new Intent(mContext, ApplyHelpActivity.class);
        }
        intent.putExtra("action_goods_id", "" + action_goods_id);
        intent.putExtra("skiptype", ConstantManager.skipType_OPENRED);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

}
