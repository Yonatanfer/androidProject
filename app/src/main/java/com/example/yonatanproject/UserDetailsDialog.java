package com.example.yonatanproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.yonatanproject.User;
import com.squareup.picasso.Picasso;

public class UserDetailsDialog extends DialogFragment {

    private final User user;

    public UserDetailsDialog(User user) {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_details, container, false);

        ImageView ivProfileLarge = view.findViewById(R.id.ivProfileLarge);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        TextView tvPhone = view.findViewById(R.id.tvPhone);

        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());

        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            Picasso.get().load(user.getImageUrl()).into(ivProfileLarge);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.8),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }
}
