package com.example.myapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ZCActivity extends AppCompatActivity {

    private EditText name, age, time_rx, phoneNumber, mima;
    private CheckBox computerNetwork, javaProgramming, embeddedSystems;
    private Spinner colleage;
    private Spinner major;
    private RadioButton male, female;
    private Button submit;
    private Student student;
    private int flag1 = 1 ;//信息学院
    private int flag2 = 2 ;//机电学院
    private int flag3 = 3 ;//土木学院
    private Long stuNumber = null;//学号
    private StudentOperatorType studentOperatorType; // 操作模式

    // 课程键值对
    private final static HashMap<String, Long> CourseStrToIdMap = new HashMap<>();

    static {
        CourseStrToIdMap.put("计算机网络", 1L);
        CourseStrToIdMap.put("Java程序设计", 2L);
        CourseStrToIdMap.put("嵌入式基础", 3L);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zcactivity);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        time_rx = findViewById(R.id.time_rx);
        phoneNumber = findViewById(R.id.phoneNumber);
        mima = findViewById(R.id.mima);

        computerNetwork = findViewById(R.id.computer);
        javaProgramming = findViewById(R.id.java);
        embeddedSystems = findViewById(R.id.embedded);

        major = findViewById(R.id.Major);
        male = findViewById(R.id.Male);
        female = findViewById(R.id.Female);
        submit = findViewById(R.id.submit);

        String userName = name.getText().toString(); //获取姓名
        String userAge = age.getText().toString(); //获取年龄
        String userTimeRx = time_rx.getText().toString();//获取入学时间

        boolean isComputerNetworkSelected = computerNetwork.isChecked();//获取选课信息
        boolean isJavaProgrammingSelected = javaProgramming.isChecked();
        boolean isEmbeddedSystemsSelected = embeddedSystems.isChecked();


        boolean isMaleSelected = male.isChecked();//男或者女
        boolean isFemaleSelected = female.isChecked();
        colleage = findViewById(R.id.colleage);
        major = findViewById(R.id.Major);

        // 接受操作类型
        ReceiveOperatorType();

        // 接收数据并且填充
        if (studentOperatorType == StudentOperatorType.Edit) {
            ReceiveData();
            SetDatas(student);
        }

        time_rx.setOnClickListener(new View.OnClickListener() {//点击选择入学时间
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() { //提交
            @Override
            public void onClick(View view) {
                if (studentOperatorType == StudentOperatorType.Edit) {
                    // 更新存储的学生信息
                    student = MergeUiIntoStudentObject();
                    // 更新学生信息
                    new UpdateStudentPutTask().execute(student);
                    // 更新选课
                    updateStudentSelectedCourses();

                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                } else if (studentOperatorType == StudentOperatorType.Add) {
                    student = MergeUiIntoStudentObject();

                    new AddStudentPostTask().execute(student);

                    Toast.makeText(getApplicationContext(), "添加成功, 您的学号为: " + student.getId(), Toast.LENGTH_LONG).show(); //告诉你学号
                }
            }
        });
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

    private void ReceiveOperatorType() {
        studentOperatorType = (StudentOperatorType) getIntent().getSerializableExtra("operationType");
    }

    private void ReceiveData() {
        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("student");
        stuNumber = student.getId();
    }

    // 设置所有的 Data

    private void SetDatas(Student student) {
        name.setText(student.getName());
        age.setText(student.getAge().toString());
        phoneNumber.setText(student.getPhone());
        mima.setText(student.getPassword());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        time_rx.setText(sdf.format(student.getEnrollmentDate()));
        if (student.getGender().equals("男")) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        // 设置学院
        colleage.setSelection(student.getCollegeId() - 1);

        // 设置专业
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) major.getAdapter();
        int position = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(student.getMajor())) {
                position = i;
                break;
            }
        }
        major.setSelection(position);

        // 获取已选课程信息，并且展示
        new ListCoursestGetTask().execute(WebApiUrl.makeListCoursesUrl(student.getId()));
    }

    private void ShowCourses(List<Course> courses) {
        for (Course course : courses) {
            if (!computerNetwork.isChecked() && course.getCourseName().equals("计算机网络")) {
                computerNetwork.setChecked(true);
                computerNetwork.setEnabled(false);
            } else if (!javaProgramming.isChecked() && course.getCourseName().equals("Java程序设计")) {
                javaProgramming.setChecked(true);
                javaProgramming.setEnabled(false);
            } else if (!embeddedSystems.isChecked() && course.getCourseName().equals("嵌入式基础")) {
                embeddedSystems.setChecked(true);
                embeddedSystems.setEnabled(false);
            }
        }
    }

    private class ListCoursestGetTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return HttpConnect.getHttpResult(urls[0]);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            List<Course> courses = JsonUtils.toBean(result, new TypeReference<List<Course>>() {});

            ShowCourses(courses);
        }
    }

    private Student MergeUiIntoStudentObject() {
        Student newStudent = new Student();
        newStudent.setName(name.getText().toString());
        if (studentOperatorType == StudentOperatorType.Edit) {
            newStudent.setId(student.getId());
        } else if (studentOperatorType == StudentOperatorType.Add) {
            newStudent.setId(GenerateStudentId());
        }
        newStudent.setAge(Integer.parseInt(age.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            newStudent.setEnrollmentDate(sdf.parse(time_rx.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        newStudent.setPhone(phoneNumber.getText().toString());
        newStudent.setPassword(mima.getText().toString());

        if (male.isChecked()) {
            newStudent.setGender("男");
        } else {
            newStudent.setGender("女");
        }

        newStudent.setCollegeId(colleage.getSelectedItemPosition());
        newStudent.setMajor((String) major.getSelectedItem());

        return newStudent;
    }

    private class UpdateStudentPutTask extends AsyncTask<Student, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Student... students) {
            return HttpConnect.putRequest(WebApiUrl.makeUpdateStudentUrl(students[0].getId()), students[0]);
        }
    }

    private void updateStudentSelectedCourses() {
        if (computerNetwork.isEnabled() && computerNetwork.isChecked()) {
            new FindCourseByIdGetTask().execute(CourseStrToIdMap.get(computerNetwork.getText().toString()));
        }
        if (javaProgramming.isEnabled() && javaProgramming.isChecked()) {
            new FindCourseByIdGetTask().execute(CourseStrToIdMap.get(javaProgramming.getText().toString()));
        }
        if (embeddedSystems.isEnabled() && embeddedSystems.isChecked()) {
            new FindCourseByIdGetTask().execute(CourseStrToIdMap.get(embeddedSystems.getText().toString()));
        }
    }

    private class AddGradePostTask extends AsyncTask<Grade, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Grade... grades) {
            return HttpConnect.postRequest(WebApiUrl.ADD_GRADE, grades[0]);
        }
    }

    private class FindCourseByIdGetTask extends AsyncTask<Long, Void, String> {

        @Override
        protected String doInBackground(Long... ids) {
            return HttpConnect.getHttpResult(WebApiUrl.makeFindCourseByIdUrl(ids[0]));
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            Course course = JsonUtils.toBean(result, Course.class);
            MergeGradeAndAddGrade(student, course);
        }
    }

    private void MergeGradeAndAddGrade(Student student, Course course) {
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(-1); // 选课默认 -1 分
        new AddGradePostTask().execute(grade);
    }

    private Long GenerateStudentId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(colleage.getSelectedItemPosition() + 1);
        stringBuilder.append(major.getSelectedItemPosition() + 1);
        String[] dates = time_rx.getText().toString().split("-");
        for (String date : dates) {
            stringBuilder.append(date);
        }
        return Long.parseLong(stringBuilder.toString());
    }

    private class AddStudentPostTask extends AsyncTask<Student, Void, String> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(Student... students) {
            return HttpConnect.postRequest(WebApiUrl.ADD_STUDENT, students[0]);
        }
    }
}