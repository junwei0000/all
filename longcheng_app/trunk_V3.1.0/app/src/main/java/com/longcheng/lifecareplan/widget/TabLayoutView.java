package com.longcheng.lifecareplan.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/***
 * Gerry
 * 增强顶部导航栏滑动控件
 */
public class TabLayoutView extends FrameLayout {

    private TabLayout mTabLayout;
    private List<String> mTabList;
    private List<View> mCustomViewList;
    private int mSelectIndicatorColor;
    private int mSelectTextColor;
    private int mUnSelectTextColor;
    private int mIndicatorHeight;
    private int mIndicatorWidth;
    private int mTabMode;
    private int mTabTextSize;
    private Context context ;
    public TabLayoutView(@NonNull Context context) {
        super(context);
        this.context =context;
        init(context,null);
    }

    public TabLayoutView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        init(context,null);
    }

    public TabLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context =context;
        init(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabLayoutView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context =context;
    }

    private void init(Context context,AttributeSet attrs){
        readAttr(context,attrs);
        this.context =context;
        mTabList = new ArrayList<>();
        mCustomViewList = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.enhance_tab_layout,this,true);
        mTabLayout = view.findViewById(R.id.enhance_tab_view);
        initTabMinWidth(mTabLayout,4);
        // 添加属性
        mTabLayout.setTabMode(mTabMode == 4 ? TabLayout.MODE_FIXED:TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i=0;i<mTabLayout.getTabCount();i++){
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    if(view == null){
                        return;
                    }

                    TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);

                    view.invalidate();
                    if(i == tab.getPosition()){ // 选中状态
                        text.setTextColor(mSelectTextColor);
                        indicator.setBackgroundColor(mSelectIndicatorColor);
                        indicator.setVisibility(View.VISIBLE);
                    }else{// 未选中状态
                        text.setTextColor(mUnSelectTextColor);
                        indicator.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void initTabMinWidth(TabLayout mTabLayout,int num){
        final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";
        try {
            int tabMinWidth = DensityUtil.screenWith(context)/num;

            Field field;
            field =  TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(mTabLayout, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        int kuan = DensityUtil.screenWith(context)/num;
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTabLayout.getLayoutParams();
//        params.width = kuan;
//        mTabLayout.setLayoutParams(params);
    }

    private void readAttr(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TabLayoutView);
        mSelectIndicatorColor = typedArray.getColor(R.styleable.TabLayoutView_tabIndicatorColor,context.getResources().getColor(R.color.yellow_ff7119));
        mUnSelectTextColor =  typedArray.getColor(R.styleable.TabLayoutView_tabTextColor, Color.parseColor("#666666"));
        mSelectTextColor = typedArray.getColor(R.styleable.TabLayoutView_tabSelectTextColor,context.getResources().getColor(R.color.yellow_ff7119));
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.TabLayoutView_tabIndicatorHeight,1);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.TabLayoutView_tabIndicatorWidth,0);
        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.TabLayoutView_tabTextSize,13);
        mTabMode = typedArray.getInt(R.styleable.TabLayoutView_tab_Mode,2);
        typedArray.recycle();
    }

    public int getmTabMode() {
        return mTabMode;
    }

    public void setmTabMode(int mTabMode) {
        this.mTabMode = mTabMode;
    }

    public List<View> getCustomViewList(){
        return mCustomViewList;
    }

    public void addOnTabSelectedListener (TabLayout.OnTabSelectedListener onTabSelectedListener){
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 与TabLayout 联动
     * @param viewPager
     */
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        mTabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager,this));
    }




    /**
     * retrive TabLayout Instance
     * @return
     */
    public TabLayout getTabLayout(){
        return mTabLayout;
    }

    /**
     * 添加tab
     * @param tab
     */
    public void addTab(String tab){
        mTabList.add(tab);
        View customView = getTabView(getContext(),tab,mIndicatorWidth,mIndicatorHeight,mTabTextSize);
        mCustomViewList.add(customView);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView));
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener{

        private final ViewPager mViewPager;
        private final WeakReference<TabLayoutView> mTabLayoutRef;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager,TabLayoutView TabLayoutView) {
            mViewPager = viewPager;
            mTabLayoutRef = new WeakReference<TabLayoutView>(TabLayoutView);
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            TabLayoutView mTabLayout = mTabLayoutRef.get();
            if(mTabLayoutRef!=null){
                List<View> customViewList = mTabLayout.getCustomViewList();
                if(customViewList == null || customViewList.size() ==0){
                    return;
                }
                for (int i=0;i<customViewList.size();i++){
                    View view = customViewList.get(i);
                    if(view == null){
                        return;
                    }
                    TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    if(i == tab.getPosition()){ // 选中状态
                        text.setTextColor(mTabLayout.mSelectTextColor);
                        indicator.setBackgroundColor(mTabLayout.mSelectIndicatorColor);
                        indicator.setVisibility(View.VISIBLE);
                    }else{// 未选中状态
                        text.setTextColor(mTabLayout.mUnSelectTextColor);
                        indicator.setVisibility(View.INVISIBLE);
                    }
                }
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            // No-op
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            // No-op
        }
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param
     * @return
     */
    public static View getTabView(Context context,String text,int indicatorWidth,int indicatorHeight,int textSize) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        TextView tabText = (TextView) view.findViewById(R.id.tab_item_text);
        if(indicatorWidth>0){
            View indicator = view.findViewById(R.id.tab_item_indicator);
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            layoutParams.width  = indicatorWidth;
            layoutParams.height = indicatorHeight;
            indicator.setLayoutParams(layoutParams);
        }
        tabText.setTextSize(textSize);
        tabText.setText(text);
        return view;
    }

}
