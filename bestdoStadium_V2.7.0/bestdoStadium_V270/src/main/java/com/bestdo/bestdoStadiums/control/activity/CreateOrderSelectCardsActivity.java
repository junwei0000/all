package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CreateOrdersGetDefaultCardBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersGetDefaultCardBusiness.GetDefaultCardCallback;
import com.bestdo.bestdoStadiums.control.adapter.CreateOrderGetCardAdapter;
import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-29 下午8:00:03
 * @Description 类描述：创建订单选择卡券
 */
public class CreateOrderSelectCardsActivity extends BaseActivity {
	private TextView pagetop_tv_name, pagetop_tv_you;
	private LinearLayout pagetop_layout_back;
	private ListView listview1;
	private TextView not_date_tv_cardinstructions;
	private LinearLayout not_date;
	private TextView tv_bottom;

	private String merid;
	private String book_day;
	private String uid;
	private String stadium_id;
	private String main_deduct_time;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String intent_from;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_you:
			Intent intent2 = new Intent(CreateOrderSelectCardsActivity.this, UserCardsMiActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent2.putExtra("intent_from", intent_from);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, CreateOrderSelectCardsActivity.this);
			break;
		case R.id.not_date_tv_cardinstructions:
			Intent intent = new Intent(this, UserCardAbstractActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.tv_bottom:
			Intent intn = getIntent();
			intn.putExtra("selectcardindex", -1);
			setResult(1, intn);
			nowFinish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.creat_order_getcards);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		Intent intent = getIntent();
		intent_from = intent.getStringExtra("intent_from");
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setText("激活");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		pagetop_tv_name.setText("卡券选择");
		not_date = (LinearLayout) findViewById(R.id.not_date);
		not_date_tv_cardinstructions = (TextView) findViewById(R.id.not_date_tv_cardinstructions);
		listview1 = (ListView) findViewById(R.id.user_card_listview);
		listview1.setOnItemClickListener(new listItemClick());
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		not_date_tv_cardinstructions.setOnClickListener(this);
		not_date_tv_cardinstructions.setVisibility(View.VISIBLE);
		tv_bottom.setOnClickListener(this);
		Intent intent = getIntent();
		if (intent != null) {
			// 创建订单切换卡时用
			uid = intent.getStringExtra("uid");
			merid = intent.getStringExtra("merid");
			book_day = intent.getStringExtra("book_day");
			stadium_id = intent.getStringExtra("stadium_id");
			main_deduct_time = intent.getStringExtra("main_deduct_time");

		}
	}

	HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processLogic() {
		if (firstComeIn) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("merid", merid);
		mhashmap.put("book_day", book_day);
		mhashmap.put("stadium_id", stadium_id);
		mhashmap.put("main_deduct_time", main_deduct_time);

		System.err.println(mhashmap);
		new CreateOrdersGetDefaultCardBusiness(this, mhashmap, new GetDefaultCardCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				firstComeIn = false;
				if (dataMap != null && dataMap.get("status").equals("200")) {
					mList = (ArrayList<CreatOrderGetDefautCardInfo>) dataMap.get("mList");
					if (mList != null && mList.size() != 0) {

					} else {
						mList = new ArrayList<CreatOrderGetDefautCardInfo>();
					}
					mCreateOrderGetCardAdapter = new CreateOrderGetCardAdapter(context, mList);
					listview1.setAdapter(mCreateOrderGetCardAdapter);
					tv_bottom.setVisibility(View.VISIBLE);
				}
				addNotDateImg();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	ArrayList<CreatOrderGetDefautCardInfo> mList;

	CreateOrderGetCardAdapter mCreateOrderGetCardAdapter;

	/**
	 ** 
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mCreateOrderGetCardAdapter == null
				|| (mCreateOrderGetCardAdapter != null && mCreateOrderGetCardAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			TextView not_date_cont2 = (TextView) findViewById(R.id.not_date_cont2);
			TextView not_date_jihuo_btn = (TextView) findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_cont2.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_jihuo_btn.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.no_cards_img);
			not_date_cont2.setText("暂无可用卡券");
			not_date_cont.setText("购买后请激活使用,点击右上角激活");
			not_date_jihuo_btn.setText("立即前往购买");
			not_date_jihuo_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String html_url=bestDoInfoSharedPrefs.getString("navThree_url", "");
					String name= bestDoInfoSharedPrefs.getString("navThree_name", "");
					CommonUtils.getInstance().toH5(context,html_url, name,"",intent_from,true,true);
				}
			});
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	/**
	 * 没有数据时加载默认图
	 */

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		CommonUtils.getInstance().mCurrentActivity = this;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	boolean firstComeIn = true;

	@Override
	protected void onResume() {
		super.onResume();
		if (!firstComeIn && !TextUtils.isEmpty(book_day) && !TextUtils.isEmpty(uid) && !TextUtils.isEmpty(merid)) {
			processLogic();
		}
	}

	class listItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (mList != null && position < mList.size()) {
				CreatOrderGetDefautCardInfo mDefautCardInfo = new CreatOrderGetDefautCardInfo();
				mDefautCardInfo.setmList(mList);
				Intent intn = getIntent();
				intn.putExtra("selectcardindex", position);
				intn.putExtra("mDefautCardInfo", mDefautCardInfo);
				setResult(1, intn);
				nowFinish();
			}
		}

	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			nowFinish();
		}
		return false;
	}

}
