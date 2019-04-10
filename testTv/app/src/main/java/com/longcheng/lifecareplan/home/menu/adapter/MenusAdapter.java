package com.longcheng.lifecareplan.home.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.home.menu.bean.MenuInfo;
import com.longcheng.lifecareplan.utils.FontUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MenusAdapter extends BaseAdapterHelper<MenuInfo> {
    ViewHolder mHolder = null;

    Context context;

    public int getSelectItem() {
        return selectItem;
    }

    private int selectItem = -1;


    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        super.notifyDataSetChanged();
    }

    public MenusAdapter(Context context, List<MenuInfo> list) {
        super(context, list);
        this.context = context;
    }

    private void setGV() {
        //        gvbottom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    mAdapter.setSelectItem(-1);
//                }
//                pageTopTvTime.setFocusable(false);//防止点击上下键还有焦点
//            }
//        });
//        gvbottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mAdapter.setSelectItem(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                mAdapter.setSelectItem(-1);
//            }
//        });
//        gvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            }
//        });
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MenuInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (selectItem == position) {
            convertView.setBackgroundResource(R.drawable.corners_bg_textselect);
        } else {
            convertView.setBackgroundResource(R.drawable.corners_bg_text);
        }
        FontUtils.setFontKaiTi(context, mHolder.item_tv_dayname);
        FontUtils.setFontKaiTi(context, mHolder.item_tv_jieeqiname);
        FontUtils.setFontKaiTi(context, mHolder.item_tv_huaname);
        FontUtils.setFontKaiTi(context, mHolder.item_tv_iconname);
        MenuInfo mInfo = list.get(position);
        mHolder.imgbg.setBackgroundResource(mInfo.getBgImgId());
        mHolder.item_tv_dayname.setText(mInfo.getNextday());
        mHolder.item_tv_jieeqiname.setText(mInfo.getJieqiname());
        mHolder.item_tv_huaname.setText(mInfo.getHuaname());
        mHolder.item_tv_iconname.setText(mInfo.getIconname());
        mHolder.item_iv_icon.setBackgroundResource(mInfo.getIconId());
        if (position == 4) {
            mHolder.item_tv_dayname.setVisibility(View.INVISIBLE);
            mHolder.item_tv_jieeqiname.setVisibility(View.INVISIBLE);
            mHolder.item_tv_huaname.setVisibility(View.INVISIBLE);
            mHolder.item_tv_iconname.setVisibility(View.INVISIBLE);
            mHolder.item_iv_icon.setVisibility(View.INVISIBLE);
        } else {
            mHolder.item_tv_dayname.setVisibility(View.VISIBLE);
            mHolder.item_tv_jieeqiname.setVisibility(View.VISIBLE);
            mHolder.item_tv_huaname.setVisibility(View.VISIBLE);
            mHolder.item_tv_iconname.setVisibility(View.VISIBLE);
            mHolder.item_iv_icon.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_icon;
        private TextView item_tv_dayname, item_tv_jieeqiname, item_tv_huaname, item_tv_iconname;
        private RelativeLayout imgbg;

        public ViewHolder(View view) {
            imgbg = (RelativeLayout) view.findViewById(R.id.imgbg);
            item_tv_dayname = (TextView) view.findViewById(R.id.item_tv_dayname);
            item_tv_jieeqiname = (TextView) view.findViewById(R.id.item_tv_jieeqiname);
            item_tv_huaname = (TextView) view.findViewById(R.id.item_tv_huaname);
            item_iv_icon = (ImageView) view.findViewById(R.id.item_iv_icon);
            item_tv_iconname = (TextView) view.findViewById(R.id.item_tv_iconname);
        }
    }
}
