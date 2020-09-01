package com.longcheng.lifecareplan.utils.myview.address;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.domainname.activity.DomainMenuActivity;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.address.bean.SelctAeraDataInfo;
import com.longcheng.lifecareplan.utils.myview.address.bean.SelctAeraInfo;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PopuAddressUtils {
    Handler mHandler;
    int mHandlerID;
    Activity mContext;
    PopupWindow window;

    int level;//4 公社 ；5 大队
    GridView gv_top;
    ListView lv_date;

    public PopuAddressUtils(Activity mContext, Handler mHandler, int mHandlerID, int level) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
        this.level = level;
    }

    /**
     * 控件下方弹出窗口
     */
    public void showAddressPopupWindow(View view1, ImageView arrow) {
        //自定义布局，显示内容
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_address_select, null);
        LinearLayout layout_bottom = view.findViewById(R.id.layout_bottom);
        layout_bottom.getBackground().setAlpha(80);
        gv_top = view.findViewById(R.id.gv_top);
        if (level == DomainMenuActivity.level_commue) {
            gv_top.setNumColumns(4);
        }
        lv_date = view.findViewById(R.id.lv_date);
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        window.setTouchable(true);
        window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                //这里如果返回true的话，touch事件将被拦截
                //拦截后 PoppWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //（注意一下！！）如果不设置popupWindow的背景，无论是点击外部区域还是Back键都无法弹框
        window.setBackgroundDrawable(new BitmapDrawable());
        showAsDropDown(view1, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (arrow != null)
                    arrow.setBackgroundResource(R.mipmap.gongshe_jiantou1_icon);
            }
        });
        init();
    }

    /**
     * 华为手机7.0以上华为7.0上popwindow位置显示错乱，飘
     *
     * @param anchor
     * @param xoff
     * @param yoff
     */
    private void showAsDropDown(View anchor, int xoff, int yoff) {
        if (window != null && window.isShowing()) {
            window.dismiss();
            return;
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                window.setHeight(height);
                window.showAsDropDown(anchor, xoff, yoff);
            } else {
                window.showAsDropDown(anchor, xoff, yoff);
            }
        }
    }


    ArrayList<SelctAeraInfo> gvTopList = new ArrayList<>();
    ArrayList<SelctAeraInfo> datelist = new ArrayList<>();
    SearchTopAdapter mTopAdapter;
    int topSelctIndex;

    private void init() {
        gvTopList.clear();
        datelist.clear();
        if (gvTopList.size() == 0) {
            gvTopList.add(new SelctAeraInfo(1, "请选择"));
        }
        topSelctIndex = gvTopList.size() - 1;
        mTopAdapter = new SearchTopAdapter(mContext, gvTopList);
        mTopAdapter.setTopSelctIndex(topSelctIndex);
        gv_top.setAdapter(mTopAdapter);
        getCityList(1);
        gv_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int parent_id = gvTopList.get(position).getParent_id();
                getCityList(parent_id);
                topSelctIndex = position;

                //
                int size = gvTopList.size();
                for (int i = topSelctIndex; i < size; i++) {
                    Log.e("onItemClick", "" + size);
                    gvTopList.remove(gvTopList.size()-1);
                }
                Log.e("onItemClick", "gvTopList.size()=" + gvTopList.size());
                if (topSelctIndex == 0) {
                    gvTopList.add(new SelctAeraInfo(1, "请选择"));
                } else {
                    gvTopList.add(new SelctAeraInfo(parent_id, "请选择"));
                }

                mTopAdapter.setTopSelctIndex(topSelctIndex);
                mTopAdapter.refreshListView(gvTopList);
            }
        });
        lv_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gvTopList.add(gvTopList.size() - 1, datelist.get(position));
                gvTopList.get(gvTopList.size() - 1).setParent_id(datelist.get(position).getCommune_id());
                topSelctIndex = gvTopList.size() - 1;
                mTopAdapter.setTopSelctIndex(topSelctIndex);
                mTopAdapter.refreshListView(gvTopList);
                if (level == DomainMenuActivity.level_commue) {
                    if (gvTopList.size() == 5) {
                        BackHandle(position);
                    } else {
                        getCityList(datelist.get(position).getCommune_id());
                    }
                } else {
                    if (gvTopList.size() == 6) {
                        BackHandle(position);
                    } else {
                        getCityList(datelist.get(position).getCommune_id());
                    }
                }

            }
        });
    }

    private void BackHandle(int position) {
        gvTopList.remove(gvTopList.size() - 1);
        String area = "";
        for (SelctAeraInfo mSelctAeraInfo : gvTopList) {
            area = area + mSelctAeraInfo.getName();
        }
        window.dismiss();
        Message message = new Message();
        message.what = mHandlerID;
        Bundle bundle = new Bundle();
        bundle.putString("commune_id", "" + datelist.get(position).getCommune_id());
        bundle.putString("short_name", datelist.get(position).getShort_name());
        bundle.putString("area", area);
        message.setData(bundle);
        mHandler.sendMessage(message);
        message = null;
    }

    public void getCityList(int parent_id) {
        Observable<SelctAeraDataInfo> observable = Api.getInstance().service.getCityList(
                UserUtils.getUserId(mContext), parent_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SelctAeraDataInfo>() {
                    @Override
                    public void accept(SelctAeraDataInfo responseBean) throws Exception {
                        String status_ = responseBean.getStatus();
                        if (status_.equals("200")) {
                            datelist = responseBean.getData();
                            SearchAeraListAdapter mSearchAeraListAdapter = new SearchAeraListAdapter(mContext, datelist, gvTopList.get(topSelctIndex).getName());
                            lv_date.setAdapter(mSearchAeraListAdapter);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }


}
