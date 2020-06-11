package com.longcheng.lifecareplan.modular.mine.bill.activity;

import com.longcheng.lifecareplan.R;

/**
 * 用户祝佑宝账单
 */

public class PionUserZYBBillActivity extends BasicssActivity {

    @Override
    public String setTitle() {
        return getString(R.string.bill);
    }

    @Override
    public int getType() {
        return TYPE_PioneerUserZYB;
    }
}