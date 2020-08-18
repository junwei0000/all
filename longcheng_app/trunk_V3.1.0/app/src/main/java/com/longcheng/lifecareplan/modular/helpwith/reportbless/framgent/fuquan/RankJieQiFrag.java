package com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuquan;

import android.widget.TextView;

/**
 *
 */
public class RankJieQiFrag extends FuQuanBaseFrag {
    TextView fuquan_tv_allnum;

    public void setFuquan_tv_allnum(TextView fuquan_tv_allnum) {
        this.fuquan_tv_allnum = fuquan_tv_allnum;
    }

    @Override
    public TextView getTextView() {
        return fuquan_tv_allnum;
    }

    @Override
    public int getType() {
        return INDEX_JIEQI;
    }

}
