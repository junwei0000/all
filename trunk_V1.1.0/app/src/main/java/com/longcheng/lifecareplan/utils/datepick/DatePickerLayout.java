package com.longcheng.lifecareplan.utils.datepick;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import net.simonvt.numberpicker.NumberPicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.longcheng.lifecareplan.R;


public class DatePickerLayout extends FrameLayout {

    private static final int YEAR_BASE = 1921;
    private NumberPicker mYearPicker;
    private NumberPicker mMonthPicker;
    private NumberPicker mDayPicker;
    // private FreeNumberPicker mYearPicker4;
    // private FreeNumberPicker mMonthPicker4;
    // private FreeNumberPicker mDayPicker4;
    private ArrayList<String> mYearShowArr = new ArrayList<String>();
    private ArrayList<String> mMonthShowArr = new ArrayList<String>();
    private ArrayList<String> mDayShowArr = new ArrayList<String>();
    private OnValueChangeListener mValueChangeLisnter;
    private boolean isNeedValidateDate = true;

    private boolean limit = false;
    private int limitMaxYear = 2049;
    private final static ArrayList<Integer> mBigMonth = new ArrayList<Integer>() {
        {
            add(1);
            add(3);
            add(5);
            add(7);
            add(8);
            add(10);
            add(12);
        }
    };

    private int mSelectedYearIndex = Calendar.getInstance().get(Calendar.YEAR)
            - YEAR_BASE;
    private int mSelectedMonthIndex = Calendar.getInstance()
            .get(Calendar.MONTH);
    private int mSelectedDayIndex = Calendar.getInstance().get(
            Calendar.DAY_OF_MONTH) - 1;

    public DatePickerLayout(Context context) {
        super(context);
        int maxYearOffset = init(null);
        initData();
        // if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
        // {
        // initViews4();
        // } else {
        initViews(maxYearOffset);
        // }
    }

    public DatePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        int maxYearOffset = init(attrs);
        initData();
        // if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
        // {
        // initViews4();
        // } else {
        initViews(maxYearOffset);
        // }
    }

    public DatePickerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        int maxYearOffset = init(attrs);
        initData();
        // if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
        // {
        // initViews4();
        // } else {
        initViews(maxYearOffset);
        // }
    }

    private int init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DatePick, 0, 0);
            limit = a.getBoolean(R.styleable.DatePick_limit, false);
            limitMaxYear = a.getInt(R.styleable.DatePick_maxYear, 2049);
        }
        int maxValue = 0;
        if (limit) {
            maxValue = limitMaxYear - YEAR_BASE;
        } else {
            limitMaxYear = Calendar.getInstance(Locale.CHINESE).get(Calendar.YEAR);
            maxValue = limitMaxYear - YEAR_BASE;
        }
        return maxValue;
    }


    // private void initViews4() {
    // View view = LayoutInflater.from(getContext()).inflate(
    // R.layout.date_pick_layout_4, null);
    // mYearPicker4 = (FreeNumberPicker) view.findViewById(R.id.year_picker);
    // mMonthPicker4 = (FreeNumberPicker) view.findViewById(R.id.month_picker);
    // mDayPicker4 = (FreeNumberPicker) view.findViewById(R.id.day_picker);
    //
    // // to init data
    // // for year
    // mYearPicker4.setMinValue(0);
    // int maxValue = Calendar.getInstance(Locale.CHINESE).get(Calendar.YEAR)
    // - YEAR_BASE;
    // mYearPicker4.setMaxValue(maxValue);
    // mYearPicker4.setValue(mSelectedYearIndex);
    // mYearPicker4.setDisplayedValues(mYearShowArr
    // .toArray(new String[mYearShowArr.size()]));
    // mYearPicker4
    // .setOnValueChangedListener(new
    // android.widget.NumberPicker.OnValueChangeListener() {
    //
    // @Override
    // public void onValueChange(
    // android.widget.NumberPicker picker, int oldVal,
    // int newVal) {
    // mSelectedYearIndex = newVal;
    // resetDayPicker4();
    // callBackValueChange();
    // }
    // });
    //
    // // for month
    // mMonthPicker4.setMinValue(0);
    // mMonthPicker4.setMaxValue(11);
    // mMonthPicker4.setDisplayedValues(mMonthShowArr
    // .toArray(new String[mMonthShowArr.size()]));
    // mMonthPicker4.setValue(mSelectedMonthIndex);
    // mMonthPicker4
    // .setOnValueChangedListener(new
    // android.widget.NumberPicker.OnValueChangeListener() {
    //
    // @Override
    // public void onValueChange(
    // android.widget.NumberPicker picker, int oldVal,
    // int newVal) {
    // if (isNeedValidateDate
    // && newVal > Calendar.getInstance().get(
    // Calendar.MONTH)
    // && (mSelectedYearIndex + YEAR_BASE) >= Calendar
    // .getInstance().get(Calendar.YEAR)) {
    // mSelectedMonthIndex = Calendar.getInstance().get(
    // Calendar.MONTH);
    // mMonthPicker4.setValue(mSelectedMonthIndex);
    // } else {
    // mSelectedMonthIndex = newVal;
    // }
    // resetDayPicker4();
    // callBackValueChange();
    // }
    // });
    //
    // // for days
    // resetDayPicker4();
    // mDayPicker4
    // .setOnValueChangedListener(new
    // android.widget.NumberPicker.OnValueChangeListener() {
    //
    // @Override
    // public void onValueChange(
    // android.widget.NumberPicker picker, int oldVal,
    // int newVal) {
    // if (isNeedValidateDate
    // && newVal > Calendar.getInstance().get(
    // Calendar.DAY_OF_MONTH) - 1
    // && (mSelectedYearIndex + YEAR_BASE) >= Calendar
    // .getInstance().get(Calendar.YEAR)) {
    // mSelectedDayIndex = Calendar.getInstance().get(
    // Calendar.DAY_OF_MONTH) - 1;
    // mDayPicker4.setValue(mSelectedDayIndex);
    // } else {
    // mSelectedDayIndex = newVal;
    // }
    // callBackValueChange();
    // }
    // });
    // this.addView(view);
    // }

    private void initViews(int maxValue) {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.date_pick_layout, null);
