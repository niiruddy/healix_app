package com.example.app_healix;

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;

public class patient_register_helper {


        private String fullName;
        private String Phone_num;
        private String Dob;
        private String Gender;
        private String Email;
        private String Pass;
        private String Height;
        private String Weight;
        private String Allergy;
        private String Meds;
        private String Medcon;

        public patient_register_helper(String fullName, String phone_num, String dob, String gender, String email, String pass, String date, String height, String weight, String allergy, String meds, String medcon) {
                this.fullName = fullName;
                Phone_num = phone_num;
                Dob = dob;
                Gender = gender;
                Email = email;
                Pass = pass;
                Height = height;
                Weight = weight;
                Allergy = allergy;
                Meds = meds;
                Medcon = medcon;
        }

        public patient_register_helper(String fullName, String phone_num, String dob, String gender, String email, String pass, String height, String weight, String allergy, String meds, String medcon) {
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getPhone_num() {
                return Phone_num;
        }

        public void setPhone_num(String phone_num) {
                Phone_num = phone_num;
        }

        public String getDob() {
                return Dob;
        }

        public void setDob(String dob) {
                Dob = dob;
        }

        public String getGender() {
                return Gender;
        }

        public void setGender(String gender) {
                Gender = gender;
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

        public String getHeight() {
                return Height;
        }

        public void setHeight(String height) {
                Height = height;
        }

        public String getWeight() {
                return Weight;
        }

        public void setWeight(String weight) {
                Weight = weight;
        }

        public String getAllergy() {
                return Allergy;
        }

        public void setAllergy(String allergy) {
                Allergy = allergy;
        }

        public String getMeds() {
                return Meds;
        }

        public void setMeds(String meds) {
                Meds = meds;
        }

        public String getMedcon() {
                return Medcon;
        }

        public void setMedcon(String medcon) {
                Medcon = medcon;
        }
}