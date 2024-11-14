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

public class dictionarycolors extends AppCompatActivity {
    LinearLayout balck,brown,blue,gold,gray,green,orange,pink,purple,red,white,yellow;
    TextView btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionarycolors);
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

        balck = findViewById(R.id.black);
        brown = findViewById(R.id.brown);
        blue = findViewById(R.id.blue);
        gold = findViewById(R.id.gold);
        gray = findViewById(R.id.gray);
        green = findViewById(R.id.green);
        orange = findViewById(R.id.orange);
        purple = findViewById(R.id.purple);
        pink = findViewById(R.id.pink);
        red = findViewById(R.id.red);
        white = findViewById(R.id.white);
        yellow = findViewById(R.id.yellow);

        btnback = findViewById(R.id.btnback);

        setLetterClickListener(balck,0);
        setLetterClickListener(brown,1);
        setLetterClickListener(blue,2);
        setLetterClickListener(gold,3);
        setLetterClickListener(gray,4);
        setLetterClickListener(green,5);
        setLetterClickListener(orange,6);
        setLetterClickListener(pink,7);
        setLetterClickListener(purple,8);
        setLetterClickListener(red,9);
        setLetterClickListener(white,10);
        setLetterClickListener(yellow,11);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionarycolors.this,dictionary.class));
                finish();
            }
        });

    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionarycolors.this, dictionarycolorsvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}