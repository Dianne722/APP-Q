package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity5 extends AppCompatActivity {
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
    }

    public void onResume() {
        super.onResume();
        isVisible = true;
        if (isVisible) {
            TextView clock = findViewById(R.id.my_date_time);
            TextView date = findViewById(R.id.my_date);
            TextView week = findViewById(R.id.my_week);
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // 设置时间格式
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

            // 格式化时间
            String currentTime = timeFormat.format(currentDate);
            String currentDateStr = dateFormat.format(currentDate);
            String currentWeekDay = weekFormat.format(currentDate);

            // 填充到 TextView 中
            clock.setText(currentTime);
            date.setText(currentDateStr);
            week.setText(currentWeekDay);

        }
    }
        public void onPause () {
            super.onPause();
            isVisible = false;
        }
    }