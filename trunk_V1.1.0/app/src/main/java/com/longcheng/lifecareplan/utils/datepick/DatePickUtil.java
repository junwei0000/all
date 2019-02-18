package com.longcheng.lifecareplan.utils.datepick;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

import net.simonvt.numberpicker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Burning on 2018/9/11.
 */

public class DatePickUtil {

    public static SimpleCustomDialog showConfirmExpandDialog(Context context, CharSequence title, View contentView,
                                                             CharSequence lbtText, CharSequence rbtText, DialogBean.OnDialogClickListener lbtnListener,
                                                             DialogBean.OnDialogClickListener rbtnListener) {
        DialogBean dialogBean = new DialogBean(title, lbtText, rbtText, contentView, lbtnListener, rbtnListener,
                DialogBean.STYLE_SIMPLE);
        return baseConfirmDialog(context, dialogBean);
    }

    /**
     * @param context
     * @param dialogBean
     * @return SimpleCustomDialog
     * @MethodName:baseConfirmDialog
     * @Description: 通用的弹框基础方法
     */
    public static SimpleCustomDialog baseConfirmDialog(Context context, DialogBean dialogBean) {
        SimpleCustomDialog.Builder.dialogBean = dialogBean;
        SimpleCustomDialog dialog = new SimpleCustomDialog.Builder(context).create();
        dialog.show();
        return dialog;
    }


    /**
     * @param context
     * @param dateTextView
     * @param defaultDate
     * @param listener     void
     * @MethodName:showDatePickerDialog
     * @Description: 弹出选择日期对话框
     */
    public static void showDatePickerDialog(Context context, final TextView dateTextView,
                                            Calendar defaultDate, final OnDatePickedListener listener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_expand_datepicker, null);
        final DatePickerLayout datePicker = (DatePickerLayout) contentView
                .findViewById(R.id.date_picker_layout);
        if (defaultDate != null) {
            datePicker.setYear(defaultDate.get(Calendar.YEAR));
            datePicker.setMonth(defaultDate.get(Calendar.MONTH));
            datePicker.setMonthOfDay(defaultDate.get(Calendar.DAY_OF_MONTH));
        }
        showConfirmExpandDialog(context, null, contentView, "取消",
                "确认", null, new DialogBean.OnDialogClickListener() {

                    @Override
                    public boolean onClick(View v) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getMonthOfDay();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String pickedDate = sdf.format(calendar.getTime());
                        if (listener != null) {
                            listener.pick(pickedDate);
                        } else {
                            dateTextView.setText(pickedDate);
                        }
                        return false;
                    }
                });

    }

    /**
     * @param dialog void
     * @MethodName:closeDialog
     * @Description: 关闭dialog
     */
    public static void closeDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
