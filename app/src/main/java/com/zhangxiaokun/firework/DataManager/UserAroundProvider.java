package com.zhangxiaokun.firework.DataManager;

import java.util.ArrayList;

/**
 * Created by zhangxiaokun on 2015/1/13.
 */
public class UserAroundProvider {
    private ArrayList<GameUserInfo> gameUserList = new ArrayList<GameUserInfo>();

    public UserAroundProvider(){}

    public void makeRandomUser(double lat,double lon){
        gameUserList.clear();
        for(int i=0;i<10;i++){
            double tempDirX = Math.random();
            double tempDirY = Math.random();
            double tempLat = Math.random() / 100;
            double tempLon = Math.random() / 100;
            if(tempDirX > 0.5){
                tempLat *= -1;
            }
            if(tempDirY > 0.5){
                tempLon *= -1;
            }
            String name = "zhang" + String.valueOf(i);
            GameUserInfo newUser = new GameUserInfo(name,(byte)i,(byte)100,100,0,lon + tempLon,lat + tempLat);
            gameUserList.add(newUser);
        }
    }

    public ArrayList<GameUserInfo> getUserList(double lat,double lon){
        makeRandomUser(lat,lon);
        return gameUserList;
    }

    public GameUserInfo getGameUserAround(int index){
        return gameUserList.get(index);
    }

    public void addGameUserAround(){}
}
