package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Category extends AppCompatActivity implements View.OnClickListener {

    private ImageView back,search;
    private LinearLayout fantasy,classic,children;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
    }

    private void initView()
    {
        back = findViewById(R.id.back_category);
        search = findViewById(R.id.img_search_category);
        fantasy = findViewById(R.id.fantasy_ly);
        classic = findViewById(R.id.classic_ly);
        children = findViewById(R.id.children_ly);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        fantasy.setOnClickListener(this);
        children.setOnClickListener(this);
        classic.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_category:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                break;
            case R.id.img_search_category:
                Intent i2 = new Intent(getApplicationContext(),SearchBook.class);
                startActivity(i2);
                break;
            case R.id.fantasy_ly:
                Intent i3 = new Intent(getApplicationContext(),ContentList.class);
                i3.putExtra("Category","Book_Fantasy");
                startActivity(i3);
                break;
            case R.id.classic_ly:
                Intent i4 = new Intent(getApplicationContext(),ContentList.class);
                i4.putExtra("Category","Audiobook_classic");
                startActivity(i4);
                break;
            case R.id.children_ly:
                Intent i5 = new Intent(getApplicationContext(),ContentList.class);
                i5.putExtra("Category","Video_children");
                startActivity(i5);
                break;

        }


    }
}
