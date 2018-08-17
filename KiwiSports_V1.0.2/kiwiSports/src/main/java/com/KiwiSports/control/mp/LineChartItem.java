package com.KiwiSports.control.mp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.KiwiSports.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;

public class LineChartItem extends ChartItem {

	private Typeface mTf;

	public LineChartItem(ChartData<?> cd, Context c) {
		super(cd);

		mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
	}

	@Override
	public int getItemType() {
		return TYPE_LINECHART;
	}

	@Override
	public View getView(int position, View convertView, Context c) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();

			convertView = LayoutInflater.from(c).inflate(
					R.layout.list_item_linechart, null);
			holder.chart = (LineChart) convertView.findViewById(R.id.chart);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.chart.setDescription("");
		holder.chart.setDrawGridBackground(false);
		holder.chart.setClickable(false);
		holder.chart.setFocusable(false);
		holder.chart.setEnabled(false);

		XAxis xAxis = holder.chart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);// x轴时间显示位置
		xAxis.setTypeface(mTf);
		xAxis.setDrawGridLines(false);// 每隔线是否显示
		xAxis.setDrawAxisLine(true);// x轴第一条线是否显示
		xAxis.setDrawLabels(true);// x轴label 是否显示
		xAxis.setSpaceBetweenLabels(8);
		xAxis.setLabelsToSkip(mChartData.getXValCount() / 4);

		YAxis leftAxis = holder.chart.getAxisLeft();
		leftAxis.setTypeface(mTf);
		leftAxis.setLabelCount(6, false);

		YAxis rightAxis = holder.chart.getAxisRight();
		rightAxis.setTypeface(mTf);
		rightAxis.setLabelCount(6, false);
		rightAxis.setDrawAxisLine(true);// 右边不显示线
		rightAxis.setDrawLabels(true);// /右边y轴label 是否显示
		// set data
		holder.chart.setData((LineData) mChartData);

		// do not forget to refresh the chart
		// holder.chart.invalidate();
		holder.chart.animateX(750);

		return convertView;
	}

	private static class ViewHolder {
		LineChart chart;
	}
}
