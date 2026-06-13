package com.example.pz27valovmaximpr23101;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chats_activity extends AppCompatActivity {

    private ConstraintLayout card1, card2, card3, card4, card5;
    private EditText searchEditText;
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chats);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация элементов
        backArrow = findViewById(R.id.imageView12);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);

        searchEditText = findViewById(R.id.editTextText2);

        setupListeners();
        setupSearch();
    }

    private void setupListeners() {
        // Кнопка "Назад"
        backArrow.setOnClickListener(v -> {
            onBackPressed(); // или finish();
        });

        // Переход в личный чат при нажатии на вторую карточку (Chinonso James)
        card2.setOnClickListener(v -> {
            Intent intent = new Intent(this, chat_activity.class);
            startActivity(intent);
        });

        // Опционально: можно сделать кликабельными и другие карточки позже
        // card1.setOnClickListener(v -> openChat(1));
        // card3.setOnClickListener(v -> openChat(3));
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDrivers(s.toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterDrivers(String query) {
        if (query.isEmpty()) {
            card1.setVisibility(ConstraintLayout.VISIBLE);
            card2.setVisibility(ConstraintLayout.VISIBLE);
            card3.setVisibility(ConstraintLayout.VISIBLE);
            card4.setVisibility(ConstraintLayout.VISIBLE);
            card5.setVisibility(ConstraintLayout.VISIBLE);
            return;
        }

        card1.setVisibility(containsName(card1, query) ? ConstraintLayout.VISIBLE : ConstraintLayout.GONE);
        card2.setVisibility(containsName(card2, query) ? ConstraintLayout.VISIBLE : ConstraintLayout.GONE);
        card3.setVisibility(containsName(card3, query) ? ConstraintLayout.VISIBLE : ConstraintLayout.GONE);
        card4.setVisibility(containsName(card4, query) ? ConstraintLayout.VISIBLE : ConstraintLayout.GONE);
        card5.setVisibility(containsName(card5, query) ? ConstraintLayout.VISIBLE : ConstraintLayout.GONE);
    }

    private boolean containsName(ConstraintLayout card, String query) {
        for (int i = 0; i < card.getChildCount(); i++) {
            if (card.getChildAt(i) instanceof android.widget.TextView) {
                android.widget.TextView tv = (android.widget.TextView) card.getChildAt(i);
                if (tv.getText().toString().toLowerCase().contains(query)) {
                    return true;
                }
            }
        }
        return false;
    }
}