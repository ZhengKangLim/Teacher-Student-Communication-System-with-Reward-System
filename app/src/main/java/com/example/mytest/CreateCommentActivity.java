package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class CreateCommentActivity extends AppCompatActivity {
    MaterialEditText Message;
    Button btn_addComments;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    String catid;
    String postid;
    //Group group;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Message = findViewById(R.id.comments);
        btn_addComments = findViewById(R.id.btn_add_comments);


        auth = FirebaseAuth.getInstance();
        catid = getIntent().getStringExtra("catid");
        postid = getIntent().getStringExtra("postid");
        btn_addComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_message = Message.getText().toString();

                if(TextUtils.isEmpty(txt_message)){
                    Toast.makeText(com.example.mytest.CreateCommentActivity.this, "Please enter the message", Toast.LENGTH_SHORT).show();
                } else{
                    create_comment(txt_message,catid,postid);
                }

            }
        });
    }

    private void create_comment(final String message, final String catid, final String postid){

        FirebaseUser firebaseUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userid = firebaseUser .getUid();
        //Log.d(TAG, "UserId = " + userid);


        //myRef = firebaseDatabase.getReference("Student").child(userid);
        myRef = firebaseDatabase.getReference("PostComment").push();
        String key = myRef.getKey();
        //group = new Group();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", key);
        hashMap.put("message", message);
        hashMap.put("imageURL", "default");
        hashMap.put("catid", catid);
        hashMap.put("sender", userid);
        hashMap.put("vote", "0");
        hashMap.put("postid", postid);
        hashMap.put("point", "false");

        // myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.setValue(hashMap);
        //  firebaseDatabase.getReference("Group").child(key).setValue(hashMap);

        myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Intent intent = new Intent(com.example.mytest.CreatePostActivity.this, ForumActivity.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // startActivity(intent);
                    Toast.makeText(com.example.mytest.CreateCommentActivity.this,"Comment Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}