package com.KiwiSports.control.activity;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.control.adapter.MainSportAdapter;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.parser.MainsportParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class MainSportActivity extends BaseActivity {

	private ListView mListView;
	private LinearLayout pagetop_layout_back;
	private int sportindex;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_type);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText(getString(R.string.sporttype));
		mListView = (ListView) findViewById(R.id.list_date);
		TextView tv_commit = (TextView) findViewById(R.id.tv_commit);
		tv_commit.setVisibility(View.GONE);
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);

		Intent intent = getIntent();
		sportindex = intent.getExtras().getInt("sportindex", 0);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mallsportList != null & mallsportList.size() > 0
						&& arg2 < mallsportList.size()) {
					sportindex = mallsportList.get(arg2).getIndex();
					adapter.setSportindex(sportindex);
					adapter.notifyDataSetChanged();
					doBack();
				}
			}
		});
	}

	protected MainSportAdapter adapter;
	private ArrayList<MainSportInfo> mallsportList;

	@Override
	protected void processLogic() {

		MainsportParser mMainsportParser = new MainsportParser();
		mallsportList = mMainsportParser.parseJSON(this);
		adapter = new MainSportAdapter(context, sportindex, mallsportList);
		mListView.setAdapter(adapter);

	}

	private void doBack() {
		Intent intent = new Intent();
		intent.putExtra("sportindex", sportindex);
		setResult(1, intent);
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
