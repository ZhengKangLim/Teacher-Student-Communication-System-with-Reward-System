package com.example.mytest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytest.Model.Chat;
import com.example.mytest.Model.GroupChat;
import com.example.mytest.Model.User;
import com.example.mytest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context context;
    private List<GroupChat> mGroupChat;
    private String imageurl;

    FirebaseUser fuser;

    public GroupMessageAdapter(Context ct, List<GroupChat> mGroupChat1, String imageurl1) {
        context = ct;
        mGroupChat = mGroupChat1;
        imageurl = imageurl1;
    }

    @NonNull
    @Override
    public GroupMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new GroupMessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.group_chat_item_left, parent, false);
            return new GroupMessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageAdapter.ViewHolder holder, int position) {

        GroupChat groupchat = mGroupChat.get(position);

        holder.show_message.setText(groupchat.getMessage());

       // if() {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(groupchat.getSender());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User users = snapshot.getValue(User.class);
                    assert users != null;
                    //username.setText(users.getUsername());
                    String anonymous = users.getAnonymous();
                    if(anonymous.equals("on")){
                        holder.show_username.setText("Anonymous");
                    }else {
                        holder.show_username.setText(users.getUsername());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

      //  }

        if(imageurl.equals("default")){
            holder.profilePicture.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imageurl).into(holder.profilePicture);
        }

    }




    @Override
    public int getItemCount() {
        //int i =userArrayList.size();
        //return this.userArrayList.size();
        return mGroupChat.size();
    }

    // This class used as view holder in recycle view list to preview users
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message, show_username;
        public ImageView profilePicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            show_username = itemView.findViewById(R.id.show_username);
            profilePicture = itemView.findViewById(R.id.profile_image);

        }
    }
    @Override
    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mGroupChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
