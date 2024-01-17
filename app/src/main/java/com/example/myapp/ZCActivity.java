package com.example.myapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class ZCActivity extends AppCompatActivity {

    private EditText name,age,time_rx;
    private CheckBox computerNetwork,javaProgramming,embeddedSystems;
    private Spinner colleage;
    private Spinner major;
    private RadioButton Male,Female;
    private Button submit;
    private Student student;
    private String flag1 = "01";//信息学院
    private String flag2 = "02";//机电学院
    private String flag3 = "03";//土木学院
    private String stuNumber=null;//学号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zcactivity);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        time_rx=findViewById(R.id.time_rx);

        computerNetwork=findViewById(R.id.computer);
        javaProgramming=findViewById(R.id.java);
        embeddedSystems=findViewById(R.id.embedded);

        major=findViewById(R.id.Major);
        Male=findViewById(R.id.Male);
        Female=findViewById(R.id.Female);
        submit=findViewById(R.id.submit);

        String userName = name.getText().toString();//获取姓名
        String userAge = age.getText().toString();//获取年龄
        String userTimeRx = time_rx.getText().toString();//获取入学时间


        boolean isComputerNetworkSelected = computerNetwork.isChecked();//获取选课信息
        boolean isJavaProgrammingSelected = javaProgramming.isChecked();
        boolean isEmbeddedSystemsSelected = embeddedSystems.isChecked();


        boolean isMaleSelected = Male.isChecked();//男或者女
        boolean isFemaleSelected = Female.isChecked();
        colleage = findViewById(R.id.colleage);
        major = findViewById(R.id.Major);

        time_rx.setOnClickListener(new View.OnClickListener() {//点击选择入学时间
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), stuNumber, Toast.LENGTH_LONG).show();//告诉你学号
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void Init(){//初始化 获取控件

    }
    private void showDatePickerDialog() {//显示日历
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 在这里处理选择的日期
                        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        time_rx.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    // 处理学院选择变化的方法

}