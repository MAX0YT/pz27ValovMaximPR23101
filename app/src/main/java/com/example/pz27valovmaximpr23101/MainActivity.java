package com.example.pz27valovmaximpr23101;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout cardCustomerCare, cardSendPackage, cardFundWallet, cardChats;

    private ImageView iconCustomerCare, iconSendPackage, iconWallet, iconChats;
    private TextView titleCustomerCare, titleSendPackage, titleWallet, titleChats;
    private TextView descCustomerCare, descSendPackage, descWallet, descChats;

    // Для фото профиля
    private ShapeableImageView profileImageView;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация карточек
        cardCustomerCare = findViewById(R.id.constraintLayout7);
        cardSendPackage = findViewById(R.id.constraintLayout9);
        cardFundWallet = findViewById(R.id.constraintLayout6);
        cardChats = findViewById(R.id.constraintLayout8);

        iconCustomerCare = findViewById(R.id.imageView21);
        iconSendPackage = findViewById(R.id.imageView22);
        iconWallet = findViewById(R.id.imageView23);
        iconChats = findViewById(R.id.imageView24);

        titleCustomerCare = findViewById(R.id.textView5);
        titleSendPackage = findViewById(R.id.textView7);
        titleWallet = findViewById(R.id.textView9);
        titleChats = findViewById(R.id.textView11);

        descCustomerCare = findViewById(R.id.textView6);
        descSendPackage = findViewById(R.id.textView8);
        descWallet = findViewById(R.id.textView10);
        descChats = findViewById(R.id.textView12);

        // Профиль
        profileImageView = findViewById(R.id.imageView6);

        setupCardListeners();
        setupProfilePhotoPicker();

        var walletInMenu = findViewById(R.id.wallet_menu_icon);
        walletInMenu.setOnClickListener(v -> {
            openWalletActivity();
        });
        var track = findViewById(R.id.track);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, track_activity.class);
                startActivity(intent);
            }
        });
    }

    // ====================== КАРТОЧКИ ======================
    private void setupCardListeners() {
        cardCustomerCare.setOnClickListener(v -> selectCard(0));
        cardSendPackage.setOnClickListener(v -> selectCard(1));
        cardFundWallet.setOnClickListener(v -> selectCard(2));
        cardChats.setOnClickListener(v -> {
            selectCard(3);           // выделяем карточку
            openChatsActivity();     // переходим в ChatsActivity
        });
    }

    private void selectCard(int selectedIndex) {
        resetAllCards();

        switch (selectedIndex) {
            case 0:
                cardCustomerCare.setBackgroundResource(R.drawable.blue_bg);
                iconCustomerCare.setImageResource(R.drawable.pz27_customer_care_white_icon);
                titleCustomerCare.setTextColor(getColor(R.color.white));
                descCustomerCare.setTextColor(getColor(R.color.white));
                break;
            case 1:
                cardSendPackage.setBackgroundResource(R.drawable.blue_bg);
                iconSendPackage.setImageResource(R.drawable.pz27_send_a_package_white_icon);
                titleSendPackage.setTextColor(getColor(R.color.white));
                descSendPackage.setTextColor(getColor(R.color.white));
                break;
            case 2:
                cardFundWallet.setBackgroundResource(R.drawable.blue_bg);
                iconWallet.setImageResource(R.drawable.pz27_wallet_white_icon);
                titleWallet.setTextColor(getColor(R.color.white));
                descWallet.setTextColor(getColor(R.color.white));
                break;
            case 3:
                cardChats.setBackgroundResource(R.drawable.blue_bg);
                iconChats.setImageResource(R.drawable.pz27_chats_white_icon);
                titleChats.setTextColor(getColor(R.color.white));
                descChats.setTextColor(getColor(R.color.white));
                break;
        }
    }

    private void resetAllCards() {
        cardCustomerCare.setBackgroundResource(R.drawable.light_grey_bg);
        iconCustomerCare.setImageResource(R.drawable.pz27_customer_care_icon);
        titleCustomerCare.setTextColor(getColor(R.color.blue));
        descCustomerCare.setTextColor(getColor(R.color.black));

        cardSendPackage.setBackgroundResource(R.drawable.light_grey_bg);
        iconSendPackage.setImageResource(R.drawable.pz27_send_a_package_icon);
        titleSendPackage.setTextColor(getColor(R.color.blue));
        descSendPackage.setTextColor(getColor(R.color.black));

        cardFundWallet.setBackgroundResource(R.drawable.light_grey_bg);
        iconWallet.setImageResource(R.drawable.pz27_wallet_icon);
        titleWallet.setTextColor(getColor(R.color.blue));
        descWallet.setTextColor(getColor(R.color.black));

        cardChats.setBackgroundResource(R.drawable.light_grey_bg);
        iconChats.setImageResource(R.drawable.pz27_chats_icon);
        titleChats.setTextColor(getColor(R.color.blue));
        descChats.setTextColor(getColor(R.color.black));
    }

    // ====================== ПЕРЕХОД В CHATS ======================
    private void openChatsActivity() {
        Intent intent = new Intent(this, chats_activity.class);
        startActivity(intent);
    }
    private void openWalletActivity() {
        Intent intent = new Intent(this, wallet_activity.class);
        startActivity(intent);
    }
    // ====================== ВЫБОР ФОТО ======================
    private void setupProfilePhotoPicker() {
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImageView.setImageURI(selectedImageUri);
                            Toast.makeText(this, "Фото профиля обновлено ✅", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openGallery();
                    } else {
                        Toast.makeText(this, "Разрешение на доступ к галерее отклонено", Toast.LENGTH_LONG).show();
                    }
                });

        profileImageView.setOnClickListener(v -> checkAndRequestPermission());
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }
}