package com.example.app_healix;

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;

public class UserProfile {


        private String Name;
        private String Phone;
        private String Dob;
        private String Role;

        public UserProfile(TextInputEditText name, TextInputEditText phone, TextInputEditText dob, AutoCompleteTextView role) {
                TextInputEditText Name = name;
                TextInputEditText Phone = phone;
                TextInputEditText Dob = dob;
                AutoCompleteTextView Role = role;
        }

        public String getName() {
                return Name;
        }

        public void setName(String name) {
                Name = name;
        }

        public String getPhone() {
                return Phone;
        }

        public void setPhone(String phone) {
                Phone = phone;
        }

        public String getDob() {
                return Dob;
        }

        public void setDob(String dob) {
                Dob = dob;
        }

        public String getRole() {
                return Role;
        }

        public void setRole(String role) {
                Role = role;
        }
}