package com.example.myapp;

import java.io.Serializable;

public class Grade implements Serializable {
    private Long gradeId;
    private Course course;
    private Student student;
    private int score;

    public Grade() {}

    public Grade(Long gradeId, Course course, Student student, int score) {
        this.gradeId = gradeId;
        this.course = course;
        this.student = student;
        this.score = score;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
