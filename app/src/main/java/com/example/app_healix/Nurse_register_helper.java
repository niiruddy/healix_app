package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Nurse_register_helper {
    String Email;
    String Pass;
    String Name;
    String DOB;
    String Phone;
    String Role;
//    String Specialty;


    public Nurse_register_helper() {
    }

    public Nurse_register_helper(String email, String pass, String name, String DOB, String phone, String role) {
        Email = email;
        Pass = pass;
        Name = name;
        this.DOB = DOB;
        Phone = phone;
        Role = role;
    }

    public Nurse_register_helper(String new_name, String new_phone) {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

}
