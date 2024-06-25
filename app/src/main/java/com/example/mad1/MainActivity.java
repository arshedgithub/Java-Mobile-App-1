package com.example.mad1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn_first;
    Button btn_second;
    Button btn_third;
    TextView txt_view;
    Boolean autoBrightnessChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        createNotificationChannel();
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_first = findViewById(R.id.btn_first);
        btn_second = findViewById(R.id.btn_second);
        btn_third = findViewById(R.id.btn_third);
        txt_view = findViewById(R.id.txt_welcome);

        DatabaseHelper dbhelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", "Arshed Ahmed");
        values.put("value", "arshed@24");
        database.insert("Page1Values", null, values);

        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { pageSelection(btn_first); }
        });

        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { pageSelection(btn_second); }
        });

        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { pageSelection(btn_third); }
        });
    }

    private void pageSelection(Button btn){
        if (btn.getId() == btn_first.getId()){
            txt_view.setText(getString(R.string.btn_txt_page1) + " selected");

            SharedPreferences sharedPreferences = getSharedPreferences("userSettings", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("message", "Message from Main Activity (shared preferences)");
            editor.apply();

            Intent page01 = new Intent(MainActivity.this, Page01.class);
            page01.putExtra("message", "Hello from MainActivity !");
            startActivity(page01);

        } else if (btn.getId() == btn_second.getId()){
            txt_view.setText(getString(R.string.btn_txt_page2) + " selected");
            Intent page02= new Intent(MainActivity.this, Page02.class);
            startActivity(page02);

        } else if (btn.getId() == btn_third.getId()){
//            showStartDialog();
            showBrightnessDialog();
        } else {
            txt_view.setText("Unknown Page");
        }
    }

    private void showStartDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game!");
        builder.setMessage("Do you want to start the game?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startGame(){
        txt_view.setText("Game selected");
        Intent page03 = new Intent(MainActivity.this, Page03.class);
        Toast.makeText(MainActivity.this, "Game Loading...", Toast.LENGTH_SHORT).show();
        startActivity(page03);
    }

    private void showBrightnessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Brightness");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        builder.setView(dialogView);

        CheckBox auto_bright_checkbox = dialogView.findViewById(R.id.checkbox_auto_bright);
        SeekBar brihgtness_seekbar = dialogView.findViewById(R.id.seekbar_brightness);

        auto_bright_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                brihgtness_seekbar.setEnabled(!isChecked);
                autoBrightnessChecked = isChecked;
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int brightnessValue = brihgtness_seekbar.getProgress();
                Toast.makeText(MainActivity.this, "Brightness set to: " + (autoBrightnessChecked ? "Automatic" : brightnessValue), Toast.LENGTH_LONG).show();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Test Notification";
            String description = "Test Notification Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("Channel_id", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Test Notifincation");
        builder.setContentText("This is a Test Notifincation");
        builder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());
    }
}