package com.longcheng.lifecareplan.modular.exchange.malldetail.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.malldetail.adapter.GoodsImgAdapter;
import com.longcheng.lifecareplan.modular.exchange.malldetail.adapter.GuiGeDetailAdapter;
import com.longcheng.lifecareplan.modular.exchange.malldetail.adapter.JieQiDetailAdapter;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.GoodsDetailDataBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.activity.ShopCartActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity.LifeStyleApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 商品-详情
 */
public class MallDetailActivity extends BaseActivityMVP<MallDetailContract.View, MallDetailPresenterImp<MallDetailContract.View>> implements MallDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.vp_img)
    ViewPager vpImg;
    @BindView(R.id.tv_imgnum)
    TextView tvImgnum;
    @BindView(R.id.iv_thelabel)
    ImageView ivThelabel;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_skb)
    TextView tvSkb;
    @BindView(R.id.tv_shopnum)
    TextView tvShopnum;
    @BindView(R.id.exchangedetail_sv)
    MyScrollView exchangedetailSv;
    @BindView(R.id.tv_goodsnum)
    TextView tvGoodsnum;
    @BindView(R.id.tv_addShoppingCart)
    TextView tvAddShoppingCart;
    @BindView(R.id.tv_tohelp)
    TextView tv_tohelp;
    @BindView(R.id.relat_img)
    RelativeLayout relat_img;
    @BindView(R.id.layout_jieqi)
    LinearLayout layout_jieqi;
    @BindView(R.id.jieqi_gv)
    MyGridView jieqi_gv;

    @BindView(R.id.layout_merchant)
    LinearLayout layout_merchant;
    @BindView(R.id.iv_merchanticon)
    ImageView iv_merchanticon;
    @BindView(R.id.tv_merchantname)
    TextView tv_merchantname;

    @BindView(R.id.layout_guige)
    LinearLayout layout_guige;
    @BindView(R.id.guige_gv)
    MyGridView guige_gv;
    @BindView(R.id.tv_showComplexH5Text)
    BridgeWebView tv_showComplexH5Text;
    @BindView(R.id.tv_backtop)
    TextView tv_backtop;
    @BindView(R.id.frame_shopcart)
    FrameLayout frame_shopcart;


    private String user_id, shop_goods_id;
    private String shop_goods_price_id = "";
    private String help_goods_id;
    private Map<String, DetailItemBean> ShoppingCartMap = new HashMap<>();
    private ShareUtils mShareUtils;
    private String shareUrl;
    private String thumb;

    /**
     * 是否允许申请互助 : 0 不允许, 1 允许，不允许申请情况下申请互助按钮不展示
     */
    private int is_allow_apply_help;
    /**
     * 用户星级，没达到1星级 弹层提示
     */
    private int userStarLevel;
    /**
     * 用户互祝需要的最小星级，对每个商品判断
     */
    private int applyHelpMinStarlevel;

    /**
     * 是否存在申请，0：不存在 1：存在 当存在申请的时候弹层提示
     */
    private int isExistsHelpGoods;
    /**
     * 用户身份是否允许购买 康农 志愿者  0 不是志愿者
     */
    private String identityIsAllowBuy;
    private String become_volunteer_url;

    private int isCer;//isCer：0 需要实名 1：不需要
    private String certification_url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (!TextUtils.isEmpty(shareUrl) && GoodsInfo != null) {
                    String text = "我发现了一个好物，已经有" + GoodsInfo.getSale_number() + "人兑换，你也来啊！";
                    String title = GoodsInfo.getName();
                    mShareUtils.setShare(text, thumb, shareUrl, title);
                }
                break;
            case R.id.tv_backtop:
                exchangedetailSv.smoothScrollTo(0, 0);
                break;
            case R.id.frame_shopcart:
                Intent intent = new Intent(mContext, ShopCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.tv_addShoppingCart:
                //只针对志愿者判断
                if (!TextUtils.isEmpty(identityIsAllowBuy) && identityIsAllowBuy.equals("0")) {
                    showConnonDialog();
                    break;
                }
                if (isCer == 0) {
                    showCertificatDialog();
                    break;

                }
                skipApplyHelp = false;
                if (goodsGuiGeList != null && goodsGuiGeList.size() > 1) {
                    showGuiGeDialog();
                } else {
                    skipActivity();
                }
                break;
            case R.id.tv_tohelp:
                //只针对志愿者判断
                if (!TextUtils.isEmpty(identityIsAllowBuy) && identityIsAllowBuy.equals("0")) {
                    showConnonDialog();
                    break;
                }
                if (isCer == 0) {
                    showCertificatDialog();
                    break;

                }
                if (userStarLevel < applyHelpMinStarlevel) {
                    showLevelDialog(applyHelpMinStarlevel);
                    break;
                }
                if (isExistsHelpGoods == 1) {
                    showNotOverDialog();
                    break;
                }
                skipApplyHelp = true;
                if (goodsGuiGeList != null && goodsGuiGeList.size() > 1) {
                    showGuiGeDialog();
                } else {
                    skipActivity();
                }
                break;
        }
    }

    boolean skipApplyHelp = false;

    /**
     * 跳转申请或加入购物车
     */
    private void skipActivity() {
        if (skipApplyHelp) {
            Intent intents = new Intent(mContext, LifeStyleApplyHelpActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("goods_id", "" + shop_goods_id);
            intents.putExtra("shop_goods_price_id", "" + shop_goods_price_id);
            startActivity(intents);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intents, mActivity);
        } else {
            setAddShoppingCartMap();
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.exchange_detail;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("商品详情");
        vpImg.addOnPageChangeListener(mOnPageChangeListener);
        setOrChangeTranslucentColor(toolbar, null);

        int fontSize = (int) getResources().getDimension(R.dimen.text_contents_size);
        Log.i(TAG, "initView: fontSize = " + fontSize);
        tv_showComplexH5Text.getSettings().setDefaultFontSize(fontSize);
        ConfigUtils.getINSTANCE().getWindowPD(mActivity);
        int height = DensityUtil.screenWith(mContext) * 610 / 750;
        relat_img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        tv_backtop.setOnClickListener(this);
        frame_shopcart.setOnClickListener(this);
        tvAddShoppingCart.setOnClickListener(this);
        tv_tohelp.setOnClickListener(this);
        exchangedetailSv.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView paramMyScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (oldScrollY > 10) {
                    tv_backtop.setVisibility(View.VISIBLE);
                } else {
                    tv_backtop.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getIntents(intent);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        getIntents(intent);
    }

    private void getIntents(Intent intent) {
        shop_goods_id = intent.getStringExtra("shop_goods_id");
        user_id = UserUtils.getUserId(mContext);
        mPresent.getDetailData(user_id, shop_goods_id);
        getShoppingCartMap();
    }

    /**
     * 获取购物车map
     */
    private void getShoppingCartMap() {
        ShoppingCartMap.clear();
        ShoppingCartMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData(user_id, DetailItemBean.class));
        if (ShoppingCartMap != null && ShoppingCartMap.size() >= 0) {
            int num = 0;
            for (String key : ShoppingCartMap.keySet()) {
                DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
                num += GoodsInfo_.getGoodsNum();
                Log.e("ShopCartItemBean", "" + ShoppingCartMap.size() + "  num=" + num);
            }
            tvGoodsnum.setText("" + num);
            Log.e("ShopCartItemBean", "" + ShoppingCartMap.size() + "  num=" + num);
        }
    }

    /**
     * 加入购物车
     */
    private void setAddShoppingCartMap() {
        if (goodsGuiGeList != null && goodsGuiGeList.size() > 0) {
            //不相同时：初始化商品数据
            GoodsInfo.setShop_goods_price_id(shop_goods_price_id);
            GoodsInfo.setSkb_price(goodsGuiGeList.get(guigeSelectPosition).getSkb_price());
            GoodsInfo.setPrice_name(goodsGuiGeList.get(guigeSelectPosition).getPrice_name());
            GoodsInfo.setGoodsNum(1);
        }
        if (TextUtils.isEmpty(shop_goods_price_id)) {
            shop_goods_price_id = "";
        }
        String key = shop_goods_id + "_" + shop_goods_price_id;
        if (ShoppingCartMap.containsKey(key)) {//相同时：数量加1
            DetailItemBean GoodsInfo_ = ShoppingCartMap.get(key);
            GoodsInfo_.setGoodsNum(GoodsInfo_.getGoodsNum() + 1);
            ShoppingCartMap.put(key, GoodsInfo_);
        } else {
            ShoppingCartMap.put(key, GoodsInfo);
        }
        SharedPreferencesUtil.getInstance().putHashMapData(user_id, ShoppingCartMap);
        getShoppingCartMap();
        ToastUtils.showToast("已加入购物车");
    }

    @Override
    protected MallDetailPresenterImp<MallDetailContract.View> createPresent() {
        return new MallDetailPresenterImp<>(mContext, this);
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
    public void DetailSuccess(GoodsDetailDataBean responseBean) {
        firstComIn = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DetailAfterBean mDetailAfterBean = (DetailAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                identityIsAllowBuy = mDetailAfterBean.getIdentityIsAllowBuy();
                become_volunteer_url = mDetailAfterBean.getBecome_volunteer_url();
                shareUrl = mDetailAfterBean.getShareUrl();
                userStarLevel = mDetailAfterBean.getUserStarLevel();
                applyHelpMinStarlevel = mDetailAfterBean.getApplyHelpMinStarlevel();
                isExistsHelpGoods = mDetailAfterBean.getIsExistsHelpGoods();
                help_goods_id = mDetailAfterBean.getHelpGoodsId();

                isCer = mDetailAfterBean.getIsCer();
                certification_url = mDetailAfterBean.getCertification_url();
                showInitData(mDetailAfterBean);
            }
        }
    }

    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    private boolean isLoop = true;

    private void initAuto() {
        // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isLoop) {
                    SystemClock.sleep(4000);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (vpImg != null)
                vpImg.setCurrentItem(vpImg.getCurrentItem() + 1);
        }
    };


    List<String> GoodsPhotoList;
    GuiGeDetailAdapter mGuiGeDetailAdapter;
    int guigeSelectPosition = 0;
    DetailItemBean GoodsInfo;
    List<DetailItemBean> goodsGuiGeList;

    private void showInitData(DetailAfterBean mDetailAfterBean) {
        GoodsInfo = mDetailAfterBean.getGoodsInfo();
        guigeSelectPosition = 0;
        if (GoodsInfo != null) {
            is_allow_apply_help = GoodsInfo.getIs_allow_apply_help();
            if (is_allow_apply_help == 1) {
                tv_tohelp.setVisibility(View.VISIBLE);
            } else {
                tv_tohelp.setVisibility(View.GONE);
            }
            thumb = GoodsInfo.getThumb();
            int is_selfmade = GoodsInfo.getIs_selfmade();
            if (is_selfmade == 1) {
                ivThelabel.setVisibility(View.VISIBLE);
            } else {
                ivThelabel.setVisibility(View.GONE);
            }
            tvName.setText(GoodsInfo.getName());
            tvShopnum.setText("已兑换" + GoodsInfo.getSale_number() + "件");
            //1 寿康宝；2 超能 ；3 混合
            buy_type = GoodsInfo.getBuy_type();
            setText(GoodsInfo.getSkb_price(), GoodsInfo.getSuper_ability());


            String merchantName = mDetailAfterBean.getMerchantName();
            String merchantLogo = mDetailAfterBean.getMerchantLogo();
            if (!TextUtils.isEmpty(merchantName) || !TextUtils.isEmpty(merchantLogo)) {
                layout_merchant.setVisibility(View.VISIBLE);
            } else {
                layout_merchant.setVisibility(View.GONE);
            }
            tv_merchantname.setText("" + merchantName);
            GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, merchantLogo, iv_merchanticon);

            String cont = GoodsInfo.getContent();
            if (!TextUtils.isEmpty(cont)) {
                ConfigUtils.getINSTANCE().showBridgeWebView(tv_showComplexH5Text, cont);
                tv_showComplexH5Text.setVisibility(View.VISIBLE);
            } else {
                tv_showComplexH5Text.setVisibility(View.GONE);
            }
            // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            tv_showComplexH5Text.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // TODO Auto-generated method stub
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    if (!TextUtils.isEmpty(cont) && cont.contains("<a")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                    return true;
                }
            });
        }
        GoodsPhotoList = mDetailAfterBean.getGoodsPhoto();
        if (GoodsPhotoList != null && GoodsPhotoList.size() > 1) {
            isLoop = true;
            initAuto();
        } else {
            isLoop = false;
        }
        if (GoodsPhotoList != null && GoodsPhotoList.size() > 0) {
            GoodsImgAdapter adapter = new GoodsImgAdapter(mContext, GoodsPhotoList);
            vpImg.setAdapter(adapter);
            tvImgnum.setText("1/" + GoodsPhotoList.size());
        } else {
            tvImgnum.setText("0/0");
        }

        List<DetailItemBean> goodsSolarTerms = mDetailAfterBean.getGoodsSolarTerms();
        if (goodsSolarTerms != null && goodsSolarTerms.size() > 0) {
            layout_jieqi.setVisibility(View.VISIBLE);
            JieQiDetailAdapter mJieQiAdapter = new JieQiDetailAdapter(mContext, goodsSolarTerms);
            jieqi_gv.setAdapter(mJieQiAdapter);
            jieqi_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (goodsSolarTerms != null && goodsSolarTerms.size() > 0) {
                        int solar_terms_id = goodsSolarTerms.get(position).getSolar_terms_id();
                        String solar_terms_name = goodsSolarTerms.get(position).getSolar_terms_name();
                        Intent intents = new Intent();
                        intents.setAction(ConstantManager.MAINMENU_ACTION);
                        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                        intents.putExtra("solar_terms_id", solar_terms_id);
                        intents.putExtra("solar_terms_name", solar_terms_name);
                        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                        ActivityManager.getScreenManager().popAllActivityOnlyMain();
                        doFinish();
                    }
                }
            });
        } else {
            layout_jieqi.setVisibility(View.GONE);
        }
        goodsGuiGeList = mDetailAfterBean.getGoodsPrice();
        if (goodsGuiGeList != null && goodsGuiGeList.size() > 0) {
            layout_guige.setVisibility(View.VISIBLE);
            shop_goods_price_id = goodsGuiGeList.get(guigeSelectPosition).getShop_goods_price_id();
            mGuiGeDetailAdapter = new GuiGeDetailAdapter(mContext, goodsGuiGeList);
            mGuiGeDetailAdapter.setGuigeSelectPosition(guigeSelectPosition);
            guige_gv.setAdapter(mGuiGeDetailAdapter);
            guige_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    guigeSelectPosition = position;
                    shop_goods_price_id = goodsGuiGeList.get(guigeSelectPosition).getShop_goods_price_id();
                    mGuiGeDetailAdapter.setGuigeSelectPosition(guigeSelectPosition);
                    mGuiGeDetailAdapter.notifyDataSetChanged();
                    setText(goodsGuiGeList.get(guigeSelectPosition).getSkb_price(), goodsGuiGeList.get(guigeSelectPosition).getSuper_ability());
                }
            });
        } else {
            layout_guige.setVisibility(View.GONE);
        }
    }

    /**
     * 1 寿康宝；2 超能 ；3 混合
     */
    int buy_type;

    private void setText(String skb_price, String super_ability) {

        if (buy_type == 2) {
            tvSkb.setText(super_ability + "超能");
        } else if (buy_type == 3) {
            tvSkb.setText(super_ability + "超能+" + skb_price + "寿康宝");
        } else {
            tvSkb.setText(skb_price + "寿康宝");
        }
    }


    //***************************************************************
    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (GoodsPhotoList != null && GoodsPhotoList.size() > 0)
                position %= GoodsPhotoList.size();
            if (position < 0) {
                position = GoodsPhotoList.size() + position;
            }
            tvImgnum.setText(position + 1 + "/" + GoodsPhotoList.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    MyDialog guigeDialog;
    TextView btn_helpsure;
    GridView guigegv;

    /**
     * 规格弹层
     */
    public void showGuiGeDialog() {
        if (guigeDialog == null) {
            guigeDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_malldetail_guige);// 创建Dialog并设置样式主题
            guigeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = guigeDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            guigeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = guigeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            guigeDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) guigeDialog.findViewById(R.id.layout_cancel);
            guigegv = (GridView) guigeDialog.findViewById(R.id.guige_gv);
            btn_helpsure = (TextView) guigeDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guigeDialog.dismiss();
                }
            });
            btn_helpsure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guigeDialog.dismiss();
                    skipActivity();
                }
            });
        } else {
            guigeDialog.show();
        }
        if (skipApplyHelp) {
            btn_helpsure.setText("确认申请");
        } else {
            btn_helpsure.setText("确认加入");
        }
        if (goodsGuiGeList != null && goodsGuiGeList.size() > 1) {
            if (goodsGuiGeList.size() > 10) {
                int heigth = DensityUtil.screenHigh(mContext) * 3 / 5;
                guigegv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heigth));
            } else {
                guigegv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            shop_goods_price_id = goodsGuiGeList.get(guigeSelectPosition).getShop_goods_price_id();
            if (mGuiGeDetailAdapter == null) {
                mGuiGeDetailAdapter = new GuiGeDetailAdapter(mContext, goodsGuiGeList);
            }
            mGuiGeDetailAdapter.setGuigeSelectPosition(guigeSelectPosition);
            guigegv.setAdapter(mGuiGeDetailAdapter);
            guigegv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    guigeSelectPosition = position;
                    shop_goods_price_id = goodsGuiGeList.get(guigeSelectPosition).getShop_goods_price_id();
                    mGuiGeDetailAdapter.setGuigeSelectPosition(guigeSelectPosition);
                    mGuiGeDetailAdapter.notifyDataSetChanged();
                    tvSkb.setText(goodsGuiGeList.get(guigeSelectPosition).getSkb_price());
                }
            });
        }
    }


    MyDialog levelDialog;
    TextView tv_xingji;

    /**
     * 等级不足提示
     */
    public void showLevelDialog(int applyHelpMinStarlevel_) {
        if (levelDialog == null) {
            levelDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_goodsdetail_level);// 创建Dialog并设置样式主题
            levelDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = levelDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            levelDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = levelDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            levelDialog.getWindow().setAttributes(p); //设置生效
            TextView btn_know = (TextView) levelDialog.findViewById(R.id.btn_know);
            tv_xingji = (TextView) levelDialog.findViewById(R.id.tv_xingji);
            judge(applyHelpMinStarlevel_);
            btn_know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    levelDialog.dismiss();
                }
            });
        } else {
            judge(applyHelpMinStarlevel_);
            levelDialog.show();
        }
    }

    private void judge(int applyHelpMinStarlevel_) {
        String[] numArray = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String sd = numArray[applyHelpMinStarlevel_];
        tv_xingji.setText("CHO" + sd + "星等级");
    }

    MyDialog notoverDialog;

    /**
     * 还有未完成任务
     */
    public void showNotOverDialog() {
        if (notoverDialog == null) {
            notoverDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_goodsdetail_notover);// 创建Dialog并设置样式主题
            notoverDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = notoverDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            notoverDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = notoverDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            notoverDialog.getWindow().setAttributes(p); //设置生效
            TextView btn_know = (TextView) notoverDialog.findViewById(R.id.btn_know);
            btn_know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notoverDialog.dismiss();
                    Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", help_goods_id);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
            });
        } else {
            notoverDialog.show();
        }
    }

    MyDialog CertificatDialog;

    private void showCertificatDialog() {
        if (CertificatDialog == null) {
            CertificatDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_malldetail_certificat);// 创建Dialog并设置样式主题
            CertificatDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = CertificatDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            CertificatDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = CertificatDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            CertificatDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) CertificatDialog.findViewById(R.id.layout_cancel);
            TextView btn_jihuo = (TextView) CertificatDialog.findViewById(R.id.btn_jihuo);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CertificatDialog.dismiss();
                }
            });
            btn_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", certification_url);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    CertificatDialog.dismiss();
                }
            });
        } else {
            CertificatDialog.show();
        }
    }


    MyDialog ConnonDialog;

    /**
     * 康农成为志愿者
     */
    public void showConnonDialog() {
        if (ConnonDialog == null) {
            ConnonDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_malldetail_connon);// 创建Dialog并设置样式主题
            ConnonDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = ConnonDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            ConnonDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = ConnonDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            ConnonDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) ConnonDialog.findViewById(R.id.layout_cancel);
            TextView btn_gohelp = (TextView) ConnonDialog.findViewById(R.id.btn_gohelp);
            TextView btn_jihuo = (TextView) ConnonDialog.findViewById(R.id.btn_jihuo);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnonDialog.dismiss();
                }
            });
            btn_gohelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnonDialog.dismiss();
                }
            });
            btn_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", become_volunteer_url);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        ConnonDialog.dismiss();
                    } else {
                        ToastUtils.showToast("请先完善资料后再成为志愿者");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, UserInfoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                            }
                        }, 100);
                    }
                }
            });
        } else {
            ConnonDialog.show();
        }
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn) {
            isLoop = false;
            identityIsAllowBuy = "";
            getShoppingCartMap();
            mPresent.getDetailData(user_id, shop_goods_id);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tv_showComplexH5Text != null) {
            tv_showComplexH5Text.destroy();
        }
        isLoop = false;
    }

    /**
     * 重写onkeydown 用于监听返回键
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
