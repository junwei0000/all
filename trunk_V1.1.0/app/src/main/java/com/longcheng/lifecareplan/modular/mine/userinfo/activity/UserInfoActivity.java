package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.UserSetBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.nanchen.calendarview.LunarCalendarUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseActivityMVP<UserInfoContract.View, UserInfoPresenterImp<UserInfoContract.View>> implements UserInfoContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.userinfo_iv_head)
    CircleImageView userinfoIvHead;
    @BindView(R.id.userinfo_relay_ablum)
    RelativeLayout userinfoRelayAblum;
    @BindView(R.id.userinfo_tv_name)
    TextView userinfoTvName;
    @BindView(R.id.userinfo_relay_name)
    RelativeLayout userinfoRelayName;
    @BindView(R.id.userinfo_tv_phone)
    TextView userinfoTvPhone;
    @BindView(R.id.userinfo_relay_phone)
    RelativeLayout userinfoRelayPhone;
    @BindView(R.id.userinfo_tv_birthday)
    TextView userinfoTvBirthday;
    @BindView(R.id.userinfo_relay_birthday)
    RelativeLayout userinfoRelayBirthday;
    @BindView(R.id.userinfo_tv_politicalstatus)
    TextView userinfoTvPoliticalstatus;
    @BindView(R.id.userinfo_relay_politicalstatus)
    RelativeLayout userinfoRelayPoliticalstatus;
    @BindView(R.id.userinfo_tv_bingyi)
    TextView userinfoTvBingyi;
    @BindView(R.id.userinfo_relay_bingyi)
    RelativeLayout userinfoRelayBingyi;

    @BindView(R.id.userinfo_tv_address)
    TextView userinfo_tv_address;
    @BindView(R.id.userinfo_relay_address)
    RelativeLayout userinfoRelayaddress;
    @BindView(R.id.btn_save)
    TextView btn_save;


    private String user_name, political_status;
    private AblumUtils mAblumUtils;
    private String user_id, is_military_service;
    AddressSelectUtils mAddressSelectUtils;
    private String pid, cid, aid, area;
    private String phone;
    private String avatar;

    private boolean birthDayChanged = false;
    private String birthday;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                prepareToFinish();
                break;
            case R.id.userinfo_relay_ablum:
                mAblumUtils.onAddAblumClick();
                break;
            case R.id.userinfo_relay_name:
                intent = new Intent(mContext, UpdateNameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_NAME);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.userinfo_relay_phone:
                if (TextUtils.isEmpty(phone)) {
                    intent = new Intent(mContext, LoginThirdSetPwActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_PHONE);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                }
                break;
            case R.id.userinfo_relay_birthday:
                intent = new Intent(mContext, CalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("showDate", birthday);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_DATE);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.userinfo_relay_politicalstatus:
                intent = new Intent(mContext, PoliticalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("user_id", user_id);
                intent.putExtra("political_status", political_status);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_POLITICAL);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.userinfo_relay_bingyi:
                intent = new Intent(mContext, BingYiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("user_id", user_id);
                intent.putExtra("is_military_service", is_military_service);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_BINGYI);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.userinfo_relay_address:
                mAddressSelectUtils.onSelectShiQu();
                break;
            case R.id.btn_save:
                mPresent.checkUserInfo(user_id);
                break;

        }
    }


    @Override
    protected UserInfoPresenterImp<UserInfoContract.View> createPresent() {
        return new UserInfoPresenterImp<>(mContext, this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_account;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("个人信息");
        setOrChangeTranslucentColor(toolbar, null);
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAddressSelectUtils = new AddressSelectUtils(mActivity, mHandler, SELECTADDRESS);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        userinfoRelayAblum.setOnClickListener(this);

        userinfoRelayName.setOnClickListener(this);
        userinfoRelayPhone.setOnClickListener(this);
        userinfoRelayBirthday.setOnClickListener(this);
        userinfoRelayPoliticalstatus.setOnClickListener(this);
        userinfoRelayBingyi.setOnClickListener(this);
        userinfoRelayaddress.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        initUserInfo();
        mPresent.getUserSET(user_id);
    }


    private void initUserInfo() {
        avatar = (String) SharedPreferencesHelper.get(mContext, "avatar", "");
        String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
        String star_level = (String) SharedPreferencesHelper.get(mContext, "star_level", "");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        user_name = (String) SharedPreferencesHelper.get(mContext, "user_name", "");
        //政治面貌 ：1:共产党员 2：民主党派 3：团员 4：群众
        political_status = (String) SharedPreferencesHelper.get(mContext, "political_status", "");
        //是否服兵役 0 未服兵役 1：现役 2：退役
        is_military_service = (String) SharedPreferencesHelper.get(mContext, "is_military_service", "");

        pid = (String) SharedPreferencesHelper.get(mContext, "pid", "");
        cid = (String) SharedPreferencesHelper.get(mContext, "cid", "");
        aid = (String) SharedPreferencesHelper.get(mContext, "aid", "");
        area = (String) SharedPreferencesHelper.get(mContext, "area", "");
        userinfoTvName.setText(user_name);
        userinfoTvPhone.setText(phone);
        userinfo_tv_address.setText(area);
        Log.e("avatar", "" + avatar);
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mContext, avatar, userinfoIvHead);
        birthday = (String) SharedPreferencesHelper.get(mContext, "birthday", "");
        shoeDateView();
    }

    private void shoeDateView() {
        try {
            if (!TextUtils.isEmpty(birthday) && !birthday.equals("0000-00-00")) {
                String year = DatesUtils.getInstance().getDateGeShi(birthday, "yyyy-MM-dd", "yyyy");
                String month = DatesUtils.getInstance().getDateGeShi(birthday, "yyyy-MM-dd", "MM");
                String day = DatesUtils.getInstance().getDateGeShi(birthday, "yyyy-MM-dd", "dd");
                if (!TextUtils.isEmpty(year) && !TextUtils.isEmpty(month) && !TextUtils.isEmpty(day)) {
                    String yinliDate = new LunarCalendarUtils().getLunarDateYYYY(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), false);
                    String yinliDateShow = new LunarCalendarUtils().getLunarDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), false);
                    userinfoTvBirthday.setText(birthday + "\n" + yinliDateShow);
                }
            }
        } catch (Exception e) {
            birthday = "0000-00-00";
        }

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void editSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            String msg = responseBean.getData();
            if (TextUtils.isEmpty(msg)) {
                msg = responseBean.getMsg();
            }
            ToastUtils.showToast(msg);
            SharedPreferencesHelper.put(mContext, "pid", pid);
            SharedPreferencesHelper.put(mContext, "cid", cid);
            SharedPreferencesHelper.put(mContext, "aid", aid);
            SharedPreferencesHelper.put(mContext, "area", area);
            userinfo_tv_address.setText(area);
        }
    }

    @Override
    public void saveInfoSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            String msg = responseBean.getData();
            if (TextUtils.isEmpty(msg)) {
                msg = responseBean.getMsg();
            }
            ToastUtils.showToast(msg);
            SharedPreferencesHelper.put(mContext, "is_cho", "1");
            prepareToFinish();
        }
    }

    @Override
    public void editBirthdaySuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            SharedPreferencesHelper.put(mContext, "birthday", birthday);
            birthDayChanged = true;
        }
    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            EditThumbDataBean.EditThumbBackDataBean m = responseBean.getData();
            if (m != null) {
                avatar = m.getUrl_path();
                SharedPreferencesHelper.put(mContext, "avatar", avatar);
            }
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getUserSetSuccess(GetUserSETDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            UserSetBean mUserSetBean = responseBean.getData();
            if (mUserSetBean != null) {
                ArrayList<UserSetBean.UserSetInfoBean> m = mUserSetBean.getMilitary_service_conf();
                ArrayList<UserSetBean.UserSetInfoBean> p = mUserSetBean.getPolitical_status_conf();
                Log.e(TAG, p.size() + "     " + m.size());
                if (p != null && p.size() > 0) {
                    for (int i = 0; i < p.size(); i++) {
                        if (political_status.equals(p.get(i).getId())) {
                            userinfoTvPoliticalstatus.setText(p.get(i).getName());
                            break;
                        }
                    }
                }
                if (m != null && m.size() > 0) {
                    for (int i = 0; i < m.size(); i++) {
                        if (is_military_service.equals(m.get(i).getId())) {
                            userinfoTvBingyi.setText(m.get(i).getName());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void editError() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }


    /*
     * 调用相册
	 */
    protected static final int UPDATEABLUM = 3;
    protected static final int SETRESULT = 4;
    private final int SELECTADDRESS = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SELECTADDRESS:
                    //上传出生地
                    Bundle bundle = msg.getData();
                    pid = bundle.getString("pid");
                    cid = bundle.getString("cid");
                    aid = bundle.getString("aid");
                    area = bundle.getString("area");
                    mPresent.editHomeplace(user_id, pid, cid, aid);
                    break;
                case UPDATEABLUM:
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    userinfoIvHead.setImageBitmap(mBitmap);
                    //上传头像
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadAblum(user_id, file);
                    break;
                case SETRESULT:
                    int requestCode = msg.arg1;
                    int resultCode = msg.arg2;
                    Intent data = (Intent) msg.obj;
                    mAblumUtils.setResult(requestCode, resultCode, data);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.USERINFO_FORRESULT_BINGYI) {
                is_military_service = data.getStringExtra("is_military_service");
                String is_military_service_name = data.getStringExtra("is_military_service_name");
                SharedPreferencesHelper.put(mContext, "is_military_service", is_military_service);
                userinfoTvBingyi.setText(is_military_service_name);
            } else if (resultCode == ConstantManager.USERINFO_FORRESULT_PHONE) {
                phone = data.getStringExtra("phone");
                userinfoTvPhone.setText(phone);
            } else if (resultCode == ConstantManager.USERINFO_FORRESULT_POLITICAL) {
                political_status = data.getStringExtra("political_status");
                String political_status_name = data.getStringExtra("political_status_name");
                SharedPreferencesHelper.put(mContext, "political_status", political_status);
                userinfoTvPoliticalstatus.setText(political_status_name);
            } else if (resultCode == ConstantManager.USERINFO_FORRESULT_NAME) {
                user_name = data.getStringExtra("user_name");
                SharedPreferencesHelper.put(mContext, "user_name", user_name);
                userinfoTvName.setText(user_name);
            } else if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                shoeDateView();
                mPresent.editBirthday(user_id, birthday);
            } else if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
                    || requestCode == mAblumUtils.RESULTCROP) {
                Message msgMessage = new Message();
                msgMessage.arg1 = requestCode;
                msgMessage.arg2 = resultCode;
                msgMessage.obj = data;
                msgMessage.what = SETRESULT;
                mHandler.sendMessage(msgMessage);
                msgMessage = null;
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 预处理返回，这里设置需要回传的参数
     */
    private void prepareToFinish() {
        if (birthDayChanged) {
            Intent intent = new Intent();
            intent.putExtra("birthdayflag", true);
            setResult(RESULT_OK, intent);
        }
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            prepareToFinish();
        }
        return false;
    }


}
