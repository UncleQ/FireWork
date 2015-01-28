package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangxiaokun on 2014/11/30.
 */
public class PowerShowView extends View {
    private boolean mIsPush;
    private int mPowerPercent;
    private Rect mPercentRect;
    private Paint mPaint;

    public PowerShowView(Context context,AttributeSet attrs){
        super(context,attrs);
        mIsPush = false;
        mPowerPercent = 0;
        mPercentRect = new Rect();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        int width = getWidth();
        int height = getHeight();
        int startPos = (int)(height * 0.98);
        int leftPos = (int)(width * 0.1);
        int rightPos = (int)(width * 0.9);
        int endPos = (int)(height * 0.02);

        if(mIsPush){
            mPaint.setColor(Color.RED);
            int lastPowerPercent = 0;
            int cent8 = (int)((startPos - endPos) / 50 * 0.9);
            int cent2 = (int)((startPos - endPos) / 50 * 0.3);
            int cent = cent2 + cent8;
            mPercentRect.left = leftPos;
            mPercentRect.right = rightPos;
            while(lastPowerPercent <= mPowerPercent) {
                mPercentRect.bottom = startPos;
                mPercentRect.top = startPos - cent8;
                startPos -= cent;
                canvas.drawRect(mPercentRect, mPaint);
                lastPowerPercent += 2;
            }
        }else{
            mPercentRect.bottom = startPos;
            mPercentRect.left = leftPos;
            mPercentRect.right = rightPos;
            mPercentRect.top = endPos;
            mPaint.setColor(Color.GREEN);
            canvas.drawRect(mPercentRect,mPaint);
            mPowerPercent = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;//消费掉触摸事件
    }

    public void setIsPush(boolean isPush){
        mIsPush = isPush;
    }

    public void setPowerPercent(int value){
        mPowerPercent = value;
    }
}
