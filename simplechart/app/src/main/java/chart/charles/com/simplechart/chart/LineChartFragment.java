package chart.charles.com.simplechart.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import chart.charles.com.simplechart.R;
import chart.charles.com.simplechart.datamodel.ChartData;
import chart.charles.com.simplechart.utils.TimeUtils;


/**
 * <b>Project:</b> project_svm_replenish<br>
 * <b>Create Date:</b> 2017/9/7<br>
 * <b>Author:</b> Charles <br>
 * <b>Description:</b>折线图 销售趋势 <br>
 */
public class LineChartFragment extends Fragment implements View.OnClickListener {

    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());

    private LineChart mChart;
    private DateTimeXAxisValueFormatter mXAxisValueFormatter;
    private Date startDate = new Date();
    private Calendar mCalendar = Calendar.getInstance();
    private TopMarkerView mMarkerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_line_chart_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart = (LineChart) view.findViewById(R.id.chart1);

        initChart();
    }

    private void initChart() {

        mChart.setRenderer(new CustomLineChartRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler()));

        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        mChart.setExtraTopOffset(30);
        mChart.setExtraBottomOffset(10);
        mChart.setBackgroundResource(android.R.color.white);


        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.getLegend().setEnabled(false); //设置表头不可用
        mChart.setHardwareAccelerationEnabled(true);
        mChart.setNoDataText("");

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        mMarkerView = new TopMarkerView(getActivity());
        mMarkerView.setChartView(mChart); // For bounds control
        mChart.setMarker(mMarkerView); // Set the marker to the chart

        XAxis xAxis = mChart.getXAxis(); //x轴
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_grey));
        xAxis.setAxisLineColor(ContextCompat.getColor(getActivity(), R.color.light_grey));
        xAxis.setGridColor(ContextCompat.getColor(getActivity(), R.color.light_grey));//设置x轴表格颜色

        mXAxisValueFormatter = new DateTimeXAxisValueFormatter();
        xAxis.setValueFormatter(mXAxisValueFormatter);

        xAxis.setAxisMaximum(12);
        xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(12);

        YAxis leftAxis = mChart.getAxisLeft(); // Y轴

        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawZeroLine(true); //设置显示Y轴表格线
        leftAxis.setDrawGridLines(true);//设置是否显示横轴表格
        leftAxis.setDrawLabels(true);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value) + "件";
            }
        });
        leftAxis.setGridColor(ContextCompat.getColor(getActivity(), R.color.light_grey)); //设置Y轴表格线颜色
        leftAxis.setTextColor(ContextCompat.getColor(getActivity(), R.color.light_grey));

        leftAxis.setAxisMaximum(5);
        leftAxis.setAxisMinimum(0);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(true);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setLineChartData(List<ChartData> salesamount) {
        if (salesamount == null || salesamount.isEmpty()) {
            mChart.clear();
            return;
        }
        if (isEntityZero(salesamount)) {
            mChart.clear();
            return;
        }
        mXAxisValueFormatter.setRange(salesamount);

        long xMin = 0;
        long xMax = 24;
        int count = 9;
        mChart.getXAxis().setAxisMaximum(xMax);
        mChart.getXAxis().setAxisMinimum(xMin);
        mChart.getXAxis().setLabelCount(count, false);

        float yMin = -1f;
        float yMax = -1f;

        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0, size = salesamount.size(); i < size; i++) {
            ChartData bean = salesamount.get(i);

            float y = Float.parseFloat(bean.getSales());
            String obj = bean.getTimes();
            if (startDate != null) {
                obj = TimeUtils.date2String(startDate, DEFAULT_FORMAT) + bean.getTimes();
            }
            values.add(new Entry(i, y, obj));

            if (yMin == -1f && yMax == -1f) {
                yMin = y;
                yMax = y;
            } else {
                if (yMin > y) {
                    yMin = y;

                }
                if (yMax < y) {
                    yMax = y;
                }
            }
        }


        YAxis leftAxis = mChart.getAxisLeft();

        double skipY = getYAxisSkip(yMin, yMax);

        leftAxis.setAxisMaximum((int) skipY * 5);
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);

        setData(values);

    }

    private boolean isEntityZero(List<ChartData> data) {
        for (ChartData cd : data) {
            if (Double.parseDouble(cd.getSales()) > 0.0) {
                return false;
            }
        }
        return true;
    }

    private double getYAxisSkip(float yMin, float yMax) {

        int scaleValue = ((int) (yMax % 5) == 0) ? (int) yMax / 5 : (int) (yMax / 5 + 1);
        String scaleValueString = String.valueOf(scaleValue);
        int intNumberMin = (int) Math.pow(10, scaleValueString.length() - 1);
        if (scaleValue <= 10) {
            scaleValue = 10;
        } else if (scaleValue < 100) {
            //最大位加1取整
            if (scaleValue % intNumberMin != 0) {
                scaleValue = (scaleValue / intNumberMin + 1) * intNumberMin;
            }

        } else {
            if (scaleValue % (intNumberMin / 10) != 0) {
                scaleValue = (scaleValue / (intNumberMin / 10) + 1) * (intNumberMin / 10);
            }
        }

        return scaleValue;
    }

    private void setData(ArrayList<Entry> values) {
        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            set1.setColor(ContextCompat.getColor(getActivity(), R.color.red));
            set1.setCircleColor(ContextCompat.getColor(getActivity(), R.color.red));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircles(false);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setDrawValues(false);
            set1.setFormSize(15.f);
            set1.setFillColor(ContextCompat.getColor(getActivity(), R.color.light_red));
            set1.setHighLightColor(ContextCompat.getColor(getActivity(), R.color.red));
            set1.setDrawHorizontalHighlightIndicator(false);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
        Legend legend = mChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        mChart.invalidate();

//        mChart.highlightValue(mChart.getLineData().g);
    }

    @Override
    public void onClick(View view) {
    }


    private class DateTimeXAxisValueFormatter implements IAxisValueFormatter {

        private List<ChartData> mRange = new ArrayList<>();

        public List<ChartData> getRange() {
            return mRange;
        }

        public void setRange(List<ChartData> range) {
            mRange = range;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (mRange.isEmpty()) {
                return "";
            }

            int x = (int) value;
            if (mRange.size() <= x || x < 0) {
                return "";
            }

            return mRange.get(x).getTimes();
        }
    }

    public int getDaysOfMonth(Date date) {
        mCalendar.setTime(date);
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}

