package com.example.pz27valovmaximpr23101;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class load_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CircularProgressIndicator progressIndicator = findViewById(R.id.progressIndicator);
        Button btnDone = findViewById(R.id.btnDone);

        // Анимация прогрессбара до 100% за 3 секунды
        android.animation.ObjectAnimator progressAnimator =
                android.animation.ObjectAnimator.ofInt(progressIndicator, "progress", 0, 100);
        progressAnimator.setDuration(3000);

        // Слушатель завершения анимации
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                Intent intent = new Intent(load_activity.this, successful_activity.class);
                startActivity(intent);
            }
        });

        progressAnimator.start();

        // Кнопка "Done" (если пользователь хочет пропустить анимацию)
        btnDone.setOnClickListener(v -> {
            // Принудительно отменяем анимацию, если она еще идет, и переходим
            if (progressAnimator.isRunning()) {
                progressAnimator.cancel();
            } else {
                finish();
                Intent intent = new Intent(load_activity.this, successful_activity.class);
                startActivity(intent);
            }
        });
    }
}