package com.example.app_healix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        submit= findViewById(R.id.submitButton);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        TextView signUpPage=findViewById(R.id.signUpPage);

        signUpPage.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
            finish();

        });

        submit.setOnClickListener(v ->{
            String em= email.getText().toString();
            String pass=password.getText().toString();

            if(TextUtils.isEmpty(em)){
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(pass)){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            checkUserAccessLevel(task.getResult().getUser().getUid());
                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(intent);
                        }
                        else {
                            String error = task.getException().getMessage();

                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
        );


    }

    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());

                if(documentSnapshot.getString("isNurse")!=null){
                    Intent log= new Intent(MainActivity.this, dashboard.class);
                    startActivity(log);
                    finish();
                }
                if(documentSnapshot.getString("isPatient")!=null){
                    Intent log= new Intent(MainActivity.this, Patient_Dashboard.class);
                    startActivity(log);
                    finish();
                }


            }
        });
    }


}