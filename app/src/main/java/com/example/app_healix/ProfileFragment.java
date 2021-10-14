package com.example.app_healix;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.jar.Attributes;


public class ProfileFragment extends Fragment {
    Button updbutt;
    TextView headName;

    TextInputEditText name, phone, role, dob;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    LocationRequest mlocationRequest;
    LocationCallback callback;
    FusedLocationProviderClient client;
    private Nurse_register_helper helper;


    String ID;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        ID = currentUser.getEmail();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.Name);
        phone = view.findViewById(R.id.Phone);
        role = view.findViewById(R.id.Role);
        dob = view.findViewById(R.id.Dob);
        updbutt = view.findViewById(R.id.upbutt);
        headName = view.findViewById(R.id.headName);





        Query query = FirebaseDatabase.getInstance().getReference().child("Nurses")
                .child("Profile_details").orderByChild("email").equalTo(ID);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    helper = ds.getValue(Nurse_register_helper.class);

                }

                name.setText(helper.getName());
                phone.setText(helper.getPhone());
                role.setText(helper.getRole());
                dob.setText(helper.getDOB());
                headName.setText(helper.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        updbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Query query = FirebaseDatabase.getInstance().getReference().child("Nurses")
                        .child("Profile_details").orderByChild("email").equalTo(ID);


                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            helper = ds.getValue(Nurse_register_helper.class);

                        }

                        String new_name = name.getText().toString();
                        String new_phone = phone.getText().toString();





                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }
        });

        return view;


    }


}