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

public class dictionaryactionword extends AppCompatActivity {

    LinearLayout Act, Bow, Buy, Clean, Cook, Cry, Cut, Dance, Dig, Drink, Draw, Dream, Eat, Fight, Fly, Laugh, Listen, Play,
            Push, Read, Ride, Ridein, Rideon, Sing, Sitdown, Sleep, Smile, Study, Talk, Think, Wait, Wash, Write;
    TextView btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionaryactionword);
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

        Act = findViewById(R.id.Act);
        Bow = findViewById(R.id.Bow);
        Buy = findViewById(R.id.Buy);
        Clean = findViewById(R.id.Clean);
        Cook = findViewById(R.id.Cook);
        Cry = findViewById(R.id.Cry);
        Cut = findViewById(R.id.Cut);
        Dance = findViewById(R.id.Dance);
        Dig = findViewById(R.id.Dig);
        Drink = findViewById(R.id.Drink);
        Draw = findViewById(R.id.Draw);
        Dream = findViewById(R.id.Dream);
        Eat = findViewById(R.id.Eat);
        Fight = findViewById(R.id.Fight);
        Fly = findViewById(R.id.Fly);
        Laugh = findViewById(R.id.Laugh);
        Listen = findViewById(R.id.Listen);
        Play = findViewById(R.id.Play);
        Push = findViewById(R.id.Push);
        Read = findViewById(R.id.Read);
        Ride = findViewById(R.id.Ride);
        Ridein = findViewById(R.id.Ridein);
        Rideon = findViewById(R.id.Rideon);
        Sing = findViewById(R.id.Sing);
        Sitdown = findViewById(R.id.Sitdown);
        Sleep = findViewById(R.id.Sleep);
        Smile = findViewById(R.id.Smile);
        Study = findViewById(R.id.Study);
        Talk = findViewById(R.id.Talk);
        Think = findViewById(R.id.Think);
        Wait = findViewById(R.id.Wait);
        Wash = findViewById(R.id.Wash);
        Write = findViewById(R.id.Write);

        btnback = findViewById(R.id.btnback);

        setLetterClickListener(Act, 0);
        setLetterClickListener(Bow, 1);
        setLetterClickListener(Buy, 2);
        setLetterClickListener(Clean, 3);
        setLetterClickListener(Cook, 4);
        setLetterClickListener(Cry, 5);
        setLetterClickListener(Cut, 6);
        setLetterClickListener(Dance, 7);
        setLetterClickListener(Dig, 8);
        setLetterClickListener(Drink, 9);
        setLetterClickListener(Draw, 10);
        setLetterClickListener(Dream, 11);
        setLetterClickListener(Eat, 12);
        setLetterClickListener(Fight, 13);
        setLetterClickListener(Fly, 14);
        setLetterClickListener(Laugh, 15);
        setLetterClickListener(Listen, 16);
        setLetterClickListener(Play, 17);
        setLetterClickListener(Push, 18);
        setLetterClickListener(Read, 19);
        setLetterClickListener(Ride, 20);
        setLetterClickListener(Ridein, 21);
        setLetterClickListener(Rideon, 22);
        setLetterClickListener(Sing, 23);
        setLetterClickListener(Sitdown, 24);
        setLetterClickListener(Sleep, 25);
        setLetterClickListener(Smile, 26);
        setLetterClickListener(Study, 27);
        setLetterClickListener(Talk, 28);
        setLetterClickListener(Think, 29);
        setLetterClickListener(Wait, 30);
        setLetterClickListener(Wash, 31);
        setLetterClickListener(Write, 32);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionaryactionword.this,dictionary.class));
                finish();
            }
        });
    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionaryactionword.this, dictionaryactionvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}