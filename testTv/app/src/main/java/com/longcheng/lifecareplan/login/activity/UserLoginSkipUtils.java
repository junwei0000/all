
package com.longcheng.lifecareplan.login.activity;

import android.app.Activity;
import android.util.Log;

import com.longcheng.lifecareplan.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.SharedPreferencesHelper;


/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-22 上午11:31:39
 * @Description 类描述：
 */
public class UserLoginSkipUtils {

    Activity mActivity;

    public UserLoginSkipUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }


    /**
     * 保存登录信息
     *
     * @param loginInfo
     */
    public void getLoginInfo(LoginAfterBean loginInfo) {
        if (loginInfo != null) {
            String avatar = loginInfo.getAvatar();
            String is_cho = loginInfo.getIs_cho();
            String phone = loginInfo.getPhone();
            String star_level = loginInfo.getStar_level();
            String user_id = loginInfo.getUser_id();
            String user_name = loginInfo.getUser_name();
            String birthday = loginInfo.getBirthday();
            String lunar_calendar_birthday = loginInfo.getLunar_calendar_birthday();
            String political_status = loginInfo.getPolitical_status();
            String is_military_service = loginInfo.getIs_military_service();
            String pid = loginInfo.getPid();
            String cid = loginInfo.getCid();
            String aid = loginInfo.getAid();
            String area = loginInfo.getArea();
            SharedPreferencesHelper.put(mActivity, "loginStatus", ConstantManager.loginStatus);
            SharedPreferencesHelper.put(mActivity, "avatar", "" + avatar);
            SharedPreferencesHelper.put(mActivity, "is_cho", "" + is_cho);
            SharedPreferencesHelper.put(mActivity, "phone", "" + phone);
            SharedPreferencesHelper.put(mActivity, "star_level", "" + star_level);
            SharedPreferencesHelper.put(mActivity, "user_id", "" + user_id);
            SharedPreferencesHelper.put(mActivity, "user_name", "" + user_name);

            SharedPreferencesHelper.put(mActivity, "birthday", "" + birthday);
            SharedPreferencesHelper.put(mActivity, "lunar_calendar_birthday", "" + lunar_calendar_birthday);
            SharedPreferencesHelper.put(mActivity, "political_status", "" + political_status);
            SharedPreferencesHelper.put(mActivity, "is_military_service", "" + is_military_service);
            SharedPreferencesHelper.put(mActivity, "pid", "" + pid);
            SharedPreferencesHelper.put(mActivity, "cid", "" + cid);
            SharedPreferencesHelper.put(mActivity, "aid", "" + aid);
            SharedPreferencesHelper.put(mActivity, "area", "" + area);
            SharedPreferencesHelper.put(mActivity, "wxToken", "" + loginInfo.getWxToken());

            int group_id = loginInfo.getGroup_id();
            int team_id = loginInfo.getTeam_id();
            SharedPreferencesHelper.put(mActivity, "group_id", group_id);
            SharedPreferencesHelper.put(mActivity, "team_id", team_id);
            if (group_id == 0 || team_id == 0) {
                SharedPreferencesHelper.put(mActivity, "haveCommune", false);
            } else {
                SharedPreferencesHelper.put(mActivity, "haveCommune", true);
            }
            skipToPage();
        }
    }

    /**
     * 登录后多方向跳转判断
     */
    public void skipToPage() {

    }


}
