package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mytest.Adapter.GroupMessageAdapter;
import com.example.mytest.Adapter.MessageAdapter;
import com.example.mytest.Model.Chat;
import com.example.mytest.Model.Group;
import com.example.mytest.Model.GroupChat;
import com.example.mytest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMessageActivity extends AppCompatActivity {
    TextView groupname;
    CircleImageView profile_image;

    FirebaseUser fuser;
    String groupid;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText txt_send;

    GroupMessageAdapter groupmessageAdapter;
    List<GroupChat> mgroupchat;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        profile_image = findViewById(R.id.profile_image);
        groupname = findViewById(R.id.groupname);
        btn_send = findViewById(R.id.btn_send);
        txt_send = findViewById(R.id.txt_send);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        groupid = getIntent().getStringExtra("groupid");
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = txt_send.getText().toString();
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("User").child(fuser.getUid());
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), groupid, msg);

                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            //assert votelist != null;
                            if (user.getOccupation().equals("student")) {
                                String point = user.getPoints();
                                int converted = Integer.parseInt(point);
                                int sum = converted + 1;
                                String total_point = Integer.toString(sum);
                                FirebaseDatabase.getInstance().getReference("User").child(fuser.getUid()).child("points").setValue(total_point);
                                FirebaseDatabase.getInstance().getReference("Student").child(fuser.getUid()).child("points").setValue(total_point);
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(GroupMessageActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
                txt_send.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Group").child(groupid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                groupname.setText(group.getGroupname());
                if (group.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(GroupMessageActivity.this).load(group.getImageURL()).into(profile_image);
                }
                readMessages(fuser.getUid(), groupid, group.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String sender, String id, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        //String key = reference.child("Chats").push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        //hashMap.put("id", key );
        hashMap.put("sender", sender);
        hashMap.put("id", id);
        hashMap.put("message", message);

        // reference.child("Chats").child(key).setValue(hashMap);
        reference.child("GroupChat").push().setValue(hashMap);

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid()).child(groupid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    chatRef.child("id").setValue(groupid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readMessages(String myid, String groupid, String imageurl) {
        mgroupchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("GroupChat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mgroupchat.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    GroupChat groupchat = snapshot1.getValue(GroupChat.class);
                    if (groupchat.getId().equals(groupid)) {
                        mgroupchat.add(groupchat);
                    }
                    groupmessageAdapter = new GroupMessageAdapter(GroupMessageActivity.this, mgroupchat, imageurl);
                    recyclerView.setAdapter(groupmessageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}