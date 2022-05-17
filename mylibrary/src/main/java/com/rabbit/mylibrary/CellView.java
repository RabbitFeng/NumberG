package com.rabbit.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by DongZF on 2022/5/17
 * 绘制单个数字或者九宫格
 */
public class CellView extends View {

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public static final int STATE_EMPTY = 0x00;
    public static final int STATE_HOLDER = 0x01;
    public static final int STATE_NOTE = 0x02;

    @IntDef({STATE_EMPTY, STATE_HOLDER, STATE_NOTE})
    public @interface State {
    }

    /**
     * 绘制线画笔
     */
    private Paint linePaint;

    /**
     * 绘制文字画笔
     */
    private TextPaint textPaint;

    /**
     * 绘制文字画笔
     */
    private TextPaint textPaintHolder;

    /**
     * 绘制线宽度
     */
    private float lineWidth;

    /**
     * 文字大小
     */
    private float textSize;

    /**
     * 文字大小
     */
    private float textHolderSize;

    /**
     * 文字最大高度
     */
    private float maxTextHeight;


    /**
     * 最小滑动距离
     */
    private float scaledTouchSlop;

    /**
     * 绘制可能数字集合
     */
    private ArrayList<Integer> possibleList = new ArrayList<Integer>() {
        {
            for (int i = 0; i < 9; i++) {
                add(i + 1);
            }
        }
    };

    private volatile int state = STATE_EMPTY;

    private volatile int holderNumber = 1;

    private void init(@NonNull Context context) {
        lineWidth = dpToPx(1);
        textSize = spToPx(18);
        textHolderSize = spToPx(30);
        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setColor(Color.LTGRAY);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaintHolder = new TextPaint(textPaint);
        textPaintHolder.setTextSize(textHolderSize);
        textPaintHolder.setColor(Color.RED);

        Paint.FontMetrics fontMetrics = textPaintHolder.getFontMetrics();
        maxTextHeight = fontMetrics.bottom - fontMetrics.top;
    }

    private float paddingTop;
    private float paddingBottom;
    private float paddingStart;
    private float paddingEnd;

    private float drawWidth;
    private float drawHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int length = Math.max(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        setMeasuredDimension(length, length);
    }

    protected int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
//            size = (int) (totalPoints * 2 * radius + (totalPoints - 1) * pointInterval + getPaddingStart() + getPaddingEnd());
            size = 100;
        }
        return size;
    }

    protected int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
//            size = (int) (radius * totalPoints + getPaddingTop() + getPaddingBottom());
            size = (int) Math.max(100, maxTextHeight * 1.2);
        }
        return size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingStart = getPaddingStart();
        paddingEnd = getPaddingEnd();

        drawWidth = w - paddingStart - paddingEnd;
        drawHeight = h - paddingTop - paddingBottom;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingLeft(), getHeight() - getPaddingTop(), linePaint);
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2f, getHeight() / 2f);
        if (state == STATE_NOTE) {
            drawLines(canvas);
            drawText(canvas);
        } else if (state == STATE_HOLDER) {
            drawHolder(canvas);
        } else {
//            canvas.drawRect(-100, -100, 100, 100, linePaint);
        }
    }

    private void drawLines(Canvas canvas) {
        float drawInterval = Math.min(drawWidth, drawHeight) / 3f;
        float halfDrawInterval = drawInterval / 2f;
        float halfDrawLength = Math.min(drawWidth, drawHeight) / 2f;

        canvas.drawLine(-halfDrawLength, -halfDrawInterval, halfDrawLength, -halfDrawInterval, linePaint);
        canvas.drawLine(-halfDrawLength, halfDrawInterval, halfDrawLength, halfDrawInterval, linePaint);

        canvas.drawLine(-halfDrawInterval, -halfDrawLength, -halfDrawInterval, halfDrawLength, linePaint);
        canvas.drawLine(halfDrawInterval, -halfDrawLength, halfDrawInterval, halfDrawLength, linePaint);
    }

    private void drawText(Canvas canvas) {
        float drawInterval = Math.min(drawWidth, drawHeight) / 3f;
//        float x0 = -drawInterval;
//        float y0 = -drawInterval;
        for (int i = 0; i < possibleList.size(); i++) {
            float x = -drawInterval + drawInterval * (i % 3);
            float y = -drawInterval + drawInterval * (int) (i / 3);
            canvas.drawText(possibleList.get(i) + "", x, y, textPaint);
        }
    }

    private void drawHolder(Canvas canvas) {
        canvas.drawText(holderNumber + "", 0, maxTextHeight / 3f, textPaintHolder);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


    private float xd;
    private float yd;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xd = event.getRawX();
                yd = event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                if (Math.max(Math.abs(xd - event.getRawX()), Math.abs(yd - event.getRawY())) < scaledTouchSlop) {
                    handleClick(event.getX(), event.getY());
                    performClick();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void handleClick(float x, float y) {
        // 添加动画
        animate().scaleXBy(1f).scaleX(0.8f)
                .scaleYBy(1f).scaleY(0.8f)
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator())
                .withEndAction(() -> {
                    animate().scaleXBy(0.8f).scaleX(1f)
                            .scaleYBy(0.8f).scaleY(1f)
                            .setDuration(200)
                            .setInterpolator(new OvershootInterpolator())
                            .start();
                })
                .start();
    }

    private int row = -1;
    private int col = -1;

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setHoldNumber(int number) {
        setState(STATE_HOLDER);
        this.holderNumber = number;
        postInvalidate();
    }

    public void setPossibleList(@NonNull ArrayList<Integer> list) {
        setState(STATE_NOTE);
        possibleList.clear();
        possibleList.addAll(list);
        postInvalidate();
    }

    /**
     * 设置状态
     *
     * @param state
     */
    public void setState(@State int state) {
        this.state = state;
        postInvalidate();
    }
}
