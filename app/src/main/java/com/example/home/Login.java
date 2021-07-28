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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private TextView register;
    private EditText username,password;
    private Button signin;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        initView();
        back.setOnClickListener(this);
        register.setOnClickListener(this);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDb.getUserName(username.getText().toString()) == null)
                {
                    Toast.makeText(Login.this,"User doesn't exist!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!myDb.getPassword(username.getText().toString()).equals(password.getText().toString()))
                    {
                        Toast.makeText(Login.this,"Wrong password!",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        CurrentUser.setCurrentUserName(username.getText().toString());
                        CurrentUser.setLoginState(true);
                        Toast.makeText(Login.this,"Welcome! "+CurrentUser.getCurrentUserName(),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });

    }

    void initView()
    {
        back = findViewById(R.id.back_login);
        register = findViewById(R.id.register_signin);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        signin = findViewById(R.id.signin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_login:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                break;
            case R.id.register_signin:
                Intent i2 = new Intent(getApplicationContext(),Register.class);
                startActivity(i2);
                break;

        }
    }
}
