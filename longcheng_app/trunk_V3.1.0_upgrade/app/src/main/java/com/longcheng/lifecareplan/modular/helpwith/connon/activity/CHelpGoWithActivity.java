package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpApplySelectAYAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 结伴互祝
 */
public class CHelpGoWithActivity extends BaseActivityMVP<CHelpContract.View, CHelpPresenterImp<CHelpContract.View>> implements CHelpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_jieqi)
    TextView tvJieqi;
    @BindView(R.id.tv_nextday)
    TextView tvNextday;
    @BindView(R.id.tv_zunum)
    TextView tv_zunum;
    @BindView(R.id.tv_ablity)
    TextView tv_ablity;
    @BindView(R.id.absolut_chair)
    AbsoluteLayout absolutChair;
    @BindView(R.id.aiyou_tv_zhufu)
    TextView aiyouTvZhufu;
    @BindView(R.id.aiyou_iv_thumb)
    ImageView aiyouIvThumb;
    @BindView(R.id.aiyou_tv_name)
    TextView aiyouTvName;
    @BindView(R.id.layout_inner)
    LinearLayout layoutInner;
    @BindView(R.id.layout_group_center)
    LinearLayout layoutGroupCenter;
    @BindView(R.id.iv_tutorimg)
    ImageView ivTutorimg;
    @BindView(R.id.tv_tutorname)
    TextView tvTutorname;
    @BindView(R.id.layout_tutor)
    LinearLayout layoutTutor;
    @BindView(R.id.relat_group)
    RelativeLayout relatGroup;
    @BindView(R.id.help_tv_create)
    TextView helpTvCreate;
    @BindView(R.id.help_tv_quit)
    TextView help_tv_quit;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView main_sv;

    private String knp_group_room_id;
    /**
     * 是否是自己发起邀请
     */
    boolean inviteRoomStatus = false;
    /**
     * 是否可以创建
     */
    boolean creatStatus = false;

    /**
     * my_process_status==2时 不能解散 退出
     */
    int my_process_status;
    int ohter_process_status;


    GrupUtils mGrupUtils;
    HashMap<Integer, CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean> mHashmap = new HashMap<>();
    int is_super_ability;
    String Ability;


    List<CHelpItemBean> patients = new ArrayList<>();
    MyDialog selectDialog;
    int selectIndex;
    String knp_msg_id;
    String knp_group_item_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_inner:
                if (patients != null && patients.size() > 0)
                    showSelectAiYou();
                break;
            case R.id.help_tv_quit:
                if (!TextUtils.isEmpty(knp_group_room_id) && my_process_status != 2) {
                    createClick = false;
                    setPayDialog();
                }
                break;
            case R.id.help_tv_create:
                if (TextUtils.isEmpty(knp_msg_id) || (!TextUtils.isEmpty(knp_msg_id) && Double.valueOf(knp_msg_id) == 0)) {
                    ToastUtils.showToast("请选择爱友");
                    break;
                }
                if (creatStatus) {
                    createClick = true;
                    setPayDialog();
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.connon_create_mrray;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("结伴互祝");
        pagetopLayoutLeft.setOnClickListener(this);
        layoutInner.setOnClickListener(this);
        helpTvCreate.setOnClickListener(this);
        help_tv_quit.setOnClickListener(this);
        help_tv_quit.setVisibility(View.VISIBLE);
        knp_group_room_id = getIntent().getStringExtra("knp_group_room_id");
        ScrowUtil.ScrollViewDownConfig(main_sv);
        main_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (main_sv != null) {
                    initDataAfter();
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        mPresent.groupRoomInfo(knp_group_room_id);
        mPresent.knpAiYouMsgList();
    }


    @Override
    protected CHelpPresenterImp<CHelpContract.View> createPresent() {
        return new CHelpPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void ListSuccess(CHelpListDataBean responseBean, int backPage) {
    }


    @Override
    public void DataSuccess(CHelpDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            patients = responseBean.getData().getPatients();
        }
    }

    @Override
    public void DetailDataSuccess(CHelpDetailDataBean responseBean) {

    }

    @Override
    public void CreatePageDataSuccess(CHelpCreatDataBean responseBean) {

    }

    int person_number;

    @Override
    public void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            CHelpGroupRoomDataBean.GroupRoomAfterBean data = responseBean.getData();
            String jieqi_name = data.getJieqi_name();
            int last_jieqi_day = data.getLast_jieqi_day();
            tvJieqi.setText(jieqi_name);
            tvNextday.setText("" + last_jieqi_day);
            CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean group_room = data.getGroup_room();
            tv_zunum.setText("当前组队数量：" + group_room.getTable_number() + "组");
            is_super_ability = group_room.getIs_super_ability();//1.超能
            Ability = group_room.getAbility();
            if (is_super_ability == 1) {
                tv_ablity.setText("需要支付 " + Ability + " 超级生命能量");
            } else {
                tv_ablity.setText("需要支付 " + Ability + " 生命能量");
            }
            String user_id = UserUtils.getUserId(mContext);
            person_number = group_room.getPerson_number();
            inviteRoomStatus = false;
            if (user_id.equals(group_room.getUser_id())) {
                inviteRoomStatus = true;
            }
            ArrayList<CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean> user_list = data.getUser_list();
            creatStatus = false;
            if (user_list.size() == person_number) {
                creatStatus = true;
            }
            mHashmap.clear();
            for (int i = 0; i < user_list.size(); i++) {
                CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean mBean = user_list.get(i);
                if (user_id.equals(mBean.getUser_id())) {
                    knp_msg_id = mBean.getKnp_msg_id();
                    knp_group_item_id = mBean.getKnp_group_item_id();
                    my_process_status = mBean.getProcess_status();
                    String patient_name = mBean.getPatient_name();
                    String patient_avatar = mBean.getPatient_avatar();
                    if (!TextUtils.isEmpty(knp_msg_id) && Double.valueOf(knp_msg_id) > 0) {
                        GlideDownLoadImage.getInstance().loadCircleImage(patient_avatar, aiyouIvThumb);
                        aiyouTvName.setText(CommonUtil.setName(patient_name));
                    }
                    mHashmap.put(0, mBean);
                    user_list.remove(i);
                    break;
                }
            }
            Log.e("ResponseBody", "user_list.size()==" + user_list.size());
            for (int i = 0; i < user_list.size(); i++) {
                CHelpGroupRoomDataBean.GroupRoomAfterBean.GroupRoomItemBean mBean = user_list.get(i);
                mHashmap.put(i + 1, mBean);
                if (mBean.getProcess_status() == 2) {
                    ohter_process_status = mBean.getProcess_status();
                }
            }
            if (creatStatus) {
                helpTvCreate.setBackgroundColor(getResources().getColor(R.color.yellow_E95D1B));
            } else {
                helpTvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
            }
            if (inviteRoomStatus) {
                help_tv_quit.setText("解散组队");
            } else {
                help_tv_quit.setText("退出组队");
            }
            if (my_process_status == 2) {
                //如果自己已经创建组队 则不能解散或退出 ，提示已创建
                help_tv_quit.setVisibility(View.GONE);
                helpTvCreate.setText("已创建");
                creatStatus = false;
                helpTvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
            } else {
                //当自己是队长并且有人已经组队 不能解散
                if (inviteRoomStatus && ohter_process_status == 2) {
                    help_tv_quit.setVisibility(View.GONE);
                } else {
                    help_tv_quit.setVisibility(View.VISIBLE);
                }
            }

            refreshChair();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
        ListUtils.getInstance().RefreshCompleteS(main_sv);

    }

    private void refreshChair() {
        if (mGrupUtils == null) {
            mGrupUtils = new GrupUtils(mActivity);
        }
        mGrupUtils.showChairAllViewGoWith(person_number, inviteRoomStatus, knp_msg_id, knp_group_room_id, mHashmap, relatGroup, absolutChair, layoutGroupCenter);

    }

    @Override
    public void editSuccess(ResponseBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            doFinish();
        }
        ToastUtils.showToast(responseBean.getMsg());
    }

    @Override
    public void CreateTableSuccess(ResponseBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            Intent intent = new Intent(mActivity, HelpApplySuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            doFinish();
        }
        ToastUtils.showToast(responseBean.getMsg());
    }

    @Override
    public void CreateRoomSuccess(EditDataBean responseBean) {

    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteS(main_sv);
    }


    public void showSelectAiYou() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_conapply_selectaiayou);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            ListView gv_date = selectDialog.findViewById(R.id.gv_date);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
            CHelpApplySelectAYAdapter mCHelpApplySelectAYAdapter = new CHelpApplySelectAYAdapter(mContext, patients, selectIndex);
            gv_date.setAdapter(mCHelpApplySelectAYAdapter);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    knp_msg_id = patients.get(selectIndex).getKnp_msg_id();
                    GlideDownLoadImage.getInstance().loadCircleImage(patients.get(selectIndex).getAvatar(), aiyouIvThumb);
                    aiyouTvName.setText(CommonUtil.setName(patients.get(selectIndex).getName()));
                    mPresent.wuwaiBindKnpAiYouMsg(knp_group_item_id, knp_msg_id);
                    refreshChair();
                }
            });
            gv_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectIndex = position;
                    mCHelpApplySelectAYAdapter.setSelectIndex(position);
                    mCHelpApplySelectAYAdapter.notifyDataSetChanged();
                }
            });
        } else {
            selectDialog.show();
        }
    }

    boolean createClick = false;
    MyDialog selectPayDialog;
    TextView tv_title, tv_title2;
    TextView tv_sure;

    public void setPayDialog() {
        if (selectPayDialog == null) {
            selectPayDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectPayDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectPayDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectPayDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectPayDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectPayDialog.getWindow().setAttributes(p); //设置生效
            tv_title = selectPayDialog.findViewById(R.id.tv_title);
            tv_title2 = selectPayDialog.findViewById(R.id.tv_title2);
            TextView tv_off = selectPayDialog.findViewById(R.id.tv_off);
            tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPayDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPayDialog.dismiss();
                    if (createClick) {
                        mPresent.wuwaiOpenTable(knp_group_item_id);
                    } else {
                        if (inviteRoomStatus) {
                            mPresent.wuaiDissolution(knp_group_room_id);
                        } else {
                            mPresent.wuaiSignOut(knp_group_room_id);
                        }
                    }
                }
            });
        } else {
            selectPayDialog.show();
        }
        String cont;
        if (createClick) {
            tv_sure.setText("确定支付");
            tv_title2.setVisibility(View.VISIBLE);
            if (is_super_ability == 1) {
                cont = "是否确认支付 " + CommonUtil.getHtmlContentBig("#e60c0c", Ability) +
                        CommonUtil.getHtmlContentStrong("#222222", " 超级") + " 生命能量";
                tv_title2.setText("天下无癌互祝组将获得绿券");
            } else {
                cont = "是否确认支付 " + CommonUtil.getHtmlContentBig("#e60c0c", Ability) + " 生命能量";
                tv_title2.setText("天下无癌互祝组将获得黄券");
            }
            tv_title.setText(Html.fromHtml(cont));
        } else {
            tv_sure.setText("确定");
            tv_title2.setVisibility(View.GONE);
            tv_title.setText("请再次确认您的操作，是否继续？");
        }

    }

}
