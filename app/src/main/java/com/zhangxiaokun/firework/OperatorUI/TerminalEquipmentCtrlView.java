package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by zhangxiaokun on 2014/12/17.
 */
public class TerminalEquipmentCtrlView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private int mWidth,mHeight,mInMax,mInMin,mRectHeight;
    private Paint mPaintUp,mPaintDown;
    private Rect mRect;
    private boolean mIsPush;
    private float mCurY,mMax,mMin,mValue;
    private TerminalEquipmentCtrlCallback mCallback;

    public TerminalEquipmentCtrlView(Context context, AttributeSet attrs){
        super(context,attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        this.setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mPaintUp = new Paint();
        mPaintUp.setColor(Color.rgb(0x43,0x76,0xA5));
        //mPaintUp.setColor(Color.rgb(0x8B,0xBD,0xEE));
        mPaintDown = new Paint();
        mPaintDown.setColor(Color.rgb(0x8B,0xBD,0xEE));
        //mPaintDown.setColor(Color.rgb(0x43,0x76,0xA5));
        mRect = new Rect();
        mIsPush = false;
        mMax = 0;
        mMin = 0;
        this.setOnTouchListener(listener);
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
        mRectHeight = (int)(mHeight * 0.03f);
        mInMax = (int)(mHeight * 0.05f);
        mInMin = (int)(mHeight * 0.95);
        mRect.left = (int)(mWidth * 0.2f);
        mRect.right = (int)(mWidth * 0.8f);
        mPaintDown.setStrokeWidth(mWidth * 0.2f);
        mPaintUp.setStrokeWidth(mWidth * 0.2f);
        mCurY = mInMin;
        mValue = mMin;
        drawLine(mCurY);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    private OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsPush = true;
                    mCurY = event.getY();
                    if(mCurY > mInMin)
                        mCurY = mInMin;
                    if(mCurY < mInMax)
                        mCurY = mInMax;
                    float cale = (mInMin - mCurY) / (mInMin - mInMax);
                    mValue = mMin + (mMax - mMin) * cale;
                    drawLine(mCurY);
                    if(mCallback != null){
                        mCallback.onTerminalEquipmentChange(mValue);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurY = event.getY();
                    mCurY = event.getY();
                    if(mCurY > mInMin)
                        mCurY = mInMin;
                    if(mCurY < mInMax)
                        mCurY = mInMax;
                    cale = (mInMin - mCurY) / (mInMin - mInMax);
                    mValue = mMin + (mMax - mMin) * cale;
                    drawLine(mCurY);
                    if(mCallback != null){
                        mCallback.onTerminalEquipmentChange(mValue);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mIsPush = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mIsPush = false;
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    public void setRange(float minValue,float maxValue){
        mMax = maxValue;
        mMin = minValue;
    }

    public void setCallback(TerminalEquipmentCtrlCallback obj){
        mCallback = obj;
    }

    private void drawLine(float value){
        if(value > mInMin)
            value = mInMin;
        if(value < mInMax)
            value = mInMax;

        mRect.top = (int)value - mRectHeight;
        mRect.bottom = (int)value + mRectHeight;
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawLine(mWidth * 0.5f,mInMin,mWidth * 0.5f,value,mPaintDown);
        canvas.drawLine(mWidth * 0.5f,value,mWidth * 0.5f,mInMax,mPaintUp);
        canvas.drawRect(mRect,mPaintDown);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public interface TerminalEquipmentCtrlCallback{
        public void onTerminalEquipmentChange(float value);
    }
}
