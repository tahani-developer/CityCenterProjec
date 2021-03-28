package com.example.irbidcitycenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        username=findViewById(R.id.username_input);
        password=findViewById(R.id.pass);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(username.getText().toString().trim()==""&&password.getText().toString().trim()=="")
                {   Intent intent =new Intent(Login.this,MainActivity.class);
                startActivity(intent);}
            }
        });

    }
}