//		ImageView iv=(ImageView) view.findViewById(R.id.iv_numberpicker);
//		iv.setImageResource(R.color.transparent);
        mYearPicker = (NumberPicker) view.findViewById(R.id.year_picker);
        mYearPicker.getInputeditText().setFocusable(false);
        mMonthPicker = (NumberPicker) view.findViewById(R.id.month_picker);
        mMonthPicker.getInputeditText().setFocusable(false);
        mDayPicker = (NumberPicker) view.findViewById(R.id.day_picker);
        mDayPicker.getInputeditText().setFocusable(false);
        // to init data
        // for year
        mYearPicker.setMinValue(0);
        mYearPicker.setMaxValue(maxValue);
        mYearPicker.setValue(mSelectedYearIndex);
        mYearPicker.setDisplayedValues(mYearShowArr
                .toArray(new String[mYearShowArr.size()]));
        mYearPicker
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        mSelectedYearIndex = newVal;
                        resetDayPicker();
                        callBackValueChange();
                    }
                });

        // for month
        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(11);
        mMonthPicker.setDisplayedValues(mMonthShowArr
                .toArray(new String[mMonthShowArr.size()]));
        mMonthPicker.setValue(mSelectedMonthIndex);
        mMonthPicker
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        if (isNeedValidateDate
                                && newVal > Calendar.getInstance().get(
                                Calendar.MONTH)
                                && (mSelectedYearIndex + YEAR_BASE) >= Calendar
                                .getInstance().get(Calendar.YEAR)) {
                            mSelectedMonthIndex = Calendar.getInstance().get(
                                    Calendar.MONTH);
                            mMonthPicker.setValue(mSelectedMonthIndex);
                        } else {
                            mSelectedMonthIndex = newVal;
                        }
                        resetDayPicker();
                        callBackValueChange();
                    }
                });

        // for days
        resetDayPicker();
        mDayPicker
                .setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal,
                                              int newVal) {
                        if (isNeedValidateDate
                                && newVal > Calendar.getInstance().get(
                                Calendar.DAY_OF_MONTH) - 1
                                && (mSelectedYearIndex + YEAR_BASE) >= Calendar
                                .getInstance().get(Calendar.YEAR)) {
                            mSelectedDayIndex = Calendar.getInstance().get(
                                    Calendar.DAY_OF_MONTH) - 1;
                            mDayPicker.setValue(mSelectedDayIndex);
                        } else {
                            mSelectedDayIndex = newVal;
                        }
                        callBackValueChange();
                    }
                });
        this.addView(view);
    }

    private void callBackValueChange() {
        if (mValueChangeLisnter != null) {
            mValueChangeLisnter.onValueChange(mSelectedYearIndex + YEAR_BASE,
                    mSelectedMonthIndex, mSelectedDayIndex + 1);
        }
    }

    // private void resetDayPicker4() {
    // int maxDays;
    // int selectMonth = mSelectedMonthIndex + 1;
    // if (mBigMonth.contains((selectMonth))) {
    // maxDays = 31;
    // } else {
    // if (selectMonth == 2) {
    // if (isLeapYear((mSelectedYearIndex + YEAR_BASE))) {
    // maxDays = 29;
    // } else {
    // maxDays = 28;
    // }
    // } else {
    // maxDays = 30;
    // }
    //
    // }
    // mDayPicker4.setDisplayedValues(null);
    // mDayPicker4.setMinValue(0);
    // mDayPicker4.setMaxValue(maxDays - 1);
    // resetDayShowArr(maxDays);
    // mDayPicker4.setDisplayedValues(mDayShowArr
    // .toArray(new String[mDayShowArr.size()]));
    // mDayPicker4.setValue(mSelectedDayIndex < maxDays ? mSelectedDayIndex
    // : (maxDays - 1));
    // }

    private void resetDayPicker() {
        int maxDays;
        int selectMonth = mSelectedMonthIndex + 1;
        if (mBigMonth.contains((selectMonth))) {
            maxDays = 31;
        } else {
            if (selectMonth == 2) {
                if (isLeapYear((mSelectedYearIndex + YEAR_BASE))) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
            } else {
                maxDays = 30;
            }

        }
        mDayPicker.setDisplayedValues(null);
        mDayPicker.setMinValue(0);
        mDayPicker.setMaxValue(maxDays - 1);
        resetDayShowArr(maxDays);
        mDayPicker.setDisplayedValues(mDayShowArr
                .toArray(new String[mDayShowArr.size()]));
        mDayPicker.setValue(mSelectedDayIndex < maxDays ? mSelectedDayIndex
                : (maxDays - 1));

    }

    public void setNeedValidateDate(boolean isNeedValidate) {
        isNeedValidateDate = isNeedValidate;
    }

    private void resetDayShowArr(int maxDays) {
        mDayShowArr.clear();
        for (int i = 1; i < (maxDays + 1); i++) {
            mDayShowArr.add(getContext().getString(R.string.day, i));
        }
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        else
            return false;
    }

    private void initData() {
//		int max = Calendar.getInstance(Locale.CHINESE).get(Calendar.YEAR);
        int max = limitMaxYear;
        for (int year = YEAR_BASE; year <= max; year++) {
            mYearShowArr.add(getContext().getString(R.string.year, year));
        }

        for (int month = 1; month < 13; month++) {
            mMonthShowArr.add(getContext().getString(R.string.month, month));
        }

    }

    /**
     * return select year
     *
     * @return
     */
    public int getYear() {
        return mSelectedYearIndex + YEAR_BASE;
    }

    /**
     * set year from 1900 to current year
     *
     * @param year
     */

    public void setYear(int year) {
        int value = year - YEAR_BASE;
        if (value < 0 || value > mYearPicker.getMaxValue()) {
            throw new IllegalArgumentException("set year is illegal");
        }
        mSelectedYearIndex = value;
        mYearPicker.setValue(value);
    }

    /**
     * return select month,which is start from 0 to 11
     *
     * @return
     */
    public int getMonth() {
        return mSelectedMonthIndex;
    }

    /**
     * set month from 0 to 11
     *
     * @param month
     */
    public void setMonth(int month) {
        int value = month;
        if (value < 0 || value > mMonthPicker.getMaxValue()) {
            throw new IllegalArgumentException("set month is illegal");
        }
        mSelectedMonthIndex = value;
        mMonthPicker.setValue(value);
    }

    /**
     * return day of month
     *
     * @return
     */
    public int getMonthOfDay() {
        return mSelectedDayIndex + 1;
    }

    /**
     * set month of day from 1 to 31(or 30 or 29 or 28) order by current month
     *
     * @param day
     */
    public void setMonthOfDay(int day) {
        int value = day - 1;
        if (value < 0 || value > mDayPicker.getMaxValue()) {
            throw new IllegalArgumentException("set month of day is illegal");
        }
        mSelectedDayIndex = value;
        mDayPicker.setValue(mSelectedDayIndex);
    }

    public interface OnValueChangeListener {
        void onValueChange(int year, int month, int day);
    }

    public void setOnValueChangeListener(
            OnValueChangeListener onValueChangeListener) {
        this.mValueChangeLisnter = onValueChangeListener;
    }

}
