package com.zhangxiaokun.firework.NavigationUI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangxiaokun.firework.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by zhangxiaokun on 2014/12/24.
 */
public class AccountManageAdapter extends BaseAdapter {
    public static final int OFFLINE_STATE = 0;
    public static final int ONLINE_STATE = 1;
    private int mState;//0 未登陆  1登陆
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> mItemListOff = new ArrayList<String>();
    private ArrayList<String> mItemListOn = new ArrayList<String>();

    public AccountManageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mItemListOff.add("Login");
        mItemListOff.add("Search Around");
        mItemListOff.add("Search Setting");
        mItemListOff.add("How to Play");

        mItemListOn.add("Search Around");
        mItemListOn.add("Search Setting");
        mItemListOn.add("Friends");
        mItemListOn.add("My Wallet");
        mItemListOn.add("Change User");
        mItemListOn.add("Logout");
        mItemListOn.add("How to Play");
        mState = OFFLINE_STATE;
    }
    @Override
    public int getCount(){
        if(mState == ONLINE_STATE)
            return mItemListOn.size();
        else
            return mItemListOff.size();
    }

    @Override
    public Objects getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        AccountManageHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.actionbar_item, null);
            holder = new AccountManageHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.actionbar_item_text);
            convertView.setTag(holder);
        }else{
            holder=(AccountManageHolder)convertView.getTag();
        }
        // 设置选中效果
        if(mState == ONLINE_STATE)
            holder.textView.setText(mItemListOn.get(position));
        else
            holder.textView.setText(mItemListOff.get(position));
        //holder.textView.setTextColor(Color.BLACK);

        //holder.textView.setText(item_names[position]);
        //holder.textView.setTextColor(Color.BLACK);
        //holder.imageView.setBackgroundResource(images[position]);

        return convertView;
    }

    public int getState(){
        return mState;
    }

    public static class  AccountManageHolder {
        public TextView textView;
        public LinearLayout layout;
    }
}
