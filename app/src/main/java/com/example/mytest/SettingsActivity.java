package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mytest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class SettingsActivity extends AppCompatActivity {

    Button btn_anonymous_on;
    Button btn_anonymous_off;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_anonymous_on = findViewById(R.id.btn_anonymous_on);
        btn_anonymous_off = findViewById(R.id.btn_anonymous_off);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userid = firebaseUser .getUid();

        myRef = firebaseDatabase.getReference("User").child(userid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User users = snapshot.getValue(User.class);
                assert users != null;
                String anonymous = users.getAnonymous();
                String occupation = users.getOccupation();
                if(anonymous.equals("on")){
                    btn_anonymous_on.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF7F")));
                    btn_anonymous_off.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                    btn_anonymous_on.setEnabled(false);
                    btn_anonymous_off.setEnabled(true);

                }else if(anonymous.equals("off")){
                    btn_anonymous_off.setEnabled(false);
                    btn_anonymous_on.setEnabled(true);
                }

                if(occupation.equals("lecturer")){
                    Toast.makeText(com.example.mytest.SettingsActivity.this,"Lecturer can't use this function", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_anonymous_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase.getReference("User").child(userid).child("anonymous").setValue("on");
                firebaseDatabase.getReference("Student").child(userid).child("anonymous").setValue("on");
                btn_anonymous_on.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF7F")));
                btn_anonymous_off.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                Toast.makeText(com.example.mytest.SettingsActivity.this,"Anonymous Mode On", Toast.LENGTH_SHORT).show();
            }
        });

        btn_anonymous_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase.getReference("User").child(userid).child("anonymous").setValue("off");
                firebaseDatabase.getReference("Student").child(userid).child("anonymous").setValue("off");
                btn_anonymous_on.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A9A9A9")));
                btn_anonymous_off.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF7F")));
                Toast.makeText(com.example.mytest.SettingsActivity.this,"Anonymous Mode Off", Toast.LENGTH_SHORT).show();

            }
        });
    }
}