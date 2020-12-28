package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import com.example.testapp.network.TestResponse;
import com.example.testapp.viewModel.TestViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    LineChart lineChart,lineChart2,lineChart3;
    TextView txtValueOne, txtValueTwo, txtValueThree;
    View backgroundViewOne, backgroundViewTwo, backgroundViewThree;
    String backColor1, backColor2, backColor3;
    TestViewModel testViewModel;

    List<TestResponse> list = new ArrayList<>();
    List<Entry> entries_RSRP ;
    List<Entry> entries_RSRP_s1 ;
    List<Entry> entries_RSRP_s2 ;
    List<Entry> entries_RSRQ;
    List<Entry> entries_RSRQ_s1;
    List<Entry> entries_RSRQ_s2;
    List<Entry> entries_SINR;
    List<Entry> entries_SINR_s1;
    List<Entry> entries_SINR_s2;
    XAxis xAxis;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.lineChart_one);
        lineChart2 = findViewById(R.id.lineChart_two);
        lineChart3 = findViewById(R.id.lineChart_three);

        entries_RSRP = new ArrayList<>();
        entries_RSRP_s1 = new ArrayList<>();
        entries_RSRP_s2= new ArrayList<>();

        entries_RSRQ = new ArrayList<>();
        entries_RSRQ_s1 = new ArrayList<>();
        entries_RSRQ_s2= new ArrayList<>();

        entries_SINR = new ArrayList<>();
        entries_SINR_s1 = new ArrayList<>();
        entries_SINR_s2= new ArrayList<>();



        settingLineChartOne();
        settingLineChartTwo();
        settingLineChartThree();

        txtValueOne = findViewById(R.id.txt_valueOne);
        txtValueTwo = findViewById(R.id.txt_valueTwo);
        txtValueThree = findViewById(R.id.txt_valueThree);
        backgroundViewOne = findViewById(R.id.back_view1);
        backgroundViewTwo = findViewById(R.id.back_view2);
        backgroundViewThree = findViewById(R.id.back_view3);

        testViewModel = ViewModelProviders.of(MainActivity.this).get(TestViewModel.class);
        testViewModel.init();
        testViewModel.getResponse();
        backColor1 = "#0B440D";
        backColor2 = "#ffee00";
        backColor3 = "#FBFD00";

        //getData();

        runnable.run();


    }

    private void setValues(TestResponse testResponse) {
        txtValueOne.setText(String.valueOf(testResponse.getRSRP()));
        txtValueTwo.setText(String.valueOf(testResponse.getRSRQ()));
        txtValueThree.setText(String.valueOf(testResponse.getSINR()));

    }



    private void getData() {
        testViewModel.getResponse();
        testViewModel.getLiveResponse().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse response) {

                if (response != null) {
                    setValues(response);
                    initColor(response);

                    ///////////

                        list.add(response);

                        float i = 0;
                        for (TestResponse testResponse : list) {
                            if (list.size() > 0) {
                                i += 5;
                                entries_RSRP.add(new Entry(i, (float) testResponse.getRSRP()));
                                entries_RSRQ.add(new Entry(i, (float) testResponse.getRSRQ()));
                                entries_SINR.add(new Entry(i, (float) testResponse.getSINR()));

                            }
                        }
                        for (TestResponse testResponse : list) {
                            if (list.size() > 0) {
                                i += 5;
                                entries_RSRP_s1.add(new Entry(i, (float) testResponse.getRSRP()));
                                entries_RSRQ_s1.add(new Entry(i, (float) testResponse.getRSRQ()));
                                entries_SINR_s1.add(new Entry(i, (float) testResponse.getSINR()));
                            }
                        }
                        for (TestResponse testResponse : list) {
                            if (list.size() > 0) {
                                i += 5;
                                entries_RSRP_s2.add(new Entry(i, (float) testResponse.getRSRP()));
                                entries_RSRQ_s2.add(new Entry(i, (float) testResponse.getRSRQ()));
                                entries_SINR_s2.add(new Entry(i, (float) testResponse.getSINR()));
                            }
                        }
                        methodSettingOne();
                        methodSettingTwo();
                        methodSettingThree();


                    //////////////

                } else {
                    Log.d("Aly", "onChanged: response is null ");
                }
            }
        });
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getData();
            initBackgroundColor();
            handler.postDelayed(runnable, 2000);
        }
    };



    private void initBackgroundColor() {
        GradientDrawable gradientDrawable1 = (GradientDrawable) backgroundViewOne.getBackground();
        gradientDrawable1.setColor(Color.parseColor(backColor1));
        GradientDrawable gradientDrawable2 = (GradientDrawable) backgroundViewTwo.getBackground();
        gradientDrawable2.setColor(Color.parseColor(backColor2));
        GradientDrawable gradientDrawable3 = (GradientDrawable) backgroundViewThree.getBackground();
        gradientDrawable3.setColor(Color.parseColor(backColor3));
    }

    private void initColor(TestResponse testResponse) {
        double value1 = testResponse.getRSRP();
        double value2 = testResponse.getRSRQ();
        double value3 = testResponse.getSINR();

        if (value1 < (-110)) {
            backColor1 = "#000A00";
        } else if (value1 < (-100)) {
            backColor1 = "#E51304";
        } else if (value1 < (-90)) {
            backColor1 = "#FAFD0C";
        } else if (value1 < (-80)) {
            backColor1 = "#02FA0E";
        } else if (value1 < (-70)) {
            backColor1 = "#0B440D";
        } else if (value1 < (-60)) {
            backColor1 = "#0EFFF8";
        } else if (value1 > (-60)) {
            backColor1 = "#0007FF";
        }

        if (value2 < (-19.5)) {
            backColor2 = "#000A00";
        } else if (value2 < (-14)) {
            backColor2 = "#ff0000";
        } else if (value2 < (-9)) {
            backColor2 = "#ffee00";
        } else if (value2 < (-3)) {
            backColor2 = "#80ff00";
        } else if (value2 > (-3)) {
            backColor2 = "#3f7806";
        }

        if (value3 < (0)) {
            backColor3 = "#000A00";
        } else if (value3 < 5) {
            backColor3 = "#F90500";
        } else if (value3 < 10) {
            backColor3 = "#FD7632";
        } else if (value3 < 15) {
            backColor3 = "#FBFD00";
        } else if (value3 < 20) {
            backColor3 = "#00FF06";
        } else if (value3 < 25) {
            backColor3 = "#027500";
        } else if (value3 < 30) {
            backColor3 = "#0EFFF8";
        } else if (value3 > 30) {
            backColor3 = "#0000F0";
        }


    }
    private void settingLineChartOne(){
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return new SimpleDateFormat("mm:ss").format(new Date());
            }
        });
    }
    private void settingLineChartTwo(){
        lineChart2.getAxisRight().setDrawAxisLine(false);
        lineChart2.getAxisRight().setEnabled(false);
        lineChart2.getDescription().setEnabled(false);
        xAxis = lineChart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return new SimpleDateFormat("mm:ss").format(new Date());
            }
        });
    }
    private void settingLineChartThree(){
        lineChart3.getAxisRight().setDrawAxisLine(false);
        lineChart3.getAxisRight().setEnabled(false);
        lineChart3.getDescription().setEnabled(false);
        xAxis = lineChart3.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return new SimpleDateFormat("mm:ss").format(new Date());
            }
        });
    }
    private void methodSettingOne(){
        /////////////////
        LineDataSet dataSet = new LineDataSet(entries_RSRP, "RSRP");
        LineDataSet dataSet_s1= new LineDataSet(entries_RSRP_s1, "RSRP s1");
        LineDataSet dataSet_s2 = new LineDataSet(entries_RSRP_s2, "RSRP s2");

        dataSet.setFillColor(Color.BLUE);
        dataSet.setColor(Color.WHITE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setCircleHoleColor(Color.BLUE);
        dataSet.setLineWidth(.5f);
        dataSet.setCircleSize(3f);
        dataSet.setDrawValues(false);



        dataSet_s1.setFillColor(Color.RED);
        dataSet_s1.setColor(Color.WHITE);
        dataSet_s1.setCircleColor(Color.RED);
        dataSet_s1.setCircleHoleColor(Color.RED);
        dataSet_s1.setLineWidth(.5f);
        dataSet_s1.setCircleSize(3f);
        dataSet_s1.setDrawValues(false);


        dataSet_s2.setFillColor(Color.GREEN);
        dataSet_s2.setColor(Color.WHITE);
        dataSet_s2.setCircleColor(Color.GREEN);
        dataSet_s2.setCircleHoleColor(Color.GREEN);
        dataSet_s2.setLineWidth(.5f);
        dataSet_s2.setCircleSize(3f);
        dataSet_s2.setDrawValues(false);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        iLineDataSets.add(dataSet_s1);
        iLineDataSets.add(dataSet_s2);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();



        ///////////////////

    }
    private void methodSettingTwo(){
        /////////////////
        LineDataSet dataSet = new LineDataSet(entries_RSRQ, "RSRQ");
        LineDataSet dataSet_s1= new LineDataSet(entries_RSRQ_s1, "RSRQ s1");
        LineDataSet dataSet_s2 = new LineDataSet(entries_RSRQ_s2, "RSRQ s2");

        dataSet.setFillColor(Color.BLUE);
        dataSet.setColor(Color.WHITE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setCircleHoleColor(Color.BLUE);
        dataSet.setLineWidth(.5f);
        dataSet.setCircleSize(3f);
        dataSet.setDrawValues(false);



        dataSet_s1.setFillColor(Color.RED);
        dataSet_s1.setColor(Color.WHITE);
        dataSet_s1.setCircleColor(Color.RED);
        dataSet_s1.setCircleHoleColor(Color.RED);
        dataSet_s1.setLineWidth(.5f);
        dataSet_s1.setCircleSize(3f);
        dataSet_s1.setDrawValues(false);


        dataSet_s2.setFillColor(Color.GREEN);
        dataSet_s2.setColor(Color.WHITE);
        dataSet_s2.setCircleColor(Color.GREEN);
        dataSet_s2.setCircleHoleColor(Color.GREEN);
        dataSet_s2.setLineWidth(.5f);
        dataSet_s2.setCircleSize(3f);
        dataSet_s2.setDrawValues(false);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        iLineDataSets.add(dataSet_s1);
        iLineDataSets.add(dataSet_s2);

        LineData lineData = new LineData(iLineDataSets);
        lineChart2.setData(lineData);
        lineChart2.invalidate();



        ///////////////////

    }
    private void methodSettingThree(){
        /////////////////
        LineDataSet dataSet = new LineDataSet(entries_SINR, "SINR");
        LineDataSet dataSet_s1= new LineDataSet(entries_SINR_s1, "SINR s1");
        LineDataSet dataSet_s2 = new LineDataSet(entries_SINR_s2, "SINR s2");

        dataSet.setFillColor(Color.BLUE);
        dataSet.setColor(Color.WHITE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setCircleHoleColor(Color.BLUE);
        dataSet.setLineWidth(.0f);
        dataSet.setCircleSize(3f);
        dataSet.setDrawValues(false);



        dataSet_s1.setFillColor(Color.RED);
        dataSet_s1.setColor(Color.WHITE);
        dataSet_s1.setCircleColor(Color.RED);
        dataSet_s1.setCircleHoleColor(Color.RED);
        dataSet_s1.setLineWidth(.0f);
        dataSet_s1.setCircleSize(3f);
        dataSet_s1.setDrawValues(false);


        dataSet_s2.setFillColor(Color.GREEN);
        dataSet_s2.setColor(Color.WHITE);
        dataSet_s2.setCircleColor(Color.GREEN);
        dataSet_s2.setCircleHoleColor(Color.GREEN);
        dataSet_s2.setLineWidth(.0f);
        dataSet_s2.setCircleSize(3f);
        dataSet_s2.setDrawValues(false);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);
        iLineDataSets.add(dataSet_s1);
        iLineDataSets.add(dataSet_s2);

        LineData lineData = new LineData(iLineDataSets);
        lineChart3.setData(lineData);
        lineChart3.invalidate();



        ///////////////////

    }
}
