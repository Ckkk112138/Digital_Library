package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Book extends AppCompatActivity implements View.OnClickListener {

    private Button startReading;
    private TextView title;
    private ImageView bookCover,add,collect,back;
    private final String[] chapters = {"1","2"};
    private ListView listView;
    ArrayAdapter adapter = null;
    DatabaseHelper myDb;
    public static boolean addedToBookshelf;
    public static boolean addedToCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        addedToBookshelf = false;
        addedToCollection = false;
        myDb = new DatabaseHelper(this);
        initView();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,chapters);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView ls = (ListView) parent;
                String chapter = ls.getItemAtPosition(position).toString();
                if(chapter.equals("1"))
                {
                    Intent intent = new Intent(getApplicationContext(),Chapter1.class);
                    startActivity(intent);
                }

            }
        });
        startReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Chapter1.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(this);
        collect.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    void initView()
    {
        bookCover = findViewById(R.id.book_page_cover);
        startReading = findViewById(R.id.btn_start_reading);
        add = findViewById(R.id.book_add);
        collect = findViewById(R.id.book_collect);
        listView = findViewById(R.id.book_page_list);
        title = findViewById(R.id.book_page_title);
        back = findViewById(R.id.back_book);


        if(CurrentUser.getLoginState())
        {   if(!myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Normal")[0].equals("-1") && myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Normal")[0].equals("Harry Potter and the Philosopher's Stone"))
            {
                add.setImageResource(R.drawable.add_true);
                addedToBookshelf = true;
            }
            else
            {
                add.setImageResource(R.drawable.add_false);
                collect.setImageResource(R.drawable.collect_false);
            }
        }
        else
        {
            add.setImageResource(R.drawable.add_false);
            collect.setImageResource(R.drawable.collect_false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.book_add:
                if(CurrentUser.getCurrentUserName() != null)
                {
                    if(!addedToBookshelf)
                    {
                        add.setImageResource(R.drawable.add_true);
                        Bitmap bitmap = ((BitmapDrawable) bookCover.getDrawable()).getBitmap();
                        myDb.setBookCover(CurrentUser.getCurrentUserName(), title.getText().toString(), "Normal", bitmap);
                        Toast.makeText(Book.this, "Adding Successful!", Toast.LENGTH_SHORT).show();
                        addedToBookshelf = true;
                    }


                }
                else
                {
                    Toast.makeText(Book.this,"Please log in first",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.book_collect:
                if(CurrentUser.getCurrentUserName() != null)
                {
                    if(!addedToCollection)
                    {
                        collect.setImageResource(R.drawable.collect_true);
                        myDb.setCollection(CurrentUser.getCurrentUserName(),title.getText().toString());
                        Toast.makeText(Book.this, "Adding Successful!", Toast.LENGTH_SHORT).show();
                        addedToCollection = true;
                    }

                }
                else
                {
                    Toast.makeText(Book.this,"Please log in first",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.back_book:
                Intent intent = new Intent(getApplicationContext(),SearchBook.class);
                startActivity(intent);
                break;
        }

    }
}
