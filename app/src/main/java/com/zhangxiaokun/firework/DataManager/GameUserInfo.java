package com.zhangxiaokun.firework.DataManager;

import com.zhangxiaokun.firework.Utils.ByteUtil;

/**
 * Created by zhangxiaokun on 2014/10/15.
 */
public class GameUserInfo {
    private String mName;
    private byte mAge;
    private byte mHP;
    private int mUserID;
    private int mWorkerID;
    private double mLat;
    private double mLon;
    private int mDisLat;//m
    private int mDisLon;//m

    public GameUserInfo(String strName,byte bAga,byte bHP,int nUserID,int nWorkerID,double dLon,double dLat){
        mName = strName;
        mAge = bAga;
        mHP = bHP;
        mUserID = nUserID;
        mWorkerID = nWorkerID;
        mLon = dLon;
        mLat = dLat;
    }

    public String getName(){
        return mName;
    }

    public double getLat(){
        return mLat;
    }

    public double getLon(){
        return mLon;
    }

    public byte[] GetLoginByte(){
        byte[] sendBuffer = new byte[42];
        ByteUtil.putInt(sendBuffer, 0, 0);
        ByteUtil.putShort(sendBuffer, (short) 42, 4);
        ByteUtil.putShort(sendBuffer, (short) 0x6101, 6);
        byte[] username = mName.getBytes();
        int nameLen = mName.length();
        for(int i=8;i<24;i++)
        {
            if(i<nameLen)
                sendBuffer[i] = username[i];
            else
                sendBuffer[i] = 0;
        }
        sendBuffer[24] = mHP;//HP
        sendBuffer[25] = mAge;//age
        ByteUtil.putInt(sendBuffer,mUserID,26);//gameuserid
        ByteUtil.putInt(sendBuffer,mWorkerID,30);//nworkerID
        ByteUtil.putInt(sendBuffer,(int)(mLat * 1000000),34);
        ByteUtil.putInt(sendBuffer,(int)(mLon * 1000000),38);
        return sendBuffer;
    }
}
