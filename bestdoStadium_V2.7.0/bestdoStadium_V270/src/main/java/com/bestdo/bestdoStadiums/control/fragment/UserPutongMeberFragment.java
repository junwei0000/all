package com.bestdo.bestdoStadiums.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.GetMeMberListBusiness;
import com.bestdo.bestdoStadiums.business.GetMeMberListBusiness.GetMeMberListCallback;
import com.bestdo.bestdoStadiums.control.activity.MainNavImgActivity;
import com.bestdo.bestdoStadiums.control.activity.UserMeberActiyity;
import com.bestdo.bestdoStadiums.control.adapter.UserBuyMberAdapter;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.model.UserMemberBuyNoteInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：
 * @Description 类描述：普通会员
 */
public class UserPutongMeberFragment extends Fragment {
	private UserMeberActiyity mContext;
	private Context context;
	private ProgressDialog mDialog;

	private MyListView mListView;
	/**
	 * 用来与外部activity交互的
	 */
	private FragmentInteraction listterner;
	private UserBuyMberAdapter userBuyMberAdapter;
	private ArrayList<UserBuyMeberInfo> imgList;
	private CheckBox zhifubao_pay_checkbox;
	private CheckBox weixin_pay_checkbox;
	protected String member_package_id;
	private TextView user_putong_member_note_title;
	private ImageView user_putong_member_note_img;
	private ImageView user_putong_member_note_img2;
	private TextView user_putong_member_note_detle, user_putong_member_note, user_putong_member_note2;
	private TextView user_putong_member_note_detle2;
	private String html_url;
	private TextView user_putong_member_note_title_right;
	private UserMeberActiyity activity;
	private HashMap<String, String> mhashmap;
	private Boolean isAttached = false;
	private ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList;
	private String uid;
	private String price;
	private SharedPreferences bestDoInfoSharedPrefs;
	protected ArrayList<UserMemberBuyNoteInfo> userMemberBuyNoteInfoList;
	protected ImageLoader asyncImageLoader;
	protected String show_more_url;
	protected String id_show_more;
	protected String price_;
	private CheckBox yinlian_pay_checkbox;

