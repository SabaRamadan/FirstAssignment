package com.example.firstassignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {
    TextView tvScore;
    TextView tvAverageScore;
    Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initializeViews();
        displayScores();
        setupButtonListener();
    }

    private void initializeViews() {
        tvScore = findViewById(R.id.tvScore);
        tvAverageScore = findViewById(R.id.tvAverageScore);
        btnBackToMain = findViewById(R.id.btnBackToMain);
    }

    private void displayScores() {
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        int score = prefs.getInt("lastScore", -1);
        int totalScore = prefs.getInt("totalScore", 0);
        int totalQuizzesTaken = prefs.getInt("totalQuizzesTaken", 0);

        Log.d("ProgressActivity", "Loaded scores: Score: " + score + ", Total Score: " + totalScore + ", Quizzes Taken: " + totalQuizzesTaken);

        if (score == -1) {
            tvScore.setText("No quizzes taken yet.");
        } else {
            tvScore.setText("Last Quiz Score: " + score);
        }

        if (totalQuizzesTaken > 0) {
            int averageScore = totalScore / totalQuizzesTaken;
            tvAverageScore.setText("Average Score: " + averageScore);
        } else {
            tvAverageScore.setText("No average score available.");
        }
    }

    private void setupButtonListener() {
        btnBackToMain.setOnClickListener(view -> {
            Toast.makeText(this, "Returning to Main Menu", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayScores();  // Refresh scores when the activity is resumed
    }
}
