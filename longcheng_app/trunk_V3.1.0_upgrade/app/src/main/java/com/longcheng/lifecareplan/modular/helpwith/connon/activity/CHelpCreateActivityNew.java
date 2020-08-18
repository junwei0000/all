package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CreateAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.SelectPeoAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CreateBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.LongClickButton;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建互祝组
 */
public class CHelpCreateActivityNew extends BaseActivityMVP<CHelpContract.View, CHelpPresenterImp<CHelpContract.View>> implements CHelpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.gv_zuduitype)
    MyGridView gvZuduitype;
    @BindView(R.id.gv_paytype)
    MyGridView gvPaytype;
    @BindView(R.id.gv_peoplenum)
    MyGridView gvPeoplenum;
    @BindView(R.id.item_layout_sub)
    LongClickButton itemLayoutSub;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_tv_add)
    LongClickButton itemTvAdd;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_tishi)
    TextView tv_tishi;


    private ArrayList<CHelpCreatDataBean.CHelpCreatAfterBean.CreatItemBean> knp_team_numbers;
    private int show_table_number = 72;
    private int max_table_number = 72;
    private int groupnumIndex = 3;
    private int base_ability = 45;
    private String knp_group_room_id = "";
    private String knp_group_item_id = "";
    int process_status;
    /**
     * 是否有正在进行的结伴组队
     */
    boolean haveGroupStatus = false;

    private int zuduitypeindex = 1;//1结缘 2结伴
    private int is_super_ability;//0普能  1超能
    private String knp_team_number_id;
    private int table_number = 1;
    private int ability;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.item_layout_sub:
                if (!clickStatus()) {
                    break;
                }
                if (table_number > 1) {
                    table_number--;
                }
                setAllAblity();
                break;
            case R.id.item_tv_add:
                if (!clickStatus()) {
                    break;
                }
                if (table_number < show_table_number) {
                    table_number++;
                }
                setAllAblity();
                break;
            case R.id.tv_next:
                if (zuduitypeindex == 1) {
                    mPresent.CreateRoom(zuduitypeindex, is_super_ability, knp_team_number_id, table_number, ability);
                } else {
                    if (haveGroupStatus) {
                        if (process_status >= 2) {
                            Intent intent = new Intent(mContext, CHelpDetailAddActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("knp_group_room_id", knp_group_room_id);
                            startActivity(intent);
                        } else {
                            setPayDialog();
                        }
                    } else {
                        mPresent.CreateRoom(zuduitypeindex, is_super_ability, knp_team_number_id, table_number, ability);
                    }
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
        return R.layout.connon_createnew;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("创建组队");
        pagetopLayoutLeft.setOnClickListener(this);
        itemLayoutSub.setOnClickListener(this);
        itemTvAdd.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        //连续减
        itemLayoutSub.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                onClick(itemLayoutSub);
            }
        }, 50);
        //连续加
        itemTvAdd.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                onClick(itemTvAdd);
            }
        }, 50);
    }

    @Override
    public void initDataAfter() {
        setAllAblity();
        initZuduiTypeViewS();
        initPayTypeViewS("0", "0");
        initPeopleViewS();
    }

    private void setAllAblity() {
        ability = base_ability * table_number;
        itemTvNum.setText("" + table_number);
        tv_tishi.setText((groupnumIndex + 2) + "人组最多可选择组队桌数" + show_table_number + "桌");
    }

    /**
     * 有结伴进行中 不能点击
     *
     * @return
     */
    private boolean clickStatus() {
        if (haveGroupStatus && zuduitypeindex == 2) {
            setPayDialog();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.createRoomPage();
    }

    private void initZuduiTypeViewS() {
        ArrayList<CreateBean> mZuduiTypeList = new ArrayList<>();
        mZuduiTypeList.add(new CreateBean("结缘互祝", "可以自主选择互祝组加入进行互祝", ""));
        mZuduiTypeList.add(new CreateBean("结伴互祝", "邀请成员组成固定队伍完成互祝", ""));
        CreateAdapter ZuduiTypeAdapter = new CreateAdapter(mActivity, mZuduiTypeList);
        ZuduiTypeAdapter.setSelectPosition(zuduitypeindex - 1);
        gvZuduitype.setAdapter(ZuduiTypeAdapter);
        gvZuduitype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                zuduitypeindex = position + 1;
                ZuduiTypeAdapter.setSelectPosition(position);
            }
        });
    }

    private void initPayTypeViewS(String ablity, String super_ablity) {
        ArrayList<CreateBean> mPayTypeList = new ArrayList<>();
        mPayTypeList.add(new CreateBean("普通生命能量", "每天最多可获得12张黄券", ablity));
        mPayTypeList.add(new CreateBean("超级生命能量", "上午场天下无癌只可获得一张绿券", super_ablity));
        CreateAdapter PayTypeAdapter = new CreateAdapter(mActivity, mPayTypeList);
        gvPaytype.setAdapter(PayTypeAdapter);
        gvPaytype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickStatus()) {
                    is_super_ability = position;
                    PayTypeAdapter.setSelectPosition(position);
                    changeGaveNum();
                }
            }
        });
    }


    private void initPeopleViewS() {
        ArrayList<CreateBean> mPeopleList = new ArrayList<>();
        mPeopleList.add(new CreateBean("2人组", "", ""));
        mPeopleList.add(new CreateBean("3人组", "", ""));
        mPeopleList.add(new CreateBean("4人组", "", ""));
        mPeopleList.add(new CreateBean("5人组", "", ""));
        SelectPeoAdapter PeopleAdapter = new SelectPeoAdapter(mActivity, mPeopleList);
        PeopleAdapter.setSelectPosition(groupnumIndex);
        gvPeoplenum.setAdapter(PeopleAdapter);
        gvPeoplenum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickStatus()) {
                    groupnumIndex = position;
                    if (knp_team_numbers != null && knp_team_numbers.size() == 4) {
                        knp_team_number_id = knp_team_numbers.get(groupnumIndex).getKnp_team_number_id();
                    }
                    PeopleAdapter.setSelectPosition(position);
                    changeGaveNum();
                }
            }
        });
    }

    /**
     * 设置允许的数量
     */
    private void changeGaveNum() {
        if (is_super_ability == 1) {
            tv_tishi.setVisibility(View.VISIBLE);
            if (knp_team_numbers != null && knp_team_numbers.size() == 4) {
                show_table_number = knp_team_numbers.get(groupnumIndex).getSuper_ability_limit_number();
            }
        } else {
            tv_tishi.setVisibility(View.GONE);
            show_table_number = max_table_number;
        }
        if (table_number > show_table_number) {
            table_number = show_table_number;
        }
        setAllAblity();
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

    }

    @Override
    public void CreatePageDataSuccess(CHelpCreatDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            CHelpCreatDataBean.CHelpCreatAfterBean data = responseBean.getData();
            initPayTypeViewS(data.getChatuser().getAbility(), data.getChatuser().getSuper_ability());
            max_table_number = data.getMax_table_number();
            show_table_number = max_table_number;
            base_ability = data.getBase_ability();
            haveGroupStatus = false;
            knp_team_numbers = data.getKnp_team_numbers();
            if (knp_team_numbers != null && knp_team_numbers.size() == 4) {
                knp_team_number_id = knp_team_numbers.get(groupnumIndex).getKnp_team_number_id();
            }
            CHelpCreatDataBean.CHelpCreatAfterBean.CreatItemBean knp_group_item = data.getKnp_group_item();
            if (knp_group_item != null && !TextUtils.isEmpty(knp_group_item.getKnp_group_room_id())) {
                knp_group_room_id = knp_group_item.getKnp_group_room_id();
                knp_group_item_id = knp_group_item.getKnp_group_item_id();
                process_status = knp_group_item.getProcess_status();
                haveGroupStatus = true;
            }
            changeGaveNum();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean) {

    }

    @Override
    public void editSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateTableSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateRoomSuccess(EditDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            Intent intent;
            if (zuduitypeindex == 1) {
                intent = new Intent(mActivity, CHelpMrrayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("knp_group_room_id", responseBean.getData());
                startActivity(intent);
            } else {
                intent = new Intent(mActivity, CHelpGoWithActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("knp_group_room_id", responseBean.getData());
                startActivity(intent);
            }
        }
        ToastUtils.showToast(responseBean.getMsg());
    }


    @Override
    public void ListError() {
    }

    MyDialog selectPayDialog;
    TextView tv_title;

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
            TextView tv_off = selectPayDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectPayDialog.findViewById(R.id.tv_sure);
            tv_sure.setText("查看");
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
                    Intent intent = new Intent(mActivity, CHelpGoWithActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("knp_group_room_id", knp_group_room_id);
                    startActivity(intent);
                }
            });
        } else {
            selectPayDialog.show();
        }
        tv_title.setText("您有正在进行的结伴互祝\n请点击查看");
    }
}