	public UserPutongMeberFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_putong_meber_fragment, container, false);
		context =  view.getContext();
		if (isAdded()) {// 判断Fragment已经依附Activity

			getData();

		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView = (MyListView) getView().findViewById(R.id.user_meber_myListView);
		zhifubao_pay_checkbox = (CheckBox) getView().findViewById(R.id.zhifubao_pay_checkbox);
		weixin_pay_checkbox = (CheckBox) getView().findViewById(R.id.weixin_pay_checkbox);
		yinlian_pay_checkbox = (CheckBox) getView().findViewById(R.id.yinlian_pay_checkbox);
		user_putong_member_note_title_right = (TextView) getView()
				.findViewById(R.id.user_putong_member_note_title_right);
		user_putong_member_note_title = (TextView) getView().findViewById(R.id.user_putong_member_note_title);
		user_putong_member_note_img = (ImageView) getView().findViewById(R.id.user_putong_member_note_img);
		user_putong_member_note_img2 = (ImageView) getView().findViewById(R.id.user_putong_member_note_img2);
		user_putong_member_note_detle = (TextView) getView().findViewById(R.id.user_putong_member_note_detle);
		user_putong_member_note_detle2 = (TextView) getView().findViewById(R.id.user_putong_member_note_detle2);
		user_putong_member_note = (TextView) getView().findViewById(R.id.user_putong_member_note);
		user_putong_member_note2 = (TextView) getView().findViewById(R.id.user_putong_member_note2);
		asyncImageLoader = new ImageLoader(context, "listdetail");
		LinearLayout yinlian_pay_lin = (LinearLayout) getView().findViewById(R.id.yinlian_pay_lin);
		LinearLayout zhifubao_pay_lin = (LinearLayout) getView().findViewById(R.id.zhifubao_pay_lin);
		LinearLayout user_member_instruction = (LinearLayout) getView().findViewById(R.id.user_member_instruction);
		yinlian_pay_checkbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(true);
				zhifubao_pay_checkbox.setChecked(false);
				weixin_pay_checkbox.setChecked(false);
				listterner.payType("yinlian");
			}
		});

		zhifubao_pay_checkbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(false);
				zhifubao_pay_checkbox.setChecked(true);
				weixin_pay_checkbox.setChecked(false);
				listterner.payType("zhifubao");
			}
		});
		weixin_pay_checkbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(false);
				zhifubao_pay_checkbox.setChecked(false);
				weixin_pay_checkbox.setChecked(true);
				listterner.payType("weixin");
			}
		});
		yinlian_pay_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(true);
				zhifubao_pay_checkbox.setChecked(false);
				weixin_pay_checkbox.setChecked(false);
				listterner.payType("yinlian");
			}
		});

		user_member_instruction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonUtils.getInstance().toH5(context,html_url, "", "",false);
			}
		});
		zhifubao_pay_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(false);
				zhifubao_pay_checkbox.setChecked(true);
				weixin_pay_checkbox.setChecked(false);
				listterner.payType("zhifubao");
			}
		});

		LinearLayout weixin_pay_lin = (LinearLayout) getView().findViewById(R.id.weixin_pay_lin);
		weixin_pay_lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				yinlian_pay_checkbox.setChecked(false);
				zhifubao_pay_checkbox.setChecked(false);
				weixin_pay_checkbox.setChecked(true);
				listterner.payType("weixin");
			}
		});

	}

	@Override
	public void onAttach(Activity activity) {
		isAttached = true;
		this.activity = (UserMeberActiyity) activity;
		if (activity instanceof FragmentInteraction) {
			listterner = (FragmentInteraction) activity;
			listterner.payType("yinlian");

		} else {
			throw new IllegalArgumentException("activity must implements FragmentInteraction");
		}
		super.onAttach(activity);
	}

	public void updateUserVisibleHint() {
		setUserVisibleHint(true);
	}

	/**
	 * Fragment的懒加载 当切换到这个fragment的时候，它才去初始化 ------------取消预加载---------------
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			showDilag();
		} else {
			// 不可见时不执行操作
		}
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog2(mContext);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getData() {
		if (isAttached) {
			bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(context);
			uid = bestDoInfoSharedPrefs.getString("uid", "");
			mhashmap = new HashMap<String, String>();
			mhashmap.put("uid", uid);

			new GetMeMberListBusiness(activity, mhashmap, new GetMeMberListCallback() {

				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					// TODO Auto-generated method stub
					if (dataMap != null) {

						String status = (String) dataMap.get("status");
						if (status.equals("200")) {
							imgList = (ArrayList<UserBuyMeberInfo>) dataMap.get("imgList");
							if (imgList != null && imgList.size() > 0) {
								show_more_url = imgList.get(0).getShow_more_url();
								id_show_more = imgList.get(0).getIs_show_more();
								if (Integer.valueOf(id_show_more) > 2) {
									user_putong_member_note_title_right.setVisibility(View.VISIBLE);
									user_putong_member_note_title_right.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											CommonUtils.getInstance().toH5(context, show_more_url, "", "", false);
										}
									});
								}
								userBuyMeberListInfoList = imgList.get(0).getUserBuyMeberListInfoList();
								userBuyMberAdapter = new UserBuyMberAdapter(context, userBuyMeberListInfoList, "1");
								mListView.setAdapter(userBuyMberAdapter);
								price = userBuyMeberListInfoList.get(0).getPriceBase();
								member_package_id = userBuyMeberListInfoList.get(0).getId();
								price_ = PriceUtils.getInstance()
										.gteDividePrice(userBuyMeberListInfoList.get(0).getPriceBase(), "100");
								listterner.process(price_, price, member_package_id);
								mListView.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
										for (int i = 0; i < userBuyMeberListInfoList.size(); i++) {
											userBuyMeberListInfoList.get(i).setSelect(false);
											if (i == position) {
												userBuyMeberListInfoList.get(position).setSelect(true);
											}
										}
										userBuyMberAdapter.setaList(userBuyMeberListInfoList);
										userBuyMberAdapter.notifyDataSetChanged();
										member_package_id = userBuyMeberListInfoList.get(position).getId();
										price = userBuyMeberListInfoList.get(position).getPriceBase();
										price_ = PriceUtils.getInstance().gteDividePrice(
												userBuyMeberListInfoList.get(position).getPriceBase(), "100");
										listterner.process(price_, price, member_package_id);
									}
								});

								userMemberBuyNoteInfoList = imgList.get(0).getUserMemberBuyNoteInfoList();// 会员特权说明
								user_putong_member_note_title.setText("百动会员特权");
								user_putong_member_note.setText(userMemberBuyNoteInfoList.get(0).getNote());
								user_putong_member_note2.setText(userMemberBuyNoteInfoList.get(1).getNote());

								user_putong_member_note_detle.setText(userMemberBuyNoteInfoList.get(0).getDiscount());
								user_putong_member_note_detle2.setText(userMemberBuyNoteInfoList.get(1).getDiscount());
								try {
									if (!TextUtils.isEmpty(userMemberBuyNoteInfoList.get(0).getImg())) {
										asyncImageLoader.DisplayImage(userMemberBuyNoteInfoList.get(0).getImg(),
												user_putong_member_note_img);
										asyncImageLoader.DisplayImage(userMemberBuyNoteInfoList.get(1).getImg(),
												user_putong_member_note_img2);

									} else {
										// club_activity_lay_top_img
										// .setBackgroundResource(R.drawable.usercenter_head_img);
									}
								} catch (Exception e) {
								}
							}
						}
					}
				}
			});

		}

	}

	/**
	 * 定义了所有activity必须实现的接口
	 */
	public interface FragmentInteraction {
		/**
		 * Fragment 向Activity传递指令，这个方法可以根据需求来定义
		 * 
		 * @param str
		 */
		void process(String show_price_, String price, String member_package_id);

		void payType(String paytype);
	}

	/**
	 * 包含的 Fragment 中统计页面：
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("UserOrderScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UserOrderScreen");
	}
}
