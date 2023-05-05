package com.example.javaav.Model;

import java.util.UUID;

abstract class Person {

    private  UUID id;
    private String name;
    private String mail;
    private String tel;
    private int age;
    private String adress;

    public Person(String name, String mail, String tel, int age, String adress) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.age = age;
        this.adress = adress;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    
}
