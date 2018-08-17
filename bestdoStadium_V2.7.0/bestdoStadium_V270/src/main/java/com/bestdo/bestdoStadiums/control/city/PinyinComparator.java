package com.bestdo.bestdoStadiums.control.city;

import java.util.Comparator;

import android.content.Context;

import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author jun
 * 
 */
public class PinyinComparator implements Comparator<SearchCityInfo> {

	Context context;

	public PinyinComparator(Context context) {
		super();
		this.context = context;
	}

	public int compare(SearchCityInfo o1, SearchCityInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals(context.getResources().getString(R.string.unit_fuhao_dalo))) {
			return -1;
		} else if (o1.getSortLetters().equals(context.getResources().getString(R.string.unit_fuhao_dalo))
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
