package com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli;

import android.annotation.SuppressLint;
import android.widget.TextView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class NotGetFrag extends FuLiBaseFrag {
    TextView fuli_tv_allnum;
    public NotGetFrag() {
        super();
    }
    public NotGetFrag(TextView fuli_tv_allnum) {
        this.fuli_tv_allnum = fuli_tv_allnum;
    }

    @Override
    public TextView getTextView() {
        return fuli_tv_allnum;
    }
    @Override
    public int getType() {
        return INDEX_NOTGET;
    }

}