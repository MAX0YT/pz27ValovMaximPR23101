package com.example.pz27valovmaximpr23101;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class add_payment_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        findViewById(R.id.ivBack).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.btnProceed).setOnClickListener(v -> {
            finish();
        });

        RadioGroup rgMain = findViewById(R.id.rgMainPayment);
        LinearLayout cardContainer = findViewById(R.id.cardContainer);
        RadioGroup rgCards = findViewById(R.id.rgCardSelection);

        rgMain.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCreditCard) {
                cardContainer.setVisibility(android.view.View.VISIBLE);
            } else {
                cardContainer.setVisibility(android.view.View.GONE);
                rgCards.clearCheck(); // Сбрасываем выбор конкретной карты при уходе с Credit Card
            }
        });
    }
}