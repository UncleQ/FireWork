package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangxiaokun.firework.DataManager.GameUserInfo;
import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2015/1/23.
 */
public class GameUserMarkView extends LinearLayout {
    private LinearLayout layout;
    private TextView markName;
    private GameUserInfo gameUserInfo;
    private int curX,curY;
    private TouchMarkCallback callBack;
    public GameUserMarkView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mark_view, this);
        layout = (LinearLayout)findViewById(R.id.mark_view_layout);
        markName = (TextView)findViewById(R.id.mark_name);
        this.setOnClickListener(clickListener);
    }

    public void setGameUserInfo(GameUserInfo obj){
        gameUserInfo = obj;
        markName.setText(gameUserInfo.getName());
    }

    public void setCurPos(int x,int y){
        curX = x;
        curY = y;
    }

    public void setName(String name){
        markName.setText(name);
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View arg){
            if(callBack != null){
                callBack.onTouchMark(curX,curY,gameUserInfo);
            }
        }
    };

    public interface TouchMarkCallback{
        void onTouchMark(int x,int y,GameUserInfo obj);
    }

    public void setTouchMarkListener(TouchMarkCallback obj){
        callBack = obj;
    }
}
