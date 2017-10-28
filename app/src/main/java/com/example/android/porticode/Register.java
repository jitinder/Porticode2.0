package com.example.android.porticode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = (Button) findViewById(R.id.register_button_register);
        Button loginButton = (Button) findViewById(R.id.login_button_register);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allGood = doCheck();
                if(allGood == true){
                    Intent intent = new Intent(v.getContext(), Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Register.this, "Please Fill in all Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean doCheck(){
        boolean allGood = false;
        EditText username = (EditText) findViewById(R.id.username_field_register);
        EditText password = (EditText) findViewById(R.id.password_field_register);
        EditText first = (EditText) findViewById(R.id.first_name);
        EditText last = (EditText) findViewById(R.id.last_name);
        EditText streetNum = (EditText) findViewById(R.id.address_street_num);
        EditText streetName = (EditText) findViewById(R.id.address_street_name);
        EditText streetCity = (EditText) findViewById(R.id.address_street_city);
        EditText streetZip = (EditText) findViewById(R.id.address_street_zip);

        String userData[] = new String[8];
        userData[0] = username.getText().toString();
        userData[1] = password.getText().toString();
        userData[2] = first.getText().toString();
        userData[3] = last.getText().toString();
        userData[4] = streetNum.getText().toString();
        userData[5] = streetName.getText().toString();
        userData[6] = streetCity.getText().toString();
        userData[7] = streetZip.getText().toString();

        for(int i = 0; i < 8; i++){
            if(userData[i].equals("")){
                allGood = false;
            } else {
                allGood = true;
            }
        }
        return allGood;
    }
}
