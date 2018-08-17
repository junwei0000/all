package com.bestdo.bestdoStadiums.utils;

import com.baidu.navisdk.util.common.StringUtils;

public class OneWheelUtil {

	/**
	 * 
	 * @return 从1000～100000
	 */
	public static String[] getData() {
		final String[] items = new String[100];
		for (int i = 1; i <= 100; i++) {
			String ch = 1000 * i + "";
			items[i - 1] = ch;
		}
		return items;
	}

	public static int getIndex(String num) {
		if (StringUtils.isEmpty(num))
			return 0;
		String[] nums = getData();
		for (int i = 0; i < nums.length; i++) {
			if (num.equals(nums[i]))
				return i;
		}
		return 0;
	}

}
