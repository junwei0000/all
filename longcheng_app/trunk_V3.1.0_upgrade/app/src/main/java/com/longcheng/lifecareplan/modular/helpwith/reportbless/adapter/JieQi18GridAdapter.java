package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ApplyItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;


public class JieQi18GridAdapter extends BaseAdapter {
    private final ArrayList<ApplyItemBean> mList;
    ViewHolder mHolder = null;
    Context mContext;
    int page;

    public JieQi18GridAdapter(Context context, ArrayList<ApplyItemBean> list, int page,int pageNum) {
        this.page = page;
        this.mContext = context;
        mList = new ArrayList<>();
        int i = page * pageNum;
        int iEnd = i + pageNum;
        System.out.println("i=" + i);
        System.out.println("iEnd=" + iEnd);
        while ((i < list.size()) && (i < iEnd)) {
            mList.add(list.get(i));
            i++;
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.dialog_mallgoods_jieqi_item, null);
            mHolder = new ViewHolder(view);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        ApplyItemBean mJieQiItemBean = mList.get(position);
        mHolder.item_tv_cn.setText(mJieQiItemBean.getSolar_terms_name());
        mHolder.item_tv_currentjieqi.setVisibility(View.GONE);
        GlideDownLoadImage.getInstance().loadCircleImage(mJieQiItemBean.getSolar_terms_img(), mHolder.item_iv_img);
        return view;
    }

    private class ViewHolder {
        private TextView item_tv_currentjieqi;
        private ImageView item_iv_img;
        private TextView item_tv_cn;

        public ViewHolder(View view) {
            item_tv_currentjieqi = view.findViewById(R.id.item_tv_currentjieqi);
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_cn = view.findViewById(R.id.item_tv_cn);

        }
    }
}
