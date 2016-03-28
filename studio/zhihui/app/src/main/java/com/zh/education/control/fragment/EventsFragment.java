package com.zh.education.control.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Text;

import com.zh.education.R;
import com.zh.education.business.EventsBusiness;
import com.zh.education.business.EventsBusiness.EventsCallback;
import com.zh.education.control.adapter.BoKeAdapter;
import com.zh.education.control.adapter.EventsAdapter;
import com.zh.education.control.datepicker.MonthDateView;
import com.zh.education.control.datepicker.MonthDateView.DateClick;
import com.zh.education.control.datepicker.WeekDayView;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.EventsInfo;
import com.zh.education.model.NoticesInfo;
import com.zh.education.model.ScheduleInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ProgressDialogUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-25 下午4:46:34
 * @Description 类描述：日程
 */
public class EventsFragment extends Fragment {
	private final static String TAG = "RiChengFragment";
	Activity activity;
	ArrayList<EventsInfo> mList = new ArrayList<EventsInfo>();
	ImageView detail_loading;
	
	private TextView iv_left;
	private TextView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private TextView tv_today;
	private MonthDateView monthDateView;
	private WeekDayView weekDayView;
	private ArrayList<EventsInfo> EventsInfoList;
	private List<Integer> list = new ArrayList<Integer>();
	private ListView events_listview;
	private int year;
	private int month;
	 
	public EventsFragment() {
		super();
	}

	public EventsFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		Log.e("channel_id", args.getInt("id", 0)+"日程");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			// fragment可见时加载数据
				Calendar calendar = Calendar.getInstance();
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH);
				init();
			
		} else {
			// fragment不可见时不执行操作

		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_events, null);
		iv_left = (TextView)view.findViewById(R.id.iv_left);
		iv_right = (TextView) view.findViewById(R.id.iv_right);
		tv_date = (TextView) view.findViewById(R.id.date_text);
		tv_week  =(TextView) view.findViewById(R.id.week_text);
		tv_today = (TextView) view.findViewById(R.id.tv_today);
		events_listview=(ListView) view.findViewById(R.id.events_listview);
		
		monthDateView = (MonthDateView) view.findViewById(R.id.monthDateView);
		monthDateView.setmDayColor(this.getResources().getColor(R.color.contents_color));
		monthDateView.setTextView(tv_date,tv_week);
		
		weekDayView= (WeekDayView)view.findViewById(R.id.weekDayView);
		weekDayView.setmTopLineColor(this.getResources().getColor(R.color.white));
		weekDayView.setmBottomLineColor(this.getResources().getColor(R.color.white));
		weekDayView.setmWeedayColor(this.getResources().getColor(R.color.contents_color));
		weekDayView.setmWeekendColor(this.getResources().getColor(R.color.contents_color));
		
		
		monthDateView.setDateClick(new DateClick() {
			@Override
			public void onClickOnDate() {
//				Toast.makeText(activity, "点击了："+ monthDateView.getmSelDay(), Toast.LENGTH_SHORT).show();
			}
		});
		setOnlistener();
		
		return view;
	}
	private void setOnlistener(){
		iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
				year=monthDateView.getmSelYear();
				month=monthDateView.getmSelMonth();
				init();
			}
		});
		
		iv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
				year=monthDateView.getmSelYear();
				month=monthDateView.getmSelMonth();
				init();
			}
			
		});
		
	}
	private void init() {
		String dateEnd=getLastDayOfMonth(year,month);
		String dateBegin=getFirstDayOfMonth(year,month);
		dateEnd=DatesUtils.getInstance().getDateToTimeStamp(dateEnd,"yyyy-MM-dd HH:mm:ss")+"";
		dateBegin=DatesUtils.getInstance().getDateToTimeStamp(dateBegin,"yyyy-MM-dd HH:mm:ss")+"";
		
		getData(dateBegin,dateEnd);
	}
	public static String getLastDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);     
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
        
       return  new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime());  
    }   
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        cal.set(Calendar.YEAR, year);     
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
       return   new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime()); 
    }   
	private void getData(String dateBegin,String dateEnd) {
		ProgressDialogUtils.showProgressDialog(activity, "数据加载中...","");
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(activity);
		String loginStr=zhedu_spf.getString("loginStr", "");
		final HashMap<String, String> mhashmap = new HashMap<String, String>();
		mhashmap.put("loginStr", loginStr);
		mhashmap.put("dateBegin", dateBegin);
		mhashmap.put("dateEnd", dateEnd);
		
		
		new EventsBusiness(activity, mhashmap, mList,new EventsCallback(){
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				ProgressDialogUtils.dismissProgressDialog("");
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						EventsInfoList = (ArrayList<EventsInfo>) dataMap.get("EventsInfoList");
						if(EventsInfoList!=null){
							
						}
						
					} else {
						Toast.makeText(activity, (String) dataMap.get("msg"),
								Toast.LENGTH_SHORT).show();
					}
					updateList();
				} else {
					Toast.makeText(activity, "网络不稳，请稍后重试！", Toast.LENGTH_SHORT)
							.show();
				}
				
				
			} ;
		});
	}
	private void updateList() {
		
		list=new ArrayList<Integer>();
		if (EventsInfoList != null && EventsInfoList.size() != 0) {
			
		} else {
			EventsInfoList=null;
			EventsInfoList =new ArrayList<EventsInfo>(); 
		}
		for (int i = 0; i < EventsInfoList.size(); i++) {
			long DayStamp=EventsInfoList.get(i).getDayStamp();
			String s=DatesUtils.getInstance().getTimeStampToDate(DayStamp,"dd");
			list.add(Integer.valueOf(s).intValue());
		}
		monthDateView.setDaysHasThingList(list);
		monthDateView.invalidate();
		
		EventsAdapter adapter=new EventsAdapter(EventsInfoList, activity);
		events_listview.setAdapter(adapter);
	}
	
	
	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
