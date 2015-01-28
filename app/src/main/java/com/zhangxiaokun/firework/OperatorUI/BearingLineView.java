package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangxiaokun on 2014/12/17.
 */
public class BearingLineView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPathLeft,mPathRight;
    private int mWidth,mHeight;
    private int m500Height,m375Height,m250Height;
    private int m125Width,m250Width,m375Width,m500Width,m625Width,m750Width,m875Width;
    public BearingLineView(Context context, AttributeSet attrs){
        super(context,attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mPaint.setColor(0xFF8BBDEE);
        mPathLeft = new Path();
        mPathRight = new Path();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mWidth = getWidth();
        mHeight = getHeight();
        m500Height = (int)(mHeight * 0.5f);
        m375Height = (int)(mHeight * 0.375f);
        m250Height = (int)(mHeight * 0.25f);
        m125Width = (int)(mWidth * 0.125f);
        m250Width = (int)(mWidth * 0.25f);
        m375Width = (int)(mWidth * 0.375f);
        m500Width = (int)(mWidth * 0.5f);
        m625Width = (int)(mWidth * 0.625f);
        m750Width = (int)(mWidth * 0.75f);
        m875Width = (int)(mWidth * 0.875f);
        mPathLeft.moveTo(0, mHeight * 0.75f);
        mPathLeft.lineTo(mHeight * 0.25f, mHeight);
        mPathLeft.lineTo(mHeight * 0.25f, mHeight * 0.5f);
        mPathLeft.lineTo(0, mHeight * 0.75f);
        mPathLeft.close();
        mPathRight.moveTo(mWidth, mHeight * 0.75f);
        mPathRight.lineTo(mWidth - mHeight * 0.25f, mHeight);
        mPathRight.lineTo(mWidth - mHeight * 0.25f, mHeight * 0.5f);
        mPathRight.lineTo(mWidth, mHeight * 0.75f);
        mPathRight.close();
        Canvas canvas = mHolder.lockCanvas(null);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawLine(0,m500Height,mWidth,m500Height,mPaint);
        canvas.drawLine(0,m500Height,0,0,mPaint);//0
        canvas.drawLine(m125Width,m500Height,m125Width,m375Height,mPaint);//12.5
        canvas.drawLine(m250Width,m500Height,m250Width,m250Height,mPaint);//25
        canvas.drawLine(m375Width,m500Height,m375Width,m375Height,mPaint);//37.5
        canvas.drawLine(m500Width,m500Height,m500Width,0,mPaint);//50
        canvas.drawLine(m625Width,m500Height,m625Width,m375Height,mPaint);//62.5
        canvas.drawLine(m750Width,m500Height,m750Width,m250Height,mPaint);//75
        canvas.drawLine(m875Width,m500Height,m875Width,m375Height,mPaint);//87.5
        canvas.drawLine(mWidth, m500Height, mWidth, 0, mPaint);//100
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void drawBearingPoint(int percent) {
        Canvas canvas = mHolder.lockCanvas(null);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawLine(0,m500Height,mWidth,m500Height,mPaint);
        canvas.drawLine(0,m500Height,0,0,mPaint);//0
        canvas.drawLine(m125Width,m500Height,m125Width,m375Height,mPaint);//12.5
        canvas.drawLine(m250Width,m500Height,m250Width,m250Height,mPaint);//25
        canvas.drawLine(m375Width,m500Height,m375Width,m375Height,mPaint);//37.5
        canvas.drawLine(m500Width,m500Height,m500Width,0,mPaint);//50
        canvas.drawLine(m625Width,m500Height,m625Width,m375Height,mPaint);//62.5
        canvas.drawLine(m750Width,m500Height,m750Width,m250Height,mPaint);//75
        canvas.drawLine(m875Width,m500Height,m875Width,m375Height,mPaint);//87.5
        canvas.drawLine(mWidth, m500Height, mWidth, 0, mPaint);//100
        if (percent == -1) {
            //mPaint.setStyle(Paint.Style.STROKE);//设置空心
            canvas.drawPath(mPathLeft, mPaint);
        } else if (percent == 101) {
            //mPaint.setStyle(Paint.Style.STROKE);//设置空心
            canvas.drawPath(mPathRight, mPaint);
        } else {
            int pos = mWidth * percent / 100;
            mPaint.setStrokeWidth((float) 3.0);
            canvas.drawLine(pos, mHeight / 2 + 5, pos, mHeight - 5, mPaint);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }
}
