package com.example.myapp;

public class WebApiUrl {
    private static String BASE_URL = "http://192.168.31.169:8080";

    public static String STUDENT_LIST = BASE_URL + "/students/list";

    public static String GRADE_LIST = BASE_URL + "/grades/list";

    public static String BASE_DELETE_STUDENT = BASE_URL + "/students/delete/";

    public static String ADD_GRADE = BASE_URL + "/grades/add";

    public static String ADD_STUDENT = BASE_URL + "/students/add";

    public static String makeDeleteStudentUrl(long id) {
        return BASE_DELETE_STUDENT + id;
    }

    public static String makeListCoursesUrl(long id) {
        return BASE_URL + "/students/" + id + "/list-courses";
    }

    public static String makeUpdateStudentUrl(long id) {
        return BASE_URL + "/students/update/" + id;
    }

    public static String makeFindCourseByIdUrl(long id) {
        return BASE_URL + "/courses/" + id;
    }

    public static String makeFindScoreUrl(long studentId, long courseId) {
        return BASE_URL + "/grades/query/" + studentId + "/" + courseId;
    }
}
