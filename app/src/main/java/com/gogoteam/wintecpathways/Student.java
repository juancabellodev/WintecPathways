package com.gogoteam.wintecpathways;

public class Student {
    private	String SID;
    private	String SName;
    private	String Email;
    private	String Specialisation;
    private	String Programme;
    private String Date_Enrolled;

    public Student() {

    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(String specialisation) {
        Specialisation = specialisation;
    }

    public String getProgramme() {
        return Programme;
    }

    public void setProgramme(String programme) {
        Programme = programme;
    }

    public String getDate_Enrolled() {
        return Date_Enrolled;
    }

    public void setDate_Enrolled(String date_Enrolled) {
        Date_Enrolled = date_Enrolled;
    }
}