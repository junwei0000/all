package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.GrupMinUtils;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.GrupUtils;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class CHelpDetailAddAdapter extends BaseAdapterHelper<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> {
    ViewHolder mHolder = null;
    Activity context;


    public CHelpDetailAddAdapter(Activity context, List<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_detal_add_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.layout_inner.setVisibility(View.INVISIBLE);
        CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean mHelpItemBean = list.get(position);
        String user_id = UserUtils.getUserId(context);
        int person_number = mHelpItemBean.getPerson_number();
        mHashmap.clear();
        ArrayList<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> knp_group_table_item = mHelpItemBean.getKnp_group_table_item();
        for (int i = 0; i < knp_group_table_item.size(); i++) {
            mHashmap.put(i, knp_group_table_item.get(i));
            if (user_id.equals(knp_group_table_item.get(i).getUser_id())) {
                mHelpItemBean.setJoinStatus(true);
            }
        }
        if (mGrupUtils == null) {
            mGrupUtils = new GrupMinUtils(context);
        }
        mGrupUtils.showChairAllViewDetailAdd(person_number, mHelpItemBean.isJoinStatus(), mHashmap, mHolder.relatGroup, mHolder.absolut_chair, mHolder.layout_group_center);

        mHolder.iv_tablestatus.setVisibility(View.GONE);
        int status = mHelpItemBean.getStatus();
        boolean joinStatus = mHelpItemBean.isJoinStatus();
        if (status != 3 && !joinStatus) {
            if (mHelpItemBean.isCheck()) {
                mHolder.iv_tablestatus.setVisibility(View.VISIBLE);
            } else {
                mHolder.iv_tablestatus.setVisibility(View.GONE);
            }
            mGrupUtils.setTableView(mHolder.iv_tablestatus);
            mHolder.iv_tablestatus.setBackgroundResource(R.mipmap.table_check);
        } else {
            if (status == 3) {
                mHolder.iv_tablestatus.setVisibility(View.VISIBLE);
                int wid = (int) (BitmapFactory.decodeResource(context.getResources(), R.mipmap.zudui_yiwanehng).getWidth() * 1.5);
                int hei = (int) (BitmapFactory.decodeResource(context.getResources(), R.mipmap.zudui_yiwanehng).getHeight() * 1.5);
                mHolder.iv_tablestatus.setLayoutParams(new LinearLayout.LayoutParams(wid, hei));
                mHolder.iv_tablestatus.setBackgroundResource(R.mipmap.zudui_yiwanehng);
            }
        }


        return convertView;
    }

    HashMap<Integer, CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> mHashmap = new HashMap<>();
    GrupMinUtils mGrupUtils;


    private class ViewHolder {

        private RelativeLayout relatGroup;
        private AbsoluteLayout absolut_chair;
        private LinearLayout layout_group_center;
        private LinearLayout layout_inner;
        private ImageView iv_tablestatus;


        public ViewHolder(View view) {
            relatGroup = view.findViewById(R.id.relat_group);
            absolut_chair = view.findViewById(R.id.absolut_chair);
            layout_group_center = view.findViewById(R.id.layout_group_center);
            layout_inner = view.findViewById(R.id.layout_inner);
            iv_tablestatus = view.findViewById(R.id.iv_tablestatus);
        }
    }
}
