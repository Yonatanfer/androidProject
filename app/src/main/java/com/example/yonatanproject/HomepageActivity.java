package com.example.yonatanproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.yonatanproject.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    FirebaseFirestore db;
    ArrayList<User> usersList;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        rvUsers = findViewById(R.id.rvUsers);
        db = FirebaseFirestore.getInstance();
        usersList = new ArrayList<>();
        adapter = new UserAdapter(usersList, this);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        loadUsers();
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
