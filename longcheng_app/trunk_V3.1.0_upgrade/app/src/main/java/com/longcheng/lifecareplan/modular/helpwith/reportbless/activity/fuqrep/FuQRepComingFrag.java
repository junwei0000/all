package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.fuqrep;

import android.annotation.SuppressLint;
import android.widget.TextView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class FuQRepComingFrag extends FuQRepBaseFrag {
    int type;//1请祈福 2领福利  3节气  4日期
    String keyword;
    public FuQRepComingFrag() {
        super();
    }
    public FuQRepComingFrag(int type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public int getSelect_status() {
        return INDEX_COMING;
    }
}