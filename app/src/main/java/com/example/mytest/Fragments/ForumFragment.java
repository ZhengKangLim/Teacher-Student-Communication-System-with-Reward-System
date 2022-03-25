package com.example.mytest.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.Adapter.ForumAdapter;
import com.example.mytest.Adapter.GroupAdapter;
import com.example.mytest.Model.ForumPost;
import com.example.mytest.Model.Group;
import com.example.mytest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ForumFragment extends Fragment {

    private RecyclerView recyclerView;

    private ForumAdapter forumAdapter;
    private List<ForumPost> mForumpost;

    public ForumFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mForumpost = new ArrayList<>();

        readPosts();

        return view;
    }

    private void readPosts() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ForumPost");
        String catid = getActivity().getIntent().getStringExtra("catid");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mForumpost.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ForumPost forumpost = snapshot.getValue(ForumPost.class);

                    assert forumpost != null;
                    if (forumpost.getCatid().equals(catid)) {
                        mForumpost.add(forumpost);

                    }
                    String imageurl = forumpost.getImageURL();

                    forumAdapter = new ForumAdapter(getContext(), mForumpost, imageurl);
                    recyclerView.setAdapter(forumAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}