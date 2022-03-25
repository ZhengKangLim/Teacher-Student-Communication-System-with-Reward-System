package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mytest.Fragments.CommentFragment;
import com.example.mytest.Fragments.ForumFragment;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    Button btn_add_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ViewPager viewPager = findViewById(R.id.view_pager);
        CommentActivity.ViewPagerAdapter viewPagerAdapter = new CommentActivity.ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CommentFragment(), "Comment");
        viewPager.setAdapter(viewPagerAdapter);

        String catid = getIntent().getStringExtra("catid");
        String postid = getIntent().getStringExtra("postid");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(catid.equals("1")){
            getSupportActionBar().setTitle("Software Engineering");
        }else if(catid.equals("2")){
            getSupportActionBar().setTitle("Traditional Chinese Medicine");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_add_comment = findViewById(R.id.btn_add_comment);

        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CommentActivity.this, CreateCommentActivity.class);
                intent.putExtra("catid", catid);
                intent.putExtra("postid", postid);
                startActivity(intent);

            }
        });
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {

            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }

}