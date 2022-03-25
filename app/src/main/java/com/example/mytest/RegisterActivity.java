package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mytest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email, password;
    Button btn_register_S;
    Button btn_register_L;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    User user;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    DatabaseReference myRef3;
    DatabaseReference myRef4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register_S = findViewById(R.id.btn_register_S);
        btn_register_L = findViewById(R.id.btn_register_L);

        auth = FirebaseAuth.getInstance();

        btn_register_S.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String txt_username = username.getText().toString();
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();

            if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                Toast.makeText(com.example.mytest.RegisterActivity.this, "Please all the fields", Toast.LENGTH_SHORT).show();
            } else{
                register_S(txt_username, txt_email, txt_password);
            }

        }
    });

        btn_register_L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(com.example.mytest.RegisterActivity.this, "Please all the fields", Toast.LENGTH_SHORT).show();
                } else{
                    register_L(txt_username, txt_email, txt_password);
                }

            }
        });
}

    private void register_S(final String username, String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    String userid = firebaseUser .getUid();
                    Log.d(TAG, "UserId = " + userid);

                    myRef = firebaseDatabase.getReference("Student").child(userid);
                    myRef2 = firebaseDatabase.getReference("User").child(userid);
                    myRef3 = firebaseDatabase.getReference("Reward").child(userid).child("1");
                    myRef4 = firebaseDatabase.getReference("Reward").child(userid).child("2");
                    user = new User();

                    HashMap<String, String> hashMap = new HashMap<>();
                     hashMap.put("id", userid);
                     hashMap.put("username", username);
                     hashMap.put("imageURL", "default");
                     hashMap.put("anonymous", "off");
                     hashMap.put("occupation", "student");
                     hashMap.put("points","0");

                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put("id", "1");
                    hashMap1.put("rname", "heart");
                    hashMap1.put("ravailability", "true");

                    HashMap<String, String> hashMap2 = new HashMap<>();
                    hashMap2.put("id", "2");
                    hashMap2.put("rname", "lol");
                    hashMap2.put("ravailability", "true");
                   // myRef = FirebaseDatabase.getInstance().getReference("Users");

                    myRef.setValue(hashMap);
                    myRef2.setValue(hashMap);
                    myRef3.setValue(hashMap1);
                    myRef4.setValue(hashMap2);

                     myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                        Intent intent = new Intent(com.example.mytest.RegisterActivity.this, MainActivity.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                         startActivity(intent);
                         Toast.makeText(com.example.mytest.RegisterActivity.this,"Successfully Registered", Toast.LENGTH_SHORT).show();
                         finish();
                            }
                         }
                      });


                }else{
                    Toast.makeText(com.example.mytest.RegisterActivity.this,"Email Already Registered", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void register_L(final String username, String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    String userid = firebaseUser .getUid();
                    Log.d(TAG, "UserId = " + userid);

                    myRef = firebaseDatabase.getReference("Lecturer").child(userid);
                    myRef2 = firebaseDatabase.getReference("User").child(userid);
                    myRef3 = firebaseDatabase.getReference("Reward").child(userid).child("1");
                    myRef4 = firebaseDatabase.getReference("Reward").child(userid).child("2");
                    user = new User();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("anonymous", "off");
                    hashMap.put("occupation", "lecturer");
                    hashMap.put("points","500");

                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put("id", "1");
                    hashMap1.put("rname", "heart");
                    hashMap1.put("ravailability", "true");

                    HashMap<String, String> hashMap2 = new HashMap<>();
                    hashMap2.put("id", "2");
                    hashMap2.put("rname", "lol");
                    hashMap2.put("ravailability", "true");

                    // myRef = FirebaseDatabase.getInstance().getReference("Users");
                    myRef.setValue(hashMap);
                    myRef2.setValue(hashMap);
                    myRef3.setValue(hashMap1);
                    myRef4.setValue(hashMap2);

                    myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(com.example.mytest.RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(com.example.mytest.RegisterActivity.this,"Successfully Registered", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });


                }else{
                    Toast.makeText(com.example.mytest.RegisterActivity.this,"Invalid Email or password", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



}