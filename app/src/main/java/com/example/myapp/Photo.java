package com.example.myapp;

import android.graphics.Bitmap;

public class Photo {
    private int id;
    private String fileName;
    private Bitmap bitmap;
    private  String info;
    private String filePath;//大图保存路径

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName){
        this.fileName=fileName;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap=bitmap;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }
    public void setInfo(String Info){
        this.info=Info;
    }
    public String getInfo(){
        return info;
    }

}
