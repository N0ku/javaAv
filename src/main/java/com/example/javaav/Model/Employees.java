package com.example.javaav.Model;

import javafx.beans.property.IntegerProperty;

public class Employees extends Person {
    String jobName;
    int workHours;
    float salary;

    public Employees(int id,String name, String mail, String tel, int age, String adress,String jobName,int workHours,float salary) {
        super(id,name, mail, tel, age, adress);
        this.jobName = jobName;
        this.workHours = workHours;
        this.salary = salary;
    }



    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employees [jobName=" + jobName + ", workHours=" + workHours + ", salary=" + salary + "]";
    }

    
}
