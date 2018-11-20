package com.example.lszlsomai.sapiadvertiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToRegistration(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
