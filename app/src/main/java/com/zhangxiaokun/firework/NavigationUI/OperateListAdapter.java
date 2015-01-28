package com.zhangxiaokun.firework.NavigationUI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.zhangxiaokun.firework.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by zhangxiaokun on 2014/11/7.
 */
public class OperateListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private ArrayList<String> mItemListString = new ArrayList<String>();
    int last_item;
    int [] images;
    private int selectedPosition = -1;
    private int nControl = 0;
    private int mMode = 0;

    public OperateListAdapter(Context context){
        this.context = context;
        mItemListString.add("手动瞄准");
        mItemListString.add("仿真模式");
        //mItemListString.add("视角跟随");
        mItemListString.add("武器选择");
        mItemListString.add("攻击");
        mItemListString.add("add");
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount(){
        return mItemListString.size();
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
        MainListViewHolder  holder = null;
        if(convertView == null){
            if(position == 0) {
                convertView = inflater.inflate(R.layout.mainlist_switch_item, null);
                holder = new MainListViewHolder();
                holder.aSwitch = (Switch)convertView.findViewById(R.id.switch_mode);
                holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            mMode = 1;//专业模式
                        }else {
                            mMode = 0;//娱乐模式
                        }
                    }
                });
                convertView.setTag(holder);
            }else if(position == 1){
                convertView = inflater.inflate(R.layout.mainlist_switch_item, null);
                holder = new MainListViewHolder();
                holder.aSwitch = (Switch)convertView.findViewById(R.id.switch_mode);
                holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            nControl = 1;//手动
                        }else {
                            nControl = 0;//参数
                        }
                    }
                });
                convertView.setTag(holder);
            }else{
                convertView = inflater.inflate(R.layout.mainlist_item, null);
                holder = new MainListViewHolder();
                holder.textView =(TextView)convertView.findViewById(R.id.main_item);
                holder.layout=(LinearLayout)convertView.findViewById(R.id.main_list_item_color_layout);
                convertView.setTag(holder);
            }
        }else{
            holder=(MainListViewHolder)convertView.getTag();
        }
        // 设置选中效果
        if(position != 0 && position != 1){
            if(selectedPosition == position){
                holder.textView.setTextColor(Color.BLUE);
                holder.layout.setBackgroundColor(Color.LTGRAY);
            } else {
                //holder.textView.setTextColor(Color.WHITE);
                holder.layout.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        if(position == 0 || position == 1){
            holder.aSwitch.setText(mItemListString.get(position));
            //holder.aSwitch.setTextColor(Color.BLACK);
        }else {
            holder.textView.setText(mItemListString.get(position));
            //holder.textView.setTextColor(Color.BLACK);
        }

        //holder.textView.setText(item_names[position]);
        //holder.textView.setTextColor(Color.BLACK);
        //holder.imageView.setBackgroundResource(images[position]);

        return convertView;
    }

    public int getControlType(){
        return nControl;
    }

    public int getModeType(){
        return mMode;
    }

    public static class MainListViewHolder {
        public TextView textView;
        public LinearLayout layout;
        public Switch aSwitch;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

}
