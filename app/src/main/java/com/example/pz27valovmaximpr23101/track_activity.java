package com.example.pz27valovmaximpr23101;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.BoundingBox;
import com.yandex.mapkit.geometry.Geometry;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;

public class track_activity extends AppCompatActivity {

    private MapView mapView;
    private MapObjectCollection mapObjects;
    private boolean isRouteAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Важно: инициализация MapKit ДО super.onCreate()
        MapKitFactory.setApiKey("de89f172-5776-4a14-aa8a-0d2512ddb109");
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapView = findViewById(R.id.mapView);

        setupBottomNavigation();

        findViewById(R.id.btnPackageInfo).setOnClickListener(v -> {
            startActivity(new Intent(this, package_info_activity.class));
        });
    }

    private void setupBottomNavigation() {
        findViewById(R.id.home_icon).setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        findViewById(R.id.wallet_icon).setOnClickListener(v ->
                startActivity(new Intent(this, wallet_activity.class)));
    }

    // Добавляем маршрут после того, как карта полностью готова
    private void addRouteToMap() {
        // Двойная проверка: чтобы маршрут не добавился дважды и карта была готова
        if (isRouteAdded || mapView.getMap() == null) return;

        try {
            Point driverPosition = new Point(6.5244, 3.3792);      // Курьер
            Point destinationPosition = new Point(6.5500, 3.3900); // Пункт доставки

            Polyline polyline = new Polyline(java.util.Arrays.asList(driverPosition, destinationPosition));

            mapObjects = mapView.getMap().getMapObjects().addCollection();
            PolylineMapObject routeLine = mapObjects.addPolyline(polyline);
            // Цвет теперь импортируется правильно
            int lineColor = Color.argb(255, 0, 102, 255); // Ярко-синий цвет
            routeLine.setStrokeColor(lineColor);
            routeLine.setStrokeWidth(7f);

            // Метки
            mapObjects.addPlacemark(driverPosition).setText("Курьер");
            mapObjects.addPlacemark(destinationPosition).setText("Пункт доставки");

            // --- ИСПРАВЛЕННЫЙ БЛОК ДЛЯ ЦЕНТРИРОВАНИЯ КАРТЫ ---
            // Этот код корректно устанавливает камеру, чтобы показать обе точки
            BoundingBox boundingBox = new BoundingBox(driverPosition, destinationPosition);
            Geometry boundingBoxGeometry = Geometry.fromBoundingBox(boundingBox);
            CameraPosition cameraPosition = mapView.getMap().cameraPosition(boundingBoxGeometry);

            mapView.getMap().move(
                    cameraPosition,
                    new Animation(Animation.Type.SMOOTH, 1.2f),
                    null
            );
            // -------------------------------------------------

            isRouteAdded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

        // Добавляем маршрут с небольшой задержкой
        mapView.postDelayed(this::addRouteToMap, 800);
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onStop();
        }
        super.onDestroy();
    }
}