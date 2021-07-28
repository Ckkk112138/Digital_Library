package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBooks extends AppCompatActivity implements View.OnClickListener {

    private ListView bookList;
    private ImageView back;
    private TextView totalBookNumber;
    private DatabaseHelper myDb;
    private List<Map<String,String>> data = new ArrayList<Map<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        myDb = new DatabaseHelper(this);
        myDb.setBookFromLibrary("12312312312","Little Prince","2022/7/22");
        initView();
        Map<String, String> map1 = new HashMap<String, String>();
        Bundle bundle = getIntent().getExtras();
        String cardNumber = bundle.getString("Card_Number");
        String[] bookNames = myDb.getLibraryBookName(cardNumber);
        if(bookNames != null && bookNames.length != 0)
        {
            for(int i = 0; i < bookNames.length; ++i)
            {
                String expireDate = myDb.getLibraryBookExpireDate(cardNumber,bookNames[i]);
                map1.put("Book_Name",bookNames[i]);
                map1.put("Expire_Date",expireDate);
            }
            totalBookNumber.setText(String.valueOf(bookNames.length));
        }
        data.add(map1);

        bookList.setAdapter(new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,
                new String[]{"Book_Name","Expire_Date"},new int[]{android.R.id.text1,android.R.id.text2}));

    }

    void initView()
    {
        bookList = findViewById(R.id.ls_my_book);
        back = findViewById(R.id.back_my_books);
        back.setOnClickListener(this);
        totalBookNumber = findViewById(R.id.total_my_books);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_my_books:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
        }

    }
}
