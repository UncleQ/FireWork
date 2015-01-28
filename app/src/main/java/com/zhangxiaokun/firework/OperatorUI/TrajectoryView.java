package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zhangxiaokun.firework.MapCalculator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangxiaokun on 2014/12/4.
 */
public class TrajectoryView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private MapCalculator mapCalculator;
    private float trajectoryTime;
    //private MyThread myThread;
    private int curIndex;
    private int arraySize;
    private int sleepTime;
    private Rect drawRect;
    private Rect showRect;

    private Timer mTimer;
    private MyTimerTask mTimerTask;
    int Y_axis[],//保存正弦波的Y轴上的点
            centerY,//中心线
            oldX,oldY,//上一个XY点
            currentX;//当前绘制到的X轴上的点

    public TrajectoryView(Context context,AttributeSet attrs){
        super(context,attrs);
        holder = this.getHolder();
        holder.addCallback(this);
        drawRect = new Rect();
        showRect = new Rect();
        trajectoryTime = 0;
        //myThread = new MyThread(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //myThread.isRun = true;
        //myThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //myThread.isRun = false;
    }

    public void setMapCalculator(MapCalculator obj){
        mapCalculator = obj;
    }

    public void startAction(){
        //myThread.isRun = true;
        //myThread.start();
        ClearDraw();
        trajectoryTime = 0;
        mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(), 0, 10);//动态绘制正弦波
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            mapCalculator.setTrajectoryTime(trajectoryTime);
            int curX = mapCalculator.getCurX();
            int curY = mapCalculator.getCurY();
            drawRect.bottom = curY + 5;
            drawRect.top = curY - 5;
            drawRect.left = curX - 5;
            drawRect.right = curX + 5;
            //ClearDraw(drawRect);
            DrawTrajectory(mapCalculator.getCurX(), mapCalculator.getCurY());
            trajectoryTime += 0.01;
            if(trajectoryTime > mapCalculator.getTrajectoryTime()){
                mTimer.cancel();
            }
        }
    }

    private void DrawTrajectory(int curX,int curY) {
        showRect.bottom = curY + 3;
        showRect.top = curY - 3;
        showRect.left = curX - 3;
        showRect.right = curX + 3;
        Canvas canvas = holder.lockCanvas(drawRect);// 关键:获取画布
        Paint mPaint = new Paint();
        mPaint.setColor(Color.GREEN);// 画笔为绿色
        mPaint.setStrokeWidth(3);// 设置画笔粗细
        canvas.drawRect(showRect, mPaint);
        holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
    }

    private void ClearDraw() {
        Canvas canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        holder.unlockCanvasAndPost(canvas);
    }

    private void ClearDraw(Rect drawRect) {
        Canvas canvas = holder.lockCanvas(drawRect);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        holder.unlockCanvasAndPost(canvas);
    }

    //线程内部类
    /*class MyThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
            curIndex = 0;
        }

        @Override
        public void run() {
            curIndex = 1;
            Paint p = new Paint(); //创建画笔
            p.setColor(Color.WHITE);
            arraySize = mPointArrayList.size();
            //Rect r = new Rect(0,0,10,10);
            //while (curIndex < arraySize) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        c.drawColor(Color.BLACK);//设置画布背景颜色

                        Paint pp = new Paint(); //创建画笔
                        pp.setStrokeWidth(10);//
                        pp.setColor(Color.WHITE);
                        Rect r = new Rect(10,10,100,100);
                        c.drawRect(r, pp);
                        c.drawText("这是第" + (curIndex) + "秒", 100, 310, pp);
                        //Thread.sleep(1000);//睡眠时间为1秒
                        //c.drawText("a",screenPoints[curIndex].x,screenPoints[curIndex].y,p);

                        //c.drawRect(r,p);
                        //c.drawLine(mPointArrayList.get(curIndex - 1).x,mPointArrayList.get(curIndex - 1).y,
                        //        mPointArrayList.get(curIndex).x,mPointArrayList.get(curIndex).y,p);
                        //c.drawText("a",mPointArrayList.get(curIndex).x,mPointArrayList.get(curIndex).y,p);
                        //Thread.sleep(sleepTime);//睡眠时间为1秒
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                        curIndex++;
                    }
                }
            //}
        }
    }*/
}
