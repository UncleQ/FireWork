package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2014/12/23.
 */
public class GameUserNumView extends LinearLayout {
    private TextView mText;
    public GameUserNumView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_view, this);
        mText = (TextView)this.findViewById(R.id.game_user_num);
    }

    public void setNum(int value){
        mText.setText(String.valueOf(value));
    }
}
