package com.example.myapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Course implements Serializable {
    @JsonProperty("courseId")
    private Long courseId;
    @JsonProperty("courseName")
    private String courseName;

    public Course() {}

    public Course(Long courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
