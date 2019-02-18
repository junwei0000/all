
package com.longcheng.lifecareplan.modular.index.login.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanActitvty;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneJoinListActivity;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneMineActivity;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.lifecareplan.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.message.activity.MessageActivity;
import com.longcheng.lifecareplan.modular.mine.signIn.activity.SignInH5Activity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;


/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-22 上午11:31:39
 * @Description 类描述：
 */
public class UserLoginSkipUtils {

    private String loginskiptostatus;
    Activity mActivity;
    Intent intents;

    public UserLoginSkipUtils(Activity mActivity) {
        this.mActivity = mActivity;
        loginskiptostatus = (String) SharedPreferencesHelper.get(mActivity, "loginSkipToStatus", "");
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
            MySharedPreferences.getInstance().saveIsLogout(false);
            boolean isFirstIn = MySharedPreferences.getInstance().getIsLogout();
            Log.e("initUserInfo", "skipToPage   isFirstIn =" + isFirstIn);
            skipToPage();
        }
    }


    public void doBackCheck() {
        if (loginskiptostatus.equals(ConstantManager.loginSkipToMainNext)) {
            intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_BACK);
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intents);
        }
    }

    /**
     * 登录后多方向跳转判断
     */
    public void skipToPage() {
        if (loginskiptostatus.equals(ConstantManager.loginSkipToMainNext)) {
            intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_NEXT);
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToMessage)) {//消息页面
            intents = new Intent(mActivity, MessageActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToInvitefriends)) {//邀请亲友
//            intents = new Intent(mActivity, InviteFriendsActivity.class);
//            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            mActivity.startActivity(intents);
            intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToHelpWithEnergy)) {//能量互祝列表
            intents = new Intent(mActivity, HelpWithEnergyActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("skiptype", "" + SharedPreferencesHelper.get(mActivity, "skiptype", ""));
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToActivatEnergy)) {//激活能量
            intents = new Intent(mActivity, ActivatEnergyActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToSINGIN)) {//每日签到
            intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
            LocalBroadcastManager.getInstance(mActivity).sendBroadcast(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToEnergyDetail)) {//能量详情
            intents = new Intent(mActivity, DetailActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("msg_id", "" + SharedPreferencesHelper.get(mActivity, "msg_id", ""));
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToBangDan)) {//榜单
            intents = new Intent(mActivity, BangDanActitvty.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("starturl", "" + SharedPreferencesHelper.get(mActivity, "starturl", ""));
            intents.putExtra("title", "" + SharedPreferencesHelper.get(mActivity, "title", ""));
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToHealthyDeli)) {//健康速递详情
            intents = new Intent(mActivity, HealthyDeliveryDetailAct.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("detail_url", "" + SharedPreferencesHelper.get(mActivity, "health_detail_url", ""));
            intents.putExtra("cont", "" + SharedPreferencesHelper.get(mActivity, "health_cont", ""));
            intents.putExtra("title", "" + SharedPreferencesHelper.get(mActivity, "health_title", ""));
            intents.putExtra("img", "" + SharedPreferencesHelper.get(mActivity, "health_newimg", ""));
            mActivity.startActivity(intents);
            mActivity.finish();
        } else if (loginskiptostatus.equals(ConstantManager.loginSkipToCommuneJoinList)) {//加入公社
            boolean haveCommune = (boolean) SharedPreferencesHelper.get(mActivity, "haveCommune", false);
            if (haveCommune) {
                intents = new Intent(mActivity, CommuneMineActivity.class);
            } else {
                intents = new Intent(mActivity, CommuneJoinListActivity.class);
            }
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mActivity.startActivity(intents);
            mActivity.finish();
        }
    }

    /**
     * 判断登录状态
     *
     * @param mContext
     * @param loginSkipToStatus 跳转方向
     * @return
     */
    public static boolean checkLoginStatus(Context mContext, String loginSkipToStatus) {
        boolean status = false;
        try {
            String loginStatus = (String) SharedPreferencesHelper.get(mContext, "loginStatus", "");
            if (loginStatus.equals(ConstantManager.loginStatus)) {
                status = true;
            } else {
                SharedPreferencesHelper.put(mContext, "loginSkipToStatus", loginSkipToStatus);
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
        }
        return status;
    }

}
