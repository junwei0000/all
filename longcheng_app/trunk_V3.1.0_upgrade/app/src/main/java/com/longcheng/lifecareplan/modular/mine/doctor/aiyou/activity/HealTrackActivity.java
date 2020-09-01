package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.SelectVideoActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.adapter.HealCalendarAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.adapter.HealPicAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackAfterBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackItemBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DateEntity;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 疗愈跟踪
 */
public class HealTrackActivity extends BaseActivityMVP<AiYouContract.View, AiYouPresenterImp<AiYouContract.View>> implements AiYouContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.flipper)
    ViewFlipper flipper;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.et_cont)
    EditText etCont;
    @BindView(R.id.layout_doc_idea)
    LinearLayout layoutDocIdea;
    @BindView(R.id.layout_day)
    LinearLayout layoutDay;
    @BindView(R.id.tv_tijiao)
    TextView tvTijiao;
    @BindView(R.id.layout_tijiao)
    LinearLayout layout_tijiao;
    @BindView(R.id.bottom_img)
    ImageView bottom_img;
    @BindView(R.id.gv_1)
    MyGridView gv_1;
    @BindView(R.id.gv_2)
    MyGridView gv_2;


    private String today;
    /**
     * 坐堂医是否6张都选择
     */
    boolean docAllSelect = true;
    String item_info_jsons;
    String SelectData;
    String knp_msg_id;
    String tijiaoType = "tijiao";

    String knp_msg_follow_item_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_tijiao:
                if (tijiaoType.equals("tijiao")) {
                    String proposal = etCont.getText().toString();
                    Log.e("HealCalendarAdapter", "" + knp_msg_follow_item_id);
                    if (docAllSelect && !TextUtils.isEmpty(proposal) && !TextUtils.isEmpty(item_info_jsons)) {
                        mPresent.dactoreExaminePatientPic(knp_msg_follow_item_id, item_info_jsons, proposal);
                    }
                } else if (tijiaoType.equals("updatePicInfo")) {
                    informBackRefrsh = true;
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/knp/knpmsgnewreport?knp_msg_id=" + knp_msg_id);
                    startActivity(intent);
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
        return R.layout.aiyou_healtrack;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layout_tijiao.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etCont, 200);
        etCont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBtn() {
        if (layout_tijiao != null && tijiaoType.equals("tijiao")) {
            String proposal = etCont.getText().toString();
            Log.e("HealCalendarAdapter", "" + item_info_jsons);
            if (docAllSelect && !TextUtils.isEmpty(proposal) && !TextUtils.isEmpty(item_info_jsons)) {
                layout_tijiao.setBackgroundColor(getResources().getColor(R.color.red));
            } else {
                layout_tijiao.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        }
    }

    @Override
    public void initDataAfter() {
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAblumUtils.setCropStaus(false);
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        knp_msg_id = intent.getStringExtra("knp_msg_id");
        SelectData = intent.getStringExtra("day");
        user_name = CommonUtil.setName(user_name);
        if (!TextUtils.isEmpty(user_name)) {
            pageTopTvName.setText(user_name + "的疗愈跟踪");
        } else {
            pageTopTvName.setText("疗愈跟踪");
        }
        identityFlag = intent.getIntExtra("identityFlag", 1);
        today = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        init();
    }

    /**
     * 上传报告返回刷新页面数据
     */
    boolean informBackRefrsh = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (informBackRefrsh) {
            informBackRefrsh = false;
            init();
        }

    }

    @Override
    protected AiYouPresenterImp<AiYouContract.View> createPresent() {
        return new AiYouPresenterImp<>(mActivity, this);
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
    public void ListSuccess(AYApplyListDataBean responseBean, int pageback) {

    }

    ArrayList<HealTeackItemBean> followItemInfos;
    ArrayList<HealTeackItemBean> validFollowItems;
    ArrayList<HealTeackItemBean> topfollowItemInfos = new ArrayList<>();
    ArrayList<HealTeackItemBean> bottomfollowItemInfos = new ArrayList<>();

    @Override
    public void BackDaySuccess(HealTeackDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            String proposal;
            HealTeackAfterBean data = responseBean.getData();
            layout_tijiao.setVisibility(View.GONE);
            validFollowItems = data.getValidFollowItems();//对号（有效的）
            if (validFollowItems != null && validFollowItems.size() > 0) {
                calV.setValidFollowItems(validFollowItems);
                calV.notifyDataSetChanged();
            }
            followItemInfos = data.getFollowItemInfos();//6张图片
            ArrayList<HealTeackItemBean> currentFollowItem = data.getCurrentFollowItem();
            if (currentFollowItem != null && currentFollowItem.size() > 0) {
                proposal = currentFollowItem.get(0).getProposal();
                knp_msg_follow_item_id = currentFollowItem.get(0).getKnp_msg_follow_item_id();
                etCont.setText(proposal);
            } else {
                knp_msg_follow_item_id = "";
                etCont.setText("");
            }
            showDataView(data);
            showPicGridView(data);
            outDateShowView(data);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    int identityFlag;//1爱友  2坐堂医 3志愿者
    boolean aiyou_add_status;
    boolean doctor_edit_status;

    private void showDataView(HealTeackAfterBean data) {
//        identityFlag = data.getIdentityFlag();
        if (identityFlag == 1) {
            tvTitle1.setText("请按要求上传图片");
            tvTitle2.setText("请上传每日三次服用照片");
            aiyou_add_status = DatesUtils.getInstance().doDateEqual(today, SelectData, "yyyy-MM-dd");
        } else if (identityFlag == 2) {
            tvTitle1.setText("爱友是否达到要求，请坐堂医选择");
            tvTitle2.setText("每日三次是否按时服用");
            tvTishi.setVisibility(View.VISIBLE);
            layoutDocIdea.setVisibility(View.VISIBLE);
            /**
             * 坐堂医第二天才能评论前一天的，只能评论一次
             */
            String SelectDataNext = DatesUtils.getInstance().getSomeDays(SelectData, 1);
            doctor_edit_status = DatesUtils.getInstance().doDateEqual(today, SelectDataNext, "yyyy-MM-dd");
            if (followItemInfos != null && followItemInfos.size() > 0) {
                for (int i = 0; i < followItemInfos.size(); i++) {
                    int Confirm_status = followItemInfos.get(i).getConfirm_status();
                    if (Confirm_status != 0) {//评论过就不再有权限评论
                        doctor_edit_status = false;
                        break;
                    }
                }
            }
            Log.e("HealCalendarAdapter", "doctor_edit_status=" + doctor_edit_status);
            if (doctor_edit_status) {
                layout_tijiao.setVisibility(View.VISIBLE);
                bottom_img.setVisibility(View.GONE);
                ConfigUtils.getINSTANCE().isEdit(true, etCont);
            } else {
                layout_tijiao.setVisibility(View.GONE);
                ConfigUtils.getINSTANCE().isEdit(false, etCont);
            }
        } else {
            tvTitle1.setText("志愿者疗愈跟踪");
            tvTitle2.setText("每日三次服用照片");
        }
    }

    private void showPicGridView(HealTeackAfterBean data) {
        topfollowItemInfos.clear();
        bottomfollowItemInfos.clear();
        if (followItemInfos != null && followItemInfos.size() > 0) {
            for (int i = 0; i < followItemInfos.size(); i++) {
                followItemInfos.get(i).setIndex(i);
                if (i < 3) {
                    topfollowItemInfos.add(followItemInfos.get(i));
                } else {
                    bottomfollowItemInfos.add(followItemInfos.get(i));
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                topfollowItemInfos.add(new HealTeackItemBean(i));
                bottomfollowItemInfos.add(new HealTeackItemBean(i + 3));
            }
        }
        topAdapter = new HealPicAdapter(mActivity, topfollowItemInfos,
                identityFlag, aiyou_add_status, doctor_edit_status, mHandler);
        gv_1.setAdapter(topAdapter);
        bottomAdapter = new HealPicAdapter(mActivity, bottomfollowItemInfos,
                identityFlag, aiyou_add_status, doctor_edit_status, mHandler);
        gv_2.setAdapter(bottomAdapter);
    }

    HealPicAdapter topAdapter;
    HealPicAdapter bottomAdapter;

    private void outDateShowView(HealTeackAfterBean data) {
        HealTeackItemBean knp_msg = data.getKnp_msg();
        String sign_start_day = knp_msg.getSign_start_day();
        String sign_end_day = knp_msg.getSign_end_day();
        int Selecttamp = DatesUtils.getInstance().getDateToTimeStamp(SelectData, "yyyy-MM-dd");
        int starttamp = DatesUtils.getInstance().getDateToTimeStamp(sign_start_day, "yyyy-MM-dd");
        int endtamp = DatesUtils.getInstance().getDateToTimeStamp(sign_end_day, "yyyy-MM-dd");
        layout_tijiao.setBackgroundColor(getResources().getColor(R.color.gray));
        tijiaoType = "tijiao";
        if (Selecttamp >= starttamp && Selecttamp <= endtamp) {
            layoutDay.setVisibility(View.VISIBLE);
            tvTijiao.setText("提交");
        } else if (Selecttamp < starttamp) {
            layoutDay.setVisibility(View.GONE);
            layout_tijiao.setVisibility(View.GONE);
        } else if (Selecttamp > endtamp) {
            layoutDay.setVisibility(View.GONE);
            int todaytamp = DatesUtils.getInstance().getDateToTimeStamp(today, "yyyy-MM-dd");
            if (identityFlag == 2 && todaytamp > endtamp) {
                layout_tijiao.setBackgroundColor(getResources().getColor(R.color.red));
                HealTeackItemBean knp_msg_new_report = data.getKnp_msg_new_report();
                tijiaoType = "updatePicInfo";
                if (knp_msg_new_report == null || (knp_msg_new_report != null && TextUtils.isEmpty(knp_msg_new_report.getId()))) {
                    tvTijiao.setText("上传爱友检查报告");
                    bottom_img.setVisibility(View.VISIBLE);
                    layout_tijiao.setVisibility(View.VISIBLE);
                } else {
                    // 审核状态:0 审核中  1已审核 2已驳回
                    int status = knp_msg_new_report.getStatus();
                    if (status == 1) {
                        tvTijiao.setText("审核通过");
                        tijiaoType = "updatePicInfo_";
                        bottom_img.setVisibility(View.GONE);
                        layout_tijiao.setVisibility(View.VISIBLE);
                    } else if (status == 2) {
                        tvTijiao.setText("本次报告被驳回，重新上传");
                        bottom_img.setVisibility(View.VISIBLE);
                        layout_tijiao.setVisibility(View.VISIBLE);
                    } else {
                        tijiaoType = "updatePicInfo_";
                        tvTijiao.setText("审核中");
                        bottom_img.setVisibility(View.GONE);
                        layout_tijiao.setVisibility(View.VISIBLE);
                        layout_tijiao.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }
            }
        }
    }

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            String cover_url = responseBean.getData();
            changeView(cover_url, true);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void AddPicSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            changeView(msendBean.getPic_url(), false);
        }
    }

    @Override
    public void DocAddIdeaSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            getData(SelectData);
        }
    }

    private void changeView(String cover_url, boolean notSendUrlStatus) {
        if (updateindex >= 3) {
            if (bottomfollowItemInfos != null && bottomfollowItemInfos.size() > 0) {
                bottomfollowItemInfos.get(updateindex - 3).setPic_url(cover_url);
                bottomfollowItemInfos.get(updateindex - 3).setNotsendUrlStatus(notSendUrlStatus);
                if (bottomAdapter != null) {
                    bottomAdapter.refreshListView(bottomfollowItemInfos);
                }
            }
        } else {
            if (topfollowItemInfos != null && topfollowItemInfos.size() > 0) {
                topfollowItemInfos.get(updateindex).setPic_url(cover_url);
                topfollowItemInfos.get(updateindex).setNotsendUrlStatus(notSendUrlStatus);
                if (topAdapter != null) {
                    topAdapter.refreshListView(topfollowItemInfos);
                }
            }
        }
    }


    int updateindex;
    HealTeackItemBean msendBean;
    private AblumUtils mAblumUtils;
    public static final int UPDATEPIC = 1;
    public static final int SEND = 2;
    public static final int UPDATEABLUM = 3;
    public static final int SETRESULT = 4;
    public static final int setTiJiaoBtnStatus = 5;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case setTiJiaoBtnStatus:
                    if (topfollowItemInfos != null && topfollowItemInfos.size() > 0
                            && bottomfollowItemInfos != null && bottomfollowItemInfos.size() > 0) {
                        docAllSelect = true;
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < 3; i++) {
                            String Pic_url = topfollowItemInfos.get(i).getPic_url();
                            int Confirm_status = topfollowItemInfos.get(i).getConfirm_status();
                            if (!TextUtils.isEmpty(Pic_url) && Confirm_status == 0) {
                                docAllSelect = false;
                                break;
                            }
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("knp_msg_follow_item_info_id", topfollowItemInfos.get(i).getKnp_msg_follow_item_info_id());
                                jsonObject.put("type", (topfollowItemInfos.get(i).getIndex() + 1));
                                jsonObject.put("confirm_status", Confirm_status);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                        }
                        for (int i = 0; i < 3; i++) {
                            String Pic_url = bottomfollowItemInfos.get(i).getPic_url();
                            int Confirm_status = bottomfollowItemInfos.get(i).getConfirm_status();
                            if (!TextUtils.isEmpty(Pic_url) && Confirm_status == 0) {
                                docAllSelect = false;
                                break;
                            }
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("knp_msg_follow_item_info_id", bottomfollowItemInfos.get(i).getKnp_msg_follow_item_info_id());
                                jsonObject.put("type", (bottomfollowItemInfos.get(i).getIndex() + 1));
                                jsonObject.put("confirm_status", Confirm_status);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                        }
                        item_info_jsons = jsonArray.toString();
                        setBtn();
                    }
                    break;
                case UPDATEPIC:
                    updateindex = (int) msg.obj;
                    mAblumUtils.onAddAblumClick();
                    break;
                case SEND:
                    msendBean = (HealTeackItemBean) msg.obj;
                    updateindex = msendBean.getIndex();
                    mPresent.patientAddPic(msendBean.getPic_url(), knp_msg_id, (updateindex + 1));
                    break;
                case UPDATEABLUM:
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    //上传头像
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadImg(file);
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
            if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
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

    //********************************************************************
    private GestureDetector gestureDetector = null;
    HealCalendarAdapter calV;
    private GridView gridView = null;
    ArrayList<DateEntity> mweekList = new ArrayList<>();

    private void showTitleDate(String day) {
        String showD = DatesUtils.getInstance().getDateGeShi(day, "yyyy-MM-dd", "yyyy-MM-dd EEEE");
        tvDate.setText(showD);
    }

    private void init() {
        gestureDetector = new GestureDetector(mContext, new MyGestureListener());
        flipper.removeAllViews();
        addGridView();
        getData(SelectData);
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
    }

    public void getData(String SelectData_) {
        SelectData = SelectData_;
        showTitleDate(SelectData_);
        mweekList.clear();
        mweekList = DatesUtils.getInstance().getWeek(SelectData_);
        if (calV == null) {
            calV = new HealCalendarAdapter(mActivity, mweekList);
        } else {
            calV.reloadListView(mweekList, true);
        }
        mPresent.scanFollowItemInfo(knp_msg_id, SelectData);
    }

    /**
     * 下一周
     */
    private void NextWeek() {
        getData(DatesUtils.getInstance().getSomeDays(SelectData, -7));
    }

    /**
     * 上一周
     */
    private void PrevWeek() {
        getData(DatesUtils.getInstance().getSomeDays(SelectData, +7));
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = scanForActivity(mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();

        gridView = new GridView(mContext);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        // gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        if (Width == 720 && Height == 1280) {
            gridView.setColumnWidth(40);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(0);
        gridView.setHorizontalSpacing(0);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                Log.e("HealCalendarAdapter", "onItemClick=" + (position));
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                if (mweekList != null && mweekList.size() > 0) {
                    getData(mweekList.get(position).date);
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    private Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            Log.e("HealCalendarAdapter", "getShowYear=" + (e1.getX() - e2.getX()));
            if (e1.getX() - e2.getX() > 120) {
                // 向左滑动
//                if (Integer.parseInt(calV.getShowYear()) == 2049 && Integer.parseInt(calV.getShowMonth()) == 12) {
//                } else {
                PrevWeek();
//                }
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
//                Log.e("HealCalendarAdapter", "getShowYear=" + calV.getShowYear() + "  ;getShowMonth=" + calV.getShowMonth());
//                if (Integer.parseInt(calV.getShowYear()) == 1901 && Integer.parseInt(calV.getShowMonth()) == 1) {
//                } else {
                NextWeek();
//                }
                return true;
            }
            return false;
        }
    }


    //**********************************************************************
}
