package com.example.firstassignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionDatabase {
    private List<SummationQuestion> questions;

    public QuestionDatabase() {
        questions = new ArrayList<>();
        populateQuestions();
    }

    private void populateQuestions() {
        // Basic single-digit additions that do not exceed a sum of 10
        questions.add(new SummationQuestion("What is 1 + 1?", 2));
        questions.add(new SummationQuestion("What is 2 + 3?", 5));
        questions.add(new SummationQuestion("What is 9 + 8?", 17));
        questions.add(new SummationQuestion("What is 10 + 8?", 18));
        questions.add(new SummationQuestion("What is 2 + 2?", 4));
        questions.add(new SummationQuestion("What is 3 + 3?", 6));
        questions.add(new SummationQuestion("What is 5 + 5?", 10));
        questions.add(new SummationQuestion("What is 0 + 0?", 0));
        questions.add(new SummationQuestion("What is 1 + 2?", 3));
        questions.add(new SummationQuestion("What is 3 + 2?", 5));


        addRandomQuestions(5);  // Add 5 randomly generated summation questions
    }

    private void addRandomQuestions(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int a = random.nextInt(6);
            int b = random.nextInt(6 - a); // Ensure the sum does not exceed 10
            questions.add(new SummationQuestion("What is " + a + " + " + b + "?", a + b));
        }
    }

    public List<SummationQuestion> getAllQuestions() {
        return questions;
    }
}
