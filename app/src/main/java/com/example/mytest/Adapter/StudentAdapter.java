package com.example.mytest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytest.MessageActivity;
import com.example.mytest.Model.Student;
import com.example.mytest.Model.User;
import com.example.mytest.R;
import com.example.mytest.SendPointsActivity;

import java.util.List;



public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context context;
    private List<Student> mStudents;


    public StudentAdapter(Context ct, List<Student> studentList) {
        context = ct;
        mStudents = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new StudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = mStudents.get(position);
        holder.username.setText(student.getUsername());
        if (student.getImageURL().equals("default")) {
            holder.profilePicture.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(context).load(student.getImageURL()).into(holder.profilePicture);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, SendPointsActivity.class);
                intent.putExtra("studentid", student.getId());
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        //int i =userArrayList.size();
        //return this.userArrayList.size();
        return mStudents.size();
    }

    // This class used as view holder in recycle view list to preview users
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profilePicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profilePicture = itemView.findViewById(R.id.profile_image);

        }
    }
}