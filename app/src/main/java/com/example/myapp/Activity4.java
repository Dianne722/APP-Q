package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Activity4 extends AppCompatActivity {
    private ImageView switcher;
    private TextView pic_name,pic_Info;
    private int position;
    private List<Photo> photos=new ArrayList<Photo>();
    Photo p;
    private ImageFileAccess imageFileAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        Init();
        p=photos.get(position);
        Bitmap bitmap1=imageFileAccess.readImageFile(p.getFileName());
        if(bitmap1!=null){
            switcher.setImageBitmap(bitmap1);
        }
        else {
            String uriS=p.getFilePath();
            Log.e("tag",uriS);
            try {
                Bitmap bitmap=imageZip(Uri.parse(uriS));
                switcher.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            pic_Info.setText(p.getInfo());
            pic_name.setText(p.getFileName());
        }
    }
    private void Init(){
        imageFileAccess =new ImageFileAccess(this);
        PhotoDAO dao=new PhotoDAO(this);
        for (Photo p: dao.findPhoto()){
            photos.add(p);
        }
        Intent intent =getIntent();
        position=intent.getIntExtra("position",-1);
        switcher=findViewById(R.id.imageShow);
        pic_Info=findViewById(R.id.pic_info);
        pic_name=findViewById(R.id.pic_name);
    }
    private Bitmap imageZip(Uri uri) throws FileNotFoundException {
        try {
            Bitmap bitmap=null;
            InputStream inputStream=null;
            inputStream=getContentResolver().openInputStream(uri);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=false;
            bitmap=BitmapFactory.decodeStream(inputStream,null,options);
            return bitmap;}
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}