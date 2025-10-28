package com.example.yonatanproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yonatanproject.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> users;
    private final FragmentActivity activity;

    public UserAdapter(List<User> users, FragmentActivity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.tvName.setText(user.getFirstName() + " " + user.getLastName());
        holder.tvEmail.setText(user.getEmail());

        if (user.getUrl() != null && !user.getUrl().isEmpty()) {
            Picasso.get()
                    .load(user.getUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.ivProfile);
        } else {
            holder.ivProfile.setImageResource(R.drawable.ic_launcher_background);
        }

        // לחיצה על המשתמש תפתח את הדיאלוג באמצע המסך
        holder.itemView.setOnClickListener(v -> {
            UserDetailsDialog dialog = new UserDetailsDialog(user);
            dialog.show(activity.getSupportFragmentManager(), "UserDetailsDialog");
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail;
        ImageView ivProfile;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            ivProfile = itemView.findViewById(R.id.ivProfile);
        }
    }
}
