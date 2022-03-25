package com.example.mytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mytest.Fragments.ChatFragment;
import com.example.mytest.Fragments.ForumFragment;
import com.example.mytest.Fragments.GroupFragment;
import com.example.mytest.Fragments.UsersFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {

    Button btn_create_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);



        ViewPager viewPager = findViewById(R.id.view_pager);
        ForumActivity.ViewPagerAdapter viewPagerAdapter = new ForumActivity.ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ForumFragment(), "Forum");
        viewPager.setAdapter(viewPagerAdapter);

        String catid = getIntent().getStringExtra("catid");

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


        btn_create_post = findViewById(R.id.btn_create_post);

        btn_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ForumActivity.this, CreatePostActivity.class);
                intent.putExtra("catid", catid);
                startActivity(intent);
                System.out.println(catid);


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