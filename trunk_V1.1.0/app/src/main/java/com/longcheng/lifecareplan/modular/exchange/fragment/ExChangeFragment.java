package com.longcheng.lifecareplan.modular.exchange.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.exchange.adapter.CategorysGAdapter;
import com.longcheng.lifecareplan.modular.exchange.adapter.CategorysLAdapter;
import com.longcheng.lifecareplan.modular.exchange.adapter.GoodsListAdapter;
import com.longcheng.lifecareplan.modular.exchange.adapter.JieQiAdapter;
import com.longcheng.lifecareplan.modular.exchange.bean.GoodsAfterBean;
import com.longcheng.lifecareplan.modular.exchange.bean.GoodsItemBean;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiAfterBean;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiItemBean;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.lifecareplan.modular.exchange.bean.MallGoodsListDataBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.exchange.shopcart.activity.ShopCartActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：兑换页面
 */

public class ExChangeFragment extends BaseFragmentMVP<ExChangeContract.View, ExChangePresenterImp<ExChangeContract.View>> implements ExChangeContract.View {

    @BindView(R.id.exchange_layout_select)
    LinearLayout exchange_layout_select;
    @BindView(R.id.exchange_et_search)
    SupplierEditText exchangeEtSearch;
    @BindView(R.id.exchange_layout_shopping)
    LinearLayout exchange_layout_shopping;

    @BindView(R.id.exchange_tv_time)
    TextView exchangeTvTime;
    @BindView(R.id.exchange_iv_timearrow)
    ImageView exchangeIvTimearrow;
    @BindView(R.id.exchange_layout_time)
    LinearLayout exchangeLayoutTime;

    @BindView(R.id.exchange_tv_price)
    TextView exchangeTvPrice;
    @BindView(R.id.exchange_iv_pricearrow)
    ImageView exchangeIvPricearrow;
    @BindView(R.id.exchange_layout_price)
    LinearLayout exchangeLayoutPrice;

    @BindView(R.id.exchange_tv_popularity)
    TextView exchangeTvPopularity;
    @BindView(R.id.exchange_iv_popularityarrow)
    ImageView exchangeIvPopularityarrow;
    @BindView(R.id.exchange_layout_popularity)
    LinearLayout exchangeLayoutPopularity;

    @BindView(R.id.exchange_tv_selectjieqi)
    TextView exchangeTvSelectjieqi;
    @BindView(R.id.exchange_layout_selectjieqi)
    LinearLayout exchangeLayoutSelectjieqi;

