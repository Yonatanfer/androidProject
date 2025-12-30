package com.example.yonatanproject;

import com.google.firebase.Timestamp;

public class Workout {

    private String exercise;
    private int reps;
    private Timestamp date;

    public Workout() {
        // חובה ל-Firestore
    }

    public Workout(String exercise, int reps, Timestamp date) {
        this.exercise = exercise;
        this.reps = reps;
        this.date = date;
    }

    public String getExercise() {
        return exercise;
    }

    public int getReps() {
        return reps;
    }

    public Timestamp getDate() {
        return date;
    }
}
