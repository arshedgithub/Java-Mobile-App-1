package com.example.mad1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Page01 extends AppCompatActivity implements SensorEventListener {

    Button btn_home;
    Button btn_second;
    Button btn_third;
    TextView txtXPg1;
    TextView txtYPg1;
    TextView txtZPg1;

    private SensorManager sensorManager;
    private Sensor accelorometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page01);

        btn_home = findViewById(R.id.btn_pg_1_home);
        btn_second = findViewById(R.id.btn_pg_1_2);
        btn_third = findViewById(R.id.btn_pg_1_3);
        txtXPg1 = findViewById(R.id.txtx_content_pg_1);
        txtYPg1 = findViewById(R.id.txty_content_pg_1);
        txtZPg1 = findViewById(R.id.txtz_content_pg_1);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelorometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this, accelorometer, SensorManager.SENSOR_DELAY_NORMAL);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbhelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Page1Values", null);

        if (cursor.moveToNext()){
            String name = cursor.getString(1);
            String value = cursor.getString(2);
            txtXPg1.setText("Name : " + name + "\nValue : " + value);
        }
        cursor.close();

//        SharedPreferences sharedPreferences = getSharedPreferences("userSettings", MODE_PRIVATE);
//        String msg = sharedPreferences.getString("message", "Shared Preference not found");
//        txtContentPg1.setText(msg);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(Page01.this, MainActivity.class)); }
        });

        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(Page01.this, Page02.class)); }
        });

        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(Page01.this, Page03.class)); }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        txtXPg1.setText("X Axis: " + event.values[0]);
        txtYPg1.setText("Y Axis: " + event.values[1]);
        txtZPg1.setText("Z Axis: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}