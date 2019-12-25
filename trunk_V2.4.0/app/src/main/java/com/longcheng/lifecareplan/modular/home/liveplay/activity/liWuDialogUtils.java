package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.LiWuAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.LongClickButton;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.ArrayList;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 * **************************礼物弹层***********************************
 */

public class liWuDialogUtils {
    private MyDialog selectDialog;
    private ScrollLayout mScrollLayout;
    private PageControlView pageControl;
    private TextView tv_allskb;
    private TextView tv_num;

    private Handler mHandler;
    private int mHandlerID;
    private Activity context;
    private ArrayList<LiveDetailItemInfo> gift;
    private DataLoading dataLoad;

    private int selectMoneyPostion;
    private int selectpage;
    private String allskb = "0";
    private int num = 1;
    private String live_gift_id, skb;

    public ArrayList<LiveDetailItemInfo> getGift() {
        return gift;
    }

    public void setGift(ArrayList<LiveDetailItemInfo> gift) {
        this.gift = gift;
    }


    public liWuDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
        dataLoad = new DataLoading();
    }


    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_liveliwu);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_bg = (LinearLayout) selectDialog.findViewById(R.id.layout_bg);
            mScrollLayout = (ScrollLayout) selectDialog.findViewById(R.id.ScrollLayoutTest);
            pageControl = (PageControlView) selectDialog.findViewById(R.id.pageControl);
            tv_allskb = (TextView) selectDialog.findViewById(R.id.tv_allskb);
            TextView tv_line = (TextView) selectDialog.findViewById(R.id.tv_line);
            tv_line.getBackground().setAlpha(70);
            layout_bg.getBackground().setAlpha(100);
            TextView tv_handsel = (TextView) selectDialog.findViewById(R.id.tv_handsel);
            LongClickButton tv_lower = (LongClickButton) selectDialog.findViewById(R.id.tv_lower);
            tv_num = (TextView) selectDialog.findViewById(R.id.tv_num);
            LongClickButton tv_add = (LongClickButton) selectDialog.findViewById(R.id.tv_add);
            tv_lower.setOnClickListener(dialogClick);
            tv_add.setOnClickListener(dialogClick);
            tv_handsel.setOnClickListener(dialogClick);
            //连续减
            tv_lower.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    processClick(R.id.tv_lower);
                }
            }, 50);
            //连续加
            tv_add.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    processClick(R.id.tv_add);
                }
            }, 50);
        } else {
            selectDialog.show();
        }
        loadSport();
    }

    private View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            processClick(v.getId());
        }
    };

    /**
     * 处理事件
     *
     * @param id
     */
    private void processClick(int id) {
        switch (id) {
            case R.id.tv_handsel:
                selectDialog.dismiss();
                Message message = new Message();
                message.what = mHandlerID;
                message.arg1 = num;
                message.obj = live_gift_id;
                mHandler.sendMessage(message);
                message = null;
                break;
            case R.id.tv_add:
                if (num < 100) {
                    num++;
                } else {
                    ToastUtils.showToast("已到最大奉献倍数");
                }
                tv_num.setText("" + num);
                allskb = PriceUtils.getInstance().gteMultiplySumPrice("" + num, skb);
                tv_allskb.setText("" + allskb);
                break;
            case R.id.tv_lower:
                if (num > 1) {
                    num--;
                } else {
                    ToastUtils.showToast("已到最小奉献倍数");
                }
                tv_num.setText("" + num);
                allskb = PriceUtils.getInstance().gteMultiplySumPrice("" + num, skb);
                tv_allskb.setText("" + allskb);
                break;
            default:

                break;
        }
    }

    private void showSKB() {
        allskb = PriceUtils.getInstance().gteMultiplySumPrice("" + num, skb);
        tv_allskb.setText("" + allskb);
        Log.e("backSelectItem", "selectpage=" + selectpage
                + "  selectMoneyPostion=" + selectMoneyPostion
                + "  skb=" + skb
                + "  live_gift_id=" + live_gift_id);
    }

    /**
     * 加载运动类型
     */
    ArrayList<LiWuAdapter> mBestDoSportAdapterList;

    int APP_PAGE_SIZE = 6;

    private void loadSport() {
        /**
         * -----------控制显示个数----------
         */
        int realPageSize = gift.size();
        if (APP_PAGE_SIZE > realPageSize) {
            APP_PAGE_SIZE = realPageSize;
        }
        String pageString = PriceUtils.getInstance().gteDividePrice(realPageSize + "", APP_PAGE_SIZE + "");
        int pageNo = (int) Math.ceil(Double.parseDouble(pageString));
        System.out.println("pageNo=" + pageNo);
        System.out.println("Math.ceil(sportTypeList.size()/ APP_PAGE_SIZE)="
                + Math.ceil(realPageSize / APP_PAGE_SIZE));
        if (mScrollLayout.getChildCount() <= 0) {
            mBestDoSportAdapterList = new ArrayList<LiWuAdapter>();
            for (int page = 0; page < pageNo; page++) {
                MyGridView appPage = new MyGridView(context);
                appPage.setSelector(R.drawable.list_notselector);
                LiWuAdapter mMainSportAdapter2 = new LiWuAdapter(context,
                        gift, page, APP_PAGE_SIZE) {
                    @Override
                    public void backSelectItem(int selectpage_, int selectMoneyPostion_, String skb_, String live_gift_id_) {
                        selectpage = selectpage_;
                        selectMoneyPostion = selectMoneyPostion_;
                        skb = skb_;
                        live_gift_id = live_gift_id_;

                        for (LiWuAdapter mLiWuAdapter : mBestDoSportAdapterList) {
                            mLiWuAdapter.setSelectpage(selectpage_);
                            mLiWuAdapter.setSelectMoneyPostion(selectMoneyPostion_);
                            mLiWuAdapter.notifyDataSetChanged();
                        }
                        showSKB();
                    }
                };
                mMainSportAdapter2.setSelectMoneyPostion(selectMoneyPostion);
                mMainSportAdapter2.setSelectpage(selectpage);
                mBestDoSportAdapterList.add(mMainSportAdapter2);
                appPage.setAdapter(mMainSportAdapter2);
                String NumColumns = PriceUtils.getInstance().gteDividePrice(APP_PAGE_SIZE + "", 2 + "");
                int NumColumns_ = (int) Math.ceil(Double.parseDouble(NumColumns));
                appPage.setNumColumns(NumColumns_);
                appPage.setPadding(0, context.getResources().getDimensionPixelSize(R.dimen.dp15), 0,
                        0);
                appPage.setVerticalSpacing(3);
                mScrollLayout.addView(appPage);
            }
            // 加载分页
            pageControl.bindScrollViewGroup(mScrollLayout);
            // 加载分页数据
            dataLoad.bindScrollViewGroup(mScrollLayout);
        }

    }

    // 分页数据
    class DataLoading {
        private int count;

        public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {
            this.count = scrollViewGroup.getChildCount();
            scrollViewGroup.setOnScreenChangeListenerDataLoad(new ScrollLayout.OnScreenChangeListenerDataLoad() {
                public void onScreenChange(int currentIndex) {
                    // TODO Auto-generated method stub
                }
            });
        }

    }

}
