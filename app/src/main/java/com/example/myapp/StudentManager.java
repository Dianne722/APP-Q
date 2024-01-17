package com.example.myapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StudentManager extends AppCompatActivity {

    private boolean isVisible = false;

    private ListView studentListView;

    private EditText findStuInfoEdit;

    private StudentAdapter adapter;
    private ArrayList<Student> studentList = new ArrayList<>();

    static int ADDCODE = 100;
    static int EDITCODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);
    }


    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        if (!isVisible) {
            return;
        }

        findAllViewsById();

        showStudentList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    private void findAllViewsById() {
        studentListView = findViewById(R.id.studentList);
        findStuInfoEdit = findViewById(R.id.findStuInfo);
    }

    private void showStudentInfo(List<Student> students) {
        adapter = new StudentAdapter(this, students);

        studentListView.setAdapter(adapter);
    }

    private class ListStudentGetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpConnect.getHttpResult(urls[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            List<Student> students = JsonUtils.toBean(result, new TypeReference<List<Student>>() {});

            if (students != null) {
                showStudentInfo(students);
            }
        }
    }

    private void showStudentList() {
        new ListStudentGetTask().execute(WebApiUrl.STUDENT_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        showStudentList();
    }
}