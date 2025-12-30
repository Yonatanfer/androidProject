package com.example.yonatanproject;

import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String imageUrl;

    private ArrayList<Workout> workouts;

    public User() {
        // חובה ל-Firestore
    }

    public User(String firstName, String lastName, String phone,
                String email, String password, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.workouts = new ArrayList<>();
    }

    public ArrayList<Workout> getWorkouts() {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }
        return workouts;
    }

    public void setWorkouts(ArrayList<Workout> workouts) {
        this.workouts = workouts;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getImageUrl() { return imageUrl; }
}
