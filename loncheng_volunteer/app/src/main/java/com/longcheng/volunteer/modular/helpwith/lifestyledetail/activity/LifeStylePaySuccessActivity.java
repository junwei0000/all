package com.longcheng.volunteer.modular.helpwith.lifestyledetail.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivity;
import com.longcheng.volunteer.utils.ConfigUtils;

import butterknife.BindView;

/**
 *
 */
public class LifeStylePaySuccessActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.tv_sname)
    TextView tv_sname;
    @BindView(R.id.tv_rname)
    TextView tv_rname;

    @BindView(R.id.btn_look)
    TextView btn_look;
    @BindView(R.id.btn_backmall)
    TextView btn_backmall;
    private String help_goods_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_look:
                Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("help_goods_id", help_goods_id);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                doFinish();
                break;
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_backmall:
                doFinish();
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_lifestyle_paysuccess;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("互祝成功");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btn_look.setOnClickListener(this);
        btn_backmall.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        help_goods_id = intent.getStringExtra("help_goods_id");
        String sponsor_user_name = intent.getStringExtra("sponsor_user_name");
        String receive_user_name = intent.getStringExtra("receive_user_name");
        String skb_price = intent.getStringExtra("skb_price");
        String cont1 = "感恩<font color=\"#e60c0c\"> " + sponsor_user_name + " </font>的祝福！";
        tv_sname.setText(Html.fromHtml(cont1));
        String cont2 = "<font color=\"#e60c0c\">" + receive_user_name + " </font>收到您<font color=\"#e60c0c\"> " + skb_price + " </font>个寿康宝";
        tv_rname.setText(Html.fromHtml(cont2));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
