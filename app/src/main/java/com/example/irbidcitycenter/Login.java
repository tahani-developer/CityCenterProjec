package com.example.irbidcitycenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.irbidcitycenter.Models.DatabaseHandler;

public class Login extends AppCompatActivity {
    EditText username,password;
    DatabaseHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler=new DatabaseHandler(Login.this);

        username=findViewById(R.id.username_input);
        password=findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(username.getText().toString().trim().equals("6") &&password.getText().toString().trim().equals("123"))
                {
                    MainActivity.SET_userNO=username.getText().toString().trim();

                    Intent intent =new Intent(Login.this,MainActivity.class);
                startActivity(intent);}
              else
              {   username.setError("");
                  password.setError("");}
            }
        });

    }
}