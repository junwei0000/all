package com.longcheng.lifecareplan.modular.mine.myaddress.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class AddressListAdapter extends BaseAdapterHelper<AddressItemBean> {
    ViewHolder mHolder = null;

    Context context;
    Handler mHandler;
    List<AddressItemBean> list;
    /**
     * 是否可以编辑和删除地址 0：否 1：是 （当接福人是自己或者自己家人的时候课编辑）
     */
    private String isCanEdit;

    public AddressListAdapter(Context context, List<AddressItemBean> list, Handler mHandler, String isCanEdit) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.list = list;
        this.isCanEdit = isCanEdit;
    }

    public List<AddressItemBean> getList() {
        return list;
    }

    public void setList(List<AddressItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<AddressItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.myaddress_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        AddressItemBean mAddressBean = list.get(position);
        String area = AddressSelectUtils.initData(context, mAddressBean.getProvince(), mAddressBean.getCity(), mAddressBean.getDistrict());
        mHolder.item_tv_name.setText(mAddressBean.getConsignee());
        mHolder.item_tv_phone.setText(mAddressBean.getMobile());
        mHolder.item_tv_address.setText(area + " " + mAddressBean.getAddress());

        mAddressBean.setPosition(position);
        mHolder.item_tv_moren.setTag(mAddressBean);
        mHolder.item_tv_edit.setTag(mAddressBean);
        mHolder.item_tv_del.setTag(mAddressBean);
        mHolder.item_tv_check.setTag(mAddressBean);
        mHolder.item_tv_check.setOnClickListener(mClickListener);
        mHolder.item_tv_moren.setOnClickListener(mClickListener);
        mHolder.item_tv_edit.setOnClickListener(mClickListener);
        mHolder.item_tv_del.setOnClickListener(mClickListener);

        //是否默认收货地址 0：否 1：是
        String is_default = mAddressBean.getIs_default();
        if (is_default.equals("1")) {
            mHolder.item_tv_check.setBackgroundResource(R.mipmap.check_true_red);
            mHolder.item_tv_moren.setText("默认地址");
            mHolder.item_tv_moren.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            mHolder.item_tv_check.setBackgroundResource(R.mipmap.check_false);
            mHolder.item_tv_moren.setText("设为默认");
            mHolder.item_tv_moren.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        }


        if (isCanEdit.equals("0")) {
            //不可编辑
            mHolder.item_tv_moren.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_tv_edit.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_tv_del.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_tv_edit.setBackgroundResource(R.drawable.corners_bg_graybian);
            mHolder.item_tv_del.setBackgroundResource(R.drawable.corners_bg_graybian);
        }
        return convertView;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddressItemBean mAddressBean = (AddressItemBean) v.getTag();
            int position = mAddressBean.getPosition();
            Message message;
            switch (v.getId()) {
                case R.id.item_tv_check:
                case R.id.item_tv_moren:
                    if (isCanEdit.equals("0")) {
                        break;
                    }
                    String is_default = mAddressBean.getIs_default();
                    if (!is_default.equals("1")) {
                        message = new Message();
                        message.what = ConstantManager.ADDRESS_HANDLE_SETMOREN;
                        message.arg1 = position;
                        message.obj = mAddressBean.getAddress_id();
                        mHandler.sendMessage(message);
                        message = null;
                    }
                    break;
                case R.id.item_tv_edit:
                    if (isCanEdit.equals("0")) {
                        break;
                    }
                    message = new Message();
                    message.what = ConstantManager.ADDRESS_HANDLE_EDIT;
                    message.arg1 = position;
                    message.obj = mAddressBean.getAddress_id();
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.item_tv_del:
                    if (isCanEdit.equals("0")) {
                        break;
                    }
                    message = new Message();
                    message.what = ConstantManager.ADDRESS_HANDLE_DEL;
                    message.arg1 = position;
                    message.obj = mAddressBean.getAddress_id();
                    mHandler.sendMessage(message);
                    message = null;
                    break;
            }
        }
    };

    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_phone;
        private TextView item_tv_address;
        private TextView item_tv_check;
        private TextView item_tv_moren;
        private TextView item_tv_edit;
        private TextView item_tv_del;

        public ViewHolder(View view) {
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_phone = (TextView) view.findViewById(R.id.item_tv_phone);
            item_tv_address = (TextView) view.findViewById(R.id.item_tv_address);
            item_tv_check = (TextView) view.findViewById(R.id.item_tv_check);
            item_tv_moren = (TextView) view.findViewById(R.id.item_tv_moren);
            item_tv_edit = (TextView) view.findViewById(R.id.item_tv_edit);
            item_tv_del = (TextView) view.findViewById(R.id.item_tv_del);

        }
    }
}
