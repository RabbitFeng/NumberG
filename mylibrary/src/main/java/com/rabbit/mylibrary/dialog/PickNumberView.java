package com.rabbit.mylibrary.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.rabbit.mylibrary.UnitUtil;

/**
 * Created by DongZF on 2022/5/17
 */
public class PickNumberView extends View {
    public PickNumberView(Context context) {
        this(context, null);
    }

    public PickNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private Paint linePaint;
    private float lineWidth;

    private TextPaint textPaint;
    private float textSize;

    @ColorInt
    private int colorChosen = Color.parseColor("#80000000");
    @ColorInt
    private int colorNormal = Color.parseColor("#20000000");
    @ColorInt
    private int colorTextChosen = Color.BLACK;
    @ColorInt
    private int colorTextNormal = Color.DKGRAY;

    private void init(Context context, @Nullable AttributeSet attrs) {
        lineWidth = UnitUtil.dpToPx(2, context);
        textSize = UnitUtil.spToPx(20, context);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(Color.LTGRAY);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStrokeCap(Paint.Cap.SQUARE);
//        linePaint.setStrokeJoin(Paint.Join.MITER);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(colorTextNormal);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private int drawWidth;
    private int drawHeight;

    private Path path = new Path();

    private int drawLength;
    float outCircleRadius;
    float innerCircleRadius;

    private int touchedArea = 0;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawWidth = Math.max(2, w - getPaddingStart() - getPaddingEnd());
        drawHeight = Math.max(2, h - getPaddingTop() - getPaddingBottom());
        drawLength = (int) (Math.min(drawWidth, drawHeight) - lineWidth);
        outCircleRadius = drawLength >> 1;
        innerCircleRadius = drawLength >> 2;

        RectF outRectF = new RectF(-outCircleRadius, -outCircleRadius, outCircleRadius, outCircleRadius);
        RectF innerRectF = new RectF(-innerCircleRadius, -innerCircleRadius, innerCircleRadius, innerCircleRadius);

        path.moveTo(0, -innerCircleRadius);
        path.lineTo(0, -outCircleRadius);
        path.arcTo(outRectF, -90, 40);
        path.lineTo((float) Math.sin(Math.toRadians(40)) * innerCircleRadius, -(float) Math.cos(Math.toRadians(40)) * innerCircleRadius);
        path.arcTo(innerRectF, -50, -40);
//        path.close();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2f, getHeight() / 2f);

        canvas.save();
        for (int i = 0; i < 9; i++) {
            linePaint.setColor((i + 1 == touchedArea) ? colorChosen : colorNormal);
            canvas.drawPath(path, linePaint);
            canvas.rotate(20);
//            canvas.drawText("text", 0, -drawLength * 3 / 8f, textPaint);// 数字也会旋转
            canvas.rotate(20);
        }
        canvas.rotate(90);
        canvas.restore();

        canvas.save();
        for (int i = 0; i < 9; i++) {
            float textBaseLineRadius = -drawLength * 3 / 8f;
            float x = (float) (textBaseLineRadius * Math.sin(Math.toRadians(20 + 40 * i)));
            float y = (float) (textBaseLineRadius * Math.cos(Math.toRadians(20 + 40 * i)));
            canvas.drawText(9 - i + "", x, y, textPaint);
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxLength = Math.min(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        setMeasuredDimension(maxLength, maxLength);
    }

    protected int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            size = (int) UnitUtil.dpToPx(150, getContext());
        }
        return size;
    }

    protected int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            size = (int) UnitUtil.dpToPx(150, getContext());
        }
        return size;
    }

    private float xd;
    private float yd;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ZFLog", "PickNumberView.onTouchEvent: ");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("ZFLog", "PickNumberView.onTouchEvent: ACTION_DOWN");
                xd = event.getX();
                yd = event.getY();
                touchedArea = calculateTouchedArea(event.getX(), event.getY());
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("ZFLog", "PickNumberView.onTouchEvent: ACTION_MOVE");
                touchedArea = calculateTouchedArea(event.getX(), event.getY());
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchedArea = calculateTouchedArea(event.getX(), event.getY());
                Log.d("ZFLog", "PickNumberView.onTouchEvent: ACTION_UP" + touchedArea);
                postInvalidate();
                performClick();
                break;
        }
        return super.onTouchEvent(event);
//        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * @param x event.getX()
     * @param y event.getY()
     * @return [0-9] 范围内值，若是0表示在内圆中
     */
    private int calculateTouchedArea(float x, float y) {
        float xRe = x - getWidth() / 2f;
        float yRe = y - getHeight() / 2f;


        float pow = xRe * xRe + yRe * yRe;
//        Log.d("ZFLog", "PickNumberView.getTouchedArea: xRe = " + xRe + ", yRe = " + yRe + " pow = " + pow + " " + innerCircleRadius * innerCircleRadius);
        if (pow < innerCircleRadius * innerCircleRadius) {
            // 位于内圆范围内
            return 0;
        } else {
            double sin = xRe / Math.sqrt(pow);
            double angle = Math.toDegrees(Math.asin(sin));
            if (yRe > 0) {
                angle = 180 - angle;
            } else if (xRe < 0) {
                angle += 360;
            }
//            Log.d("ZFLog", "PickNumberView.getTouchedArea: angle " + angle);
            return (int) (angle / 40 + 1);
        }
    }

    public int getTouchedArea() {
        return touchedArea;
    }
}
