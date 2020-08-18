package com.longcheng.lifecareplan.modular.mine.bill.activity;

import com.longcheng.lifecareplan.R;

/**
 * 福祺宝账单
 */

public class PionFQBBillActivity extends BasicssActivity {

    @Override
    public String setTitle() {
        return getString(R.string.bill);
    }

    @Override
    public int getType() {
        return TYPE_PioneerFQB;
    }
}