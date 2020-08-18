package com.longcheng.lifecareplan.modular.mine.relief;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.DaiFuActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class ReliefDetailAdapter extends BaseAdapter {
    ViewHolder mHolder = null;
    Context context;
    List<ReliefDetailsListBean.DataBean> list;
    private LayoutInflater inflater;

    public ReliefDetailAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setData(List<ReliefDetailsListBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reliefdetail_adapter, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Log.d("ReliefDetailAdapter", "user_name" + list.get(position).user_name);
        Log.d("ReliefDetailAdapter", "avatar" + list.get(position).avatar);
        mHolder.item_tv_name.setText(list.get(position).user_name);
        String desc = "<font color='#000000'>送来" + list.get(position).number + "个节气能量" + "</font>";

        mHolder.item_tv_typetitle.setText(Html.fromHtml(desc));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mHolder.item_tv_typetitle.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mHolder.item_tv_typetitle.setText(Html.fromHtml(desc));
        }

        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, list.get(position).avatar, mHolder.item_iv_img);
        List<String> images = list.get(position).images;
        mHolder.item_layout_shenfen.removeAllViews();
        if (images != null && images.size() > 0) {
            for (String url : images) {
                LinearLayout linearLayout = new LinearLayout(context);
                ImageView imageView = new ImageView(context);
                int dit = DensityUtil.dip2px(context, 18);
                int jian = DensityUtil.dip2px(context, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(context, url, imageView);
                linearLayout.addView(imageView);
                mHolder.item_layout_shenfen.addView(linearLayout);
            }
        }

        return convertView;

    }


    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_typetitle;
        private LinearLayout item_layout_shenfen;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_typetitle = convertView.findViewById(R.id.item_tv_typetitle);
            item_layout_shenfen = convertView.findViewById(R.id.item_layout_shenfen);

        }
    }
}
