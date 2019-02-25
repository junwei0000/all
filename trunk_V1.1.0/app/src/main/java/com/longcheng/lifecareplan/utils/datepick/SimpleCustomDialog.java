package com.longcheng.lifecareplan.utils.datepick;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

public class SimpleCustomDialog extends Dialog {

    public SimpleCustomDialog(Context context) {
        super(context);
    }

    public SimpleCustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder implements View.OnClickListener {
        public static DialogBean dialogBean;

        private TextView bt_left;// 左边按钮

        private TextView bt_right;// 右边按钮

        private View mView;// 扩展View

        private LinearLayout ll_container;// 扩展view的容器

        private int layoutId;

        private Context context;

        public SimpleCustomDialog dialog;

        private View view_line_hor;// 竖向分割线
        private View view_hor_div;

        private TextView tv_title;

        public Builder(Context context) {
            this.context = context;
        }

        public SimpleCustomDialog create() {
            int style = dialogBean.getStyle();
            // switch (style)
            // {
            // case DialogBean.STYLE_SIMPLE:
            layoutId = R.layout.dialog_custom_style_simple;
            // break;
            // case DialogBean.STYLE_TITLE:
            // layoutId = R.layout.dialog_custom_style_title;
            // break;
            // default:
            // layoutId = R.layout.dialog_custom_style_simple;
            // break;
            // }
            // 加载通用布局
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(layoutId, null);
            view_line_hor = dialogView.findViewById(R.id.view_line_hor);
            view_hor_div = dialogView.findViewById(R.id.view_hor_divder);
            tv_title = (TextView) dialogView.findViewById(R.id.tv_title);
            bt_left = (TextView) dialogView.findViewById(R.id.bt_left);
            bt_right = (TextView) dialogView.findViewById(R.id.bt_right);
            ll_container = (LinearLayout) dialogView
                    .findViewById(R.id.ll_container);

            // 填充扩展布局
            mView = dialogBean.getContentView();
            ll_container.removeAllViews();
            if (mView != null) {
                ll_container.addView(getContentView(mView));
            } else {
                ll_container.setVisibility(View.GONE);
            }

            // 设置dialog样式
            dialog = new SimpleCustomDialog(context, R.style.custom_dialog);
            // 设置dialog整体布局
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            dialog.setContentView(dialogView, layoutParams);

            // 设置数据信息
            CharSequence titleText = dialogBean.getTitle();
            CharSequence lbtText = dialogBean.getLbtText();
            CharSequence rbtText = dialogBean.getRbtText();

            if (!TextUtils.isEmpty(titleText)) {
                tv_title.setText(titleText);
            } else {
                tv_title.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(lbtText)) {
                bt_left.setVisibility(View.GONE);
                view_line_hor.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rbtText)) {
                    view_line_hor.setVisibility(View.GONE);
                    bt_right.setVisibility(View.GONE);
                } else {
//					bt_right.setBackgroundResource(R.drawable.dialog_btn_single_selecter);
                    bt_right.setText(rbtText);
                }
            } else {
                bt_left.setText(lbtText);
                if (TextUtils.isEmpty(rbtText)) {
                    view_line_hor.setVisibility(View.GONE);
//					bt_left.setBackgroundResource(R.drawable.dialog_btn_single_selecter);
                    bt_right.setVisibility(View.GONE);
                } else {
                    bt_right.setText(rbtText);
                }
            }

//			if (TextUtils.isEmpty(lbtText)) {
//				bt_left.setVisibility(View.GONE);
//				view_line_hor.setVisibility(View.GONE);
//				bt_right.setBackgroundResource(R.drawable.dialog_btn_single_selecter);
//				if (TextUtils.isEmpty(rbtText)) {
//					view_hor_div.setVisibility(View.GONE);
//					bt_right.setVisibility(View.GONE);
//				} else {
//					bt_right.setText(rbtText);
//				}
//			} else {
//				((TextView) bt_left).setText(lbtText);
//			}
//			if (TextUtils.isEmpty(rbtText)) {
//				bt_right.setVisibility(View.GONE);
//				view_line_hor.setVisibility(View.GONE);
//				bt_left.setBackgroundResource(R.drawable.dialog_btn_single_selecter);
//			} else {
//				bt_right.setText(rbtText);
//			}
            // 设置监听
            setListener();
            return dialog;
        }

        private View getContentView(View mView) {
            if (mView instanceof ViewGroup) {
                if (mView.getLayoutParams() == null) {
                    LayoutParams params = new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT);
                    mView.setLayoutParams(params);
                }
            }
            return mView;
        }

        private void setListener() {
            bt_left.setOnClickListener(this);
            bt_right.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_left:
                    DialogBean.OnDialogClickListener lbtnListener = dialogBean
                            .getLbtnListener();
                    if (lbtnListener == null) {
                        dialog.dismiss();
                    } else {
                        if (!lbtnListener.onClick(v)) {
                            dialog.dismiss();
                        }
                    }
                    break;
                case R.id.bt_right:
                    DialogBean.OnDialogClickListener rbtnListener = dialogBean
                            .getRbtnListener();
                    if (rbtnListener == null) {
                        dialog.dismiss();
                    } else {
                        if (!rbtnListener.onClick(v)) {
                            dialog.dismiss();
                        }
                    }
                    break;

                default:
                    break;
            }
        }

    }


}
