package com.example.mad1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn_first;
    Button btn_second;
    Button btn_third;
    TextView txt_view;

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

        btn_first = findViewById(R.id.btn_first);
        btn_second = findViewById(R.id.btn_second);
        btn_third = findViewById(R.id.btn_third);
        txt_view = findViewById(R.id.txt_welcome);

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
            txt_view.setText(getString(R.string.btn_txt_page3) + " selected");
            Intent page03 = new Intent(MainActivity.this, Page03.class);
            startActivity(page03);

        } else {
            txt_view.setText("Unknown Page");
        }
    }
}