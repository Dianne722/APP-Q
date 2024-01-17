package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoList extends AppCompatActivity {
    private ImageView camera;
    private List<Photo> photos=new ArrayList<Photo>();
    private ListView photoList;
    private String filePath;
    private PhotoAdapter photoAdapter;
    private int REQUEST_IMAGE_CAPTURE=300;

    PhotoDAO photoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        camera=findViewById(R.id.btn_camera);
        photoList=findViewById(R.id.photoList);
        try {
            InitPhoto("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        askPermission();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });
        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(PhotoList.this, Activity4.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    private void InitPhoto(String key) throws FileNotFoundException {
        photos.clear();
        ImageFileAccess imageFileAccess =new ImageFileAccess(this);
        PhotoDAO dao=new PhotoDAO(this);
        for (Photo p: dao.findPhoto()){
            String uriS=p.getFilePath();
            Log.e("tag",uriS);
            Bitmap bitmap=imageZip(Uri.parse(uriS));
            if(bitmap==null){
                bitmap=imageFileAccess.readImageFile(p.getFileName());
            }
            p.setBitmap(bitmap);
            photos.add(p);
        }
        photoAdapter=new PhotoAdapter(this,photos);
        photoList.setAdapter(photoAdapter);
    }
    private Bitmap imageZip(Uri uri) throws FileNotFoundException {
        try {
            Bitmap bitmap=null;
            InputStream inputStream=null;
            inputStream=getContentResolver().openInputStream(uri);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=false;
            options.inSampleSize=5;
            bitmap=BitmapFactory.decodeStream(inputStream,null,options);
            return bitmap;}
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    private void askPermission(){
        String[] permissions={
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;

        requestPermissions(permissions, requestCode);
    }
    private void takeCarma(){
        Intent intent=new Intent();
        askPhotoPermissions();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }
    private void addPic(){
        CharSequence[] item={"拍照","图库"};
        AlertDialog.Builder myDialog =new AlertDialog.Builder(this);
        myDialog.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        takeCarma();break;
                    case 1:
                        getPhoto();break;
                }
            }
        });
        myDialog.show();
    }
    private void getPhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,200);
    }
    public String getImageFile(){
        String path= getDataDir().getPath();
        DateFormat sdf=new SimpleDateFormat("yyyyMMdd_hhmmss");
        String myDate=sdf.format(new Date());
        String mFilePath=path+"/"+myDate+".jpg";
        return mFilePath;
    }
    public void delete(Uri uri){
        String path=uri.getPath();
        File file=new File(uri.getPath());
        file.delete();
        if(file.exists()){
            if(file.delete()){
                System.out.println("file Deleted"+uri.getPath());
            }
            else {
                System.out.println("file not Deleted"+uri.getPath());
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View infoInputView = View.inflate(this, R.layout.mydialog, null);
        final EditText name_edit = infoInputView.findViewById(R.id.pName);
        final EditText info_edit = infoInputView.findViewById(R.id.pInfo);
        photoDAO=new PhotoDAO(this);
        ImageFileAccess imageFileAccess=new ImageFileAccess(PhotoList.this);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            info_edit.setHint("信息");
            AlertDialog.Builder myDialog = new AlertDialog.Builder(PhotoList.this);
            myDialog.setTitle("描述一下");
            myDialog.setView(infoInputView);
            myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = name_edit.getText().toString();
                    String info = info_edit.getText().toString();
                    imageFileAccess.saveImageFile(name,imageBitmap);
                    filePath = imageFileAccess.getImageFile();
                    Photo photo = new Photo();
                    photo.setFileName(name);
                    photo.setBitmap(imageBitmap);
                    photo.setInfo(info);
                    photo.setFilePath(filePath);
                    long i = photoDAO.insert(photo);//保存图片信息到数据库
                    //利用数据库数据刷新
                    photos.add(photo);
                    photoAdapter=new PhotoAdapter(PhotoList.this,photos);
                    photoList.setAdapter(photoAdapter);
                }
            });
            myDialog.setNegativeButton("取消", null);
            myDialog.show();
        }
        if (requestCode == 200) {//请求码是获得照片
            if (resultCode == RESULT_OK) {//从URI中获得图片
                Uri uri = data.getData();
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    info_edit.setHint("信息");
                    AlertDialog.Builder myDialog = new AlertDialog.Builder(PhotoList.this);
                    myDialog.setTitle("描述一下");
                    myDialog.setView(infoInputView);
                    myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = name_edit.getText().toString();
                            String info = info_edit.getText().toString();
                            Photo photo = new Photo();
                            photo.setFileName(name);
                            photo.setBitmap(bitmap);
                            photo.setInfo(info);
                            filePath=uri.toString();
                            photo.setFilePath(filePath);
                            long i = photoDAO.insert(photo);//保存图片信息到数据库
                            //利用数据库数据刷新
                            try {
                                InitPhoto("");
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    myDialog.setNegativeButton("取消", null);
                    myDialog.show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    protected void askPhotoPermissions() {

        String[] permissions = {
                "android.permission.CAMERA"
        };

        int requestCode = 200;

        requestPermissions(permissions, requestCode);

    }

}
