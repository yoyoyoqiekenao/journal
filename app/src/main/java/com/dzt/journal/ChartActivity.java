package com.dzt.journal;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.dzt.journal.base.ActivityBase;
import com.dzt.journal.utils.JLogUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


public class ChartActivity extends ActivityBase implements OnChartValueSelectedListener {

	@ViewInject(R.id.pie_chart)
	private PieChart pieChart;
	protected Typeface mTfRegular;
	protected Typeface mTfLight;
	protected String[] mParties = new String[]{
			"Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
			"Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
			"Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
			"Party Y", "Party Z"
	};

	@Override
	protected int getLayoutId() {
		return R.layout.activity_chart;
	}

	@Override
	protected void initParams(Bundle savedInstanceState) {
		mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
		pieChart.setUsePercentValues(true);
		pieChart.setEnabled(false);
		pieChart.setExtraOffsets(5, 10, 5, 5);
		pieChart.setDragDecelerationFrictionCoef(0.95f);

		pieChart.setCenterTextTypeface(mTfLight);
		pieChart.setCenterText(generateCenterSpannableText());

		pieChart.setDrawHoleEnabled(true);
		pieChart.setHoleColor(Color.WHITE);

		pieChart.setTransparentCircleColor(Color.WHITE);
		pieChart.setTransparentCircleAlpha(110);

		pieChart.setHoleRadius(58f);
		pieChart.setTransparentCircleRadius(61f);

		pieChart.setDrawCenterText(true);

		pieChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		pieChart.setRotationEnabled(true);
		pieChart.setHighlightPerTapEnabled(true);

		// pieChart.setUnit(" €");
		// pieChart.setDrawUnitsInChart(true);

		// add a selection listener
		pieChart.setOnChartValueSelectedListener(this);
		setData(4, 100);
		pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

		// entry label styling
		pieChart.setEntryLabelColor(Color.WHITE);
		pieChart.setEntryLabelTypeface(mTfRegular);
		pieChart.setEntryLabelTextSize(12f);
	}

	private void setData(int count, float range) {
		float mult = range;
		ArrayList<PieEntry> entries = new ArrayList<>();
		// NOTE: The order of the entries when being added to the entries array determines their position around the center of
		// the chart.
		for (int i = 0; i < count; i++) {
			entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
					mParties[i % mParties.length],
					ContextCompat.getDrawable(this, R.mipmap.star)));
		}

		PieDataSet dataSet = new PieDataSet(entries, "Election Results");
		dataSet.setDrawIcons(false);
		dataSet.setSliceSpace(3f);
		dataSet.setIconsOffset(new MPPointF(0, 40));
		dataSet.setSelectionShift(5f);
		// add a lot of colors
		ArrayList<Integer> colors = new ArrayList<>();
		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());
		dataSet.setColors(colors);
		//dataSet.setSelectionShift(0f);
		PieData data = new PieData(dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.WHITE);
		data.setValueTypeface(mTfLight);
		pieChart.setData(data);
		// undo all highlights
		pieChart.highlightValues(null);
		pieChart.invalidate();
	}

	private SpannableString generateCenterSpannableText() {
		SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
		s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
		s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
		s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
		s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
		s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
		s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
		return s;
	}

	@Override
	public void onValueSelected(Entry e, Highlight h) {
		if (null == e)
			return;
		JLogUtils.getInstance().i("value = " + e.getY() + " ,index = " + h.getX()
				+ " ,DataSet index = " + h.getDataSetIndex());
	}

	@Override
	public void onNothingSelected() {
		JLogUtils.getInstance().i("nothing selected");
	}
}
