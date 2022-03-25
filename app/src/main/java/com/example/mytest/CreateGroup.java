package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mytest.Model.Group;
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

public class CreateGroup extends AppCompatActivity {
    MaterialEditText groupname;
    Button btn_createGroup;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    Group group;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Group");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        groupname = findViewById(R.id.groupname);
        btn_createGroup = findViewById(R.id.btn_create_group);


        auth = FirebaseAuth.getInstance();

        btn_createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_groupname = groupname.getText().toString();

                if(TextUtils.isEmpty(txt_groupname)){
                    Toast.makeText(com.example.mytest.CreateGroup.this, "Please enter a group name", Toast.LENGTH_SHORT).show();
                } else{
                    create_group(txt_groupname);
                }

            }
        });
    }

    private void create_group(final String groupname){

                    //FirebaseUser firebaseUser = auth.getCurrentUser();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    //String userid = firebaseUser .getUid();
                    //Log.d(TAG, "UserId = " + userid);


                    //myRef = firebaseDatabase.getReference("Student").child(userid);
                    myRef = firebaseDatabase.getReference("Group").push();
                    String key = myRef.getKey();
                    group = new Group();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", key);
                    hashMap.put("groupname", groupname);
                    hashMap.put("imageURL", "default");

                    // myRef = FirebaseDatabase.getInstance().getReference("Users");
                    myRef.setValue(hashMap);
                  //  firebaseDatabase.getReference("Group").child(key).setValue(hashMap);

                    myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Intent intent = new Intent(com.example.mytest.CreateGroup.this, MainActivity.class);
                               // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                               // startActivity(intent);
                                Toast.makeText(com.example.mytest.CreateGroup.this,"Successfully Created", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

    }
}