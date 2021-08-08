package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

    private EditText fname,lname,phone,email, pass, conPass;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView SignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        fname=findViewById(R.id.Fname);
        lname=findViewById(R.id.Lname);
        phone=findViewById(R.id.pNum);
        Button submit=findViewById(R.id.signupButton);
        email=findViewById(R.id.signupEmail);
        pass=findViewById(R.id.signupPassword);
        conPass=findViewById(R.id.signupConfirmPassword);
        progressBar=findViewById(R.id.signupProgressBar);
        SignIn=findViewById(R.id.signinPage);
        RadioButton Nurse = findViewById(R.id.nurse);
        RadioButton Patient = findViewById(R.id.patient);

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





        submit.setOnClickListener(view -> {
            String Email=email.getText().toString();
            String Pass=pass.getText().toString();
            String ConPass=conPass.getText().toString();
            String fName=fname.getText().toString();
            String lName=lname.getText().toString();
            String Phone =phone.getText().toString();

            if(!(Nurse.isChecked()||Patient.isChecked())){
                Toast.makeText(this, "Select User Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(fName)){
                Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(lName)){
                Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(Phone)){
                Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(Email)){
                Toast.makeText(this, "Enter your email address", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(Pass)){
                Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
            }else if(!Pass.equals(ConPass)){
                Toast.makeText(this, "Both passwords do not match", Toast.LENGTH_SHORT).show();
            }else{
               progressBar.setVisibility(View.VISIBLE);
               mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           progressBar.setVisibility(View.GONE);
                           FirebaseUser user=mAuth.getCurrentUser();

                           DocumentReference df = fStore.collection("Users").document(user.getUid());
                           Map<String,Object> userInfo=new HashMap<>();
                           userInfo.put("FirstName",fname.getText().toString());
                           userInfo.put("LastName", lname.getText().toString());
                           userInfo.put("Email",email.getText().toString());
                           userInfo.put("PhoneNumber",phone.getText().toString());

                           if(Nurse.isChecked()){
                               userInfo.put("isNurse","1");
                           }
                           if(Patient.isChecked()){
                               userInfo.put("isPatient", "0");
                           }

                           df.set(userInfo);
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                           finish();
                           Toast.makeText(SignUp.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           progressBar.setVisibility(View.GONE);
                           String error=task.getException().getMessage();
                           Toast.makeText(SignUp.this,error, Toast.LENGTH_SHORT).show();
                       }

                   }
               });
            }
        });
    }
}