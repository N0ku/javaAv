package com.example.javaav.Model;

import javafx.beans.property.IntegerProperty;

public class Employees extends Person {
    String jobName;
    int workHours;
    float salary;

    public Employees(String name, String mail, String tel, int age, String adress,String jobName,int workHours,float salary) {
        super(name, mail, tel, age, adress);
        this.jobName = jobName;
        this.workHours = workHours;
        this.salary = salary;
    }



    
    /** 
     * @return String
     */
    public String getJobName() {
        return jobName;
    }

    
    /** 
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    
    /** 
     * @return int
     */
    public int getWorkHours() {
        return workHours;
    }

    
    /** 
     * @param workHours
     */
    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    
    /** 
     * @return float
     */
    public float getSalary() {
        return salary;
    }

    
    /** 
     * @param salary
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Employees [jobName=" + jobName + ", workHours=" + workHours + ", salary=" + salary + "]";
    }

    
}
