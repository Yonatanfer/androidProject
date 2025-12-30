package com.example.yonatanproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView rvWorkouts;
    WorkoutAdapter adapter;
    ArrayList<Workout> workouts = new ArrayList<>();
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        userEmail = getIntent().getStringExtra("userEmail");

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        rvWorkouts = findViewById(R.id.rvWorkouts);
        Button btnStart = findViewById(R.id.btnStartWorkout);

        adapter = new WorkoutAdapter(workouts);
        rvWorkouts.setLayoutManager(new LinearLayoutManager(this));
        rvWorkouts.setAdapter(adapter);

        btnStart.setOnClickListener(v -> {
            Intent i = new Intent(this, CreateWorkoutActivity.class);
            i.putExtra("userEmail", userEmail);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    private void loadWorkouts() {
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userEmail)
                .get()
                .addOnSuccessListener(doc -> {
                    User user = doc.toObject(User.class);
                    workouts.clear();

                    if (user != null && user.getWorkouts() != null) {
                        workouts.addAll(user.getWorkouts());
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}
