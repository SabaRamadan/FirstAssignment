package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartLessons = findViewById(R.id.btnStartLessons);
        Button btnTakeQuiz = findViewById(R.id.btnTakeQuiz);
        Button btnViewProgress = findViewById(R.id.btnViewProgress);

        btnStartLessons.setOnClickListener(v -> {
            // Intent to start SummationLessonActivity
            startActivity(new Intent(MainActivity.this, SummationLessonActivity.class));
        });

        btnTakeQuiz.setOnClickListener(v -> {
            // Intent to start SummationQuizActivity
            startActivity(new Intent(MainActivity.this, SummationQuizActivity.class));
        });

        btnViewProgress.setOnClickListener(v -> {
            // Intent to start ProgressActivity
            startActivity(new Intent(MainActivity.this, ProgressActivity.class));
        });
    }

}