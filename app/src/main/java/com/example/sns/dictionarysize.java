package com.example.sns;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dictionarysize extends AppCompatActivity {

    TextView btnback;
    LinearLayout centi, degree, gallon, inch, large, liter, medium, meter, milimeter, mileperhour, ounce, pound, small, square, weight, yard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionarysize);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        btnback = findViewById(R.id.btnback);

        centi = findViewById(R.id.centimeter);
        degree = findViewById(R.id.degree);
        gallon = findViewById(R.id.gallon);
        inch = findViewById(R.id.inch);
        large = findViewById(R.id.large);
        liter = findViewById(R.id.liter);
        medium = findViewById(R.id.medium);
        meter = findViewById(R.id.Meter);
        milimeter = findViewById(R.id.milimeter);
        mileperhour = findViewById(R.id.mph);
        ounce = findViewById(R.id.ounce);
        pound = findViewById(R.id.pound);
        small = findViewById(R.id.small);
        square = findViewById(R.id.Square);
        weight = findViewById(R.id.weight);
        yard = findViewById(R.id.yard);




        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionarysize.this,dictionary.class));
                finish();
            }
        });

        setLetterClickListener(centi, 0);
        setLetterClickListener(degree, 1);
        setLetterClickListener(gallon, 2);
        setLetterClickListener(inch, 3);
        setLetterClickListener(large, 4);
        setLetterClickListener(liter, 5);
        setLetterClickListener(medium, 6);
        setLetterClickListener(meter, 7);
        setLetterClickListener(milimeter, 8);
        setLetterClickListener(mileperhour, 9);
        setLetterClickListener(ounce, 10);
        setLetterClickListener(pound, 11);
        setLetterClickListener(small, 12);
        setLetterClickListener(square, 13);
        setLetterClickListener(weight, 14);
        setLetterClickListener(yard, 15);
    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionarysize.this, dictionarysizevid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}