package com.example.myapp;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private Integer collegeId;
    private String major;
    private Date enrollmentDate;
    private String phone;
    private String password;
    private String gender;

    public Student() {}

    public Student(Long id, String name, Integer age, Integer collegeId, String major, Date enrollmentDate, String phone, String password, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.collegeId = collegeId;
        this.major = major;
        this.enrollmentDate = enrollmentDate;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
