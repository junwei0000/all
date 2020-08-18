package com.longcheng.lifecareplan.modular.index.welcome.frag;

import android.view.View;

import com.longcheng.lifecareplan.R;

/**
 * Created by Burning on 2018/9/27.
 */

public class GuidePage2Frag extends GuidePageBaseFrag {

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void initView(View view) {
        ivBg1.setImageResource(R.mipmap.bootpage_two_mountain);
        ivBg2.setImageResource(R.mipmap.bootpage_two);
        tv.setText(R.string.guide_page2);
    }
}
