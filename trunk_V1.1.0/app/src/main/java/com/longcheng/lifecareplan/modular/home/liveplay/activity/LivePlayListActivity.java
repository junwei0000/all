package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.PlayListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePlayItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MineActivity;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 直播列表
 */
public class LivePlayListActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.play_view)
    GridView playView;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.tv_playlist_video)
    TextView tvPlaylistVideo;
    @BindView(R.id.tv_playlist_video_line)
    TextView tvPlaylistVideoLine;
    @BindView(R.id.layout_playlist_video)
    LinearLayout layoutPlaylistVideo;
    @BindView(R.id.tv_playlist_live)
    TextView tvPlaylistLive;
    @BindView(R.id.tv_playlist_live_line)
    TextView tvPlaylistLiveLine;
    @BindView(R.id.layout_playlist_live)
    LinearLayout layoutPlaylistLive;
    @BindView(R.id.layout_playlist_mine)
    LinearLayout layoutPlaylistMine;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;

    private String uid;
    private String Pushurl;


    /**
     * 是否选中直播
     */
    boolean liveSeleStatus = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                mPresent.getLivePush(uid);
                break;
            case R.id.layout_playlist_video:
                liveSeleStatus = false;
                changeData();
                break;
            case R.id.layout_playlist_live:
                liveSeleStatus = true;
                changeData();
                break;
            case R.id.layout_playlist_mine:
                Intent intent = new Intent(mActivity, MineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_play_list;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void setListener() {
        layout_notlive.setVisibility(View.GONE);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        layoutPlaylistVideo.setOnClickListener(this);
        layoutPlaylistLive.setOnClickListener(this);
        layoutPlaylistMine.setOnClickListener(this);
        Intent intent = getIntent();
        int IsLiveBroadcast = intent.getIntExtra("IsLiveBroadcast", 0);
        playView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (playList != null && playList.size() > 0) {
                    Intent intent = new Intent(mActivity, LivePlayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("playuid", playList.get(position).getUid());
                    intent.putExtra("playTitle", playList.get(position).getPlayTile());
                    intent.putExtra("live_name", playList.get(position).getName());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        uid = UserUtils.getUserId(mContext);
        liveSeleStatus = false;
        changeData();
    }

    private void changeData() {
        tvPlaylistVideoLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvPlaylistVideo.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvPlaylistLiveLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvPlaylistLive.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        if (liveSeleStatus) {
            tvPlaylistVideoLine.setVisibility(View.INVISIBLE);
            tvPlaylistLiveLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvPlaylistLive.setTextColor(getResources().getColor(R.color.red));
            mPresent.getLivePlayList();
        } else {
            tvPlaylistVideoLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setVisibility(View.INVISIBLE);
            tvPlaylistVideoLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvPlaylistVideo.setTextColor(getResources().getColor(R.color.red));
            mPresent.getVideoPlayList();
        }
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mContext, this);
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
    public void BackPushSuccess(LivePushDataInfo responseBean) {
        Pushurl = responseBean.getPushurl();
        String playTile = "";
        String live_name = "";
        if (playList != null) {
            for (LivePlayItemInfo livePlayItemInfo : playList) {
                if (uid.equals(livePlayItemInfo.getUid())) {
                    playTile = livePlayItemInfo.getPlayTile();
                    live_name = livePlayItemInfo.getName();
                    break;
                }
            }
        }
        if (!TextUtils.isEmpty(Pushurl)) {
            LivePushActivity.startActivity(this, Pushurl);
        } else {
            ToastUtils.showToast("获取直播信息失败");
        }
    }

    @Override
    public void BackPlaySuccess(LivePushDataInfo responseBean) {

    }

    ArrayList<LivePlayItemInfo> playList;

    @Override
    public void BackPlayListSuccess(LivePushDataInfo responseBean) {
        playList = responseBean.getPlayList();
        String time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd HH:mm:ss");
        playList = new ArrayList<>();
        playList.add(new LivePlayItemInfo("113", R.mipmap.zhang, "生命呵护计划-海南调研", "张秋利", HomeFragment.jieqi_name, time));
        playList.add(new LivePlayItemInfo("128767", R.mipmap.yun, "国际大数据与数据科学进展主题论坛", "云莉雅", HomeFragment.jieqi_name, time));
        playList.add(new LivePlayItemInfo("942", R.mipmap.yun, "测试", "孤鬼", HomeFragment.jieqi_name, time));
        PlayListAdapter mAdapter = new PlayListAdapter(mContext, playList, liveSeleStatus);
        playView.setAdapter(mAdapter);
        playView.setVisibility(View.VISIBLE);
        layout_notlive.setVisibility(View.GONE);
    }

    @Override
    public void BackVideoListSuccess(LivePushDataInfo responseBean) {
//        playList = responseBean.getPlayList();
//        String time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd HH:mm:ss");
//        playList = new ArrayList<>();
//        playList.add(new LivePlayItemInfo("113", R.mipmap.zhang, "生命呵护计划-海南调研", "张秋利", HomeFragment.jieqi_name, time));
//        playList.add(new LivePlayItemInfo("128767", R.mipmap.yun, "国际大数据与数据科学进展主题论坛", "云莉雅", HomeFragment.jieqi_name, time));
//        playList.add(new LivePlayItemInfo("128767", R.mipmap.yun, "国际大数据与数据科学进展主题论坛", "云莉雅", HomeFragment.jieqi_name, time));
//        PlayListAdapter mAdapter = new PlayListAdapter(mContext, playList,liveSeleStatus);
//        playView.setAdapter(mAdapter);
        playView.setVisibility(View.GONE);
        layout_notlive.setVisibility(View.VISIBLE);

    }

    @Override
    public void Error() {

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
