package com.longcheng.volunteer.modular.index.welcome.frag;

import android.view.View;
import android.widget.TextView;

import com.longcheng.volunteer.R;

import butterknife.BindView;

/**
 * Created by Burning on 2018/9/27.
 */

public class GuidePage3Frag extends GuidePageBaseFrag {

    @BindView(R.id.bt_in)
    TextView btnIn;

    IGuidePageInListener listener;

    public void setPageListener(IGuidePageInListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_guidepage_3;
    }

    @Override
    protected void initView(View view) {
        ivBg1.setImageResource(R.mipmap.bootpage_three_mountain);
        ivBg2.setImageResource(R.mipmap.bootpage_three);
        tv.setText(R.string.guide_page3);
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.handleButtonInClick();
                }
            }
        });
    }

    public interface IGuidePageInListener {
        void handleButtonInClick();
    }
}
