package com.example.yonatanproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.google.firebase.firestore.*;

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

        loadWorkouts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    private void loadWorkouts() {
        FirebaseFirestore.getInstance()
                .collection("workouts")
                .whereEqualTo("userEmail", userEmail)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    workouts.clear();
                    if (value != null) {
                        for (DocumentSnapshot doc : value) {
                            workouts.add(doc.toObject(Workout.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
