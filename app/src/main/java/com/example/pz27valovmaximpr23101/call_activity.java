package com.example.pz27valovmaximpr23101;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.imageview.ShapeableImageView;

public class call_activity extends AppCompatActivity {

    private ShapeableImageView endCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация кнопки завершения звонка
        endCallButton = findViewById(R.id.imageView50);

        setupListeners();
    }

    private void setupListeners() {
        // Красный круг — завершить звонок и вернуться назад
        endCallButton.setOnClickListener(v -> {
            finish();           // или onBackPressed();
        });
    }
}