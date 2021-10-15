package com.example.app_healix;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Nurse_signup_form extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase fDB;
    DatabaseReference root;
    Chip Stroke,Can,Park,Dem,Pall,EOL;
    TextInputEditText name;
    TextInputEditText mdate;
    TextInputEditText phone;
    AutoCompleteTextView _role;
    Button submit;
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
        setContentView(R.layout.activity_nurse_signup_form);
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



        submit.setOnClickListener(view -> {
            final String FName= Objects.requireNonNull(name.getText()).toString();
            final String Phone= Objects.requireNonNull(phone.getText()).toString();
            final String DOB= Objects.requireNonNull(mdate.getText()).toString();
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


        });

        mdate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    Nurse_signup_form.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();

        });
        onDateSetListener= (view, year1, month1, dayofMonth) -> {
            month1 = month1 +1;
            String date= dayofMonth+"/"+ month1 +"/"+ year1;
            mdate.setText(date);
        };




        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent=new Intent(Nurse_signup_form.this, SignUp.class);
            startActivity(intent);
            finish();
        });



    }

    private void SignInUser(String FName, String Phone, String DOB, String Role) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String email = getIntent().getStringExtra("email");
        final String pass = getIntent().getStringExtra("pass");
        final String conPass = getIntent().getStringExtra("conPass");


        mAuth.createUserWithEmailAndPassword(email, conPass).addOnCompleteListener(Nurse_signup_form.this, task -> {
            if (task.isSuccessful()){
                Toast.makeText(Nurse_signup_form.this, "SignUp Successfull", Toast.LENGTH_SHORT).show();
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
                ref.child(String.valueOf(sendid + 1)).setValue(helper);

                Intent intent = new Intent(getApplicationContext(),dashboard.class);

                // to prevent the user from using the back button to get to this page
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


            }else{
                Toast.makeText(Nurse_signup_form.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                name.requestFocus();
            }
        });
    }







}