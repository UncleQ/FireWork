package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangxiaokun on 2014/12/20.
 */
public class WindBearingView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPath;
    private float mWidth,mHeight,mCentX,mCentY,mRadius;
    private Point mPA,mPB,mPC;

    public WindBearingView(Context context,AttributeSet attrs){
        super(context,attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        this.setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(0x8B, 0xBD, 0xEE));
        mPath = new Path();
        mPA = new Point();
        mPB = new Point();
        mPC = new Point();
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
        mCentX = mWidth / 2;
        mCentY = mHeight / 2;
        if(mWidth < mHeight){
            mRadius = mCentX * 0.6f;
        }else{
            mRadius = mCentY * 0.6f;
        }
        //draw(0,30);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    void draw(float bearing,float tilt){
        Canvas canvas = mHolder.lockCanvas();
        float rad = (float)Math.toRadians(bearing);
        float fAx = mRadius * (float)Math.sin(rad);
        float fAy = mRadius * (float)Math.cos(rad);
        float bearingB = (bearing + 150) % 360;
        rad = (float)Math.toRadians(bearingB);
        float fBx = mRadius * (float)Math.sin(rad);
        float fBy = mRadius * (float)Math.cos(rad);
        float bearingC = (bearing + 210) % 360;
        rad = (float)Math.toRadians(bearingC);
        float fCx = mRadius * (float)Math.sin(rad);
        float fCy = mRadius * (float)Math.cos(rad);
        rad = (float)Math.toRadians(tilt);
        float scale = (float)Math.cos(rad);
        if(fAy > 0){
            //up
            mPA.y = (int)(mCentY - fAy * scale);
            mPA.x = (int)(mCentX - fAx * scale);
        }else{
            mPA.y = (int)(mCentY - fAy / scale);
            mPA.x = (int)(mCentX - fAx / scale);
        }
        if(fBy > 0){
            //up
            mPB.y = (int)(mCentY - fBy * scale);
            mPB.x = (int)(mCentX - fBx * scale);
        }else{
            mPB.y = (int)(mCentY - fBy / scale);
            mPB.x = (int)(mCentX - fBx / scale);
        }
        if(fBy > 0){
            //up
            mPC.y = (int)(mCentY - fCy * scale);
            mPC.x = (int)(mCentX - fCx * scale);
        }else{
            mPC.y = (int)(mCentY - fCy / scale);
            mPC.x = (int)(mCentX - fCx / scale);
        }
        mPath.moveTo(mPA.x,mPA.y);
        mPath.lineTo(mPB.x, mPB.y);
        mPath.lineTo(mPC.x,mPC.y);
        mPath.lineTo(mPA.x,mPA.y);
        mPath.close();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawPath(mPath,mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }
}
