package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zhangxiaokun.firework.DataManager.GameUserInfo;
import com.zhangxiaokun.firework.R;

import java.util.jar.Attributes;

/**
 * Created by zhangxiaokun on 2014/12/22.
 */
public class GameUserInfoView extends LinearLayout {
    public GameUserInfoView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.game_user_info_view, this);
    }

    public void setGameUserInfo(GameUserInfo obj){

    }
}
