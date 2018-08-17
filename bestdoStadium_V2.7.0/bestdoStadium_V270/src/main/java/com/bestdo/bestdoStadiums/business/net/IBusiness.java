package com.bestdo.bestdoStadiums.business.net;

import com.android.volley.VolleyError;

/**
 * Created by YuHua on 2017/5/24 19:46 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public interface IBusiness {
	void onSuccess(BaseResponse response);

	void onError(VolleyError error);
}
