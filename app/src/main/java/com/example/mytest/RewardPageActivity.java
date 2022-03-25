package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.Model.Lecturer;
import com.example.mytest.Model.Reward;
import com.example.mytest.Model.Student;
import com.example.mytest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardPageActivity extends AppCompatActivity {

    TextView student_points;
    ImageButton love, lol;

    String studentid;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef, myRef2, myRef3;
    int sum = 0;
    Boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Points Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        student_points = findViewById(R.id.student_points);

        love = findViewById(R.id.love);
        lol = findViewById(R.id.lol);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        studentid = firebaseUser .getUid();

        myRef3 = firebaseDatabase.getReference("User").child(studentid);
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                //assert votelist != null;
                String a = user.getPoints();
                String occupation = user.getOccupation();

                if(occupation.equals("lecturer")){
                    Toast.makeText(com.example.mytest.RewardPageActivity.this,"Lecturer can't use this function", Toast.LENGTH_SHORT).show();
                    finish();
                }
                student_points.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef2 = firebaseDatabase.getReference("Reward").child(studentid).child("1");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reward reward = snapshot.getValue(Reward.class);
                //assert votelist != null;
                String a = reward.getRavailability();
                if(a.equals("false"))
                {
                    love.setEnabled(false);
                    love.setBackgroundResource(R.drawable.heartstickerblack);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myRef = firebaseDatabase.getReference("Reward").child(studentid).child("2");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reward reward = snapshot.getValue(Reward.class);
                //assert votelist != null;
                String a = reward.getRavailability();
                if(a.equals("false"))
                {
                    lol.setEnabled(false);
                    lol.setBackgroundResource(R.drawable.lolstickerblack);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String points = student_points.getText().toString();
                int converted = Integer.parseInt(points);
                if(converted>=50) {
                    int sum = converted - 50;
                    String total_points = Integer.toString(sum);
                    student_points.setText(total_points);
                    FirebaseDatabase.getInstance().getReference("User").child(studentid).child("points").setValue(total_points);
                    FirebaseDatabase.getInstance().getReference("Student").child(studentid).child("points").setValue(total_points);
                    FirebaseDatabase.getInstance().getReference("Reward").child(studentid).child("1").child("ravailability").setValue("false");
                    Toast.makeText(com.example.mytest.RewardPageActivity.this,"Successfully Purchased", Toast.LENGTH_SHORT).show();
                    love.setEnabled(false);
                    love.setBackgroundResource(R.drawable.heartstickerblack);
                }else{
                    Toast.makeText(com.example.mytest.RewardPageActivity.this,"You don't have enough points to buy this", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String points = student_points.getText().toString();
                int converted = Integer.parseInt(points);
                if(converted>=50) {
                    int sum = converted - 50;
                    String total_points = Integer.toString(sum);
                    student_points.setText(total_points);
                    FirebaseDatabase.getInstance().getReference("User").child(studentid).child("points").setValue(total_points);
                    FirebaseDatabase.getInstance().getReference("Student").child(studentid).child("points").setValue(total_points);
                    FirebaseDatabase.getInstance().getReference("Reward").child(studentid).child("2").child("ravailability").setValue("false");
                    Toast.makeText(com.example.mytest.RewardPageActivity.this,"Successfully Purchased", Toast.LENGTH_SHORT).show();
                    lol.setEnabled(false);
                    lol.setBackgroundResource(R.drawable.lolstickerblack);

                }else{
                    Toast.makeText(com.example.mytest.RewardPageActivity.this,"You don't have enough points to buy this", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}