package com.quannv.flirtatiouschat.model;

public class User {

    private String uid, name, status, image, address, education, relationship;
    private int age;

    public User() {

    }

    public User(String uid, String name, String status, String image, String address, String education, String relationship, int age) {
        this.uid = uid;
        this.name = name;
        this.status = status;
        this.image = image;
        this.address = address;
        this.education = education;
        this.relationship = relationship;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
