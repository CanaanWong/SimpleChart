package chart.charles.com.simplechart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import chart.charles.com.simplechart.R;


/**
 * <b>Project:</b> MpChart<br>
 * <b>Create Date:</b> 2017/7/27<br>
 * <b>Author:</b> Charles <br>
 * <b>Description:</b> <br>
 */
public class TopMarkerView extends MarkerView {

    private MPPointF mOffset2 = new MPPointF();
    private BubbleLinearLayout mBubbleLayout;
    private AppCompatTextView tvContent;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public TopMarkerView(Context context) {
        super(context, R.layout.layout_top_mark_view);
        mBubbleLayout = (BubbleLinearLayout) findViewById(R.id.bubble_layout);
        tvContent = (AppCompatTextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText("我是Markiew " + String.valueOf(e.getY()));

        super.refreshContent(e, highlight);

        float posX = highlight.getDrawX();

        updateBubbleArrow(posX);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {

        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);

        int saveId = canvas.save();
        // translate to the correct position and draw
        canvas.translate(posX + offset.x, -getHeight() / 2 + getChartView().getExtraTopOffset() / 2 + Utils.convertDpToPixel(3));
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    private void updateBubbleArrow(float posX) {
        MPPointF offset = getOffset();
        mOffset2.x = offset.x;
        mOffset2.y = offset.y;

        Chart chart = getChartView();

        float width = getWidth();

        if (posX + mOffset2.x < 0) {
            mOffset2.x = -posX;
        } else if (chart != null && posX + width + mOffset2.x > chart.getWidth()) {
            mOffset2.x = chart.getWidth() - posX - width;
        }

        if (getWidth() / 2 > posX || getWidth() / 2 + posX > getChartView().getWidth()) {
            mBubbleLayout.setArrowCenter(false);
            mBubbleLayout.setArrowPosition(Math.abs(mOffset2.x) - Utils.convertDpToPixel(4));
        } else {
            mBubbleLayout.setArrowCenter(true);
        }
        mBubbleLayout.setUpBubbleDrawable();
    }
}
