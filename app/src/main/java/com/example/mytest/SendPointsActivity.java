package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.Model.Group;
import com.example.mytest.Model.Lecturer;
import com.example.mytest.Model.Student;
import com.example.mytest.Model.User;
import com.example.mytest.Model.Votelist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class SendPointsActivity extends AppCompatActivity {
    Button btn_send_points;
    TextView lecturer_points;
    TextView points;
    ImageButton up, down;

    String studentid;
    String lecturerid;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef, myRef2, myRef3;
    int sum = 0;
    Boolean success = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_points);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Points Distribution");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_send_points = findViewById(R.id.btn_send_point);
        lecturer_points = findViewById(R.id.lecturer_point);
        points = findViewById(R.id.textView2);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        studentid = getIntent().getStringExtra("studentid");

        points.setText("0");


        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        lecturerid = firebaseUser .getUid();

        myRef3 = firebaseDatabase.getReference("User").child(lecturerid);
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lecturer lecturer = snapshot.getValue(Lecturer.class);
                //assert votelist != null;
                String a = lecturer.getPoints();
                lecturer_points.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sum = sum + 1;
                String total_points = Integer.toString(sum);
                points.setText(total_points);

            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sum!= 0) {
                    sum = sum - 1;
                    String total_points = Integer.toString(sum);
                    points.setText(total_points);
                }

            }
        });

        btn_send_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt= points.getText().toString();

                if(sum==0){
                    Toast.makeText(com.example.mytest.SendPointsActivity.this, "Point distribute cannot be zero", Toast.LENGTH_SHORT).show();
                } else{
                    send_points(txt);
                }

            }
        });
    }

    private void send_points(final String points){

        //Log.d(TAG, "UserId = " + userid);


        //myRef = firebaseDatabase.getReference("Student").child(userid);
        myRef = firebaseDatabase.getReference("User").child(lecturerid);
        myRef2 = firebaseDatabase.getReference("User").child(studentid);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lecturer lecturer = snapshot.getValue(Lecturer.class);
                //assert votelist != null;
                String a = lecturer.getPoints();

                    int converted_S = Integer.parseInt(points);
                    int converted_L = Integer.parseInt(a);
                    if(converted_L >= converted_S) {
                        success = true;
                        int sum = converted_L - converted_S;
                        String total_points = Integer.toString(sum);
                        FirebaseDatabase.getInstance().getReference("User").child(lecturerid).child("points").setValue(total_points);
                        FirebaseDatabase.getInstance().getReference("Lecturer").child(lecturerid).child("points").setValue(total_points);
                        Toast.makeText(com.example.mytest.SendPointsActivity.this,"Successfully Distributed", Toast.LENGTH_SHORT).show();
                    }else if(converted_L < converted_S){
                        Toast.makeText(com.example.mytest.SendPointsActivity.this,"You don't have so many points to distribute", Toast.LENGTH_SHORT).show();
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // myRef = FirebaseDatabase.getInstance().getReference("Users");

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                //assert votelist != null;
                String a = student.getPoints();

                if(success.equals(true)) {
                    int converted_S = Integer.parseInt(points);
                    int converted = Integer.parseInt(a);
                    int sum = converted + converted_S;
                    String total_points = Integer.toString(sum);
                    FirebaseDatabase.getInstance().getReference("User").child(studentid).child("points").setValue(total_points);
                    FirebaseDatabase.getInstance().getReference("Student").child(studentid).child("points").setValue(total_points);
                    success=false;
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}