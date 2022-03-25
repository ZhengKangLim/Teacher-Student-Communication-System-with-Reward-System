package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DiscussionCategoryActivity extends AppCompatActivity {

    Button btn_software_engineering;
    Button btn_traditional_chinese_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose a Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_software_engineering = findViewById(R.id.btn_software_engineering);
        btn_traditional_chinese_medicine = findViewById(R.id.btn_traditional_chinese_medicine);

        btn_software_engineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscussionCategoryActivity.this, ForumActivity.class);
                String catid = "1";
                intent.putExtra("catid", catid);
                //startActivity(new Intent(DiscussionCategoryActivity.this, ForumActivity.class));
                startActivity(intent);

            }
        });

        btn_traditional_chinese_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscussionCategoryActivity.this, ForumActivity.class);
                String catid = "2";
                intent.putExtra("catid", catid);
                //startActivity(new Intent(DiscussionCategoryActivity.this, ForumActivity.class));
                startActivity(intent);
            }
        });

    }
}