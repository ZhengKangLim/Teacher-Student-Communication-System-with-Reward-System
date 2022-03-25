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
import com.example.mytest.GroupMessageActivity;
import com.example.mytest.MessageActivity;
import com.example.mytest.Model.Group;
import com.example.mytest.Model.User;
import com.example.mytest.R;

import java.util.List;

//import com.example.comvus.MessageActivity;
//import com.example.comvus.Model.Chat;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private Context context;
    private List<Group> mGroups;


    public GroupAdapter(Context ct, List<Group> groupList) {
        context = ct;
        mGroups = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group currentGroup = mGroups.get(position);
        holder.groupname.setText(currentGroup.getGroupname());
        if (currentGroup.getImageURL().equals("default")) {
            holder.profilePicture.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(context).load(currentGroup.getImageURL()).into(holder.profilePicture);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, GroupMessageActivity.class);
                intent.putExtra("groupid", currentGroup.getId());
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        //int i =userArrayList.size();
        //return this.userArrayList.size();
        return mGroups.size();
    }

    // This class used as view holder in recycle view list to preview users
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView groupname;
        public ImageView profilePicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupname = itemView.findViewById(R.id.groupname);
            profilePicture = itemView.findViewById(R.id.profile_image);

        }
    }
}