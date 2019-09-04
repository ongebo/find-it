package com.example.find_it.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.find_it.R;

public class TokensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokens);

        TextView accessToken = findViewById(R.id.access_token);
        TextView refreshToken = findViewById(R.id.refresh_token);

        Intent intent = getIntent();
        accessToken.setText(intent.getStringExtra("ACCESS_TOKEN"));
        refreshToken.setText(intent.getStringExtra("REFRESH_TOKEN"));
    }
}
