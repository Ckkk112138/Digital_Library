package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private EditText username,password,password2;
    private Button signup;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        initView();
        back.setOnClickListener(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDb.getUserName(username.getText().toString()) != null)
                {
                    Toast.makeText(Register.this,"UserName already exists!",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if (password.getText().toString().equals("") || !password.getText().toString().equals(password2.getText().toString())) {
                        Toast.makeText(Register.this,"Please enter the same password again",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        myDb.setUserAccount(username.getText().toString(),password.getText().toString(),null);

                        CurrentUser.setCurrentUserName(username.getText().toString());
                        Toast.makeText(Register.this,"Register Successful!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);

                    }
                }
            }
        });
    }

    void initView()
    {
        back = findViewById(R.id.back_register);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        password2= findViewById(R.id.register_password2);
        signup= findViewById(R.id.signup);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_register:
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                break;

        }
    }
}
