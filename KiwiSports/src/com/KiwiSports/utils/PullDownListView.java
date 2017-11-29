package com.KiwiSports.utils;

import javax.security.auth.callback.Callback;

import com.KiwiSports.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

public class PullDownListView extends FrameLayout
		implements GestureDetector.OnGestureListener, Animation.AnimationListener {

	public int MAX_LENGHT = 0;
	public int STATE_REFRESH = 1;
	public int SCROLL_TO_CLOSE = 2;
	public int SCROLL_TO_REFRESH = 3;
	public double SCALE = 0.9d;
	private int CLOSEDELAY = 300;
	private int REFRESHDELAY = 300;
	private Animation mAnimationDown;
	private Animation mAnimationUp;
	private ImageView mArrow;
	private View emptyHeaderView;
	private ProgressBar mProgressBar;
	private TextView more;
	private ProgressBar mProgressBar2;
	private int mState;
	private TextView mTitle;
	public ListView mListView;
	LinearLayout foot;
	LinearLayout footer_layout;
	LinearLayout header;
	private GestureDetector mDetector;
	private FlingRunnable mFlinger;
	private int mPading;
	private int mDestPading;
	private int mLastTop;
	private LinearLayout mFirstChild;
	private FrameLayout mUpdateContent;
	private OnRefreshListioner mRefreshListioner;
	private boolean hasMore = true;
	private boolean isEnd = true;
	private boolean listviewDoScroll = false;
	private boolean isFirstLoading = false;
	private boolean mLongPressing;// 如果设置为true说明刚好到了执行长按的时间
	private boolean mPendingRemoved = false;//
	private String pulldowntorefresh;
	private String releasetorefresh;
	Rect r = new Rect();
	private MotionEvent downEvent;
	private CheckForLongPress mPendingCheckForLongPress = new CheckForLongPress();
	private CheckForLongPress2 mPendingCheckForLongPress2 = new CheckForLongPress2();
	private float lastY;
	private boolean useempty = true;
	// 这个标签作为测试用
	String TAG = "PullDownListView";

	String loadstatus = "";
	ImageView refreshbottom_line;

	/**
	 * 长按检查方法执行1线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class CheckForLongPress implements Runnable {
		public void run() {
			if (mListView.getOnItemLongClickListener() == null) {
			} else {
				postDelayed(mPendingCheckForLongPress2, 100);
			}
		}
	}

	/**
	 * 长按检查方法执行2线程 ----> 延后 100
	 * 
	 * @author Administrator
	 * 
	 */
	private class CheckForLongPress2 implements Runnable {
		public void run() {
			mLongPressing = true;
			MotionEvent e = MotionEvent.obtain(downEvent.getDownTime(),
					downEvent.getEventTime() + ViewConfiguration.getLongPressTimeout(), MotionEvent.ACTION_CANCEL,
					downEvent.getX(), downEvent.getY(), downEvent.getMetaState());
			PullDownListView.super.dispatchTouchEvent(e);
		}
	}

	class FlingRunnable implements Runnable {

		private void startCommon() {
			removeCallbacks(this);
		}

		public void run() {
			boolean noFinish = mScroller.computeScrollOffset();
			int curY = mScroller.getCurrY();
			int deltaY = curY - mLastFlingY;
			if (noFinish) {
				move(deltaY, true);
				mLastFlingY = curY;
				post(this);
			} else {
				removeCallbacks(this);
				if (mState == SCROLL_TO_CLOSE) {
					mState = -1;
				}
			}
		}

		public void startUsingDistance(int distance, int duration) {
			if (distance == 0)
				distance--;
			startCommon();
			mLastFlingY = 0;
			mScroller.startScroll(0, 0, 0, distance, duration);
			post(this);
		}

		private int mLastFlingY;
		private Scroller mScroller;

		public FlingRunnable() {
			mScroller = new Scroller(getContext());
		}
	}

	/**
	 * 下拉刷新以及加载更多回调监听接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnRefreshListioner {
		public abstract void onRefresh();

		public abstract void onLoadMore();
	}

	/**
	 * 直接new时调用的构造方法
	 * 
	 * @param context
	 */
	public PullDownListView(Context context) {
		super(context);
		this.context = context;
		mDetector = new GestureDetector(context, this);
		mFlinger = new FlingRunnable();
		init();
		addRefreshBar();
	}

	Context context;

	/**
	 * 在xml中使用时调用的构造方法
	 * 
	 * @param context
	 */
	public PullDownListView(Context context, AttributeSet att) {
		super(context, att);
		this.context = context;
		useempty = att.getAttributeBooleanValue(null, "useempty", true);
		mDetector = new GestureDetector(this);
		mFlinger = new FlingRunnable();
		init();
		addRefreshBar();
	}

	View view;
	private TextView refreshbottom_morebottom;
	private ImageView re_img;

	/**
	 * 添加刷新头部的控件
	 */
	private void addRefreshBar() {

		// 向上滑动的动画
		mAnimationUp = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_up);
		mAnimationUp.setAnimationListener(this);

		// 向下滑动的动画
		mAnimationDown = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_down);
		mAnimationDown.setAnimationListener(this);

		// 刷新头部的view
		view = LayoutInflater.from(getContext()).inflate(R.layout.refresh_top, null);
		// 添加view在本控件中
		addView(view);

		/**
		 * 以下都是刷新头部的一些控件的设置
		 */
		mFirstChild = (LinearLayout) view;
		mUpdateContent = (FrameLayout) getChildAt(0).findViewById(R.id.refreshtop_iv_content);
		re_img = (ImageView) getChildAt(0).findViewById(R.id.refresh_img);
		try {
			AnimationDrawable frameAnimation = null;
			if (frameAnimation == null) {
				frameAnimation = (AnimationDrawable) context.getResources().getDrawable(R.anim.xiala_anim);
			}
			re_img.setBackgroundDrawable(frameAnimation);
			frameAnimation.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
		mArrow = new ImageView(getContext());
		FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutparams.gravity = Gravity.CENTER;
		mArrow.setScaleType(ImageView.ScaleType.FIT_CENTER);
		mArrow.setLayoutParams(layoutparams);
		mArrow.setImageResource(R.drawable.arrow_down);
		mUpdateContent.addView(mArrow);
		FrameLayout.LayoutParams layoutparams1 = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		layoutparams1.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
		mProgressBar = new ProgressBar(getContext());
		mProgressBar.setIndeterminate(false);
		mProgressBar.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog_style_xml_icon));
		int i = getResources().getDimensionPixelSize(R.dimen.updatebar_padding);
		mProgressBar.setPadding(i, i, i, i);
		mProgressBar.setLayoutParams(layoutparams1);
		mUpdateContent.addView(mProgressBar);
		mTitle = (TextView) findViewById(R.id.refreshtop_tv_title);
	}

	public void setGone() {
		mTitle.setVisibility(View.GONE);
		mUpdateContent.setVisibility(View.GONE);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		mListView = (ListView) getChildAt(1);
		footer_layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.refresh_lay, null);
		foot = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.refresh_foot, null);
		more = (TextView) foot.findViewById(R.id.refreshbottom_more);
		refreshbottom_morebottom = (TextView) foot.findViewById(R.id.refreshbottom_morebottom);
		mProgressBar2 = (ProgressBar) foot.findViewById(R.id.refreshbottom_refbar);
		mProgressBar2.setIndeterminate(false);
		mProgressBar2.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog_style_xml_icon));
		mProgressBar2.setVisibility(View.GONE);
		if (useempty) {
			header = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.refresh_lay, null);
			mListView.addHeaderView(header);
		}
		refreshbottom_line = (ImageView) foot.findViewById(R.id.refreshbottom_line);
		// 初始化添加并隐藏
		addFoot();
		mListView.addFooterView(footer_layout);
		footer_layout.setVisibility(View.INVISIBLE);
		// foot.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// if (!isAutoLoadMore)
		// onLoadMore();
		// }
		// });
		loadingtishiString = getContext().getString(R.string.loading);
		mListView.setOnScrollListener(new OnScrollListener() {

			@SuppressLint("NewApi")
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				/**
				 * listView.getFirstVisiblePosition()表示在现时屏幕第一个ListItem(
				 * 第一个ListItem部分显示也算) 在整个ListView的位置（下标从0开始）
				 **/
				int f = mListView.getFirstVisiblePosition();
				/**
				 * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem(
				 * 最后ListItem要完全显示出来才算) 在整个ListView的位置（下标从0开始）
				 **/
				int lastv = mListView.getLastVisiblePosition();
				int t = view.getCount();
				System.err.println("onScrollStateChanged== " + f + "  " + lastv + "  " + t + " ");
				/**
				 * 数据全部显示listview不滚动的情况下
				 */
				if (!LOADMORESTATUS && f == 0 && lastv + 1 == t) {
					isEnd = true;
				}
				System.err
						.println(LOADMORESTATUS + " " + isEnd + " " + MOVELOAD + " " + hasMore + " " + isAutoLoadMore);
				// 1.没有正在加载更多；2.到底部；3.是否是滑动来执行加载操作； 4.当屏幕停止滚动时;5.可以加载更多；6.自动加载更多
				if (!LOADMORESTATUS && isEnd && MOVELOAD && scrollState == SCROLL_STATE_IDLE && hasMore
						&& isAutoLoadMore) {
					onLoadMore();
				}
			}

			// firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
			// visibleItemCount：当前能看见的列表项个数（小半个也算）
			// totalItemCount：列表项共数
			public void onScroll(AbsListView view, int f, int v, int t) {
				if (isAutoLoadMore) {
					// 注释---：滑动显示出加载更多时置为true
					if (f + v >= t) {
						isEnd = true;
					} else {
						isEnd = false;
					}
				}
				System.err.println("onScroll== " + f + "  " + v + "  " + t + " ");
				System.err.println("onScroll==" + isEnd);
			}
		});
	}

	/**
	 * 设置没有数据时默认图片
	 * 
	 * @param empty
	 */
	public void setEmptyHeaderView(View empty) {
		emptyHeaderView = empty;
	}

	/**
	 * 添加空的定部view
	 */
	public void addEmptyHeaderView() {
		header.removeAllViews();
		if (emptyHeaderView != null)
			header.addView(emptyHeaderView);
	}

	/**
	 * 移除顶部空view
	 */
	public void removeEmptyHeaderView() {
		if (emptyHeaderView != null)
			header.removeView(emptyHeaderView);
	}

	/**
	 * 初始化设置及变量
	 */
	private void init() {
		MAX_LENGHT = getResources().getDimensionPixelSize(R.dimen.updatebar_height);// 62.0dip
		setDrawingCacheEnabled(false);
		setBackgroundDrawable(null);
		setClipChildren(false);
		mDetector.setIsLongpressEnabled(false);
		mPading = -MAX_LENGHT;
		mLastTop = -MAX_LENGHT;
		pulldowntorefresh = getContext().getText(R.string.drop_dowm).toString();
		releasetorefresh = getContext().getText(R.string.release_update).toString();
	}

	/** deltaY > 0 向上 */
	private boolean move(float deltaY, boolean auto) {
		// move 方法执行 "
		if (deltaY > 0 && mFirstChild.getTop() == -MAX_LENGHT) {
			mPading = -MAX_LENGHT;
			return false;
		}

		if (auto) {
			// move 方法执行
			if (mFirstChild.getTop() - deltaY < mDestPading) {
				deltaY = mFirstChild.getTop() - mDestPading;
			}
			mFirstChild.offsetTopAndBottom((int) -deltaY);
			mListView.offsetTopAndBottom((int) -deltaY);
			mPading = mFirstChild.getTop();
			if (mDestPading == 0 && mFirstChild.getTop() == 0 && mState == SCROLL_TO_REFRESH) {
				// onRefresh 刷新方法执行
				onRefresh();
			} else if (mDestPading == -MAX_LENGHT) {
			}
			invalidate();
			updateView();
			return true;
		} else {
			if (mState != STATE_REFRESH || (mState == STATE_REFRESH && deltaY > 0)) {
				mFirstChild.offsetTopAndBottom((int) -deltaY);
				mListView.offsetTopAndBottom((int) -deltaY);
				mPading = mFirstChild.getTop();
			} else if (mState == STATE_REFRESH && deltaY < 0 && mFirstChild.getTop() <= 0) {
				if (mFirstChild.getTop() - deltaY > 0) {
					deltaY = mFirstChild.getTop();
				}
				mFirstChild.offsetTopAndBottom((int) -deltaY);
				mListView.offsetTopAndBottom((int) -deltaY);
				mPading = mFirstChild.getTop();
			}
		}
		if (deltaY > 0 && mFirstChild.getTop() <= -MAX_LENGHT) {
			mPading = -MAX_LENGHT;
			deltaY = -MAX_LENGHT - mFirstChild.getTop();
			mFirstChild.offsetTopAndBottom((int) deltaY);
			mListView.offsetTopAndBottom((int) deltaY);
			mPading = mFirstChild.getTop();
			updateView();
			invalidate();
			return false;
		}
		updateView();
		invalidate();
		return true;
	}

	private void updateView() {
		String s = "";
		if (mState != STATE_REFRESH) {
			if (mFirstChild.getTop() < 0) {
				mArrow.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);
				mTitle.setText(pulldowntorefresh);

				if (mLastTop >= 0 && mState != SCROLL_TO_CLOSE) {
					mArrow.startAnimation(mAnimationUp);// 向上移动动画
				}

			} else if (mFirstChild.getTop() > 0) {
				mTitle.setText(releasetorefresh + s);
				mProgressBar.setVisibility(View.INVISIBLE);
				mArrow.setVisibility(View.VISIBLE);

				if (mLastTop <= 0) {
					mArrow.startAnimation(mAnimationDown);// 向下移动动画
				}
			}
		}
		mLastTop = mFirstChild.getTop();
	}

	// release 方法执行
	private boolean release() {
		if (listviewDoScroll) {
			listviewDoScroll = false;
			return true;
		}
		if (mFirstChild.getTop() > 0) {
			scrollToUpdate(false);
		} else {
			scrollToClose();
		}
		invalidate();
		return false;
	}

	private void scrollToClose() {
		mDestPading = -MAX_LENGHT;
		mFlinger.startUsingDistance(MAX_LENGHT, CLOSEDELAY);
	}

	// scrollToUpdate 方法执行
	public void scrollToUpdate(boolean load) {
		mState = SCROLL_TO_REFRESH;

		mDestPading = 0;
		if (load) {
			mFlinger.startUsingDistance(50, REFRESHDELAY);
			load = false;
		} else
			mFlinger.startUsingDistance(mFirstChild.getTop(), REFRESHDELAY);
	}

	private void onRefresh() {

		mState = STATE_REFRESH;
		mTitle.setText(R.string.refreshing);
		mProgressBar.setVisibility(View.VISIBLE);
		mArrow.setVisibility(View.INVISIBLE);
		if (mRefreshListioner != null) {
			mRefreshListioner.onRefresh();
		}
	}

	public void onRefreshComplete() {
		loadstatus = "";
		mState = SCROLL_TO_CLOSE;
		mArrow.setImageResource(R.drawable.arrow_down);
		mProgressBar2.setVisibility(View.INVISIBLE);
		updateCommon();
		scrollToClose();
	}

	public void initBg(Context context, BaseAdapter mAdapter) {
		this.setBackgroundColor(getResources().getColor(R.color.gray_bg));
		int heightPixel = ConfigUtils.getInstance().getPhoneWidHeigth(context).heightPixels;
		if (mAdapter != null && heightPixel > 800)
			mAdapter.notifyDataSetChanged();
	}

	private void updateCommon() {
		if (mListView.getCount() == (mListView.getHeaderViewsCount() + mListView.getFooterViewsCount())) {
			if (useempty)
				addEmptyHeaderView();
		} else {
			removeEmptyHeaderView();
			mListView.setFooterDividersEnabled(false);
			// footer_layout.removeAllViews();
		}
	}

	public void onFirstLoad() {
		if (footer_layout.getChildCount() == 0) {
			footer_layout.addView(foot);
		}
		isFirstLoading = true;
		foot.setEnabled(false);
		// onFirstLoad 方法执行
		mState = STATE_REFRESH;
		mProgressBar2.setVisibility(View.VISIBLE);
		more.setText(loadingtishiString);
	}

	/**
	 * 是否是滑动来执行加载操作,防止数据少的时候都在当前屏幕显示多次加载
	 */
	private boolean MOVELOAD = false;
	/**
	 * false没有正在加载更多；true正在加载更多
	 */
	private boolean LOADMORESTATUS = false;
	private String loadingtishiString;
	private String skipfrom;

	public String getLoadingtishiString() {
		return loadingtishiString;
	}

	public void setLoadingtishiString(String loadingtishiString) {
		this.loadingtishiString = loadingtishiString;
	}

	public void setMore(boolean hasMore) {
		if (hasMore) {
			mListView.setFooterDividersEnabled(true);
			// 注释---：不在删除，每次加载完隐藏
			// footer_layout.removeAllViews();
			// footer_layout.addView(foot);
		} else {
			mListView.setFooterDividersEnabled(false);
			footer_layout.removeAllViews();
		}
	}

	/**
	 * 1每次加载更多完成时，显示
	 * 
	 * @param date
	 */
	public void onLoadMore() {
		LOADMORESTATUS = true;
		showMore();
		foot.setEnabled(false);
		mState = STATE_REFRESH;
		if (mRefreshListioner != null) {
			mRefreshListioner.onLoadMore();
		}
		if (loadstatus.equals("over")) {
		} else {
			mProgressBar2.setVisibility(View.VISIBLE);
			more.setText(loadingtishiString);
		}
	}

	/**
	 * 加载完成后隐藏
	 */
	public void hiddenMore() {
		if (loadstatus.equals("over")) {
			System.err.println("=====hiddenMore======" + loadstatus);
			footer_layout.setVisibility(View.INVISIBLE);
			if (TextUtils.isEmpty(skipfrom)) {
				mListView.smoothScrollBy(-footer_layout.getMeasuredHeight() - 5, 1);
			} else {
				mListView.smoothScrollBy(
						-footer_layout.getMeasuredHeight() + refreshbottom_morebottom.getMeasuredHeight() + 25, 1);
			}
			// 注释---：每次都初始化
			loadstatus = "";
			isEnd = false;
			MOVELOAD = false;
		}
	}

	/**
	 * 滑动加载更多时显示
	 */
	public void showMore() {
		footer_layout.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示场馆列表底部高度
	 */
	public void setMoreBottom() {
		skipfrom = "haveBottom";
		refreshbottom_morebottom.setVisibility(View.VISIBLE);
	}

	/**
	 * 2每次加载更多完成后，隐藏
	 * 
	 * @param date
	 */
	public void onLoadMoreComplete(String date, String loadstata) {
		loadstatus = loadstata;
		mState = -1;
		mProgressBar2.setVisibility(View.GONE);
		more.setText(date);
		updateCommon();
		if (isFirstLoading)
			isFirstLoading = false;
		foot.setEnabled(true);
		if (LOADMORESTATUS)
			mPullDownViewHandler.sendEmptyMessageDelayed(YANCHI, 1000);
	}

	/**
	 * 加载完延迟发送隐藏
	 */
	private final int YANCHI = 1;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case YANCHI:
				hiddenMore();
				LOADMORESTATUS = false;
				break;
			}
		}
	};

	public void setOrderBottomMoreLine(String skip) {
		if (skip.equals("list")) {
			refreshbottom_line.setVisibility(View.GONE);
		}
	}

	public boolean dispatchTouchEvent(MotionEvent e) {
		if (isFirstLoading) {
			return false;
		}
		int action;
		float y = e.getY();
		action = e.getAction();
		if (mLongPressing && action != MotionEvent.ACTION_DOWN) {
			return false;
		}
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			mLongPressing = true;
		}
		boolean handled = true;
		handled = mDetector.onTouchEvent(e);
		switch (action) {
		case MotionEvent.ACTION_UP:
			boolean f1 = mListView.getTop() <= e.getY() && e.getY() <= mListView.getBottom();
			if (!handled && mFirstChild.getTop() == -MAX_LENGHT && f1 || mState == STATE_REFRESH) {
				super.dispatchTouchEvent(e);
			} else {
				// 执行释放方法
				handled = release();
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			handled = release();
			super.dispatchTouchEvent(e);
			break;
		case MotionEvent.ACTION_DOWN:
			downEvent = e;
			mLongPressing = false;
			// 长按的时间间隔
			postDelayed(mPendingCheckForLongPress, ViewConfiguration.getLongPressTimeout() + 100);
			mPendingRemoved = false;
			super.dispatchTouchEvent(e);
			break;
		case MotionEvent.ACTION_MOVE:
			if (!LOADMORESTATUS) {
				MOVELOAD = true;
				System.err.println(MOVELOAD);
			}

			float deltaY = lastY - y;
			lastY = y;
			if (!mPendingRemoved) {
				removeCallbacks(mPendingCheckForLongPress);
				mPendingRemoved = true;
			}

			if (!handled && mFirstChild.getTop() == -MAX_LENGHT) {
				try {
					return super.dispatchTouchEvent(e);
				} catch (Exception e2) {
					e2.printStackTrace();
					return true;
				}
			} else if (handled && mListView.getTop() > 0 && deltaY < 0) {// deltaY小于0，向�?
				e.setAction(MotionEvent.ACTION_CANCEL);
				super.dispatchTouchEvent(e);
			}
			break;

		default:
			break;
		}

		return true;
	}

	public void onAnimationEnd(Animation animation) {
		int top = mFirstChild.getTop();
		if (top < 0)
			mArrow.setImageResource(R.drawable.arrow_down);
		else if (top > 0)
			mArrow.setImageResource(R.drawable.arrow_up);
		else {
			if (top < mLastTop) {
				mArrow.setImageResource(R.drawable.arrow_down);
			} else {
				mArrow.setImageResource(R.drawable.arrow_up);
			}
		}
	}

	public void onAnimationRepeat(Animation animation) {
	}

	public void onAnimationStart(Animation animation) {
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent motionevent, MotionEvent e, float f, float f1) {
		return false;
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l) {
		int top = mPading;
		int w = getMeasuredWidth();
		mFirstChild.layout(0, top, w, top + MAX_LENGHT);

		int h = getMeasuredHeight() + mPading + MAX_LENGHT;
		mListView.layout(0, top + MAX_LENGHT, w, h);
	}

	public void onLongPress(MotionEvent e) {
	}

	/** deltaY > 0 向上 */
	public boolean onScroll(MotionEvent curdown, MotionEvent cur, float deltaX, float deltaY) {
		deltaY = (float) ((double) deltaY * SCALE);
		boolean handled = false;
		boolean flag = false;
		if (mListView.getCount() == 0) {
			flag = true;
		} else {
			View c = mListView.getChildAt(0);
			if (mListView.getFirstVisiblePosition() == 0 && c != null && c.getTop() == 0) {
				flag = true;
			}
		}
		if ((deltaY < 0F && flag || getChildAt(0).getTop() > -MAX_LENGHT) && !LOADMORESTATUS) { // deltaY
			// <
			// 向下
			handled = move(deltaY, false);
		} else
			handled = false;
		return handled;
	}

	public void onShowPress(MotionEvent motionevent) {
	}

	public boolean onSingleTapUp(MotionEvent motionevent) {
		return false;
	}

	public void setRefreshListioner(OnRefreshListioner RefreshListioner) {
		mRefreshListioner = RefreshListioner;
	}

	private boolean isAutoLoadMore = true;// 是否自动加载

	// public boolean isAutoLoadMore() {
	// return isAutoLoadMore;
	// }
	// public void setAutoLoadMore(boolean isAutoLoadMore) {
	// this.isAutoLoadMore = isAutoLoadMore;
	// if (!isAutoLoadMore) {
	// foot.setOnClickListener(new View.OnClickListener() {
	//
	// public void onClick(View v) {
	// onLoadMore();
	// }
	// });
	// mListView.setOnScrollListener(null);
	// } else {
	// mListView.setOnScrollListener(new OnScrollListener() {
	//
	// public void onScrollStateChanged(AbsListView view,
	// int scrollState) {
	// if (isEnd && scrollState == SCROLL_STATE_IDLE && hasMore) {
	// onLoadMore();
	// }
	// }
	//
	// public void onScroll(AbsListView view, int f, int v, int t) {
	// if (f + v >= t - 1)
	// isEnd = true;
	// else
	// isEnd = false;
	// }
	// });
	// foot.setOnClickListener(null);
	// }
	// }

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public void removeFoot() {
		footer_layout.removeAllViews();
	}

	public void addFoot() {
		footer_layout.removeAllViews();
		footer_layout.addView(foot);
	}

}
