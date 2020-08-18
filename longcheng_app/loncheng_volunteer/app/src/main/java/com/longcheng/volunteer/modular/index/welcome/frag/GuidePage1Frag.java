package com.longcheng.volunteer.modular.index.welcome.frag;

import android.view.View;

import com.longcheng.volunteer.R;

/**
 * Created by Burning on 2018/9/27.
 */

public class GuidePage1Frag extends GuidePageBaseFrag {

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void initView(View view) {
        ivBg1.setImageResource(R.mipmap.bootpage_one_mountain);
        ivBg2.setImageResource(R.mipmap.bootpage_one);
        tv.setText(R.string.guide_page1);
    }
}
