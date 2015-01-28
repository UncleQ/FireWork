package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangxiaokun on 2014/12/16.
 */
public class BearingButtonView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;
    private SurfaceHolder mHolder;
    private int mType;
    private Paint mPaint;
    private Path mPath;
    private int mWidth,mHeight,mInWidth,mInHeight;

    public BearingButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mPath = new Path();
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
        drawButton(false);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void setType(int value){
        mType = value;
    }

    public void drawButton(boolean isPush){
        int left = (int)(mWidth * 0.2f);
        int right = (int)(mWidth * 0.8f);
        int up = (int)(mHeight * 0.2f);
        int bottom = (int)(mHeight * 0.8f);
        if(mType == LEFT_BUTTON) {
            mPath.moveTo(left,mHeight / 2);
            mPath.lineTo(right,bottom);
            mPath.lineTo(right,up);
            mPath.lineTo(left,mHeight / 2);
            mPath.close();
        }else{
            mPath.moveTo(right,mHeight / 2);
            mPath.lineTo(left,bottom);
            mPath.lineTo(left,up);
            mPath.lineTo(right,mHeight / 2);
            mPath.close();
        }
        mPaint.setStrokeWidth(10);
        //mPaint.setStyle(Paint.Style.STROKE);
        if(isPush){
            //mPaint.setColor(Color.rgb(0x8B,0xBD,0xEE));
            //mPaint.setColor(Color.rgb(0x43,0x76,0xA5));
            //mPaint.setColor(Color.rgb(0xA4,0xC5,0xE4));
            mPaint.setColor(Color.rgb(0xC3,0xE5,0xEF));
        }else{
            //mPaint.setColor(Color.rgb(0x43,0x76,0xA5));
            mPaint.setColor(Color.rgb(0x8B,0xBD,0xEE));
        }
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawPath(mPath, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }
}
