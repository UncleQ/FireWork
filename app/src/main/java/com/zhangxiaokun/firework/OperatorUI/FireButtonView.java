package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zhangxiaokun on 2014/12/17.
 */
public class FireButtonView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Rect mRect;

    public FireButtonView(Context context,AttributeSet attrs){
        super(context,attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        int width = getWidth();
        int height = getHeight();
        mRect.bottom = (int)(height * 0.9f);
        mRect.top = (int)(height * 0.1f);
        mRect.left = (int)(width * 0.1f);
        mRect.right = (int)(width * 0.9f);
        drawButton(false);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void drawButton(boolean isPush){
        if(isPush){
            //mPaint.setColor(Color.rgb(0x8B, 0xBD, 0xEE));
            //mPaint.setColor(Color.rgb(0x43,0x76,0xA5));
            mPaint.setColor(Color.rgb(0xC3,0xE5,0xEF));
        }else{
            //mPaint.setColor(Color.rgb(0x43,0x76,0xA5));
            mPaint.setColor(Color.rgb(0x8B, 0xBD, 0xEE));
        }
        mPaint.setStrokeWidth(10);
        //mPaint.setStyle(Paint.Style.STROKE);
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawRect(mRect, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }
}
