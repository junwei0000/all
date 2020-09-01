package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.bean.fupackage.OneDayOneHoldBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.CHelpListActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.FuQListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.LoveResultActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.MyCircleActivity;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneDayOneHoleActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.name_top)
    TextView nameTop;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;

    LayoutInflater inflater = null;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;


        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.onedayhole_layout;
    }

    @Override
    public void initView(View view) {
        nameTop.setText("每日一穴位");
        setOrChangeTranslucentColor(toolbar, null);
        int hei = (int) (DensityUtil.screenWith(mContext) * 0.546);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hei));
    }
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        successData(GetDate());

    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    TextView item_title;
    TextView item_title2;
    TextView item_daytime;

    private LinearLayout itemTopLayout(OneDayOneHoldBean biaoti, int type) {
        LinearLayout top_layout = null;
        if (biaoti != null) {
            top_layout = (LinearLayout) inflater.inflate(R.layout.hole_top_item, null);
            item_title = top_layout.findViewById(R.id.item_title);
            item_title2 = top_layout.findViewById(R.id.item_title_2);
            item_daytime = top_layout.findViewById(R.id.item_daytime);
            if (type == 1) {
                item_title.setVisibility(View.VISIBLE);
                item_title2.setVisibility(View.GONE);
                item_title.setText(biaoti.getTitle());
                item_daytime.setText(biaoti.getStrtime());
            } else {
                item_title.setVisibility(View.GONE);
                item_title2.setVisibility(View.VISIBLE);
                item_title2.setText(biaoti.getTitle());
                item_daytime.setVisibility(View.GONE);
            }
        }
        return top_layout;
    }


    TextView item_content;

    private LinearLayout itemContentLayout(OneDayOneHoldBean biaoti) {
        LinearLayout content_layout = null;
        if (biaoti != null && !biaoti.equals("")) {
            content_layout = (LinearLayout) inflater.inflate(R.layout.hole_content_item, null);
            item_content = content_layout.findViewById(R.id.item_content);
            item_content.setText(biaoti.getContent());
        }
        return content_layout;
    }

    ImageView item_image;

    private LinearLayout itemImageLayout(int intimage) {
        LinearLayout image_layout = null;
        if (intimage != 0) {
            image_layout = (LinearLayout) inflater.inflate(R.layout.hole_image_item, null);
            item_image = image_layout.findViewById(R.id.iv_thumb);
            item_image.setBackgroundResource(intimage);
        }
        return image_layout;
    }

    List<OneDayOneHoldBean> oneDayOneHoldBeans = null;

    private List<OneDayOneHoldBean> GetDate() {
        oneDayOneHoldBeans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            OneDayOneHoldBean oneDayOneHoldBean = new OneDayOneHoldBean();
            if (i == 0) {
                oneDayOneHoldBean.setTitle(this.getResources().getString(R.string.hold_top_title));
                oneDayOneHoldBean.setContent(this.getResources().getString(R.string.hold_top_content));
                oneDayOneHoldBean.setStrtime(this.getResources().getString(R.string.hold_top_time));
                oneDayOneHoldBean.setType(1);
            }
            if (i == 1) {
                oneDayOneHoldBean.setTitle(this.getResources().getString(R.string.hold_top_title1));
                oneDayOneHoldBean.setContent(this.getResources().getString(R.string.hold_top_content1));
                oneDayOneHoldBean.setType(2);
            }
            if (i == 2) {
                oneDayOneHoldBean.setTitle(this.getResources().getString(R.string.hold_top_title2));
                oneDayOneHoldBean.setContent(this.getResources().getString(R.string.hold_top_content2));
                oneDayOneHoldBean.setType(2);
            }
            if (i == 3) {
                oneDayOneHoldBean.setTitle(this.getResources().getString(R.string.hold_top_title2));
                oneDayOneHoldBean.setContent(this.getResources().getString(R.string.hold_top_content2));
                oneDayOneHoldBean.setType(2);
            }
            if (i == 4) {
                List<OneDayOneHoldBean.ImageBean> imageBeans = new ArrayList<>();
                OneDayOneHoldBean.ImageBean imageBean = new OneDayOneHoldBean.ImageBean();
                imageBean.setImageurl(R.mipmap.certificat_icon);
                oneDayOneHoldBean.setImageBeans(imageBeans);
                oneDayOneHoldBean.setType(3);
            }
            oneDayOneHoldBeans.add(oneDayOneHoldBean);
        }

        return oneDayOneHoldBeans;

    }

    private void successData(List<OneDayOneHoldBean> items) {
        if (!items.isEmpty()) {
            contentLayout.removeAllViews();
            for (int i = 0; i < items.size(); i++) {
                OneDayOneHoldBean oneitem = items.get(i);
                int type = oneitem.getType();
                if (type == 0) {
                    contentLayout.addView(itemTopLayout(oneitem, type));
                    contentLayout.addView(itemContentLayout(oneitem));
                }
                if (type == 1) {
                    contentLayout.addView(itemTopLayout(oneitem, type));
                    contentLayout.addView(itemContentLayout(oneitem));
                }
                if (type == 2) {
                    contentLayout.addView(itemTopLayout(oneitem, type));
                    contentLayout.addView(itemContentLayout(oneitem));
                }
                if (type == 3) {
                    contentLayout.addView(itemImageLayout(oneitem.getImageBeans().get(0).getImageurl()));
                }
            }
        }

    }



}
