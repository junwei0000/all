package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CircleImageView;
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
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class userThumbShoaUtils {
    HashMap<Marker, VenuesUsersInfo> mMarkerClickmap = new HashMap<Marker, VenuesUsersInfo>();
    HashMap<String, Marker> mMarkermap = new HashMap<String, Marker>();
    HashMap<String, Bitmap> mBitmapDescriptormap = new HashMap<String, Bitmap>();
    private Context mActivity;
    private AMap mBaiduMap;

    /**
     * 已显示名字弹层的用户
     */
    private String showUid = "";
    ArrayList<VenuesUsersInfo> mMapList;
    private ImageSize mImageSize;

    public userThumbShoaUtils(Context mActivity, AMap mBaiduMap) {
        super();
        this.mActivity = mActivity;
        this.mBaiduMap = mBaiduMap;
        addMarkeClick();
    }

    /**
     * marker 点击事件监听
     */
    private void addMarkeClick() {
        /**
         * 设置Marker的监听
         */
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                if (mMarkerClickmap.containsKey(marker)) {
                    VenuesUsersInfo mVenuesUsersInfom = mMarkerClickmap
                            .get(marker);
                    String uid = mVenuesUsersInfom.getUid();
                    if (!uid.equals(showUid)) {
                        showUid = uid;
                        initMyOverlay(mMapList);
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
            clearDate();

            Log.e("mapsize", "mMapList.size   " + mMapList.size()
                    + "--mMapList.size--" + mMarkermap.size());
            if (mMapList != null && mMapList.size() != 0) {
                // 遍历显示
                for (int i = 0; i < mMapList.size(); i++) {
                    String Album_url = mMapList.get(i).getAlbum_url();
                    String is_anonymous = mMapList.get(i).getIs_anonymous();

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

            } else {
            }
        } catch (Exception e) {
        }
    }

    public void cleaiMap() {
        clearDate();
        mMarkermap.clear();
        mMarkerClickmap.clear();
        showUid = "";
    }

    /**
     * 没有数据时清除所有
     */
    private void clearDate() {
        if (mMarkermap.size() > 0) {
            Iterator<Entry<String, Marker>> it = mMarkermap.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<String, Marker> entry = it.next();
                Marker mMarker = entry.getValue();
                if (mMarker != null) {
                    mMarker.remove();
                    mMarker = null;
                }
            }
            Iterator<Entry<String, Bitmap>> itw = mBitmapDescriptormap
                    .entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Bitmap> entry = itw.next();
                Bitmap mMarker = entry.getValue();
                if (mMarker != null) {
                    mMarker.recycle();
                    mMarker = null;
                }
            }
            mBitmapDescriptormap.clear();
            mMarkerClickmap.clear();
            mMarkermap.clear();
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
        /**
         * 刷新显示昵称
         */
        if (!TextUtils.isEmpty(showUid) && showUid.equals(userid)) {
            TextView mTextView1 = (TextView) convertView
                    .findViewById(R.id.tv_name1);
            TextView mTextView = (TextView) convertView
                    .findViewById(R.id.tv_name);
            mTextView.setVisibility(View.VISIBLE);
            mTextView1.setVisibility(View.INVISIBLE);
            String name = mVenuesUsersInfom.getNick_name();
            String is_anonymous = mVenuesUsersInfom.getIs_anonymous();
            if (TextUtils.isEmpty(name)) {
                name = "     ";
            }
            if ((!TextUtils.isEmpty(is_anonymous) && is_anonymous.equals("1"))) {
                name = "匿名用户";// 匿名时不显示头像
            }
            mTextView1.setText(name);
            mTextView.setText(name);
        }
        Marker mMarker = null;
        // #####需要内存梳理######
        Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
        if (markerIcon != null) {
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                    .draggable(false);
            mMarker = (Marker) mBaiduMap.addMarker(ooA);
            if (!mMarkermap.containsKey(userid)) {
                mMarkermap.put(userid, mMarker);
                mBitmapDescriptormap.put(userid, markerIcon);
                mMarkerClickmap.put(mMarker, mVenuesUsersInfom);
            }
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
