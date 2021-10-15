package com.example.app_healix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUp extends AppCompatActivity {

    private EditText email, pass, conPass;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    TextView SignIn;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        next =findViewById(R.id.next);
        email=findViewById(R.id.signupEmail);
        pass=findViewById(R.id.signupPassword);
        conPass=findViewById(R.id.signupConfirmPassword);
        progressBar=findViewById(R.id.signupProgressBar);
        SignIn=findViewById(R.id.signinPage);
        RadioButton Nurse = findViewById(R.id.nurse);
        RadioButton Patient = findViewById(R.id.patient);


        Toolbar toolbar = findViewById(R.id.signupToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                Intent intent=new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

Nurse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()){
            Patient.setChecked(false);
        }
    }
});

Patient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()){
            Nurse.setChecked(false);
        }
    }
});


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });





        next.setOnClickListener(view -> {
            String Email=email.getText().toString();
            String Pass=pass.getText().toString();
            String ConPass=conPass.getText().toString();
            String usertype = "0";

            if(!(Nurse.isChecked()||Patient.isChecked())){
                Toast.makeText(this, "Select User Type", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(TextUtils.isEmpty(Email)){
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(Pass)){
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            }else if(!Pass.equals(ConPass)){
                Toast.makeText(this, "Both passwords do not match", Toast.LENGTH_SHORT).show();
            }else if (Nurse.isChecked()){
                Intent intent = new Intent(getApplicationContext(), Nurse_signup_form.class);
                intent.putExtra("email", Email);
                intent.putExtra("pass", Pass);
                intent.putExtra("conPass", ConPass);
                startActivity(intent);
                finish();
            }else if(Patient.isChecked()){
                Intent intent = new Intent(getApplicationContext(), Patient_Signup_form.class);
                intent.putExtra("email", Email);
                intent.putExtra("pass", Pass);
                intent.putExtra("conPass", ConPass);
                startActivity(intent);
                finish();
            }


        });
    }


}