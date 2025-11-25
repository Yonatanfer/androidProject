package com.example.yonatanproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    FirebaseFirestore db;
    ArrayList<User> usersList;
    UserAdapter adapter;
    Button btnSettings; // הכפתור

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        rvUsers = findViewById(R.id.rvUsers);
        btnSettings = findViewById(R.id.btnSettings); // מצביע לכפתור

        db = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        adapter = new UserAdapter(usersList, this);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        loadUsers();

        // לחיצה על הכפתור פותחת את SettingsActivity
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void loadUsers() {
        db.collection("users").get().addOnSuccessListener(query -> {
            usersList.clear();
            for (DocumentSnapshot doc : query) {
                User user = doc.toObject(User.class);
                usersList.add(user);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to load users: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
