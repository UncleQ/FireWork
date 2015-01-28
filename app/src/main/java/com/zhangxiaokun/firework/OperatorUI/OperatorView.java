package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.zhangxiaokun.firework.MapCalculator;
import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2014/11/17.
 */
public class OperatorView extends LinearLayout implements
        TerminalEquipmentCtrlView.TerminalEquipmentCtrlCallback{
    public static final int TURN_LEFT = 0;
    public static final int TURN_RIGHT = 1;
    private double mLat,mLon,mTargetLat,mTargetLon;
    private TerminalEquipmentCtrlView mVerticalSeekBar;
    private WindLevelView mWindLevelView;
    private WindBearingView mWindBearingView;
    private TextView mLargeTextView;
    private TextView mWeakTextView;
    private TextView mTextBearing;
    private TextView mTextAngle;
    private TextView mTextPower;
    private BearingButtonView mLeftButtonView;
    private BearingButtonView mRightButtonView;
    private FireButtonView mFireButtonView;
    private BearingLineView mBearingLineView;
    private SurfaceView mPowerShowView;
    private SurfaceHolder mPowerShowHolder;
    private SurfaceHolder mTrajectoryHolder;
    private TrajectoryView mTrajectoryView;
    private MapCalculator mMapCalculator;
    private boolean mIsPushFire;
    private boolean mIsPushDir;
    private int mStanderTime;
    private PowerShowAsyncTask mPowerShowAsyncTask;
    private DirChangeAsyncTask mDirChangeAsyncTask;
    private float mCurDir;
    private float mCurAngle;
    private float mTargetDir;
    private float mRangeDegree;
    private int mDirLinePercent;

    private boolean mIsBigDisDir;
    private float mBigDisDir;
    private float mLittleDisDir;

    private Paint mPaint;
    private Path mPath;

    private AMap mMap;

    public OperatorView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.opp_layout, this);

        mIsBigDisDir = true;
        mLargeTextView = (TextView)this.findViewById(R.id.large_text);
        mWeakTextView = (TextView)this.findViewById(R.id.weak_text);
        mLargeTextView.setBackgroundColor(0xFF8BBDEE);
        mWeakTextView.setBackgroundColor(0xFF1B273D);

        mWindLevelView = (WindLevelView)this.findViewById(R.id.wind_line);
        mWindBearingView = (WindBearingView)this.findViewById(R.id.wind_view);

        mVerticalSeekBar = (TerminalEquipmentCtrlView) this.findViewById(R.id.angle_seek_bar);
        mVerticalSeekBar.setRange(0, 45);
        mVerticalSeekBar.setCallback(this);

        mTextBearing = (TextView) this.findViewById(R.id.text_bearing);
        mTextAngle = (TextView) this.findViewById(R.id.text_angle);
        mTextPower = (TextView) this.findViewById(R.id.text_power);

        mTrajectoryView = (TrajectoryView) this.findViewById(R.id.trajectory_view);
        mTrajectoryView.setZOrderOnTop(true);
        mTrajectoryHolder = mTrajectoryView.getHolder();
        mTrajectoryHolder.setFormat(PixelFormat.TRANSLUCENT);

        mBearingLineView = (BearingLineView) this.findViewById(R.id.dir_image_line);
        mBearingLineView.setZOrderOnTop(true);

        mPowerShowView = (SurfaceView) this.findViewById(R.id.power_show_view);
        mPowerShowHolder = mPowerShowView.getHolder();
        mPowerShowView.setZOrderOnTop(true);
        mPowerShowHolder.setFormat(PixelFormat.TRANSLUCENT);

        mLeftButtonView = (BearingButtonView) this.findViewById(R.id.left_button_surface_view);
        mLeftButtonView.setZOrderOnTop(true);
        mLeftButtonView.setType(BearingButtonView.LEFT_BUTTON);
        mRightButtonView = (BearingButtonView) this.findViewById(R.id.right_button_surface_view);
        mRightButtonView.setZOrderOnTop(true);
        mRightButtonView.setType(BearingButtonView.RIGHT_BUTTON);
        mFireButtonView = (FireButtonView) this.findViewById(R.id.fire_button_surface_view);
        mFireButtonView.setZOrderOnTop(true);

        mFireButtonView.setOnTouchListener(fireButtonListener);
        mLeftButtonView.setOnTouchListener(leftListener);
        mRightButtonView.setOnTouchListener(rightListener);
        mLargeTextView.setOnTouchListener(largeListener);
        mWeakTextView.setOnTouchListener(weakListener);

        mPaint = new Paint();
        mPath = new Path();
        mIsPushFire = false;
        mIsPushDir = false;
        mStanderTime = 1;
        mCurDir = 60;
        mBigDisDir = 5;
        mLittleDisDir = 1;
        mTargetDir = 45;
        mRangeDegree = 90;
    }

    private OnTouchListener fireButtonListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsPushFire = true;
                    mFireButtonView.drawButton(mIsPushFire);
                    mPowerShowAsyncTask  = new PowerShowAsyncTask(mStanderTime);
                    mPowerShowAsyncTask.execute(0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    mIsPushFire = false;
                    mFireButtonView.drawButton(mIsPushFire);
                    mPowerShowAsyncTask.draw(0);
                    fireAction();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mIsPushFire = false;
                    mFireButtonView.drawButton(mIsPushFire);
                    mPowerShowAsyncTask.draw(0);
                    fireAction();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private OnTouchListener leftListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsPushDir = true;
                    mLeftButtonView.drawButton(mIsPushDir);
                    mDirChangeAsyncTask = new DirChangeAsyncTask(TURN_LEFT);
                    mDirChangeAsyncTask.execute(0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    mIsPushDir = false;
                    mLeftButtonView.drawButton(mIsPushDir);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mIsPushDir = false;
                    mLeftButtonView.drawButton(mIsPushDir);
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private OnTouchListener rightListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsPushDir = true;
                    mRightButtonView.drawButton(mIsPushDir);
                    mDirChangeAsyncTask = new DirChangeAsyncTask(TURN_RIGHT);
                    mDirChangeAsyncTask.execute(0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    mIsPushDir = false;
                    mRightButtonView.drawButton(mIsPushDir);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mIsPushDir = false;
                    mRightButtonView.drawButton(mIsPushDir);
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private OnTouchListener largeListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(mIsBigDisDir == false) {
                        mIsBigDisDir = true;
                        mLargeTextView.setBackgroundColor(0xFF8BBDEE);
                        mWeakTextView.setBackgroundColor(0xFF1B273D);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private OnTouchListener weakListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(mIsBigDisDir == true) {
                        mIsBigDisDir = false;
                        mLargeTextView.setBackgroundColor(0xFF1B273D);
                        mWeakTextView.setBackgroundColor(0xFF8BBDEE);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    public void onTerminalEquipmentChange(float value){
        mCurAngle = value;
        if(mCurAngle > 0 && mCurAngle < 45 && mMap != null){
            mMap.moveCamera(CameraUpdateFactory.changeTilt(mCurAngle));
            mWindBearingView.draw(mCurDir,mCurAngle);
        }
        mTextAngle.setText(String.format("TE:%.0f",mCurAngle));
    }

    public void setStanderTime(int time){
        mStanderTime = time;
        if(mPowerShowAsyncTask != null){
            mPowerShowAsyncTask.setPercentTime(mStanderTime);
        }
    }

    public void setMap(AMap map){
        mMap = map;
    }

    public void setPosition(double lat,double lon){
        mLat = lat;
        mLon = lon;
    }

    public void setTarget(double lat,double lon){
        mTargetLat = lat;
        mTargetLon = lon;
    }

    public void setTargetDir(float value){
        mTargetDir = value;
    }

    public void setRange(float value){
        mRangeDegree = value;
    }

    public void setDisDir(float bigValue,float littleValue){
        mBigDisDir = bigValue;
        mLittleDisDir = littleValue;
    }

    public void setMapCalculator(MapCalculator obj){
        mMapCalculator = obj;
        mTrajectoryView.setMapCalculator(obj);
    }

    private void fireAction(){
        mMapCalculator.setViewWidth(mTrajectoryView.getWidth());
        //mMapCalculator.setTrajectory(mLat,mLon,mCurAngle,-mCurDir,100);
        mMapCalculator.setTrajectory(mLat,mLon,mCurAngle,-40,100);
        mTrajectoryView.startAction();
    }

    private void changeDir(int dir){
        if(dir == TURN_LEFT){
            if(mIsBigDisDir) {
                mCurDir += mBigDisDir;
            }else {
                mCurDir += mLittleDisDir;
            }
            mCurDir %= 360;
        }else {
            if (mIsBigDisDir) {
                mCurDir -= mBigDisDir;
            } else {
                mCurDir -= mLittleDisDir;
            }
            mCurDir = (mCurDir + 360) % 360;
        }
        float left = (mCurDir + (mRangeDegree / 2)) % 360;
        int percent = getDirPercent(mTargetDir, left);
        mMap.moveCamera(CameraUpdateFactory.changeBearing(-mCurDir));
        mWindBearingView.draw(mCurDir,mCurAngle);
        //mTextBearing.setText(String.format("Bearing:%.0f",mCurDir));
        if(mDirLinePercent != percent){
            mBearingLineView.drawBearingPoint(percent);
            mDirLinePercent = percent;
        }
    }

    private int getDirPercent(float degree,float left){
        if(degree > left){
            degree -= 360;
        }
        float dis = left - degree;
        if(dis <= mRangeDegree){
            return (int)(dis / mRangeDegree * 100);
        }
        if(dis > mRangeDegree && dis < (180 + mRangeDegree / 2)){
            return 101;
        }
        if(dis >= (180 + mRangeDegree / 2)){
            return -1;
        }
        return 50;
    }

    public void setWeaponInfo(){

    }

    private class DirChangeAsyncTask extends AsyncTask<Integer,Integer,Integer>{
        private int mDirType;
        public DirChangeAsyncTask(int dir){
            mDirType = dir;
        }

        @Override
        protected Integer doInBackground(Integer... params){
            while (mIsPushDir){
                try {
                    changeDir(mDirType);
                    publishProgress((int)mCurDir);
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result){}

        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            mTextBearing.setText(String.format("Bearing:%d",value));
        }
    }


    private class PowerShowAsyncTask extends AsyncTask<Integer,Integer,Integer>{
        private int mPercent;
        private int mPercentTime;
        private Rect mPercentRect;
        private Paint mPaint;
        private int startPos,leftPos,rightPos,endPos,cent2,cent8;

        public PowerShowAsyncTask(int standerTime){
            super();
            mPercentTime = standerTime * 10;
            mPercentRect = new Rect();
            mPaint = new Paint();
            int viewWidth = mPowerShowView.getWidth();
            int viewHeight = mPowerShowView.getHeight();
            startPos = (int)(viewHeight * 0.98);
            leftPos = (int)(viewWidth * 0.1);
            rightPos = (int)(viewWidth * 0.9);
            mPercentRect.left = leftPos;
            mPercentRect.right = rightPos;
            endPos = (int)(viewHeight * 0.02);
            cent8 = (int)((startPos - endPos) / 50 * 0.9);
            cent2 = (int)((startPos - endPos) / 50 * 0.3);
        }
        @Override
        protected Integer doInBackground(Integer... params){
            mPercent = 0;
            for(int i=0;i<100;i++){
                if(mIsPushFire == true) {
                    try {
                        mPercent += 1;
                        publishProgress(mPercent);
                        Thread.sleep(mPercentTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    return mPercent;
                }
            }
            return mPercent;
        }

        @Override
        protected void onPostExecute(Integer result){
            if(result == 100){
                draw(result);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            mTextPower.setText(String.format("Power:%d",value));
            if(value % 2 == 0) {
                draw(value);
            }
        }

        void setPercentTime(int percentTime){
            mPercentTime = percentTime;
        }

        void draw(int powerPercent){
            if(mIsPushFire){
                mPaint.setColor(0xFF8BBDEE);
                int cent = cent2 + cent8;
                mPercentRect.bottom = startPos - cent * powerPercent / 2;
                mPercentRect.top = mPercentRect.bottom - cent8;
                Canvas canvas = mPowerShowHolder.lockCanvas(mPercentRect);
                canvas.drawRect(mPercentRect, mPaint);
                mPowerShowHolder.unlockCanvasAndPost(canvas);
            }else{
                Canvas canvas = mPowerShowHolder.lockCanvas(null);
                //canvas.drawColor(Color.RED);
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                mPowerShowHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
