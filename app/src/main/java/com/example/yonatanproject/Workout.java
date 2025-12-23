package com.example.yonatanproject;

import com.google.firebase.Timestamp;

public class Workout {
    private String userEmail;
    private String exercise;
    private int reps;
    private Timestamp date;

    public Workout() {}

    public Workout(String userEmail, String exercise, int reps, Timestamp date) {
        this.userEmail = userEmail;
        this.exercise = exercise;
        this.reps = reps;
        this.date = date;
    }

    public String getUserEmail() { return userEmail; }
    public String getExercise() { return exercise; }
    public int getReps() { return reps; }
    public Timestamp getDate() { return date; }
}
