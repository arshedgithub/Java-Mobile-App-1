package com.example.mad1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Page02 extends AppCompatActivity {

    Button btn_home;
    Button btn_second;
    Button btn_third;
    TextView output_txt;

    String operation = null;
    Integer num1 = null;
    Integer num2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page02);

        btn_home = findViewById(R.id.btn_pg_2_home);
        btn_second = findViewById(R.id.btn_pg_2_2);
        btn_third = findViewById(R.id.btn_pg_2_3);
        output_txt = findViewById(R.id.output_txt);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Page02.this, MainActivity.class)); }
        });

        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Page02.this, Page01.class)); }
        });

        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(new Intent(Page02.this, Page03.class)); }
        });
    }

    public void actionHandler(View view){ operation = (String) view.getTag(); }

    public void numberHandler(View view){
        String num_str = (String) view.getTag();
        if (num1 == null) num1 = Integer.parseInt(num_str);
        else num2 = Integer.parseInt(num_str);
    }

    public void calculate(View view){
        switch (operation){
            case "add":
                output_txt.setText("+");
                break;
            case "sub":
                output_txt.setText("-");
                break;
            case "mul":
                output_txt.setText("*");
                break;
            case "div":
                output_txt.setText("/");
                break;
        }
    }

}