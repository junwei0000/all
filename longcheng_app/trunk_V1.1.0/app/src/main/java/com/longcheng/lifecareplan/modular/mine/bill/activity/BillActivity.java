package com.longcheng.lifecareplan.modular.mine.bill.activity;

import com.longcheng.lifecareplan.R;

/**
 * 账单
 */

public class BillActivity extends BasicssActivity {

    @Override
    public String setTitle() {
        return getString(R.string.bill);
    }

    @Override
    public int getType() {
        return TYPE_BILL;
    }
}