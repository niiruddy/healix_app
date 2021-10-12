package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.jar.Attributes;

public class Nurse_Dashboard extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase fDB;
    DatabaseReference root;
    Chip Stroke,Can,Park,Dem,Pall,EOL;
    TextInputEditText name;
    TextInputEditText mdate;
    TextInputEditText phone;
    AutoCompleteTextView _role;
    Button submit;
    ProgressBar progressBar;
    ChipGroup specAreas;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    ArrayList<String> selectedSpec;
    ArrayList<String> arrayList_roles;
    ArrayAdapter<String> arrayAdapter_roles;
    long sendid = 0;
    // private CompoundButton.OnCheckedChangeListener checkedChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_dashboard);
        final Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        fAuth=FirebaseAuth.getInstance();
        fDB= FirebaseDatabase.getInstance();
        root= fDB.getReference().child("Caregiver Details");

        name=findViewById(R.id.FirstNameIE);
        phone=findViewById(R.id.phoneIE);
        Stroke=findViewById(R.id.stroke);
        Can=findViewById(R.id.can);
        Park=findViewById(R.id.park);
        Dem=findViewById(R.id.dem);
        Pall=findViewById(R.id.pall);
        EOL=findViewById(R.id.eol);
        mdate=findViewById(R.id.dateFormat);
        submit=findViewById(R.id.submit);
        _role=findViewById(R.id.role);






        arrayList_roles=new ArrayList<>();
        arrayList_roles.add("Caregiver");
        arrayList_roles.add("Therapists");
        arrayList_roles.add("Nurse");
        arrayAdapter_roles=new ArrayAdapter<>(getApplicationContext(),R.layout.tv_entity,arrayList_roles);
        _role.setAdapter(arrayAdapter_roles);
        _role.setThreshold(1);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String FName=name.getText().toString();
                final String Phone=phone.getText().toString();
                final String DOB=mdate.getText().toString();
                final String Role=_role.getText().toString();

                if (FName.isEmpty()){
                    name.setError("Name Required");
                }else if(Phone.isEmpty()){
                    phone.setError("Phone Required");

                }else if (DOB.isEmpty()){
                    mdate.setError("Date of Birth Required");

                }else if (Role.isEmpty()){
                    _role.setError("Role required");
                } else{

                    SignInUser(FName,Phone,DOB,Role);
                }


            }
        });

        mdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Nurse_Dashboard.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        onDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                month=month+1;
                String date= dayofMonth+"/"+month+"/"+year;
                mdate.setText(date);
            }
        };




        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Nurse_Dashboard.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void SignInUser(String FName, String Phone, String DOB, String Role) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String email = getIntent().getStringExtra("email");
        final String pass = getIntent().getStringExtra("pass");
        final String conPass = getIntent().getStringExtra("conPass");


        mAuth.createUserWithEmailAndPassword(email, conPass).addOnCompleteListener(Nurse_Dashboard.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Nurse_Dashboard.this, "SignUp Successfull", Toast.LENGTH_SHORT).show();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Nurses").child("Profile_details");
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

                    Nurse_register_helper helper = new Nurse_register_helper(email,pass,FName,DOB,Phone,Role);
                    ref.child(String.valueOf(sendid)).setValue(helper);

                    Intent intent = new Intent(getApplicationContext(),dashboard.class);

                    // to prevent the user from using the back button to get to this page
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(Nurse_Dashboard.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                }
            }
        });
    }







}