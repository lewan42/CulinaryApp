package com.example.lewan.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lewan.myapplication.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {


    protected EditText field_login;
    protected EditText field_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(MainActivity.this,CreateAccountActivity.class);
        //startActivity(intent);

        Button btn = findViewById(R.id.btnMap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


        Button btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }


    public void OnClickCreateAccount(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }


    public void OnClickAutorization(View view) {

        field_login = findViewById(R.id.field_login);
        field_password = findViewById(R.id.field_password);
        //field_password = findViewById(R.id.field_password);

        CallBack callBack = new CallBack();

//        Thread t = new Thread(new Authorization(field_login.getText().toString(), field_password.getText().toString(), callBack, "logIn"));
//        t.start();

//        try {
//            t.join();
//        } catch (Exception e) {
//
//        }

//        Intent intent = new Intent(MainActivity.this, GridViewImageTextActivity.class);
//        startActivity(intent);

//        Intent intent = new Intent(MainActivity.this, GridViewImageTextActivity.class);
//        startActivity(intent);

        if (!field_login.getText().toString().equals("") && !field_password.getText().toString().equals("")) {
            Thread t = new Thread(new Authorization(field_login.getText().toString(), field_password.getText().toString(), callBack, "logIn"));
            t.start();

            try {
                t.join();
            } catch (Exception e) {

            }

            Toast toast = Toast.makeText(this, callBack.getStatus(), Toast.LENGTH_LONG);
            toast.show();
            

            if (callBack.getStatus().equals("yes")) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }


        } else {
            Toast toast = Toast.makeText(this, "Заполните поля", Toast.LENGTH_LONG);
            toast.show();
            field_password.setBackgroundColor(Color.RED);

        }
    }

}
