package com.example.myapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.stream.Collectors;

public class Chafen extends AppCompatActivity {
    private ListView gradesListView;
    private EditText findStudentScoreEdit;
    private GradeAdapter gradeAdapter;
    private List<Grade> gradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chafen);

        findAllViewsById();

        LoadAllGrades();

        findStudentScoreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    return;
                }

                String filterId = charSequence.toString();
                List<Grade> grades = gradeList.stream()
                        .filter(grade -> grade.getStudent().getId().toString().equals(filterId))
                        .collect(Collectors.toList());

                showGradesInfo(grades);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void findAllViewsById() {
        gradesListView = findViewById(R.id.coursesList);
        findStudentScoreEdit = findViewById(R.id.stuNumber);
    }

    private void showGradesInfo(List<Grade> grades) {
        gradeAdapter = new GradeAdapter(this, grades);

        gradesListView.setAdapter(gradeAdapter);
    }

    private void LoadAllGrades() {
        new LoadAllGradesGetTask().execute(WebApiUrl.GRADE_LIST);
    }

    private class LoadAllGradesGetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpConnect.getHttpResult(urls[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            gradeList = JsonUtils.toBean(result, new TypeReference<List<Grade>>() {});
        }
    }
}