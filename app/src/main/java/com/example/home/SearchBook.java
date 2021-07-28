package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchBook extends AppCompatActivity implements View.OnClickListener {
    private List<ImageListArray> bookList = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        initView();
        back.setOnClickListener(this);
        addingData();
        final ImageListAdapter imageListAdapter = new ImageListAdapter(SearchBook.this, R.layout.image_list, bookList);
        listView.setAdapter(imageListAdapter);

        //open filter

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Enter a book name here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchBook.this,query,Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText))
                {
                    imageListAdapter.filter("");
                    listView.clearTextFilter();
                }
                else
                {


                    imageListAdapter.filter(newText);
                }
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppCompatTextView textView = view.findViewById(R.id.text_title_list);
                String title = textView.getText().toString();
                Toast.makeText(SearchBook.this, title, Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    void initView()
    {
        listView = findViewById(R.id.lv_search_book);
        searchView = findViewById(R.id.sv_search_book);
        back = findViewById(R.id.back_search);
    }

    public void addingData()
    {
        ImageListArray hpBook = new ImageListArray("Harry Potter and the Philosopher's Stone","J.K. Rowling",R.drawable.book_hp2);
        bookList.add(hpBook);
        ImageListArray osBook = new ImageListArray("The Old Man and the Sea","Ernest Hemingway",R.drawable.book2_os);
        bookList.add(osBook);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_search:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
