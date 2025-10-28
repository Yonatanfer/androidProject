package com.example.yonatanproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPass;
    Button btnLogin, btnGoToRegister;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etLoginEmail);
        etPass = findViewById(R.id.etLoginPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> loginUser());
        btnGoToRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(email).get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                String dbPass = doc.getString("password");
                if (dbPass.equals(pass)) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomepageActivity.class));
                } else {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
