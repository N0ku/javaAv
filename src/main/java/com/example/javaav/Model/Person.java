package com.example.javaav.Model;

import org.json.JSONObject;

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

    
    /** 
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    
    /** 
     * @return UUID
     */
    public UUID getId() {
        return id;
    }
    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** 
     * @return String
     */
    public String getMail() {
        return mail;
    }
    
    /** 
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    /** 
     * @return String
     */
    public String getTel() {
        return tel;
    }
    
    /** 
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    /** 
     * @return int
     */
    public int getAge() {
        return age;
    }
    
    /** 
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /** 
     * @return String
     */
    public String getAdress() {
        return adress;
    }
    
    /** 
     * @param adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    /** 
     * @return JSONObject
     */
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("name", this.getName());
        jsonObject.put("mail", this.getMail());
        jsonObject.put("tel", this.getTel());
        jsonObject.put("age", this.getAge());
        jsonObject.put("adress", this.getAdress());
        return jsonObject;
    }
    
}
