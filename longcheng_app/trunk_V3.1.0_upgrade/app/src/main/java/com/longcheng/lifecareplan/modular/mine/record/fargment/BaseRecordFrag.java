package com.longcheng.lifecareplan.modular.mine.record.fargment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;
import com.openxu.cview.chart.barchart.BarVerticalChart;
import com.openxu.cview.chart.bean.BarBean;
import com.openxu.utils.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.toolsfinal.DateUtils;

public class BaseRecordFrag extends BaseListFrag<RecordContract.View, RecordPresenterImp<RecordContract.View>> implements RecordContract.View {

    boolean refreshStatus = false;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.date_scrollview)
    ScrollView dateScrollview;
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
    private static final String FRAGMENT_BODYGUARD = "frag_info_entity";
    private static final String FRAGMENT_BODYINT = "frag_info_type";
    @BindView(R.id.item_tv_up)
    TextView itemTvUp;
    @BindView(R.id.item_tv_fuqibao)
    TextView itemTvFuqibao;
    @BindView(R.id.item_tv_jieqibao)
    TextView itemTvJieqibao;
    @BindView(R.id.item_tv_zhuyoubao)
    TextView itemTvZhuyoubao;
    @BindView(R.id.item_tv_shoukang)
    TextView itemTvShoukang;
    @BindView(R.id.item_tv_time)
    TextView itemTvTime;
    @BindView(R.id.chart_view)
    BarVerticalChart chartView;

    private int ftype = -1;

    int[] colors = {R.color.ebe60c0c, R.color.e60c0c, R.color.color_e1a503, R.color.color_d93e3f, R.color.yellow_8d4c0c, R.color.yellow_ff7119, R.color.color_d93e3f, R.color.yellow_8d4c0c, R.color.yellow_ff7119};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            String list = bundle.getString(FRAGMENT_BODYGUARD);
            ftype = bundle.getInt(FRAGMENT_BODYINT, 0);
            LogUtils.v("TAG", "list" + list + "ftype" + ftype);
//            dataSet(list);

        }
    }

    public static BaseRecordFrag newInstance(String list, int ftype) {
        BaseRecordFrag fragment = new BaseRecordFrag();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_BODYGUARD, list);
        args.putInt(FRAGMENT_BODYINT, ftype);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showDividerTop(true);
        return view;
    }

    @Override
    public int bindLayout() {
        return R.layout.base_record_fragment_layout;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

    }

    @Override
    public void doBusiness(Context mContext) {
        //onActivityCreated  Activity UI 交互
        initView(ftype);
    }

    @Override
    public void widgetClick(View v) {
        //点击事件

    }

    @Override
    protected RecordPresenterImp<RecordContract.View> createPresent() {
        return new RecordPresenterImp<>(this);
    }

    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    private void initView(int type) {

        itemTvUp.setText(new StringBuffer().append("+").append(getNum(6000)));
        itemTvFuqibao.setText(new StringBuffer().append("+").append(getNum(1000)));
        itemTvJieqibao.setText(new StringBuffer().append("+").append(getNum(1000)));
        itemTvZhuyoubao.setText(new StringBuffer().append("+").append(getNum(1000)));
        itemTvShoukang.setText(new StringBuffer().append("+").append(getNum(1000)));
        //DateUtils.getDate()

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        itemTvTime.setText(simpleDateFormat.format(date)); //当前时间戳

        if (type == 0) {
            initChart();
        } else if (type == 1) {
            initChart2();
        } else {
            initChart3();
        }

    }

    private void initChart() {
        chartView.setLoading(true);
        chartView.setDebug(false);
        chartView.setBarNum(1);
        chartView.setBarColor(new int[]{Color.parseColor("#ff7119")});
        //X轴
        List<String> strXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> dataList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(getNum(100), "月增长量"));
            dataList.add(list);
            strXList.add((i + 1) + "月");
        }
        chartView.setLoading(false);
        chartView.setData(dataList, strXList);
    }

    private void initChart2() {
        chartView.setBarSpace(DensityUtil.dip2px(BaseRecordFrag.this.getContext(), 1));  //双柱间距
        chartView.setBarItemSpace(DensityUtil.dip2px(BaseRecordFrag.this.getContext(), 20));  //柱间距
        chartView.setLoading(true);
        chartView.setDebug(false);
        chartView.setBarNum(2); //一组柱子数量
        chartView.setBarColor(new int[]{Color.parseColor("#ff7119"), Color.parseColor("#ff2c2c")});
        //X轴
        List<String> strXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> dataList = new ArrayList<>();
        String[] weakday = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        for (int i = 0; i < 7; i++) {
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(getNum(300), "祈福宝"));
            list.add(new BarBean(getNum(500), "祝福宝"));
            dataList.add(list);
            strXList.add(weakday[i]);
        }
        chartView.setLoading(false);
        chartView.setData(dataList, strXList);
    }

    private void initChart3() {
        chartView.setBarSpace(DensityUtil.dip2px(BaseRecordFrag.this.getContext(), 1));  //双柱间距
        chartView.setBarItemSpace(DensityUtil.dip2px(BaseRecordFrag.this.getContext(), 20));  //柱间距
        chartView.setLoading(true);
        chartView.setDebug(false);
        chartView.setBarNum(4); //一组柱子数量
        chartView.setBarColor(new int[]{Color.parseColor("#ff7119"), Color.parseColor("#ff2c2c"),Color.parseColor("#ff7119"), Color.parseColor("#ff2c2c")});
        //X轴
        List<String> strXList = new ArrayList<>();
        //柱状图数据
        List<List<BarBean>> dataList = new ArrayList<>();
        String[] weakday = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        for (int i = 0; i < 7; i++) {
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(getNum(300), "福祺宝"));
            list.add(new BarBean(getNum(500), "节气包"));
            list.add(new BarBean(getNum(500), "祝佑宝"));
            list.add(new BarBean(getNum(1000), "寿康宝"));
            dataList.add(list);
            strXList.add(weakday[i]);
        }
        chartView.setLoading(false);
        chartView.setData(dataList, strXList);
    }

    public static int getNum(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

    @Override
    public void ListError() {
        if (layoutNotdate != null) {
//            if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
//                layoutNotdate.setVisibility(View.VISIBLE);
//            } else {
//                layoutNotdate.setVisibility(View.GONE);
//            }
        }
    }


}
