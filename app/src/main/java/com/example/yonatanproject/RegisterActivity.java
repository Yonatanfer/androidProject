package com.example.yonatanproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import com.example.yonatanproject.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    Bitmap photo;
    EditText etFirst, etLast, etPhone, etEmail, etPass;
    Button btnRegister, btnGoToLogin, btnCamera;
    ImageView imageView;
    FirebaseFirestore db;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // קישור רכיבים מה-XML
        etFirst = findViewById(R.id.etFirstName);
        etLast = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);
        btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.imageView);

        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(v -> registerUser());
        btnGoToLogin.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        btnCamera.setOnClickListener(v -> openCamera());
    }

    private void openCamera() {
        // בקשת הרשאה למצלמה
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            // פתיחת המצלמה
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    private void registerUser() {
        String first = etFirst.getText().toString().trim();
        String last = etLast.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (first.isEmpty() || last.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(email).get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            } else {
                String url = "";

                // רק אם יש תמונה – נעלה אותה
                if (photo != null) {
                    SimpleUpload upload = new SimpleUpload();
                    try {
                        url = upload.uploadBitmap("profile_" + System.currentTimeMillis() + ".jpg", photo);
                    } catch (IOException e) {
                        Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                // צור אובייקט משתמש עם URL
                User user = new User(first, last, phone, email, pass, url);

                db.collection("users").document(email).set(user)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show());
            }
        });
    }

}
