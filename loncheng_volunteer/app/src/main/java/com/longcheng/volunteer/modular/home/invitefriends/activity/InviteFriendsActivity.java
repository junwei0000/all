package com.longcheng.volunteer.modular.home.invitefriends.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivity;
import com.longcheng.volunteer.config.Config;
import com.longcheng.volunteer.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.utils.share.ShareUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.volunteer.zxing.CreateQRImage;

import butterknife.BindView;

/**
 * 邀请亲友
 */
public class InviteFriendsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.iv_userimg)
    ImageView iv_userimg;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.layout_qrbg)
    LinearLayout layoutQrbg;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;

    ShareUtils mShareUtils;
    private String username;
    private String invite_user_url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (!TextUtils.isEmpty(invite_user_url)) {
                    String text = "人生最大的意义，莫过于让生命能量流动起来，祝福更多的人。";
                    String title = username + "邀请您加入健康互祝公社";
                    mShareUtils.setShare(text, "", invite_user_url, title);
                }
                break;
            case R.id.btn_ok:
                Intent intent = new Intent(mContext, InvitationAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home_invitefirends;
    }

    @Override
    public void initView(View view) {
        int width = DensityUtil.screenWith(mContext);
        int height = (int) (width * 0.649);
        iv_top.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        int heightl = (int) ((width - DensityUtil.dip2px(mContext, 10)) * 1.043);
        layoutQrbg.setLayoutParams(new LinearLayout.LayoutParams(width - DensityUtil.dip2px(mContext, 10), heightl));
        setOrChangeTranslucentOtherColor(toolbar, null, getResources().getColor(R.color.bluebg));
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        username = UserUtils.getUserName(mContext);
        invite_user_url = (String) SharedPreferencesHelper.get(mContext, "invite_user_url", "");
        if (TextUtils.isEmpty(invite_user_url)) {
            invite_user_url = Config.BASE_HEAD_URL + "home/wxuser/index/shareuid/";
        }
        invite_user_url = invite_user_url + UserUtils.getUserId(mContext);
        Log.e("invite_user_url", "invite_user_url=" + invite_user_url);
        CreateQRImage.createQRImage(invite_user_url, ivQr);
        String avatar = (String) SharedPreferencesHelper.get(mContext, "avatar", "");
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, avatar, iv_userimg, ConstantManager.image_angle);
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
