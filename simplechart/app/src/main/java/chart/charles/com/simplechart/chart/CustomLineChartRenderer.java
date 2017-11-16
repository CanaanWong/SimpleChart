package chart.charles.com.simplechart.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * <b>Project:</b> MpChart<br>
 * <b>Create Date:</b> 2017/7/26<br>
 * <b>Author:</b> 王灿 <br>
 * <b>Description:</b> <br>
 */
public class CustomLineChartRenderer extends LineChartRenderer {

    protected Paint mCirclePaintInner1;

    public CustomLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);

        mCirclePaintInner1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaintInner1.setStyle(Paint.Style.FILL);
        mCirclePaintInner1.setColor(Color.parseColor("#FFDE514D"));
    }

    @Override
    protected void drawHighlightLines(Canvas c, float x, float y, ILineScatterCandleRadarDataSet set) {
        super.drawHighlightLines(c, x, y, set);

        drawHighlightCircle(c, x, y, set);
    }

    private void drawHighlightCircle(Canvas canvas, float x, float y, ILineScatterCandleRadarDataSet set) {

        ILineDataSet lineSet = (ILineDataSet) set;
        canvas.restoreToCount(1);
        canvas.drawCircle(
                x,
                y,
                lineSet.getCircleRadius(),
                mCirclePaintInner1);

        canvas.save();
    }
}
