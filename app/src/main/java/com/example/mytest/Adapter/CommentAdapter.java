package com.example.mytest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytest.MessageActivity;
import com.example.mytest.Model.ForumPost;
import com.example.mytest.Model.PostComment;
import com.example.mytest.Model.User;
import com.example.mytest.Model.Votelist;
import com.example.mytest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<PostComment> mPostComment;
    private String imageurl;
    FirebaseUser fuser;

    public CommentAdapter(Context ct, List<PostComment> postCommentList, String imageurl1) {
        context = ct;
        mPostComment = postCommentList;
        imageurl = imageurl1;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostComment postcomment = mPostComment.get(position);
        int Number;
        holder.show_message.setText(postcomment.getMessage());
        holder.show_vote.setText(postcomment.getVote());

        // if() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(postcomment.getSender());
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


        DatabaseReference voteRef = FirebaseDatabase.getInstance().getReference("Votelist").child(postcomment.getId()).child(fuser.getUid());

        voteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    voteRef.child("id").setValue(fuser.getUid());
                    voteRef.child("downvoted").setValue("false");
                    voteRef.child("upvoted").setValue("false");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(postcomment.getPoint().equals("true")) {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("User").child(postcomment.getSender());
            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    // assert votelist != null;
                    if (user.getOccupation().equals("student")) {
                        String point = user.getPoints();
                        int converted = Integer.parseInt(point);
                        int sum = converted + 1;
                        String total_point = Integer.toString(sum);
                        FirebaseDatabase.getInstance().getReference("User").child(postcomment.getSender()).child("points").setValue(total_point);
                        FirebaseDatabase.getInstance().getReference("Student").child(postcomment.getSender()).child("points").setValue(total_point);
                        FirebaseDatabase.getInstance().getReference("PostComment").child(postcomment.getId()).child("point").setValue("earned");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        holder.up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String votes = postcomment.getVote();
                
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Votelist").child(postcomment.getId()).child(fuser.getUid());

                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Votelist votelist = snapshot.getValue(Votelist.class);
                        //assert votelist != null;
                        String a = votelist.getUpvoted();
                        if(a.equals("false")) {
                            int converted = Integer.parseInt(votes);
                            int sum = converted + 1;
                            String total_votes = Integer.toString(sum);
                            FirebaseDatabase.getInstance().getReference("PostComment").child(postcomment.getId()).child("vote").setValue(total_votes);
                            reference1.child("downvoted").setValue("false");
                            reference1.child("upvoted").setValue("true");

                            if (total_votes.equals("10") && postcomment.getPoint().equals("false")) {
                                FirebaseDatabase.getInstance().getReference("PostComment").child(postcomment.getId()).child("point").setValue("true");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.down.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String votes = postcomment.getVote();

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Votelist").child(postcomment.getId()).child(fuser.getUid());

                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Votelist votelist = snapshot.getValue(Votelist.class);
                        //assert votelist != null;
                        String a = votelist.getDownvoted();
                        if(votes.equals("0")){

                        }else if(a.equals("false")){
                            int converted = Integer.parseInt(votes);
                            int sum = converted - 1;
                            String total_votes = Integer.toString(sum);
                            FirebaseDatabase.getInstance().getReference("PostComment").child(postcomment.getId()).child("vote").setValue(total_votes);
                            reference1.child("downvoted").setValue("true");
                            reference1.child("upvoted").setValue("false");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }



    @Override
    public int getItemCount() {
        //int i =userArrayList.size();
        //return this.userArrayList.size();
        return mPostComment.size();
    }

    // This class used as view holder in recycle view list to preview users
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message, show_username, show_vote;
        public ImageView profilePicture;
        public ImageButton up, down;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.comment_message);
            show_username = itemView.findViewById(R.id.username);
            profilePicture = itemView.findViewById(R.id.profile_image);
            show_vote = itemView.findViewById(R.id.vote_count);
            up = itemView.findViewById(R.id.upvote);
            down = itemView.findViewById(R.id.downvote);

        }
    }
}