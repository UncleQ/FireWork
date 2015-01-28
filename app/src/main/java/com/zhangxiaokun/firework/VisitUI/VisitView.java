package com.zhangxiaokun.firework.VisitUI;

import android.content.Context;
import android.graphics.Point;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.zhangxiaokun.firework.DataManager.GameUserInfo;
import com.zhangxiaokun.firework.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiaokun on 2014/12/22.
 */
public class VisitView extends LinearLayout implements AMap.OnCameraChangeListener,GameUserMarkView.TouchMarkCallback{
    private Context context;
    private double minLat,minLon,maxLat,maxLon;
    private AMap aMap;
    private Map<GameUserInfo,GameUserMarkView> userViewMap;
    private AbsoluteLayout absLayout;
    private GameUserInfoView gameUserInfoView;
    private GameUserListView gameUserListView;
    private GameUserListAdapter gameUserListAdapter;
    private ArrayList<GameUserInfo> arrayListGameUserInfo;
    private ArrayList<GameUserInfo> arrayListShowGameUserInfo;

    public VisitView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.visit_layout, this);
        absLayout = (AbsoluteLayout)this.findViewById(R.id.abs_visit_view);
        gameUserListView = new GameUserListView(this.getContext());
        userViewMap = new HashMap<GameUserInfo,GameUserMarkView>();
        arrayListGameUserInfo = new ArrayList<GameUserInfo>();
        for(int i=0;i<10;i++){
            arrayListGameUserInfo.add(new GameUserInfo("zxk",(byte)27,(byte)100,12345,1,1000,10000));
        }
        gameUserListAdapter = new GameUserListAdapter(this.getContext(),arrayListGameUserInfo);
        gameUserListView.setAdapter(gameUserListAdapter);
        gameUserInfoView = new GameUserInfoView(this.getContext());
        gameUserInfoView.setVisibility(INVISIBLE);
        AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,0,0);
        absLayout.addView(gameUserInfoView, lp);
    }

    @Override
    protected void onFinishInflate(){
        int width = getWidth();
        int height = getHeight();
        int littleWidth = width / 10;
        int littleHeight = height / 20;
    }

    @Override
    public void onCameraChange(CameraPosition position){
        Projection pro = aMap.getProjection();
        for (GameUserInfo curInfo : userViewMap.keySet()) {
            GameUserMarkView curView = userViewMap.get(curInfo);
            Point curPos = pro.toScreenLocation(new LatLng(curInfo.getLat(),curInfo.getLon()));
            curView.setX(curPos.x);
            curView.setY(curPos.y);
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition position){
        Projection pro = aMap.getProjection();
        for (GameUserInfo curInfo : userViewMap.keySet()) {
            GameUserMarkView curView = userViewMap.get(curInfo);
            Point curPos = pro.toScreenLocation(new LatLng(curInfo.getLat(),curInfo.getLon()));
            curView.setX(curPos.x);
            curView.setY(curPos.y);
        }
    }

    @Override
    public void onTouchMark(int x,int y,GameUserInfo obj){
        showUserInfoView(x, y, obj);
    }

    public void showList(ArrayList<GameUserInfo> gameUserList){
        Projection pro = aMap.getProjection();
        for(GameUserInfo curInfo:gameUserList){
            Point curPos = pro.toScreenLocation(new LatLng(curInfo.getLat(),curInfo.getLon()));
            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,curPos.x,curPos.y);
            GameUserMarkView newMarkView = new GameUserMarkView(this.getContext());
            newMarkView.setTouchMarkListener(this);
            newMarkView.setCurPos(curPos.x,curPos.y);
            absLayout.addView(newMarkView,params);
            newMarkView.setName(curInfo.getName());
            userViewMap.put(curInfo,newMarkView);
        }
    }

    public void setMap(AMap obj){
        aMap = obj;
        aMap.setOnCameraChangeListener(this);
    }

    public void showUserInfoView(int x,int y,GameUserInfo gameUserInfo){
        if(gameUserInfoView == null) {
            gameUserInfoView = new GameUserInfoView(this.getContext());
            gameUserInfoView.setVisibility(INVISIBLE);
        }
        gameUserInfoView.setX(x);
        gameUserInfoView.setY(y);
        gameUserInfoView.setGameUserInfo(gameUserInfo);
        gameUserInfoView.setVisibility(VISIBLE);
    }

    public void hideUserInfoView(){
        if(gameUserInfoView != null){
            gameUserInfoView.setVisibility(INVISIBLE);
        }
    }
}
