package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;

import java.util.ArrayList;

/**
 * 首页运动类型列表适配
 *
 * @author jun
 */
public abstract class LiWuAdapter extends BaseAdapter {

    private ArrayList<LiveDetailItemInfo> mList;
    private ImageLoader asyncImageLoader;
    private Activity context;
    private ViewHolder viewHolder = null;
    private int currentpage;
    private int selectMoneyPostion;
    private int selectpage;

    public abstract void backSelectItem(int selectpage, int selectMoneyPostion, String skb, String live_gift_id);

    public void setSelectMoneyPostion(int selectMoneyPostion) {
        this.selectMoneyPostion = selectMoneyPostion;
    }

    public void setSelectpage(int selectpage) {
        this.selectpage = selectpage;
    }

    public LiWuAdapter(Activity context, ArrayList<LiveDetailItemInfo> gift,
                       int page, int APP_PAGE_SIZE) {
        this.context = context;
        this.currentpage = page;
        mList = new ArrayList<LiveDetailItemInfo>();
        asyncImageLoader = new ImageLoader(context, "listitem");
        int i = page * APP_PAGE_SIZE;
        int iEnd = i + APP_PAGE_SIZE;
        System.out.println("i=" + i);
        System.out.println("iEnd=" + iEnd);
        while ((i < gift.size()) && (i < iEnd)) {
            mList.add(gift.get(i));
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
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.live_liwu_item, null);
            viewHolder.item_layout_money = view
                    .findViewById(R.id.item_layout_money);
            viewHolder.item_tv_money = view
                    .findViewById(R.id.item_tv_money);
            viewHolder.item_tv_moneyimg = view
                    .findViewById(R.id.item_tv_moneyimg);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LiveDetailItemInfo sportTypeInfo = mList.get(position);
        String thumb = sportTypeInfo.getPic_url();
        viewHolder.item_tv_money.setText(sportTypeInfo.getSkb() + "寿康宝");
        if (!TextUtils.isEmpty(thumb)) {
            asyncImageLoader.DisplayImage(thumb, viewHolder.item_tv_moneyimg);
        }
        sportTypeInfo.setPage(currentpage);
        sportTypeInfo.setPosition(position);
        viewHolder.item_layout_money.setTag(sportTypeInfo);
        viewHolder.item_layout_money.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LiveDetailItemInfo sportTypeInfo = (LiveDetailItemInfo) arg0.getTag();
                selectpage = sportTypeInfo.getPage();
                selectMoneyPostion = sportTypeInfo.getPosition();
                backSelectItem(selectpage, selectMoneyPostion, sportTypeInfo.getSkb(), sportTypeInfo.getLive_gift_id());
            }
        });
        if (selectpage == currentpage && selectMoneyPostion == position) {//默认
            backSelectItem(selectpage, selectMoneyPostion, sportTypeInfo.getSkb(), sportTypeInfo.getLive_gift_id());
            viewHolder.item_layout_money.setBackgroundColor(context.getResources().getColor(R.color.liwu_bg));
        } else {
            viewHolder.item_layout_money.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
        viewHolder.item_layout_money.getBackground().setAlpha(90);
        return view;
    }

    class ViewHolder {
        TextView item_tv_money;
        ImageView item_tv_moneyimg;
        LinearLayout item_layout_money;
    }
}
