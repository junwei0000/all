package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesAddBusiness.GetVenuesAddCallback;
import com.KiwiSports.business.VenuesAddBusiness;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.SupplierEditText;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenuesAddNextActivity extends BaseActivity {

    private LinearLayout pagetop_layout_back;
    private TextView pagetop_tv_name;
    private SupplierEditText tv_name;
    private TextView tv_next;
    private String field_name;
    private String address;
    private String top_left_x;
    private String top_left_y;
    private String bottom_right_x;
    private String bottom_right_y;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_back:
                doBack();
                break;
            case R.id.tv_next:
                field_name = tv_name.getText().toString();
                if (!TextUtils.isEmpty(field_name)) {
                    Intent intent = new Intent(context, VenuesAddCommitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("address", address + "");
                    intent.putExtra("field_name", field_name + "");
                    intent.putExtra("top_left_x", top_left_x + "");
                    intent.putExtra("top_left_y", top_left_y + "");
                    intent.putExtra("bottom_right_x", bottom_right_x + "");
                    intent.putExtra("bottom_right_y", bottom_right_y + "");
                    startActivity(intent);
                    CommonUtils.getInstance().setPageIntentAnim(intent, context);

                } else {
                    CommonUtils.getInstance().initToast(
                            getString(R.string.venues_add_nametishi));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.venues_addnext);
        CommonUtils.getInstance().addActivity(this);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void findViewById() {
        pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_name = (SupplierEditText) findViewById(R.id.tv_name);
    }

    @Override
    protected void setListener() {
        pagetop_layout_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        pagetop_tv_name.setText(getString(R.string.venues_add_nametishi));
    }

    @SuppressLint("NewApi")
    @Override
    protected void processLogic() {
        Intent intent = getIntent();
        field_name = "";
        address = intent.getExtras().getString("address", "");
        top_left_x = intent.getExtras().getString("top_left_x", "");
        top_left_y = intent.getExtras().getString("top_left_y", "");
        bottom_right_x = intent.getExtras().getString("bottom_right_x", "");
        bottom_right_y = intent.getExtras().getString("bottom_right_y", "");
    }


    private void doBack() {
        finish();
        CommonUtils.getInstance().setPageBackAnim(this);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doBack();
        }
        return false;
    }
}
