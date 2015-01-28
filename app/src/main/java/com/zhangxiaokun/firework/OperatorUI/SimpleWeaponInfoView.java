package com.zhangxiaokun.firework.OperatorUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2014/12/2.
 */
public class SimpleWeaponInfoView extends LinearLayout {
    private ImageView mWeaponView;
    private TextView mText1;
    private TextView mText2;
    public SimpleWeaponInfoView(Context context,AttributeSet attrs){
        super(context,attrs);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.simple_weapon_info_view,this);
    }

    public void setWeaponViewInfo(){

    }
}
