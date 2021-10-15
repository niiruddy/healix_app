package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Patient_Signup_form extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase fDB;
    DatabaseReference root;
    AutoCompleteTextView Gender;
    TextInputEditText Name;
    TextInputEditText Date;
    TextInputEditText Phone;
    TextInputEditText Height;
    TextInputEditText Weight;
    TextInputEditText Meds;
    TextInputEditText MedCon;
    TextInputEditText Allergy;
    Button Submit;
    ArrayList<String> arrayList_gender;
    ArrayAdapter<String> arrayAdapter_gender;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    long sendid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup_form);

        final Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        fAuth=FirebaseAuth.getInstance();
        fDB= FirebaseDatabase.getInstance();
        root= fDB.getReference().child("Patients");


        Gender=findViewById(R.id.p_gender);
        Phone=findViewById(R.id.pNum);
        Date=findViewById(R.id.Pdate);
        Name=findViewById(R.id.p_name);
        Submit=findViewById(R.id.p_submit);
        Height=findViewById(R.id.p_height);
        Weight=findViewById(R.id.p_weight);
        Meds=findViewById(R.id.meds);
        MedCon=findViewById(R.id.medcon);
        Allergy=findViewById(R.id.P_allerg);



        arrayList_gender=new ArrayList<>();
        arrayList_gender.add("Male");
        arrayList_gender.add("Female");
        arrayAdapter_gender=new ArrayAdapter<>(getApplicationContext(),R.layout.tv_entity,arrayList_gender);
        Gender.setAdapter(arrayAdapter_gender);
        Gender.setThreshold(1);



        Submit.setOnClickListener(view -> {
            final String FullName= Objects.requireNonNull(Name.getText()).toString();
            final String phone_num= Objects.requireNonNull(Phone.getText()).toString();
            final String dob= Objects.requireNonNull(Date.getText()).toString();
            final String gender=Gender.getText().toString();
            final String height=Objects.requireNonNull(Height.getText().toString());
            final String weight=Objects.requireNonNull(Weight.getText().toString());
            final String MEDS=Objects.requireNonNull(Meds.getText().toString());
            final String MEDCON=Objects.requireNonNull(MedCon.getText().toString());
            final String all=Objects.requireNonNull(Allergy.getText().toString());


            if (FullName.isEmpty()){
                Name.setError("Name Required");

            }else if(phone_num.isEmpty()){
                Phone.setError("Phone Required");

            }else if (dob.isEmpty()){
                Date.setError("Date of Birth Required");

            }else if (gender.isEmpty()){
                Gender.setError("Gender required");
            }
            else if (height.isEmpty()){
                Height.setError("Height required");
            }
            else if (weight.isEmpty()){
                Weight.setError("Weight required");
            }
            else if (MEDS.isEmpty()){
                Meds.setError("Medications required");
            }

            else if (MEDCON.isEmpty()){
                MedCon.setError("Weight required");
            }
            else if (all.isEmpty()){
                Allergy.setError("Weight required");
            }
            else{

                SignInUser(FullName,phone_num,dob,gender,height,weight,all,MEDCON,MEDS);
            }




    });





        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent=new Intent(Patient_Signup_form.this, SignUp.class);
            startActivity(intent);
            finish();
        });

        Date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    Patient_Signup_form.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();

        });
        onDateSetListener= (view, year1, month1, dayofMonth) -> {
            month1 = month1 +1;
            String date= dayofMonth+"/"+ month1 +"/"+ year1;
            Date.setText(date);
        };



    }

    private void SignInUser(String fullName, String phone_num, String dob, String gender, String height, String weight, String allergy, String medcon, String meds) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String email = getIntent().getStringExtra("email");
        final String pass = getIntent().getStringExtra("pass");
        final String conPass = getIntent().getStringExtra("conPass");


        mAuth.createUserWithEmailAndPassword(email, conPass).addOnCompleteListener(Patient_Signup_form.this, task ->{
           if(task.isSuccessful()){
               Toast.makeText(Patient_Signup_form.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
               DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patients").child("Profile_details");
               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()){
                           sendid =(snapshot.getChildrenCount());
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

               patient_register_helper helper = new patient_register_helper(fullName,phone_num,dob,gender,email,pass,height,weight,allergy,meds,medcon);
               ref.child(String.valueOf(sendid + 1)).setValue(helper);

               Intent intent = new Intent(getApplicationContext(),dashboard.class);

               // to prevent the user from using the back button to get to this page
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
               finish();


           }else{
               Toast.makeText(Patient_Signup_form.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
               Name.requestFocus();
           }

           }


        );

    }
}