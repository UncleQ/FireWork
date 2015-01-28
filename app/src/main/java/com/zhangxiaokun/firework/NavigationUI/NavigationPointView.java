package com.zhangxiaokun.firework.NavigationUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zhangxiaokun.firework.NavigationDrawerFragment;

/**
 * Created by zhangxiaokun on 2014/12/25.
 */
public class NavigationPointView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPathUp,mPathDown;
    public NavigationPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setZOrderOnTop(true);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaint = new Paint();
        mPaint.setColor(0xFF8BBDEE);
        mPathUp = new Path();
        mPathDown = new Path();
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
        mPathDown.moveTo(width / 3,height / 3);
        mPathDown.lineTo(width / 3 * 2,height / 3);
        mPathDown.lineTo(width / 2,height / 3 * 2);
        mPathDown.lineTo(width / 3,height / 3);
        mPathDown.close();
        mPathUp.moveTo(width / 3,height / 3 * 2);
        mPathUp.lineTo(width / 3 * 2,height / 3 * 2);
        mPathUp.lineTo(width / 2,height / 3);
        mPathUp.lineTo(width / 3,height / 3 * 2);
        mPathUp.close();
        drawPoint(NavigationDrawerFragment.ACCOUNT_STATE);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void drawPoint(int type){
        Canvas canvas = mHolder.lockCanvas();
        if(type == NavigationDrawerFragment.ACCOUNT_STATE){
            canvas.drawPath(mPathUp,mPaint);
        }else{
            canvas.drawPath(mPathDown,mPaint);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }
}
