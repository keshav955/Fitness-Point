package com.example.hunny.fitnesspoint;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Pie_chart extends AppCompatActivity {

    PieChart pie_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pie_chart = (PieChart) findViewById(R.id.pie_chart);
        pie_chart.setBackgroundColor(Color.WHITE);
        moveOffScreen();

        pie_chart.setUsePercentValues(false);
        pie_chart.getDescription().setEnabled(false);
        pie_chart.setExtraOffsets(5,10,5,5);

        pie_chart.setMaxAngle(180);
        pie_chart.setRotationAngle(180);

        pie_chart.setCenterTextOffset(0, -20);

        pie_chart.animateY(1000, Easing.EasingOption.EaseInCubic);
        /*pie_chart.setDragDecelerationFrictionCoef(0.95f);*/

        Legend l = pie_chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pie_chart.setEntryLabelColor(Color.WHITE);
        pie_chart.setEntryLabelTextSize(12f);

        pie_chart.setRotationEnabled(false);
        pie_chart.setHighlightPerTapEnabled(true);

        pie_chart.setDrawHoleEnabled(true);
        pie_chart.setHoleColor(Color.WHITE);
        pie_chart.setTransparentCircleRadius(60f);

        pie_chart.setEntryLabelColor(Color.WHITE);
        pie_chart.setEntryLabelTextSize(10f);

        ArrayList<PieEntry> Values = new ArrayList<>();

        Values.add(new PieEntry(31f, "obesed"));
        Values.add(new PieEntry(30f, "Over Weight"));
        Values.add(new PieEntry(24.9f, "Normal"));
        Values.add(new PieEntry(18f, "Under Weight"));

        PieDataSet dataSet = new PieDataSet(Values,"BMI");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pie_chart.setData(data);
        pie_chart.invalidate();
    }

    private void moveOffScreen() {

        Display display = getWindowManager().getDefaultDisplay();
        display.getHeight();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;  // deprecated


        int offset =(int)(height * 0.8); /* percent to move */

        RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) pie_chart.getLayoutParams();
        Params.setMargins(0, 0, 0, -offset);
        pie_chart.setLayoutParams(Params);

    }
}
