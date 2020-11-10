package com.android.online.voteapp.Candidate;

/**
 * Created by Kiduyu klaus
 * on 06/11/2020 14:35 2020
 */
public class FormModelClass {
    String name,idnumber,renumber,department,description,location,seat,imageurl,status,phone,userimageurl;


   public FormModelClass(){

    }

    public FormModelClass(String name, String idnumber, String renumber, String department, String description, String location, String seat, String imageurl, String status, String phone, String userimageurl) {
        this.name = name;
        this.idnumber = idnumber;
        this.renumber = renumber;
        this.department = department;
        this.description = description;
        this.location = location;
        this.seat = seat;
        this.imageurl = imageurl;
        this.status = status;
        this.phone = phone;
        this.userimageurl = userimageurl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserimageurl() {
        return userimageurl;
    }

    public void setUserimageurl(String userimageurl) {
        this.userimageurl = userimageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getRenumber() {
        return renumber;
    }

    public void setRenumber(String renumber) {
        this.renumber = renumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
