package com.badri.drivingl;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class progress  extends AppCompatActivity {

    TextView tvR, tvPython, tvCPP, tvJava , statsfinished;
    PieChart pieChart;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Link those objects with their respective
        // id's that we have given in .XML file

        tvR = findViewById(R.id.tvR);
        tvPython = findViewById(R.id.tvPython);
        /*tvCPP = findViewById(R.id.tvCPP);
        tvJava = findViewById(R.id.tvJava);*/
        pieChart = findViewById(R.id.piechart);
        statsfinished = findViewById(R.id.statsfinished);

        // Creating a method setData()
        // to set the text in text view and pie chart
        setData();


    }



    public static final String FILE_NAME = "statistics.txt";
    public static final String FILE_NAME2 = "statistics2.txt";

    public static int loadStatsCorrect;
    public static int loadStatsFalse;
    public void setData()
    {
        try {
            BufferedReader brTest = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME));
            BufferedReader brTest2 = new BufferedReader(new FileReader(getFilesDir() + "/" + FILE_NAME2));
            loadStatsCorrect = Integer.parseInt(brTest.readLine());
            loadStatsFalse = Integer.parseInt(brTest2.readLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Set the percentage of language used
        tvR.setText(Integer.toString(loadStatsCorrect));
        tvPython.setText(Integer.toString(loadStatsFalse));
        /*tvCPP.setText(Integer.toString(5));
        tvJava.setText(Integer.toString(25));*/

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "სწორი პასუხები",
                        Integer.parseInt(tvR.getText().toString()),
                        Color.parseColor("#00ff00")));
        pieChart.addPieSlice(
                new PieModel(
                        "არასწორი პასუხები",
                        Integer.parseInt(tvPython.getText().toString()),
                        Color.parseColor("#ff0000")));
        tvR.setText("სწორი პასუხები - " + loadStatsCorrect);
        tvPython.setText("არასწორი პასუხები - " + loadStatsFalse);
        /*pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt(tvCPP.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt(tvJava.getText().toString()),
                        Color.parseColor("#29B6F6")));*/

        // To animate the pie chart
        pieChart.startAnimation();
    }


}
