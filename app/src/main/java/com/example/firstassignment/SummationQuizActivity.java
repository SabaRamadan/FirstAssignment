package com.example.firstassignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SummationQuizActivity extends AppCompatActivity {
    private ListView quizOptions;
    private TextView quizQuestion;
    private Button submitAnswer;
    private SharedPreferences sharedPreferences;
    private QuestionDatabase database;
    private List<SummationQuestion> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswersCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initializeViews();
        initializeSharedPreferences();
        database = new QuestionDatabase();
        questions = database.getAllQuestions();
        if (!questions.isEmpty()) {
            displayQuestion(questions.get(currentQuestionIndex));
        }
        setupButtonListener();
    }

    private void initializeViews() {
        quizQuestion = findViewById(R.id.questionText);
        quizOptions = findViewById(R.id.quizOptions);
        submitAnswer = findViewById(R.id.submitAnswer);
    }

    private void initializeSharedPreferences() {
        sharedPreferences = this.getSharedPreferences("SummationAppPrefs", Context.MODE_PRIVATE);
    }

    private void displayQuestion(SummationQuestion question) {
        quizQuestion.setText(question.getQuestion());
        List<String> options = generateOptions(question.getAnswer());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, options);
        quizOptions.setAdapter(adapter);
        quizOptions.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private List<String> generateOptions(int correctAnswer) {
        List<String> options = new ArrayList<>();
        // Generate random wrong answers for variety
        while (options.size() < 3) {
            int wrongAnswer = (int) (Math.random() * 10) + 1;
            if (wrongAnswer != correctAnswer && !options.contains(String.valueOf(wrongAnswer))) {
                options.add(String.valueOf(wrongAnswer));
            }
        }
        options.add(String.valueOf(correctAnswer));
        Collections.shuffle(options);  // Shuffle options to randomize position
        return options;
    }


    private void setupButtonListener() {
        submitAnswer.setOnClickListener(v -> {
            int position = quizOptions.getCheckedItemPosition();
            if (position != -1) {
                // Check if the selected answer is correct
                String selectedOption = quizOptions.getItemAtPosition(position).toString();
                int correctAnswer = questions.get(currentQuestionIndex).getAnswer();
                if (Integer.parseInt(selectedOption) == correctAnswer) {
                    correctAnswersCount++;
                }

                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;  // Move to the next question
                    displayQuestion(questions.get(currentQuestionIndex));
                    quizOptions.setItemChecked(position, false);
                } else {
                    showCompletionDialog();
                }
            } else {
                Toast.makeText(this, "Please select an answer before submitting.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void showCompletionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Quiz Completed")
                .setMessage("You've completed all questions! Do you want to restart the quiz or view your results?")
                .setPositiveButton("Restart", (dialog, which) -> restartQuiz())
                .setNegativeButton("View Results", (dialog, which) -> {
                    completeQuiz();  // Calculate score and navigate to results
                })
                .show();
    }

    private void completeQuiz() {
        saveQuizResults(correctAnswersCount); // Save results
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }



    private void restartQuiz() {
        correctAnswersCount = 0;
        currentQuestionIndex = 0;
        displayQuestion(questions.get(currentQuestionIndex)); // Display the first question again
        quizOptions.clearChoices();
        quizOptions.requestLayout();
    }

    private void saveQuizResults(int score) {
        SharedPreferences prefs = getSharedPreferences("QuizPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("lastScore", score);
        int totalScore = prefs.getInt("totalScore", 0) + score;
        int totalQuizzesTaken = prefs.getInt("totalQuizzesTaken", 0) + 1;
        editor.putInt("totalScore", totalScore);
        editor.putInt("totalQuizzesTaken", totalQuizzesTaken);
        editor.apply();
    }


}
