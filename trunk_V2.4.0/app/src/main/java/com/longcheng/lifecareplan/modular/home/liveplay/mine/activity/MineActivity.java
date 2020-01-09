package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyFouseFrag;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyLiveFrag;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyVideoFrag;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.AllFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.ComingFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.PendingFragment;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的
 */
public class MineActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_skbnum)
    TextView tvSkbnum;
    @BindView(R.id.tv_showtitle)
    TextView tv_showtitle;
    @BindView(R.id.tv_myvideo)
    TextView tvMyvideo;
    @BindView(R.id.tv_mylive)
    TextView tvMylive;
    @BindView(R.id.tv_mylike)
    TextView tvMylike;
    @BindView(R.id.vPager)
    ViewPager vPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_myvideo:
                position = 0;
                selectPage();
                break;
            case R.id.tv_mylive:
                position = 1;
                selectPage();
                break;
            case R.id.tv_mylike:
                position = 2;
                selectPage();
                break;
            case R.id.tv_showtitle:
                if (!TextUtils.isEmpty(video_user_id) && video_user_id.equals(UserUtils.getUserId(mContext)))
                    showPopupWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_mine;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }


    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvMyvideo.setOnClickListener(this);
        tvMylive.setOnClickListener(this);
        tvMylike.setOnClickListener(this);
        tv_showtitle.setOnClickListener(this);
    }


    String video_user_id;

    @Override
    public void initDataAfter() {
        video_user_id = getIntent().getStringExtra("video_user_id");

        position = 0;
        initFragment();
        setPageAdapter();
        mPresent.getMineInfo(video_user_id);
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        MyVideoFrag mMyVideoFrag = new MyVideoFrag();
        mMyVideoFrag.setVideo_user_id(video_user_id);
        fragmentList.add(mMyVideoFrag);

        MyLiveFrag mMyLiveFrag = new MyLiveFrag();
        mMyLiveFrag.setVideo_user_id(video_user_id);
        fragmentList.add(mMyLiveFrag);

        MyFouseFrag mMyFouseFrag = new MyFouseFrag();
        mMyFouseFrag.setVideo_user_id(video_user_id);
        fragmentList.add(mMyFouseFrag);
    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    FragmentAdapter tabPageAdapter;

    private void setPageAdapter() {
        tabPageAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new AllFragment());
                list.add(new ComingFragment());
                list.add(new PendingFragment());
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
            }
        });
        vPager.setAdapter(tabPageAdapter);
        selectPage();
        vPager.setOffscreenPageLimit(3);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position_) {
                position = position_;
                selectPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 选择某页
     */
    private void selectPage() {
        vPager.setCurrentItem(position, false);
        tvMyvideo.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvMylive.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvMylike.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        if (position == 0) {
            tvMyvideo.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 1) {
            tvMylive.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 2) {
            tvMylike.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
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
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {
        MineItemInfo mMineItemInfo = responseBean.getData();
        if (mMineItemInfo != null) {
            MineItemInfo userExtra = mMineItemInfo.getUserExtra();
            if (userExtra != null && tvLikenum != null) {
                tvLikenum.setText("" + userExtra.getLike_number());
                tvSkbnum.setText("" + userExtra.getSkb());
                tv_showtitle.setText("" + userExtra.getShow_title());

                pageTopTvName.setText(userExtra.getUser_name());
                String avatar = userExtra.getAvatar();
                GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mActivity, avatar, ivHead);
            }
        }
    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {
        tv_showtitle.setText("" + content);
    }

    @Override
    public void cancelFollowSuccess(BasicResponse responseBean) {

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {

    }

    @Override
    public void Error() {
    }

    String content;
    MyDialog selectDialog;
    EditText et_content;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_live_minetitle);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            final EditText et = new EditText(mContext);
            et.setHint("说点啥吧");
            selectDialog.setView(et);//给对话框添加一个EditText输入文本框
            selectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            selectDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            selectDialog.getWindow().setAttributes(p); //设置生效
            et_content = (EditText) selectDialog.findViewById(R.id.et_content);
            TextView btn_sure = (TextView) selectDialog.findViewById(R.id.btn_sure);
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content = et_content.getText().toString();
                    if (!TextUtils.isEmpty(content)) {
                        selectDialog.dismiss();
                        mPresent.updateShowTitle(content);
                    } else {
                        ToastUtils.showToast("说点啥吧");
                    }
                }
            });
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_content, 40);
        } else {
            selectDialog.show();
        }
        et_content.setText("");
    }

    private void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
