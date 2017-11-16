package chart.charles.com.simplechart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chart.charles.com.simplechart.chart.LineChartFragment;
import chart.charles.com.simplechart.datamodel.ChartData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChartFragment  fragment = (LineChartFragment) getSupportFragmentManager().findFragmentById(R.id.chart_fragment);

        List<ChartData> list = new ArrayList<>();
        list.add(new ChartData("00:00", "0.00"));
        list.add(new ChartData("01:00", "1.00"));
        list.add(new ChartData("02:00", "2.00"));
        list.add(new ChartData("03:00", "3.00"));
        list.add(new ChartData("04:00", "4.00"));
        list.add(new ChartData("05:00", "10.00"));
        list.add(new ChartData("06:00", "15.00"));
        list.add(new ChartData("07:00", "20.00"));
        list.add(new ChartData("09:00", "12.00"));
        list.add(new ChartData("10:00", "12.00"));
        list.add(new ChartData("11:00", "12.00"));
        list.add(new ChartData("12:00", "12.00"));
        list.add(new ChartData("13:00", "12.00"));
        list.add(new ChartData("14:00", "12.00"));
        list.add(new ChartData("15:00", "12.00"));
        list.add(new ChartData("16:00", "12.00"));
        list.add(new ChartData("17:00", "12.00"));
        list.add(new ChartData("18:00", "0.00"));
        list.add(new ChartData("19:00", "12.00"));
        list.add(new ChartData("20:00", "16.00"));
        list.add(new ChartData("21:00", "19.00"));
        list.add(new ChartData("22:00", "12.00"));
        list.add(new ChartData("23:00", "28.00"));

        fragment.setStartDate(new Date());
        fragment.setLineChartData(list);

    }
}
