package com.bestdo.bestdoStadiums.control.city;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestdo.bestdoStadiums.control.city.PinnedHeaderListView.PinnedHeaderAdapter;
import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.R;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * Adapter for sections.
 */
public class SectionListAdapter
		implements ListAdapter, OnItemClickListener, PinnedHeaderAdapter, SectionIndexer, OnScrollListener {
	private SectionIndexer mIndexer;
	private String[] mSections;// 所有分组的名字
	private int[] mCounts;// 所有分组的个数
	private int mSectionCounts = 0;
	private DataSetObserver dataSetObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			updateTotalCount();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
			updateTotalCount();
		};
	};

	private StandardArrayAdapter linkedAdapter;
	private Map<String, View> currentViewSections = new HashMap<String, View>();
	private int viewTypeCount;
	protected LayoutInflater inflater;

	private OnItemClickListener linkedListener;

	public void clearCache() {
		mIndexer = null;
		mSections = null;
		mCounts = null;
		dataSetObserver = null;
		if (currentViewSections != null) {
			currentViewSections.clear();
			currentViewSections = null;
		}

	}

	Handler mReMenHandler;
	int mReMenHandlerID;

	public SectionListAdapter(LayoutInflater inflater, StandardArrayAdapter linkedAdapter, Handler mReMenHandler,
			int mReMenHandlerID) {
		this.linkedAdapter = linkedAdapter;
		this.inflater = inflater;
		this.mReMenHandler = mReMenHandler;
		this.mReMenHandlerID = mReMenHandlerID;
		linkedAdapter.registerDataSetObserver(dataSetObserver);
		updateTotalCount();
		mIndexer = new MySectionIndexer(mSections, mCounts);
	}

	private boolean isTheSame(String previousSection, String newSection) {
		if (previousSection == null) {
			return newSection == null;
		} else {
			return previousSection.equals(newSection);
		}
	}

	private void fillSections() {
		mSections = new String[mSectionCounts];
		mCounts = new int[mSectionCounts];
		int count = linkedAdapter.getCount();
		String currentSection = null;
		int sectionIndex = 0;// 分组的序列号，比如说有A、B、C、D4个分组，序列号依次为0，1，2，3
		int sectionCounts = 0;// 分组下面的成员个数，计数器
		String previousSection = null;
		for (int i = 0; i < count; i++) {
			sectionCounts++;
			currentSection = linkedAdapter.SourceDateList.get(i).getSortLettersShow();
			// 如果当前分组名和上一个分组名不一样，说明产生的新的分组，这时可以计算出前一个分组内成员的个数
			if (!isTheSame(previousSection, currentSection)) {
				mSections[sectionIndex] = currentSection;
				previousSection = currentSection;

				if (sectionIndex == 0) {// 如果是第一个， 则第一个分组的成员个数计为1
					mCounts[0] = 1;
				} else if (sectionIndex != 0) {// 计算前一个分组的成员个数，由于当前计数已经加上了下一个分组的第一个成员，要剔除掉
					mCounts[sectionIndex - 1] = sectionCounts - 1;
				}
				if (i > 0) {// 产生新的分组时，重新从1开始计数
					sectionCounts = 1;
				}
				sectionIndex++;
			}
			if (i == count - 1) {// 如果是最后一个,因为进入的时候把newSectionCounts置为0，下次不会计数，少加了一次
				mCounts[sectionIndex - 1] = sectionCounts;
			}

		}
		// for(String a : mSections) {
		// System.out.println(a);
		// }
		// for(int a : mCounts)
		// System.out.println(a);
	}

	private void updateTotalCount() {
		String currentSection = null;
		viewTypeCount = linkedAdapter.getViewTypeCount() + 1;
		int count = linkedAdapter.getCount();
		for (int i = 0; i < count; i++) {
			SearchCityInfo item = (SearchCityInfo) linkedAdapter.getItem(i);
			if (!isTheSame(currentSection, item.getSortLettersShow())) {
				mSectionCounts++;
				currentSection = item.getSortLettersShow();
			}
		}
		fillSections();
	}

	@Override
	public int getCount() {
		return linkedAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		int linkedItemPosition = getLinkedPosition(position);
		return linkedAdapter.getItem(linkedItemPosition);
	}

	@Override
	public long getItemId(int position) {
		return linkedAdapter.getItemId(getLinkedPosition(position));
	}

	protected Integer getLinkedPosition(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return linkedAdapter.getItemViewType(getLinkedPosition(position));
	}

	protected void setSectionText(String section, View sectionView) {
		TextView textView = (TextView) sectionView.findViewById(R.id.stadiumsearchcity_item_tv_catalog);
		textView.setText(section);
	}

	protected synchronized void replaceSectionViewsInMaps(String section, View theView) {
		if (currentViewSections.containsKey(theView)) {
			currentViewSections.remove(theView);
		}
		currentViewSections.put(section, theView);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder viewHolder = null;
		SearchCityInfo mInfo = linkedAdapter.SourceDateList.get(position);

		if (view == null) {
			viewHolder = new ViewHolder();
			view = inflater.inflate(R.layout.search_city_item, null);
			viewHolder.renmen_gridview = (MyGridView) view.findViewById(R.id.renmen_gridview);
			viewHolder.stadiumsearchcity_item_tv_name = (TextView) view
					.findViewById(R.id.stadiumsearchcity_item_tv_name);
			viewHolder.stadiumsearchcity_item_tv_catalog = (TextView) view
					.findViewById(R.id.stadiumsearchcity_item_tv_catalog);
			viewHolder.stadiumsearchcity_item_ivtopname_line = (ImageView) view
					.findViewById(R.id.stadiumsearchcity_item_ivtopname_line);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPositionSide(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		String showSortLetters = mInfo.getSortLettersShow();
		if (position == getPositionForSectionSide(section)) {
			viewHolder.stadiumsearchcity_item_ivtopname_line.setVisibility(View.GONE);
			viewHolder.stadiumsearchcity_item_tv_catalog.setVisibility(View.VISIBLE);
			viewHolder.stadiumsearchcity_item_tv_catalog.setText(showSortLetters);
		} else {
			viewHolder.stadiumsearchcity_item_ivtopname_line.setVisibility(View.VISIBLE);
			viewHolder.stadiumsearchcity_item_tv_catalog.setVisibility(View.GONE);
		}
		if (showSortLetters.equals("热门城市")) {
			viewHolder.renmen_gridview.setVisibility(View.VISIBLE);
			viewHolder.stadiumsearchcity_item_tv_name.setVisibility(View.GONE);
			viewHolder.stadiumsearchcity_item_ivtopname_line.setVisibility(View.GONE);

			List<SearchCityInfo> mhotCityList = linkedAdapter.mhotCityList;
			ReMenCityAdapter menCityAdapter = new ReMenCityAdapter(linkedAdapter.context,
					linkedAdapter.myselectcurrentcitystatus, mhotCityList, linkedAdapter.cityid_select,
					linkedAdapter.cityid_current);
			viewHolder.renmen_gridview.setAdapter(menCityAdapter);
			viewHolder.renmen_gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Message msg = new Message();
					msg.what = mReMenHandlerID;
					msg.arg1 = position;
					mReMenHandler.sendMessage(msg);
					msg = null;
				}
			});
		} else {
			viewHolder.stadiumsearchcity_item_tv_name.setPadding(
					ConfigUtils.getInstance().dip2px(linkedAdapter.context, 15),
					ConfigUtils.getInstance().dip2px(linkedAdapter.context, 8),
					ConfigUtils.getInstance().dip2px(linkedAdapter.context, 15),
					ConfigUtils.getInstance().dip2px(linkedAdapter.context, 8));
			viewHolder.renmen_gridview.setVisibility(View.GONE);
			viewHolder.stadiumsearchcity_item_tv_name.setVisibility(View.VISIBLE);
			viewHolder.stadiumsearchcity_item_tv_name.setText(mInfo.getCitynameShow());
			viewHolder.stadiumsearchcity_item_tv_name.setBackgroundResource(R.drawable.corners_bg);
			// 当前为我的附近
			if (!TextUtils.isEmpty(linkedAdapter.myLocationokstatus)) {
				if (position == 0) {
					if (!TextUtils.isEmpty(linkedAdapter.myselectcurrentcitystatus)) {
						viewHolder.stadiumsearchcity_item_tv_name.setBackgroundResource(R.drawable.corners_selectbtnbg);
					} else {
						viewHolder.stadiumsearchcity_item_tv_name.setBackgroundResource(R.drawable.corners_btnbg);
					}
				}
			} else {
				viewHolder.stadiumsearchcity_item_tv_name.setPadding(0,
						ConfigUtils.getInstance().dip2px(linkedAdapter.context, 8), 0,
						ConfigUtils.getInstance().dip2px(linkedAdapter.context, 8));
			}

		}
		return view;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPositionSide(int position) {
		return linkedAdapter.SourceDateList.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSectionSide(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = linkedAdapter.SourceDateList.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	class ViewHolder {
		TextView stadiumsearchcity_item_tv_catalog;
		MyGridView renmen_gridview;
		TextView stadiumsearchcity_item_tv_name;
		ImageView stadiumsearchcity_item_ivtopname_line;
	}

	@Override
	public int getPositionForSection(int section) {
		if (mIndexer == null) {
			return -1;
		}
		return mIndexer.getPositionForSection(section);
	}

	@Override
	public int getSectionForPosition(int position) {
		if (mIndexer == null) {
			return -1;
		}
		return mIndexer.getSectionForPosition(position);
	}

	@Override
	public int getViewTypeCount() {
		return viewTypeCount;
	}

	@Override
	public boolean hasStableIds() {
		return linkedAdapter.hasStableIds();
	}

	@Override
	public boolean isEmpty() {
		return linkedAdapter.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		linkedAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		linkedAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return linkedAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		return linkedAdapter.isEnabled(getLinkedPosition(position));
	}

	public int getRealPosition(int pos) {
		return pos - 1;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (linkedListener != null) {
			linkedListener.onItemClick(parent, view, getLinkedPosition(position), id);
		}

	}

	public void setOnItemClickListener(OnItemClickListener linkedListener) {
		this.linkedListener = linkedListener;
	}

	@Override
	public int getPinnedHeaderState(int position) {
		int realPosition = position;// 这里没什么别的意思，主要是通讯录中，listview中第一个显示的是所有的联系人，
									// 不是真实的数据，所以会-1,这里偷懒，直接把后面的去掉，还有其他有类似的地方，原因一样
		if (mIndexer == null) {
			return PINNED_HEADER_GONE;
		}
		if (realPosition < 0) {
			return PINNED_HEADER_GONE;
		}
		int section = getSectionForPosition(realPosition);// 得到此item所在的分组位置
		int nextSectionPosition = getPositionForSection(section + 1);// 得到下一个分组的位置
		if (nextSectionPosition != -1 && realPosition == nextSectionPosition - 1) {
			return PINNED_HEADER_PUSHED_UP;
		}
		return PINNED_HEADER_VISIBLE;
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		int realPosition = position;
		int section = getSectionForPosition(realPosition);

		String title = (String) mIndexer.getSections()[section];
		((TextView) header.findViewById(R.id.search_city_listsectioin_header)).setText(title);

	}

	@Override
	public Object[] getSections() {
		if (mIndexer == null) {
			return new String[] { "" };
		} else {
			return mIndexer.getSections();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (view instanceof PinnedHeaderListView) {
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}

	}

}
