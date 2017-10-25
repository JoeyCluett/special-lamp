package com.example.joey.sicksensordisplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joey on 10/10/17.
 */

public class CanvasSickPlot extends View {

    private Paint mPaint, botPaint, plotPaint;
    private float canHeight, canWidth, canHalfHeight, canHalfWidth;
    private float scale_f = 2.0f;

    // size of snowplow
    private final float BOT_WIDTH  = 686.0f;  // mm
    private final float BOT_LENGTH = 1143.0f; // mm
    private float BOT_HALF_WIDTH  = BOT_WIDTH / 2.0f;

    private float[] raw_values = new float[541];
    private float[] sickX = new float[541]; // x
    private float[] sickY = new float[541]; // y

    private NetworkThread nt;

    public CanvasSickPlot(Context c, AttributeSet attrs) {
        super(c, attrs);

        // set a new Paint with desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        botPaint = new Paint();
        botPaint.setAntiAlias(true);
        botPaint.setColor(Color.RED);
        botPaint.setStyle(Paint.Style.STROKE);
        botPaint.setStrokeJoin(Paint.Join.ROUND);
        botPaint.setStrokeWidth(2f);

        plotPaint = new Paint();
        plotPaint.setAntiAlias(true);
        plotPaint.setColor(Color.GREEN);
        plotPaint.setStyle(Paint.Style.STROKE);
        plotPaint.setStrokeJoin(Paint.Join.ROUND);
        plotPaint.setStrokeWidth(5f);
    }

    public void setHostnamePortNumber(String hostname, int port_number) {
        nt = new NetworkThread(hostname, port_number);
        nt.start();
    }

    public void zoomIn() {
        scale_f = scale_f / 1.1f;
    }

    public void zoomOut() {
        scale_f = scale_f * 1.1f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        this.canHeight     = (float)canvas.getHeight();
        this.canHalfHeight = this.canHeight / 2f;
        this.canWidth      = (float)canvas.getWidth();
        this.canHalfWidth  = this.canWidth / 2f;

        // draw the target
        canvas.drawLine(0, canHalfHeight, canWidth, canHalfHeight, mPaint);
        canvas.drawLine(canHalfWidth, 0f, canHalfWidth, canHalfHeight, mPaint);

        // draw two limit lines
        float max_pts = canHalfHeight;
        if(canHalfWidth < canHalfHeight)
            max_pts = canHalfWidth;

        canvas.drawLine(canHalfWidth, canHalfHeight, canHalfWidth-max_pts, canHalfHeight+max_pts, mPaint); // down to left
        canvas.drawLine(canHalfWidth, canHalfHeight, canHalfWidth+max_pts, canHalfHeight+max_pts, mPaint); // down to right

        // draw scale model of snowplow
        canvas.drawRect(
                canHalfWidth-(BOT_HALF_WIDTH/scale_f),
                canHalfHeight,
                canHalfWidth+(BOT_HALF_WIDTH/scale_f),
                canHalfHeight+(BOT_LENGTH/scale_f),
                botPaint);

        // retrieve data and calculate cartesian coordinates
        nt.getNewData(raw_values);
        calculateNewSickValues(raw_values);

        // draw calculated Cartesian data
        for(int i = 0; i < 541; i++)
            canvas.drawPoint(canHalfWidth+sickX[i]/scale_f, canHeight-(canHalfHeight+sickY[i]/scale_f), plotPaint);
/*
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        invalidate(); // do it all again
    }

    public void calculateNewSickValues(float[] raw_values) {
        for(int i = 0; i < 541; i++) {
            double angle = (i - 90) / 2.0f / 57.29f;
            sickX[i] = (float)(raw_values[i] * Math.cos(angle));
            sickY[i] = (float)(raw_values[i] * Math.sin(angle));
        }
        invalidate();
    }

}
