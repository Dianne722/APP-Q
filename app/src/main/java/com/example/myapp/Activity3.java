package com.example.myapp;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;

public class Activity3 extends AppCompatActivity {
    private boolean isVisible = false;
    private Uri photoUri;
    private ImageView photo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    public void onResume() {
        ImageView clock = findViewById(R.id.clock1);
        ImageView timer = findViewById(R.id.timer1);
        ImageView calendar = findViewById(R.id.calendar1);
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        EditText message = findViewById(R.id.message1);
        ImageView photo2 = findViewById(R.id.photo2);
        ImageView call = findViewById(R.id.call1);
        ImageView talk = findViewById(R.id.talk1);
        ImageView people = findViewById(R.id.peoples);
        ImageView camera = findViewById(R.id.takePhoto);
        ImageView ZHI = findViewById(R.id.ZHI);
        Button baiDu = findViewById(R.id.baiDu);
        super.onResume();
        isVisible = true;
        if (isVisible) {
            clock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                    startActivity(intent);
                }
            });
            ZHI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String packageName="com.eg.android.AlipayGphone";
                    String className="com.eg.android.AlipayGphone.AlipayLogin";
                    turnActivity(packageName,className);
                }
            });
            timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AlarmClock.ACTION_SHOW_TIMERS);
                    startActivity(intent);
                }
            });
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                    startActivity(intent);
                }
            });
            baiDu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("https://www.baidu.com");
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(ContactsContract.Contacts.CONTENT_URI);
                    startActivity(intent);
                }
            });
           camera.setOnClickListener(new View.OnClickListener() {
           @Override
                public void onClick(View view) {
                  dispatchTakePictureIntent();
             }
            });
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber2 = phoneNumber.getText().toString().trim();

                    // 检查电话号码是否为空
                    if (!phoneNumber2.isEmpty()) {
                        // 创建一个拨号的Intent
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber2));
                        startActivity(dialIntent);
                    } else {
                        // 提示用户输入有效的电话号码
                        Toast.makeText(getApplicationContext(), "请输入有效的电话号码", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            talk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber3 = phoneNumber.getText().toString().trim();
                    String message2 = message.getText().toString().trim();
                    // 检查电话号码和短信内容是否为空
                    if (!phoneNumber3.isEmpty() && !message2.isEmpty()) {
                        // 创建Intent对象，指定ACTION_SENDTO和电话号码的Uri
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber3));
                        // 设置短信内容
                        intent.putExtra("sms_body", message2);
                        // 启动Activity
                        startActivity(intent);
                        // 提示用户短信发送成功
                        Toast.makeText(getApplicationContext(), "短信已发送", Toast.LENGTH_SHORT).show();
                    } else {
                        // 提示用户输入有效的电话号码和短信内容
                        Toast.makeText(getApplicationContext(), "请输入有效的电话号码和短信内容", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        }
    protected void askPhotoPermissions() {

        String[] permissions = {
                "android.permission.CAMERA"
        };

        int requestCode = 200;

        requestPermissions(permissions, requestCode);

    }
    private void dispatchTakePictureIntent() {
        askPhotoPermissions();
        Intent intent = new Intent(); // 指定拍照\
        //
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        photo2 = findViewById(R.id.photo2);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo2.setImageBitmap(imageBitmap);
        }
    }
    public void turnActivity(String packageName,String className){
        Intent intent =new Intent();

        ComponentName cmp =new ComponentName(packageName, className);

        intent.setAction(Intent.ACTION_MAIN);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setComponent(cmp);

        startActivity(intent);
    }
    public void onPause () {
            super.onPause();
            isVisible = false;
    }
}