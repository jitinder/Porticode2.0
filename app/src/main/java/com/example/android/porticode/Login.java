package com.example.android.porticode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = (Button) findViewById(R.id.login_button);
        Button registerButton = (Button) findViewById(R.id.register_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allGood = doInputCheck();
                if(allGood == true){
                    Toast.makeText(Login.this, "Correct Credentials", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(v.getContext(), Login.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Invalid Credentials. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    public boolean doInputCheck(){
        boolean allGood = false;
        EditText usernameField = (EditText) findViewById(R.id.username_field_login);
        EditText passwordField = (EditText) findViewById(R.id.password_field_login);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        if(username.equals(password)){
            if(username.equals("") || password.equals("")){
                return false;
            } else {
                allGood = true;
            }
        }
        return allGood;
    }
}
