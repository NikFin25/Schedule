package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button button_Close_FullSchedule;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button_Close_FullSchedule = findViewById(R.id.button_Close_FullSchedule);

        button_Close_FullSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackActivity(button_Close_FullSchedule);
            }
        });
    }

    public  void goBackActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}