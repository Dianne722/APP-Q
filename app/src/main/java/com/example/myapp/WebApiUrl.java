package com.example.myapp;

public class WebApiUrl {
    private static String BASE_URL = "http://192.168.137.1:8080";

    public static String STUDENT_LIST = BASE_URL + "/students/list";

    public static String BASE_DELETE_STUDENT = BASE_URL + "/students/delete/";

    public static String makeDeleteStudentUrl(long id) {
        return BASE_DELETE_STUDENT + id;
    }

    public static String makeListCoursesUrl(long id) {
        return BASE_URL + "/student/" + id + "/list-courses";
    }
}
