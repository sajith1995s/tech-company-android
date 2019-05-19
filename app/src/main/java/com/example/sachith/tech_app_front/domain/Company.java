/**
 *
 * Created by Sajith Priyankara
 *
 **/
package com.example.sachith.tech_app_front.domain;

// this class help to manage company information
public class Company {

    private int id;
    private String name;
    private String address;
    private String contactNum;
    private String web;
    private String description;

    public Company () { }

    public Company(int id, String name, String address, String contactNum, String web, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNum = contactNum;
        this.web = web;
        this.description = description;
    }

    public  int getId(){return id;}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getWeb() {
        return web;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id){this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
