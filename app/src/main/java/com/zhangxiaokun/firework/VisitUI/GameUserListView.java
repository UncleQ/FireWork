package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.zhangxiaokun.firework.R;

/**
 * Created by zhangxiaokun on 2014/12/23.
 */
public class GameUserListView extends ListView {
    public GameUserListView (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackground(getResources().getDrawable(R.drawable.linearlayout));
    }

    public GameUserListView (Context context) {
        super(context);
        this.setBackground(getResources().getDrawable(R.drawable.linearlayout));
    }
}
