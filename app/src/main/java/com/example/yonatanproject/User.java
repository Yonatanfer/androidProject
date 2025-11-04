package com.example.yonatanproject;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String imageUrl; // <--- שדה חדש

    public User() { }

    public User(String firstName, String lastName, String phone, String email, String password, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getImageUrl() { return imageUrl; }
}
