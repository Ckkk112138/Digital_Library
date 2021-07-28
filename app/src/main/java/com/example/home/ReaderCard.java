package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReaderCard extends AppCompatActivity {

    Button submit;
    EditText cardNumber;
    DatabaseHelper myDb;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_card);
        initView();
        myDb = new DatabaseHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber1 = cardNumber.getText().toString();
                if(cardNumber1.length() != 11)
                {
                    Toast.makeText(getApplicationContext(),"Please enter a vild card number",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    myDb.setCardNumber(CurrentUser.getCurrentUserName(), cardNumber1);
                    Toast.makeText(getApplicationContext(),"Binding Successful",Toast.LENGTH_SHORT).show();

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView()
    {
        submit = findViewById(R.id.submit_card_number);
        cardNumber = findViewById(R.id.et_card_number);
        back = findViewById(R.id.back_reader_card);
    }


}
