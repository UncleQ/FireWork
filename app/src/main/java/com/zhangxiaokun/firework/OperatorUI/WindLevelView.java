package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangxiaokun on 2014/12/18.
 */
public class WindLevelView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Rect mRect;
    private float mWidth;
    private float mHeight;
    private float mDepart;
    private float mPart;

    public WindLevelView(Context context, AttributeSet attrs){
        super(context,attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mRect = new Rect();
        mPaint.setColor(0xFF8BBDEE);
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
        mDepart = mHeight * 0.1f / 11;
        mPart = mHeight * 0.9f /12;
        mRect.left = 0;
        mRect.right = (int)mWidth;
        drawLine(4);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void drawLine(int level){
        Canvas canvas = mHolder.lockCanvas();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        for(int i=0;i<level;i++){
            mRect.bottom = (int)(mHeight - i * (mPart + mDepart));
            mRect.top = (int)(mHeight - i * (mPart + mDepart) - mPart);
            canvas.drawRect(mRect,mPaint);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }

}
