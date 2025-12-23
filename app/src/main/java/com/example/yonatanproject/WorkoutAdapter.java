package com.example.yonatanproject;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.VH> {

    List<Workout> workouts;

    public WorkoutAdapter(List<Workout> workouts) {
        this.workouts = workouts;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvExercise, tvReps;

        VH(View v) {
            super(v);
            tvExercise = v.findViewById(R.id.tvExercise);
            tvReps = v.findViewById(R.id.tvReps);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int vType) {
        return new VH(LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_workout, p, false));
    }

    @Override
    public void onBindViewHolder(VH h, int pos) {
        Workout w = workouts.get(pos);
        h.tvExercise.setText(w.getExercise());
        h.tvReps.setText("Reps: " + w.getReps());
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}
