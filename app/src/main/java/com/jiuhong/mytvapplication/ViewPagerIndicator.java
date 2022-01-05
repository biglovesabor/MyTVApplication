package com.jiuhong.mytvapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ViewPagerIndicator extends View {
    private Paint mPaint;//未选中的画笔
    private Paint selectPaint;//选中的画笔
    private int selectPosition = 0; //当前选中的位置
    private float positionOffset = 0;//偏移量

    private int defaultColor = Color.BLACK;
    private int selectColor = Color.RED;
    private float circlerWidth = 20;

    private int circlerCount = 0;//circler的个数
    private float itemWidth = 0;//每一个cirler的总长度

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        defaultColor = ta.getColor(R.styleable.ViewPagerIndicator_unSelectCirclerColor, context.getColor(R.color.default_color));
        selectColor = ta.getColor(R.styleable.ViewPagerIndicator_selectCirclerColor, Color.WHITE);
        circlerWidth = ta.getDimension(R.styleable.ViewPagerIndicator_circlerSize, 20);
        itemWidth = ta.getDimension(R.styleable.ViewPagerIndicator_itemWidth, 0);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(defaultColor);
        mPaint.setStrokeWidth((float) 3.0);              //线宽
        mPaint.setStyle(Paint.Style.STROKE);                   //空心效果

        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setColor(selectColor);
        selectPaint.setStrokeWidth((float) 3.0);              //线宽
        selectPaint.setStyle(Paint.Style.FILL);                   //空心效果
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (circlerCount > 0) {
            if (itemWidth == 0) {
                for (int i = 0; i < circlerCount; i++) {
                    canvas.drawCircle(width / circlerCount / 2 + i * width / circlerCount, height / 2, circlerWidth, mPaint);
                }
                float x = width * positionOffset / circlerCount;
                canvas.drawCircle(width * selectPosition / circlerCount + width / circlerCount / 2 + x, height / 2, circlerWidth, selectPaint);
            } else {
                for (int i = 0; i < circlerCount; i++) {
                    canvas.drawCircle(itemWidth * (i + 1), height / 2, circlerWidth, mPaint);
                }
                float x = itemWidth * positionOffset;
                float scrollCirclerWidth = 0;
                if (positionOffset > 0.5) {
                    scrollCirclerWidth = circlerWidth + (circlerWidth * (1 - positionOffset)) / 2;
                } else {
                    scrollCirclerWidth = circlerWidth + (circlerWidth * positionOffset) / 2;
                }
                canvas.drawCircle(itemWidth * (selectPosition + 1) + x, height / 2, scrollCirclerWidth, selectPaint);
            }
        }
    }

    public void setSelectPosition(int position) {
        selectPosition = position;
        positionOffset = 0;
        invalidate();
    }

    public void setPositionOffset(int position, float positionOffset) {
        selectPosition = position;
        this.positionOffset = positionOffset;
        invalidate();
    }

    public void setCirclerCount(int circlerCount) {
        this.circlerCount = circlerCount;
        setSelectPosition(0);
        invalidate();
    }

}
