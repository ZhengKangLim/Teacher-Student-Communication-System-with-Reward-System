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

import com.example.mytest.Adapter.CommentAdapter;
import com.example.mytest.Adapter.ForumAdapter;
import com.example.mytest.Model.ForumPost;
import com.example.mytest.Model.PostComment;
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

public class CommentFragment extends Fragment {

    private RecyclerView recyclerView;

    private CommentAdapter commentAdapter;
    private List<PostComment> mPostcomment;

    public CommentFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPostcomment = new ArrayList<>();

        readComments();

        return view;
    }

    private void readComments() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PostComment");
        String catid = getActivity().getIntent().getStringExtra("catid");
        String postid = getActivity().getIntent().getStringExtra("postid");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPostcomment.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PostComment postcomment = snapshot.getValue(PostComment.class);

                    assert postcomment != null;
                    if (postcomment.getCatid().equals(catid) && postcomment.getPostid().equals(postid)) {
                        mPostcomment.add(postcomment);

                    }
                    String imageurl = postcomment.getImageURL();

                    commentAdapter = new CommentAdapter(getContext(), mPostcomment, imageurl);
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}