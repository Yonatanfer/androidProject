package com.example.yonatanproject;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateWorkoutActivity extends AppCompatActivity {

    RadioGroup rgExercises;
    EditText etReps;
    Button btnSave;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        userEmail = getIntent().getStringExtra("userEmail");

        rgExercises = findViewById(R.id.rgExercises);
        etReps = findViewById(R.id.etReps);
        btnSave = findViewById(R.id.btnSaveWorkout);

        btnSave.setOnClickListener(v -> saveWorkout());
    }

    private void saveWorkout() {
        int selectedId = rgExercises.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Choose exercise", Toast.LENGTH_SHORT).show();
            return;
        }

        String repsStr = etReps.getText().toString();
        if (repsStr.isEmpty()) {
            Toast.makeText(this, "Enter reps", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String exercise = rb.getText().toString();
        int reps = Integer.parseInt(repsStr);

        Workout workout = new Workout(
                userEmail,
                exercise,
                reps,
                Timestamp.now()
        );

        FirebaseFirestore.getInstance()
                .collection("workouts")
                .add(workout)
                .addOnSuccessListener(doc -> finish());
    }
}
