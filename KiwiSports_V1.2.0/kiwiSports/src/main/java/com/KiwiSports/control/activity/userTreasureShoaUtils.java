package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesTreasInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.PriceUtils;
import com.amap.api.maps.AMap;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class userTreasureShoaUtils {
    HashMap<String, Marker> mMarkermap = new HashMap<String, Marker>();
    HashMap<String, Bitmap> mBitmapDescriptormap = new HashMap<String, Bitmap>();

    private Context mActivity;
    AMap mBaiduMap;

    public userTreasureShoaUtils(Context mActivity, AMap mBaiduMap) {
        super();
        this.mActivity = mActivity;
        this.mBaiduMap = mBaiduMap;
    }

    /**
     * 场地宝贝marker
     */
    public void initMyOverlayTreasure(
            ArrayList<VenuesTreasInfo> mMapTreasureList) {
        try {
            clearDate();

            Log.e("map", "mMarkermap.size  statietteitie-----------  "
                    + mMarkermap.size());
            if (mMapTreasureList != null && mMapTreasureList.size() != 0) {
                // 遍历显示
                for (int i = 0; i < mMapTreasureList.size(); i++) {
                    String type = mMapTreasureList.get(i).getType();
                    if (type.equals("4")) {
                        //航标
                        addMarkerBuoy(mMapTreasureList.get(i));
                    } else {
                        //红包
                        String is_receive = mMapTreasureList.get(i).getIs_receive();
                        if (is_receive.equals("0")) {// 未领取
                            addMarkerNotReceived(mMapTreasureList.get(i));
                        } else {
                            String thumb = mMapTreasureList.get(i).getThumb();
                            if (!TextUtils.isEmpty(thumb)) {
                                loadToBitmap(thumb, mMapTreasureList.get(i));
                            } else {
                                // #####需要内存梳理######
                                Bitmap Roundbitmap = CommonUtils.getInstance()
                                        .readBitMap(mActivity,
                                                R.drawable.map_treasurered_img);
                                mMapTreasureList.get(i).setRoundbitmap(Roundbitmap);
                                addMarkerReceived(mMapTreasureList.get(i));
                            }
                        }

                    }
                }

            } else {
            }
        } catch (Exception e) {
        }
    }

    public void cleaiMap() {
        mMarkermap.clear();
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
            mMarkermap.clear();
        }
        ImageLoader.getInstance().clearMemoryCache();
    }


    /**
     * 航标
     *
     * @param mVenuesUsersInfom
     * @return
     */
    private Marker addMarkerBuoy(VenuesTreasInfo mVenuesUsersInfom) {
        String Reward_item_id = mVenuesUsersInfom.getReward_item_id();
        double latitude = mVenuesUsersInfom.getLatitude();
        double longitude = mVenuesUsersInfom.getLongitude();
        LatLng point = new LatLng(latitude, longitude);
        View convertView = LayoutInflater.from(mActivity).inflate(
                R.layout.venues_map_markerbuoy, null);
        TextView tv_sequence=convertView.findViewById(R.id.tv_sequence);
        tv_sequence.setText(mVenuesUsersInfom.getSequence());
        // #####需要内存梳理######
        Marker mMarker = null;
        Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
        if (markerIcon != null) {
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                    .draggable(false);
            mMarker = (Marker) mBaiduMap.addMarker(ooA);
            if (!mMarkermap.containsKey(Reward_item_id)) {
                mMarkermap.put(Reward_item_id, mMarker);
                mBitmapDescriptormap.put(Reward_item_id, markerIcon);
            }
        }
        point = null;
        return mMarker;
    }

    /**
     * 已领取的宝贝marker
     *
     * @param mVenuesUsersInfom
     * @return
     */
    @SuppressLint("NewApi")
    private Marker addMarkerReceived(VenuesTreasInfo mVenuesUsersInfom) {
        String Reward_item_id = mVenuesUsersInfom.getReward_item_id();
        double latitude = mVenuesUsersInfom.getLatitude();
        double longitude = mVenuesUsersInfom.getLongitude();
        LatLng point = new LatLng(latitude, longitude);
        Bitmap bitmap = mVenuesUsersInfom.getRoundbitmap();
        String type = mVenuesUsersInfom.getType();
        String name = mVenuesUsersInfom.getNick_name();
        String money = mVenuesUsersInfom.getMoney();
        String receive_time = mVenuesUsersInfom.getReceive_time();
        receive_time = DatesUtils.getInstance().getDateGeShi(receive_time,
                "yyyy-MM-dd HH:mm:ss", "HH:mm");
        money = PriceUtils.getInstance().gteDividePrice(money, "100");
        View convertView = LayoutInflater.from(mActivity).inflate(
                R.layout.venues_map_markertreasure, null);
        CircleImageView iv_head = (CircleImageView) convertView
                .findViewById(R.id.iv_head);
        TextView treasure_tv_money = (TextView) convertView
                .findViewById(R.id.treasure_tv_money);
        TextView treasure_tv_cont = (TextView) convertView
                .findViewById(R.id.treasure_tv_cont);
        TextView treasure_tv_contzuo = (TextView) convertView
                .findViewById(R.id.treasure_tv_contzuo);
        treasure_tv_money.setText(money + "元");
        if (type.equals("3")) {
            treasure_tv_money.setVisibility(View.GONE);
        }
        treasure_tv_contzuo.setText(receive_time + "被" + name + "抢到");
        treasure_tv_cont.setText(receive_time + "被" + name + "抢到");
        iv_head.setImageBitmap(bitmap);
        // #####需要内存梳理######
        Marker mMarker = null;
        Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
        if (markerIcon != null) {
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                    .draggable(false);
            mMarker = (Marker) mBaiduMap.addMarker(ooA);
            if (!mMarkermap.containsKey(Reward_item_id)) {
                mMarkermap.put(Reward_item_id, mMarker);
                mBitmapDescriptormap.put(Reward_item_id, markerIcon);
            }
        }
        point = null;
        return mMarker;
    }


    /**
     * 未领取的宝贝marker
     *
     * @param mVenuesUsersInfom
     * @return
     */
    private Marker addMarkerNotReceived(VenuesTreasInfo mVenuesUsersInfom) {
        String Reward_item_id = mVenuesUsersInfom.getReward_item_id();
        double latitude = mVenuesUsersInfom.getLatitude();
        double longitude = mVenuesUsersInfom.getLongitude();
        LatLng point = new LatLng(latitude, longitude);
        View convertView = LayoutInflater.from(mActivity).inflate(
                R.layout.venues_map_markertreasurenot, null);
        // #####需要内存梳理######
        Marker mMarker = null;
        Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
        if (markerIcon != null) {
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                    .draggable(false);
            mMarker = (Marker) mBaiduMap.addMarker(ooA);
            if (!mMarkermap.containsKey(Reward_item_id)) {
                mMarkermap.put(Reward_item_id, mMarker);
                mBitmapDescriptormap.put(Reward_item_id, markerIcon);
            }
        }
        point = null;
        return mMarker;
    }

    ImageSize mImageSize;

    public void loadToBitmap(String Album_url,
                             final VenuesTreasInfo mVenuesUsersInfom) {
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.map_treasurered_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.map_treasurered_img)
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
                            mVenuesUsersInfom.setRoundbitmap(arg2);
                            addMarkerReceived(mVenuesUsersInfom);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {

                    }
                });
    }

}
