package com.example.pz27valovmaximpr23101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chat_activity extends AppCompatActivity {

    private ImageView backArrow;
    private ImageView phoneIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация кнопок
        backArrow = findViewById(R.id.imageView12);
        phoneIcon = findViewById(R.id.imageView38);

        setupListeners();
    }

    private void setupListeners() {
        // Кнопка "Назад" — возвращаемся в ChatsActivity
        backArrow.setOnClickListener(v -> {
            onBackPressed(); // или finish();
        });

        // Кнопка телефона — переход в CallActivity
        phoneIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, call_activity.class);
            startActivity(intent);
        });
    }
}