package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangxiaokun.firework.DataManager.GameUserInfo;
import com.zhangxiaokun.firework.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by zhangxiaokun on 2014/12/23.
 */
public class GameUserListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<GameUserInfo> listGameUserInfo;
    private int selectedPosition;

    public GameUserListAdapter(Context context,ArrayList<GameUserInfo> list){
        this.context = context;
        inflater = LayoutInflater.from(context);
        listGameUserInfo = list;
    }

    @Override
    public int getCount(){
        return listGameUserInfo.size();
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
        GameUserListViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.game_user_info_view, null);
            holder = new GameUserListViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.game_user_image);
            holder.textViewID = (TextView)convertView.findViewById(R.id.game_user_id);
            holder.textViewSex = (TextView)convertView.findViewById(R.id.game_user_sex);
            holder.textViewAge = (TextView)convertView.findViewById(R.id.game_user_age);
            convertView.setTag(holder);
        }else{
            holder = (GameUserListViewHolder)convertView.getTag();
        }

        if(selectedPosition == position){
            holder.textViewID.setTextColor(Color.BLUE);
            //holder.layout.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.textViewID.setTextColor(Color.WHITE);
            //holder.layout.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    public void setListGameUserInfo(ArrayList<GameUserInfo> list){
        listGameUserInfo = list;
    }

    public static class  GameUserListViewHolder {
        public ImageView imageView;
        public TextView textViewID;
        public TextView textViewSex;
        public TextView textViewAge;
    }
}
