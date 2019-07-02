package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.GPSUtil;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class userThumbShoaUtils {
    HashMap<Marker, VenuesUsersInfo> mMarkerClickmap = new HashMap<Marker, VenuesUsersInfo>();
    HashMap<String, Bitmap> mBitmapDescriptormap = new HashMap<String, Bitmap>();

    HashMap<String, Marker> mMarkermap_now = new HashMap<String, Marker>();
    HashMap<String, Marker> mMarkermap_before = new HashMap<String, Marker>();//记录上次有效用户
    private Context mActivity;
    private AMap mBaiduMap;

    /**
     * 已显示名字弹层的用户
     */
    private String showUid = "";
    ArrayList<VenuesUsersInfo> mMapList;
    HashMap<String, String> mUserListHideMap;
    private ImageSize mImageSize;
    Handler mHandle;
    int mHandleID;

    public userThumbShoaUtils(Context mActivity, AMap mBaiduMap, Handler mHandle, int mHandleID) {
        super();
        this.mActivity = mActivity;
        this.mBaiduMap = mBaiduMap;
        this.mHandle = mHandle;
        this.mHandleID = mHandleID;
        addMarkeClick();
    }

    public HashMap<String, String> getmUserListHideMap() {
        return mUserListHideMap;
    }

    public void setmUserListHideMap(HashMap<String, String> mUserListHideMap) {
        this.mUserListHideMap = mUserListHideMap;
    }

    /**
     * marker 点击事件监听，点击显示通话弹层
     */
    private void addMarkeClick() {
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                if (mMarkerClickmap.containsKey(marker)) {
                    VenuesUsersInfo mVenuesUsersInfom = mMarkerClickmap
                            .get(marker);
                    String uid = mVenuesUsersInfom.getUid();
                    if (!uid.equals(showUid) && mHandle != null) {
                        showUid = uid;
                        Message message = new Message();
                        message.what = mHandleID;
                        message.obj = mVenuesUsersInfom.getAlbum_url();
                        message.getData().putString("uid", uid);
                        mHandle.sendMessage(message);
                        message = null;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 场地用户marker
     */
    public void initMyOverlay(ArrayList<VenuesUsersInfo> mMapList) {
        try {
            this.mMapList = mMapList;
            removeNotShowMarker();
            clearBitmapDate();
            Log.e("mapsize", "mMapList.size   " + mMapList.size()
                    + "--mMarkermap_now.size--" + mMarkermap_now.size());
            if (mMapList != null && mMapList.size() != 0) {
                // 遍历显示
                mMarkermap_before.clear();
                for (int i = 0; i < mMapList.size(); i++) {
                    String Album_url = mMapList.get(i).getAlbum_url();
                    String is_anonymous = mMapList.get(i).getIs_anonymous();

                    /**
                     * 设置隐藏的用户不让显示marker
                     */
                    String uid = mMapList.get(i).getUid();
                    if (mUserListHideMap != null && mUserListHideMap.containsKey(uid)) {

                    } else {

                        if (!TextUtils.isEmpty(is_anonymous)
                                && is_anonymous.equals("1")) {
                            Album_url = "";// 匿名时不显示头像
                        }
                        if (!TextUtils.isEmpty(Album_url)) {
                            loadToBitmap(Album_url, mMapList.get(i));
                        } else {
                            Bitmap Roundbitmap = CommonUtils.getInstance()
                                    .readBitMap(mActivity, R.drawable.ic_launcher);
                            Roundbitmap = CommonUtils.getInstance().toRoundCorner(
                                    mActivity, Roundbitmap, 10);
                            mMapList.get(i).setRoundbitmap(Roundbitmap);
                            addMarker(mMapList.get(i));
                        }
                    }

                }

            } else {
            }
        } catch (Exception e) {
        }
    }

    /**
     * 移除无效的不显示用户
     */
    private void removeNotShowMarker() {
        if (mMarkermap_before.size() >= 0 && mMarkermap_now.size() > 0) {
            Iterator<Entry<String, Marker>> it = mMarkermap_now.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<String, Marker> entry = it.next();
                String userid = entry.getKey();
                if (!mMarkermap_before.containsKey(userid)) {
                    Marker mMarker = entry.getValue();
                    if (mMarker != null) {
                        mMarker.remove();
                        mMarker = null;
                    }
                    mMarkermap_now.remove(userid);
                }
            }
            //当用户消失时同步隐藏 通话弹层
            if (mHandle != null && !TextUtils.isEmpty(showUid) && !mMarkermap_before.containsKey(showUid)) {
                showUid = "";
                Message message = new Message();
                message.what = mHandleID;
                message.obj = "";
                message.getData().putString("uid", showUid);
                mHandle.sendMessage(message);
                message = null;
            }
        }
    }

    public void clearMap() {
        clearBitmapDate();
        mMarkermap_now.clear();
        mMarkermap_before.clear();
        showUid = "";
    }

    /**
     * 没有数据时清除所有
     */
    private void clearBitmapDate() {
        if (mBitmapDescriptormap.size() > 0) {
            Iterator<Entry<String, Bitmap>> itw = mBitmapDescriptormap
                    .entrySet().iterator();
            while (itw.hasNext()) {
                Map.Entry<String, Bitmap> entry = itw.next();
                Bitmap mMarker = entry.getValue();
                if (mMarker != null) {
                    mMarker.recycle();
                    mMarker = null;
                }
            }
            mBitmapDescriptormap.clear();
            mMarkerClickmap.clear();
        }
        ImageLoader.getInstance().clearMemoryCache();
    }

    @SuppressLint("NewApi")
    private Marker addMarker(VenuesUsersInfo mVenuesUsersInfom) {
        String userid = mVenuesUsersInfom.getUid();
        double latitude = mVenuesUsersInfom.getLatitude();
        double longitude = mVenuesUsersInfom.getLongitude();
        LatLng point = new LatLng(latitude, longitude);
        Bitmap bitmap = mVenuesUsersInfom.getRoundbitmap();
        View convertView = LayoutInflater.from(mActivity).inflate(
                R.layout.venues_map_marker, null);
        ImageView iv_head = (ImageView) convertView
                .findViewById(R.id.iv_head);
        iv_head.setImageBitmap(bitmap);
        ImageView iv_sportimg = (ImageView) convertView
                .findViewById(R.id.iv_sportimg);
        String sporttype = mVenuesUsersInfom.getCurrent_sports_type();
        if (!TextUtils.isEmpty(sporttype)) {
            iv_sportimg.setVisibility(View.VISIBLE);
            showSportPic(sporttype, iv_sportimg);
        } else {
            iv_sportimg.setVisibility(View.GONE);
        }
        TextView mTextView = (TextView) convertView
                .findViewById(R.id.tv_name);
        CommonUtils.getInstance().setTextViewTypeface(mActivity, mTextView);
        String name = mVenuesUsersInfom.getNick_name();
        String is_anonymous = mVenuesUsersInfom.getIs_anonymous();
        if (TextUtils.isEmpty(name)) {
            name = "     ";
        }
        if ((!TextUtils.isEmpty(is_anonymous) && is_anonymous.equals("1"))) {
            name = "匿名用户";// 匿名时不显示头像
        }
        mTextView.setText(name);
        Marker mMarker = null;
        // #####需要内存梳理######
        Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
        if (markerIcon != null) {
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                    .draggable(false);

            if (!mMarkermap_now.containsKey(userid)) {
                mMarker = (Marker) mBaiduMap.addMarker(ooA);
                mMarkermap_now.put(userid, mMarker);
            } else {
                mMarker = mMarkermap_now.get(userid);
                mMarker.setPosition(point);
                mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(markerIcon));
            }
            mMarkermap_before.put(userid, mMarker);
            mBitmapDescriptormap.put(userid, markerIcon);
            mMarkerClickmap.put(mMarker, mVenuesUsersInfom);
        }
        point = null;
        return mMarker;
    }

    private void showSportPic(String sportindex, ImageView iv_sportimg) {
        if (sportindex.equals("0")) {// 走路
            iv_sportimg.setBackgroundResource(R.drawable.head_walk_img);
        } else if (sportindex.equals("1")) {// 跑步
            iv_sportimg.setBackgroundResource(R.drawable.head_run_img);
        } else if (sportindex.equals("2")) {// 骑行
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_riding_img);
        } else if (sportindex.equals("13")) {// 单板滑雪
            iv_sportimg.setBackgroundResource(R.drawable.head_sky_img);
        } else if (sportindex.equals("14")) {// 双板滑雪
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_skytwo_img);
        } else if (sportindex.equals("5")) {// 越野跑
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_runyueye_img);
        } else if (sportindex.equals("6")) {// 山地车越野
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_driveyueye_img);
        } else if (sportindex.equals("7")) {// 轮滑
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_lunhua_img);
        } else if (sportindex.equals("8")) {// 摩托车
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_autobike_img);
        } else if (sportindex.equals("11")) {// 长板
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_longboard_img);
        } else if (sportindex.equals("10")) {// 骑马
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_horseride_img);
        } else if (sportindex.equals("16")) {// 帆板
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_windsurfing_img);
        } else if (sportindex.equals("17")) {// 风筝冲浪
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_kitesurfing_img);
        } else if (sportindex.equals("3")) {// 开车
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_drive_img);
        } else {
            iv_sportimg
                    .setBackgroundResource(R.drawable.head_other_img);
        }
    }


    public void loadToBitmap(String Album_url,
                             final VenuesUsersInfo mVenuesUsersInfom) {
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)
                // 设置图片加载或解码过程中发生错误显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true).build();
        if (mImageSize == null) {
            mImageSize = new ImageSize(100, 100);
        }
        ImageLoader.getInstance().loadImage(Album_url, mImageSize, options,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {

                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        if (arg2 != null) {
                            arg2 = CommonUtils.getInstance().toRoundCorner(
                                    mActivity, arg2, 10);
                            mVenuesUsersInfom.setRoundbitmap(arg2);
                            addMarker(mVenuesUsersInfom);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {

                    }
                });
    }

}
