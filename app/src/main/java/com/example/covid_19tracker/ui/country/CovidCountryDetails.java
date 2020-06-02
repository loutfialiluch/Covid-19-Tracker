package com.example.covid_19tracker.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.covid_19tracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class CovidCountryDetails extends AppCompatActivity {
    TextView cases, deaths, recovered, countryName;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_details);
        cases = findViewById(R.id.totalCases);
        deaths = findViewById(R.id.totalDeaths);
        recovered = findViewById(R.id.totalRecovered);
        countryName = findViewById(R.id.countryName);
        Intent intent = getIntent();
        cases.setText(intent.getStringExtra("cases"));
        deaths.setText(intent.getStringExtra("deaths"));
        recovered.setText(intent.getStringExtra("recovered"));
        countryName.setText(intent.getStringExtra("countryName"));

        //BarChart part
        barChart = findViewById(R.id.barChart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new String[]{"", "Cases", "Deaths","Recovered"}));


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, Integer.parseInt(intent.getStringExtra("cases"))));
        barEntries.add(new BarEntry(2, Integer.parseInt(intent.getStringExtra("deaths"))));
        barEntries.add(new BarEntry(3, Integer.parseInt(intent.getStringExtra("recovered"))));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Data set 1");
        barDataSet.setHighlightEnabled(false);
        barDataSet.setColors(getResources().getColor(R.color.colorTotalConfirmed)
                , getResources().getColor(R.color.colorTotalDeaths),
                getResources().getColor(R.color.colorTotalRecovered));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        barChart.setData(barData);

    }
}
