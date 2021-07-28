package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContentList extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private TextView title;
    private ListView bookList;
    private List<ImageListArray> contents = new ArrayList<>();
    private ImageListAdapter imageListAdapter;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        initView();
        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("Category");
        addData();
        final ImageListAdapter imageListAdapter = new ImageListAdapter(ContentList.this,R.layout.image_list,contents);
        bookList.setAdapter(imageListAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppCompatTextView textView = view.findViewById(R.id.text_title_list);
                String title = textView.getText().toString();
                Toast.makeText(ContentList.this, title, Toast.LENGTH_SHORT).show();
                if(title.equals("Harry Potter and the Philosopher's Stone"))
                {
                    Intent intent = new Intent(getApplicationContext(),Book.class);
                    startActivity(intent);
                }
                else if(title.equals("The Old Man and the Sea"))
                {
                    Intent intent = new Intent(getApplicationContext(),Audiobook.class);
                    startActivity(intent);
                }
                else if (title.equals("Three Little Princesses"))
                {
                    Intent intent = new Intent(getApplicationContext(),VideoPlaying.class);
                    startActivity(intent);
                }
            }
        });
    }

    void initView()
    {
        back = findViewById(R.id.back_content_list);
        title = findViewById(R.id.tv_content_title);
        bookList = findViewById(R.id.content_list_lv);
        back.setOnClickListener(this);
    }

    void addData()
    {
        if(category.equals("Book_Fantasy"))
        {
            title.setText("Book");
            ImageListArray hpBook = new ImageListArray("Harry Potter and the Philosopher's Stone","J.K. Rowling",R.drawable.book_hp2);
            contents.add(hpBook);
        }
        else if(category.equals("Audiobook_classic"))
        {
            title.setText("Audiobook");
            ImageListArray osBook = new ImageListArray("The Old Man and the Sea","Ernest Hemingway",R.drawable.book2_os);
            contents.add(osBook);
        }
        else if (category.equals("Video_children"))
        {
            title.setText("Video");
            ImageListArray tpVideo= new ImageListArray("Three Little Princesses","Youtube",R.drawable.video_three_princesses);
            contents.add(tpVideo);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_content_list:
                Intent intent = new Intent(getApplicationContext(),Category.class);
                startActivity(intent);
        }

    }
}
