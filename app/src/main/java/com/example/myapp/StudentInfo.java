package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StudentInfo extends AppCompatActivity {
    private TextView courses,xinbie,name,major,xueyuan,stuNumber,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zcactivity);
        Init();
        //这里可以写 如果找到了信息 就把详细信息显示在TextView里 小明可以替换为任意字符串
        //name.setText("小明");
    }
    public void Init(){//初始化 获取控件
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        xueyuan=findViewById(R.id.xueyuan);
        xinbie=findViewById(R.id.xinbie);
        major=findViewById(R.id.major);
        stuNumber=findViewById(R.id.stuNumber);
        courses=findViewById(R.id.courses);
    }
}