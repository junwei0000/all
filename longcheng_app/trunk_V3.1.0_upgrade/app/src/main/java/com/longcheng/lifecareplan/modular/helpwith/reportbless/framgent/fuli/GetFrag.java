package com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli;

import android.annotation.SuppressLint;
import android.widget.TextView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class GetFrag extends FuLiBaseFrag {
    TextView fuli_tv_allnum;

    public GetFrag() {
        super();
    }
    public GetFrag(TextView fuli_tv_allnum) {
        this.fuli_tv_allnum = fuli_tv_allnum;
    }

    @Override
    public TextView getTextView() {
        return fuli_tv_allnum;
    }
    @Override
    public int getType() {
        return INDEX_GET;
    }

}