    @BindView(R.id.layout_select)
    LinearLayout layoutSelect;
    @BindView(R.id.exchange_listview)
    MyGridView exchangeListview;
    @BindView(R.id.exchange_sv)
    PullToRefreshScrollView exchange_sv;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.layout_footercontent)
    LinearLayout layout_footercontent;


    @BindView(R.id.layout_page)
    LinearLayout layout_page;
    @BindView(R.id.tv_showScrollPage)
    TextView tv_showScrollPage;
    @BindView(R.id.tv_allPage)
    TextView tv_allPage;
    @BindView(R.id.tv_topline)
    TextView tv_topline;


    @BindView(R.id.layout_categorys)
    LinearLayout layout_categorys;
    @BindView(R.id.categorys_gv)
    GridView categorys_gv;
    @BindView(R.id.categorys_listview)
    ListView categorys_listview;

    //--------------------------
    private List<GoodsItemBean> mGoodsAllList = new ArrayList<>();
    private List<JieQiItemBean> jieQiList = new ArrayList<>();
    private String current_solar_en = "";
    private String current_solar_cn = "";
    private List<JieQiItemBean> CategorysList = new ArrayList<>();
    private String user_id;
    private int category = 0;//分类ID
    private int time_sort = 2;//1-升序 2-降序
    private int price_sort = 0;//1-升序 2-降序
    private int hot_sort = 0;//1-升序 2-降序
    private int solar_terms = 0;//节气ID
    private String searchCont = "";
    private String solar_terms_name = "24节气";
    private int page = 0;
    private int pageSize = 10;
    private int allPage;

    @Override
    public int bindLayout() {
        return R.layout.fragment_exchange;
    }

    @Override
    public void initView(View view) {
        ScrowUtil.ScrollViewConfigAll(exchange_sv);
        showNoMoreData(false);
        notDateCont.setText("找不到搜索的内容噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.not_searched_img);
        getList(1);
        mPresent.getJieQiList(user_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BottomMenuActivity.position == BottomMenuActivity.tab_position_exchange && !toDetailBackStatus) {
            initLoad(solar_terms, solar_terms_name);
        }
    }

    /**
     * 跳转详情回来不刷新
     */
    boolean toDetailBackStatus = false;

    public void initLoad(int solar_terms_id, String solar_terms_name_) {
        if (solar_terms_id != solar_terms && !solar_terms_name.equals(solar_terms_name_)) {
            solar_terms = solar_terms_id;
            solar_terms_name = solar_terms_name_;
        }
        exchangeTvSelectjieqi.setText(solar_terms_name);
        getList(1);
        mPresent.getJieQiList(user_id);
    }

    private void setFocuse() {
        if (page == 1) {
            exchange_sv.post(
                    new Runnable() {
                        public void run() {
                            /**
                             * 从本质上来讲，pulltorefreshscrollview 是 LinearLayout，那么要想让它能滚动到顶部，我们就需要将它转为 ScrollView
                             */
                            ScrollView scrollview = exchange_sv.getRefreshableView();
                            scrollview.smoothScrollTo(0, 0);
                        }
                    });
        }
    }

    /**
     * http://test.t.asdyf.com/api/v1_1_0/shop/index?user_id=942&token=d3ac1d359e1d2720647
     * 28971545170d4&category=32&time_sort=0&price_sort=2&hot_sort=0&solar_terms=7&search=
     *
     * @param page
     */
    private void getList(int page) {
        layout_categorys.setVisibility(View.GONE);
        user_id = UserUtils.getUserId(mContext);
        mPresent.getGoodsList(user_id, category, time_sort, price_sort,
                hot_sort, solar_terms, searchCont, page, pageSize);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doBusiness(Context mContext) {
        layout_page.getBackground().setAlpha(90);
        tv_topline.getBackground().setAlpha(90);
        exchange_layout_select.setOnClickListener(this);
        exchange_layout_shopping.setOnClickListener(this);
        layout_categorys.setOnClickListener(this);
        exchangeLayoutTime.setOnClickListener(this);
        exchangeLayoutPrice.setOnClickListener(this);
        exchangeLayoutPopularity.setOnClickListener(this);
        exchangeLayoutSelectjieqi.setOnClickListener(this);
        exchange_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getList(page + 1);
            }
        });
        exchangeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGoodsAllList != null && mGoodsAllList.size() > 0) {
                    toDetailBackStatus = true;
                    ConfigUtils.getINSTANCE().closeSoftInput(getActivity());
                    Intent intent = new Intent(mContext, MallDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("shop_goods_id", mGoodsAllList.get(position).getShop_goods_id());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            }
        });
        exchangeEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    ConfigUtils.getINSTANCE().closeSoftInput(getActivity());
                    searchCont = exchangeEtSearch.getText().toString();
                    if (TextUtils.isEmpty(searchCont)) {
                        return true;
                    }
                    getList(1);
                    return true;
                }
                return false;
            }
        });
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(exchangeEtSearch, 40);
        exchangeEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (TextUtils.isEmpty(exchangeEtSearch.getText())) {
                    searchCont = exchangeEtSearch.getText().toString();
                    ConfigUtils.getINSTANCE().closeSoftInput(getActivity());
                    getList(1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ScrollView mScrollView = exchange_sv.getRefreshableView();
        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (mGoodsAllList != null && mGoodsAllList.size() > 0 && exchangeListview != null) {
                    View view = exchangeListview.getChildAt(0);
                    if (view == null || tv_showScrollPage == null) {
                        return;
                    }
                    int itemHeight = view.getMeasuredHeight();
                    int showScrollPage = oldScrollY / (itemHeight * (pageSize / 2));
                    showScrollPage = showScrollPage + 1;
                    if (showScrollPage > allPage) {
                        showScrollPage = allPage;
                    }
                    tv_showScrollPage.setText("" + showScrollPage);
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(getActivity());
        switch (v.getId()) {
            case R.id.layout_categorys:
                break;
            case R.id.exchange_layout_select:
                ConfigUtils.getINSTANCE().closeSoftInput(getActivity());
                if (layout_categorys.getVisibility() == View.GONE) {
                    if (CategorysList != null && CategorysList.size() > 0) {
                        layout_categorys.setVisibility(View.VISIBLE);
                        slectCategorys();
                    } else {
                        mPresent.getJieQiList(user_id);
                    }
                } else {
                    layout_categorys.setVisibility(View.GONE);
                }
                break;
            case R.id.exchange_layout_shopping:
                layout_categorys.setVisibility(View.GONE);
                Intent intent = new Intent(mContext, ShopCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.exchange_layout_time:
                initSelectView("time_sort");
                break;
            case R.id.exchange_layout_price:
                initSelectView("price_sort");
                break;
            case R.id.exchange_layout_popularity:
                initSelectView("hot_sort");
                break;
            case R.id.exchange_layout_selectjieqi:
                if (jieQiList != null && jieQiList.size() > 0) {
                    showJieQiPopupWindow();
                } else {
                    mPresent.getJieQiList(user_id);
                }
                break;
        }
    }

    /**
     * 切换 ：时间、价格、人气 ；选中状态
     */
    private void initSelectView(String selectStatus) {
        int colorid = R.color.text_biaoTi_color;
        int mipmapid = R.mipmap.ic_arrow_gray;
        exchangeTvTime.setTextColor(getResources().getColor(colorid));
        exchangeIvTimearrow.setBackgroundResource(mipmapid);
        exchangeTvPrice.setTextColor(getResources().getColor(colorid));
        exchangeIvPricearrow.setBackgroundResource(mipmapid);
        exchangeTvPopularity.setTextColor(getResources().getColor(colorid));
        exchangeIvPopularityarrow.setBackgroundResource(mipmapid);
        if (selectStatus.equals("time_sort")) {
            if (time_sort != 0) {//当前选中状态
                if (time_sort == 1) {
                    time_sort = 2;
                    mipmapid = R.mipmap.ic_arrow_up_red;
                } else {
                    time_sort = 1;
                    mipmapid = R.mipmap.ic_arrow_down_red;
                }
            } else {
                time_sort = 2;
                mipmapid = R.mipmap.ic_arrow_up_red;
            }
            price_sort = 0;
            hot_sort = 0;
            colorid = R.color.blue;
            exchangeTvTime.setTextColor(getResources().getColor(colorid));
            exchangeIvTimearrow.setBackgroundResource(mipmapid);
        } else if (selectStatus.equals("price_sort")) {
            if (price_sort != 0) {//当前选中状态
                if (price_sort == 1) {
                    price_sort = 2;
                    mipmapid = R.mipmap.ic_arrow_down_red;
                } else {
                    price_sort = 1;
                    mipmapid = R.mipmap.ic_arrow_up_red;
                }
            } else {
                price_sort = 1;
                mipmapid = R.mipmap.ic_arrow_up_red;
            }
            time_sort = 0;
            hot_sort = 0;
            colorid = R.color.blue;
            exchangeTvPrice.setTextColor(getResources().getColor(colorid));
            exchangeIvPricearrow.setBackgroundResource(mipmapid);
        } else if (selectStatus.equals("hot_sort")) {
            if (hot_sort != 0) {//当前选中状态
                if (hot_sort == 1) {
                    hot_sort = 2;
                    mipmapid = R.mipmap.ic_arrow_down_red;
                } else {
                    hot_sort = 1;
                    mipmapid = R.mipmap.ic_arrow_up_red;
                }
            } else {
                hot_sort = 1;
                mipmapid = R.mipmap.ic_arrow_up_red;
            }
            time_sort = 0;
            price_sort = 0;
            colorid = R.color.blue;
            exchangeTvPopularity.setTextColor(getResources().getColor(colorid));
            exchangeIvPopularityarrow.setBackgroundResource(mipmapid);
        }
        getList(1);
    }

    @Override
    protected ExChangePresenterImp<ExChangeContract.View> createPresent() {
        return new ExChangePresenterImp<>(this);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void ListSuccess(MallGoodsListDataBean responseBean, int backPage) {
        RefreshComplete();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            GoodsAfterBean mGoodsAfterBean = responseBean.getData();
            if (mGoodsAfterBean != null) {
                int count = mGoodsAfterBean.getCount();
                allPage = count % pageSize == 0 ? count / pageSize : (count / pageSize + 1);
                List<GoodsItemBean> mGoodsList = mGoodsAfterBean.getGoods_list();
                int size = mGoodsList == null ? 0 : mGoodsList.size();
                if (backPage == 1) {
                    mGoodsAllList.clear();
                    mAdapter = null;
                    showNoMoreData(false);
                    layout_categorys.setVisibility(View.GONE);
                    if (size == 0) {
                        layout_page.setVisibility(View.GONE);
                    } else {
                        layout_page.setVisibility(View.VISIBLE);
                    }
                    tv_showScrollPage.setText("1");
                    tv_allPage.setText("" + allPage);
                }
                if (size > 0) {
                    mGoodsAllList.addAll(mGoodsList);
                }
                if (mAdapter == null) {
                    mAdapter = new GoodsListAdapter(mContext, mGoodsList);
                    exchangeListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(mGoodsList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        }
    }

    @Override
    public void JieQiListSuccess(JieQiListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("200")) {
            JieQiAfterBean mJieQiAfterBean = responseBean.getData();
            if (mJieQiAfterBean != null) {
                List<JieQiItemBean> jieQiList_ = mJieQiAfterBean.getSolar();
                List<JieQiItemBean> CategorysList_ = mJieQiAfterBean.getCategorys();
                if (jieQiList_ != null && jieQiList_.size() > 0) {
                    jieQiList.clear();
                    jieQiList.add(new JieQiItemBean(0, "全部"));
                    jieQiList.addAll(jieQiList_);
                }
                if (CategorysList_ != null && CategorysList_.size() > 0) {
                    CategorysList.clear();
                    CategorysList.add(new JieQiItemBean("全部", 0));
                    CategorysList.addAll(CategorysList_);
                }
                JieQiItemBean current_solar = mJieQiAfterBean.getCurrent_solar();
                if (current_solar != null) {
                    current_solar_en = current_solar.getCurrent_solar_en();
                    current_solar_cn = current_solar.getCurrent_solar_cn();
                }
            }
        }
    }

    @Override
    public void ListError() {
        RefreshComplete();
        checkLoadOver(0);
    }

    private void checkLoadOver(int size) {
        Log.e("checkLoadOver", "" + pageSize + "  " + size);
        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(exchange_sv);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true);
            }
        } else {
            setFocuse();
            ScrowUtil.ScrollViewConfigAll(exchange_sv);
        }
    }

    /**
     * @param flag true:显示footer；false 不显示footer
     */
    public void showNoMoreData(boolean flag) {
        if (layout_footercontent != null) {
            if (flag) {
                layout_footercontent.setVisibility(View.VISIBLE);
            } else {
                layout_footercontent.setVisibility(View.GONE);
            }
        }
    }

    private void RefreshComplete() {
        toDetailBackStatus = false;
        ListUtils.getInstance().RefreshCompleteS(exchange_sv);
    }

    int selectPoistion = 1;//1级菜单
    int selectCatGCid = -1;//2级菜单
    List<JieQiItemBean> ChildList;

    private void slectCategorys() {
        CategorysLAdapter mCategorysLAdapter = new CategorysLAdapter(mContext, CategorysList, selectPoistion);
        categorys_listview.setAdapter(mCategorysLAdapter);

        ChildList = CategorysList.get(selectPoistion).getChild();
        CategorysGAdapter mCategorysGAdapter = new CategorysGAdapter(mContext, ChildList, selectCatGCid);
        categorys_gv.setAdapter(mCategorysGAdapter);

        categorys_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CategorysList != null && CategorysList.size() > 0) {
                    if (position == 0) {
                        //选择“全部”有一个显示效果
                        selectPoistion = 0;
                        mCategorysLAdapter.setSelectPoistion(selectPoistion);
                        mCategorysLAdapter.notifyDataSetChanged();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 *要执行的操作
                                 */
                                selectPoistion = 1;
                                selectCatGCid = -1;
                                category = 0;
                                getList(1);
                                layout_categorys.setVisibility(View.GONE);
                            }
                        }, 100);//秒后执行Runnable中的run方法
                    } else {
                        selectPoistion = position;
                        mCategorysLAdapter.setSelectPoistion(selectPoistion);
                        mCategorysLAdapter.notifyDataSetChanged();

                        ChildList = CategorysList.get(position).getChild();
                        mCategorysGAdapter.refreshListView(ChildList);
                    }
                }
            }
        });
        categorys_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ChildList != null && ChildList.size() > 0) {
                    category = ChildList.get(position).getCid();
                    selectCatGCid = category;
                    mCategorysGAdapter.setSelectCatGCid(selectCatGCid);
                    mCategorysGAdapter.notifyDataSetChanged();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
                            getList(1);
                            layout_categorys.setVisibility(View.GONE);
                        }
                    }, 100);//秒后执行Runnable中的run方法
                }
            }
        });
    }

    GoodsListAdapter mAdapter;
    MyDialog selectDialog;

    private void showJieQiPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_mallgoods_jieqi);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.RIGHT);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            selectDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            p.height = d.getHeight();
            selectDialog.getWindow().setAttributes(p); //设置生效
            GridView jieqi_gv = (GridView) selectDialog.findViewById(R.id.jieqi_gv);
            TextView tv_currentjieqi = (TextView) selectDialog.findViewById(R.id.tv_currentjieqi);
            tv_currentjieqi.setText("当前节气：" + current_solar_cn);
            JieQiAdapter mJieQiAdapter = new JieQiAdapter(mContext, jieQiList, current_solar_en);
            jieqi_gv.setAdapter(mJieQiAdapter);
            jieqi_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (jieQiList != null && jieQiList.size() > 0) {
                        solar_terms = jieQiList.get(position).getSolar_terms_id();
                        if (solar_terms == 0) {
                            solar_terms_name = "24节气";
                        } else {
                            solar_terms_name = jieQiList.get(position).getSolar_terms_name();
                        }
                        BottomMenuActivity.solar_terms_id = solar_terms;
                        BottomMenuActivity.solar_terms_name = solar_terms_name;
                        exchangeTvSelectjieqi.setText(solar_terms_name);
                        getList(1);
                        selectDialog.dismiss();
                    }
                }
            });
        } else {
            selectDialog.show();
        }
    }
}
