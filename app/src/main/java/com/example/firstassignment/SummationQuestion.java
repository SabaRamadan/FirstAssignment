package com.example.firstassignment;

import androidx.annotation.NonNull;

public class SummationQuestion {
    private String question;
    private int answer;


    public SummationQuestion(String question, int answer) {
        this.question = question;
        this.answer = answer;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }



    @NonNull
    @Override
    public String toString() {
        return question + " Answer: " + answer;
    }


}
