package com.example.lewan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    protected EditText field_login;
    protected EditText field_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void OnClick(View view) {

        field_login = findViewById(R.id.field_login);
        field_password = findViewById(R.id.field_password);
        field_password = findViewById(R.id.field_password);

        CallBack callBack = new CallBack();
        Thread t = new Thread(new Authorization(field_login.getText().toString(), field_password.getText().toString(), callBack));
        t.start();

        try {
            t.join();
        } catch (Exception e) {

        }

        Toast toast = Toast.makeText(this, callBack.getStatus(), Toast.LENGTH_LONG);
        toast.show();


    }
}
