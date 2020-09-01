package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpDetailAddAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CreateTopUserAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CreateBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小桌互祝
 */
public class CHelpDetailAddActivity extends BaseActivityMVP<CHelpContract.View, CHelpPresenterImp<CHelpContract.View>> implements CHelpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;


    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieqi)
    TextView itemTvJieqi;
    @BindView(R.id.item_tv_time)
    TextView itemTvTime;
    @BindView(R.id.tv_jieqi)
    TextView tvJieqi;
    @BindView(R.id.tv_nextday)
    TextView tvNextday;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_tv_ablity)
    TextView itemTvAblity;
    @BindView(R.id.gv_zu)
    MyGridView gvZu;
    @BindView(R.id.help_tv_create)
    TextView helpTvCreate;

    @BindView(R.id.tv_check)
    TextView tvCheck;
    @BindView(R.id.tv_selectnum)
    TextView tvSelectnum;
    @BindView(R.id.layout_select)
    LinearLayout layoutSelect;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.gv_user)
    MyGridView gv_user;
    @BindView(R.id.layout_bottom)
    LinearLayout layout_bottom;
    @BindView(R.id.layout_aiyouinfo)
    LinearLayout layout_aiyouinfo;


    private boolean haveAllCheck = true;
    //允许选择的最大数
    int allowSelectAllnum;
    ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> knpGroupItemList;
    CreateTopUserAdapter mCreateTopUserAdapter;
    CHelpDetailAddAdapter mAdapter;
    ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> helpList;


    int is_super_ability;
    String knp_group_room_id;
    int ability;
    int number;
    String knp_group_table_ids;

    boolean loading = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_select:
                if (!loading) {
                    loading = true;
                    haveAllCheck = !haveAllCheck;
                    setAllCheckStatus();
                }
                break;
            case R.id.help_tv_create:
                if (!TextUtils.isEmpty(knp_group_table_ids)) {
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
        return R.layout.connon_detal_add;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("天下无癌互祝组");
        pagetopLayoutLeft.setOnClickListener(this);
        layoutSelect.setOnClickListener(this);
        helpTvCreate.setOnClickListener(this);
        itemTvName.setFocusable(true);
        itemTvName.setFocusableInTouchMode(true);
        itemTvName.requestFocus();
        gv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (knpGroupItemList != null && knpGroupItemList.size() > 0) {
                    mCreateTopUserAdapter.setSelectPosition(position);
                    mPresent.getRoomInfo(knp_group_room_id, knpGroupItemList.get(position).getKnp_group_item_id());
                }
            }
        });
        gvZu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (helpList != null && helpList.size() > 0) {
                    //状态：0 未处理,1 匹配互祝进行中,2 匹配完成, 3 互祝完成
                    int status = helpList.get(position).getStatus();
                    boolean joinStatus = helpList.get(position).isJoinStatus();
                    if (status != 3 && !joinStatus) {
                        if (helpList.get(position).isCheck()) {
                            helpList.get(position).setCheck(false);
                        } else {
                            helpList.get(position).setCheck(true);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (status == 3) {
                            Intent intent = new Intent(mActivity, DetailOverActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("knp_group_table_id", helpList.get(position).getKnp_group_table_id());
                            startActivity(intent);
                        }
                    }
                    calculateBottomList();
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        knp_group_room_id = getIntent().getStringExtra("knp_group_room_id");
    }

    @Override
    protected void onResume() {
        super.onResume();

//        long startTime = System.currentTimeMillis();
//        LogUtils.v("TAG","时间开始："+startTime);
        mPresent.getRoomInfo(knp_group_room_id, "");
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

    }

    @Override
    public void DetailDataSuccess(CHelpDetailDataBean responseBean) {
        loading = false;
//        long currentTime = System.currentTimeMillis();
//        LogUtils.v("TAG","结束时间"+currentTime);
        if (responseBean.getStatus().equals("200")) {

            CHelpDetailDataBean.CHelpDetailAfterBean data = responseBean.getData();
            showTable(data);
            showAiYouView(data);

        } else if (responseBean.getStatus().equals("421")) {
            ToastUtils.showToast(responseBean.getMsg());
            doFinish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void CreatePageDataSuccess(CHelpCreatDataBean responseBean) {

    }

    @Override
    public void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean) {

    }


    @Override
    public void editSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            Intent intent = new Intent(mActivity, HelpApplySuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (responseBean.getStatus().equals("421")) {
            ToastUtils.showToast(responseBean.getMsg());
            doFinish();
        } else {
            onResume();
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void CreateTableSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateRoomSuccess(EditDataBean responseBean) {

    }


    @Override
    public void ListError() {

    }

    private void setAllCheckStatus() {
        if (helpList != null && helpList.size() > 0) {
            for (int i = 0; i < helpList.size(); i++) {
                if (haveAllCheck) {
                    helpList.get(i).setCheck(true);
                } else {
                    helpList.get(i).setCheck(false);
                }
            }
            mAdapter.notifyDataSetChanged();
            calculateBottomList();
        }
    }

    /**
     * item 刷新时
     */
    private void calculateBottomList() {
        int haveSelectNum = 0;
        ability = 0;
        number = 0;
        knp_group_table_ids = "";
        for (CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean GoodsInfo_ : helpList) {
            boolean check = GoodsInfo_.isCheck();
            int status = GoodsInfo_.getStatus();
            boolean joinStatus = GoodsInfo_.isJoinStatus();
            if (status != 3 && !joinStatus && check) {
                haveSelectNum++;
                if (TextUtils.isEmpty(knp_group_table_ids)) {
                    knp_group_table_ids = "" + GoodsInfo_.getKnp_group_table_id();
                } else {
                    knp_group_table_ids = knp_group_table_ids + "," + GoodsInfo_.getKnp_group_table_id();
                }

            }
        }
        ability = haveSelectNum * 45;
        number = haveSelectNum;


        if (haveSelectNum > 0 && haveSelectNum < allowSelectAllnum) {
            haveAllCheck = false;
            tvCheck.setBackgroundResource(R.mipmap.connon_zudui_aiyou_weixuan);
            tvSelectnum.setTextColor(getResources().getColor(R.color.c9));
            tvJifen.setText("已选" + haveSelectNum + "桌  " + haveSelectNum + " 积分");
            helpTvCreate.setBackgroundColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (haveSelectNum == 0) {
            haveAllCheck = false;
            tvCheck.setBackgroundResource(R.mipmap.connon_zudui_aiyou_weixuan);
            tvSelectnum.setTextColor(getResources().getColor(R.color.c9));
            tvJifen.setText("");
            helpTvCreate.setBackgroundColor(getResources().getColor(R.color.gray));
        } else if (haveSelectNum == allowSelectAllnum) {
            haveAllCheck = true;
            tvCheck.setBackgroundResource(R.mipmap.connon_zudui_aiyou_xuanzhong);
            tvSelectnum.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tvJifen.setText("已选" + haveSelectNum + "桌  " + haveSelectNum + " 积分");
            helpTvCreate.setBackgroundColor(getResources().getColor(R.color.yellow_E95D1B));
        }
        Log.e("calculateBottomList", "knp_group_table_ids==" + knp_group_table_ids +
                "  haveSelectNum==" + haveSelectNum + "  allowSelectAllnum==" + allowSelectAllnum);
        helpTvCreate.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = false;
            }
        }, 500);
    }

    /**
     * 是否是自己发起邀请
     */
    boolean inviteRoomStatus = false;

    private void showAiYouView(CHelpDetailDataBean.CHelpDetailAfterBean data) {
        String jieqi_name = data.getJieqi_name();
        String last_jieqi_day = data.getLast_jieqi_day();
        int type = data.getType();
        String user_id = UserUtils.getUserId(mContext);
        CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean knpGroupItem = data.getKnpGroupItem();
        ArrayList<String> lastSixSolarTerms = data.getLastSixSolarTerms();
        inviteRoomStatus = false;
        String zu_userid = knpGroupItem.getUser_id();
        if (zu_userid.equals(user_id)) {
            inviteRoomStatus = true;
            layout_bottom.setVisibility(View.GONE);
        } else {
            if (allowSelectAllnum == 0) {
                layout_bottom.setVisibility(View.GONE);
            } else {
                layout_bottom.setVisibility(View.VISIBLE);
            }
        }
        if (type == 1) {
            gv_user.setVisibility(View.GONE);
        } else {
            gv_user.setVisibility(View.VISIBLE);
            knpGroupItemList = data.getKnpGroupItemList();
            if (knpGroupItemList != null && knpGroupItemList.size() > 0) {
                mCreateTopUserAdapter = new CreateTopUserAdapter(mActivity, knpGroupItemList);
                gv_user.setAdapter(mCreateTopUserAdapter);
                for (int i = 0; i < knpGroupItemList.size(); i++) {
                    if (zu_userid.equals(knpGroupItemList.get(i).getUser_id())) {
                        mCreateTopUserAdapter.setSelectPosition(i);
                        break;
                    }
                }
            }
        }
        GlideDownLoadImage.getInstance().loadCircleImage(knpGroupItem.getPatient_avatar(), itemIvThumb);
        itemTvName.setText(CommonUtil.setName(knpGroupItem.getPatient_name()));
        itemTvJieqi.setText(knpGroupItem.getPatient_jieqi_name());
        if (lastSixSolarTerms != null && lastSixSolarTerms.size() > 0) {
            String jieqi = "";
            for (String name : lastSixSolarTerms) {
                jieqi = jieqi + name + " ";
            }
            itemTvTime.setText("缺失节气：" + jieqi);
        }
        tvJieqi.setText(jieqi_name);
        tvNextday.setText("" + last_jieqi_day);

        itemTvNum.setText("当前组队数量：" + knpGroupItem.getTable_number() + "组");
        is_super_ability = knpGroupItem.getIs_super_ability();
        if (is_super_ability == 0) {
            itemTvAblity.setText("需要支付 " + knpGroupItem.getAbility() + " 生命能量");
        } else {
            itemTvAblity.setText("需要支付 " + knpGroupItem.getAbility() + " 超级生命能量");
        }
    }


    private void showTable(CHelpDetailDataBean.CHelpDetailAfterBean data) {
        ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> notJoinTables = data.getNotJoinTables();
        ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> joinTables = data.getJoinTables();

        helpList = new ArrayList<>();
        if (notJoinTables != null && notJoinTables.size() > 0) {
            helpList.addAll(notJoinTables);
        }
        if (joinTables != null && joinTables.size() > 0) {
            helpList.addAll(joinTables);
        }

        if (helpList != null && helpList.size() > 0) {
            itemIvThumb.setVisibility(View.VISIBLE);
            layout_aiyouinfo.setVisibility(View.VISIBLE);
        } else {
            itemIvThumb.setVisibility(View.GONE);
            layout_aiyouinfo.setVisibility(View.GONE);
        }


        allowSelectAllnum = 0;
        String user_id = UserUtils.getUserId(mContext);
        for (CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean GoodsInfo_ : helpList) {
            int status = GoodsInfo_.getStatus();
            ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> knp_group_table_item = GoodsInfo_.getKnp_group_table_item();
            for (int i = 0; i < knp_group_table_item.size(); i++) {
                if (user_id.equals(knp_group_table_item.get(i).getUser_id())) {
                    GoodsInfo_.setJoinStatus(true);
                }
            }
            boolean joinStatus = GoodsInfo_.isJoinStatus();
            if (status != 3 && !joinStatus) {
                allowSelectAllnum++;
            }
        }

        mAdapter = new CHelpDetailAddAdapter(mActivity, helpList);
        gvZu.setAdapter(mAdapter);

        haveAllCheck = false;
        setAllCheckStatus();
    }


    MyDialog selectPayDialog;
    TextView tv_title, tv_title2;

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
            tv_title2.setVisibility(View.VISIBLE);
            tv_title2.setTextColor(getResources().getColor(R.color.text_contents_color));
            TextView tv_off = selectPayDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_sure.setText("确定支付");
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
                    mPresent.JoinHelpRoom(knp_group_room_id, ability, number, knp_group_table_ids);
                }
            });
        } else {
            selectPayDialog.show();
        }
        String cont = "完成后可获得 " + CommonUtil.getHtmlContentBig("#e60c0c", "" + number) + " 积分";
        String cont2 = "";
        if (is_super_ability == 0) {
            cont2 = "是否确认支付 " + CommonUtil.getHtmlContentBig("#e60c0c", "" + ability) + " 生命能量";
        } else {
            cont2 = "是否确认支付 " + CommonUtil.getHtmlContentBig("#e60c0c", "" + ability) + " 超级生命能量";
        }
        tv_title.setText(Html.fromHtml(cont));
        tv_title2.setText(Html.fromHtml(cont2));
    }
}
