package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2014/12/2.
 */
public class SimpleTargetInfoView extends LinearLayout {
    private TextView mName;
    private TextView mDistance;
    private TextView mAngle;
    public SimpleTargetInfoView(Context context,AttributeSet attrs){
        super(context,attrs);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.simple_target_info_view,this);
        mName = (TextView)findViewById(R.id.simple_target_name);
        mDistance = (TextView)findViewById(R.id.simple_target_distance);
        mAngle = (TextView)findViewById(R.id.simple_target_angle);
    }

    public void setTargetName(String targetName){
        mName.setText(targetName);
    }

    public void setTargetDistance(String targetDistance){
        mDistance.setText(targetDistance);
    }

    public void setTargetAngle(String targetAngle){
        mAngle.setText(targetAngle);
    }
}
