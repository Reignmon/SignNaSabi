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

public class dictionarycommunity extends AppCompatActivity {

    LinearLayout actor, athlete, auntie, brother, brotherinlaw, chief, cousin, daughter, doctor,
            engineer, entrepreneur, farmer, father, grandfather, grandmother, lawyer, mother, nephew, nurse, pilot,
            policeofficer, principal, scientist, shoulder, sister, sisterinlaw, son, stepbrother, stepmother, stepsister,
            teacher, uncle,Neice;

    TextView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionarycommunity);
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

        actor = findViewById(R.id.Actor);
        athlete = findViewById(R.id.athlete);
        auntie = findViewById(R.id.auntie);
        brother = findViewById(R.id.brother);
        brotherinlaw = findViewById(R.id.brotherinlaw);
        chief = findViewById(R.id.cheif);
        cousin = findViewById(R.id.cousin);
        daughter = findViewById(R.id.daughter);
        doctor = findViewById(R.id.doctor);
        engineer = findViewById(R.id.engineer);
        entrepreneur = findViewById(R.id.entrep);
        farmer = findViewById(R.id.Farmer);
        father = findViewById(R.id.Father);
        grandfather = findViewById(R.id.Grandfather);
        grandmother = findViewById(R.id.Grandmother);
        lawyer = findViewById(R.id.Lawyer);
        mother = findViewById(R.id.Mother);
        nephew = findViewById(R.id.Nephew);
        Neice = findViewById(R.id.Neice);
        nurse = findViewById(R.id.Nurse);
        pilot = findViewById(R.id.Pilot);
        policeofficer = findViewById(R.id.Policeofficer);
        principal = findViewById(R.id.Principal);
        scientist = findViewById(R.id.Scientist);
        shoulder = findViewById(R.id.Shoulder);
        sister = findViewById(R.id.Sister);
        sisterinlaw = findViewById(R.id.Sisterinlaw);
        son = findViewById(R.id.Son);
        stepbrother = findViewById(R.id.Stepbrother);
        stepmother = findViewById(R.id.Stepmother);
        stepsister = findViewById(R.id.Stepsister);
        teacher = findViewById(R.id.Teacher);
        uncle = findViewById(R.id.Uncle);

        btnback = findViewById(R.id.btnback);


        setLetterClickListener(actor, 0);
        setLetterClickListener(athlete, 1);
        setLetterClickListener(auntie, 2);
        setLetterClickListener(brother, 3);
        setLetterClickListener(brotherinlaw, 4);
        setLetterClickListener(chief, 5);
        setLetterClickListener(cousin, 6);
        setLetterClickListener(daughter, 7);
        setLetterClickListener(doctor, 8);
        setLetterClickListener(engineer, 9);
        setLetterClickListener(entrepreneur, 10);
        setLetterClickListener(farmer, 11);
        setLetterClickListener(father, 12);
        setLetterClickListener(grandfather, 13);
        setLetterClickListener(grandmother, 14);
        setLetterClickListener(lawyer, 15);
        setLetterClickListener(mother, 16);
        setLetterClickListener(nephew, 17);
        setLetterClickListener(Neice, 18);
        setLetterClickListener(nurse, 19);
        setLetterClickListener(pilot, 20);
        setLetterClickListener(policeofficer, 21);
        setLetterClickListener(principal, 22);
        setLetterClickListener(scientist, 23);
        setLetterClickListener(shoulder, 24);
        setLetterClickListener(sister, 25);
        setLetterClickListener(sisterinlaw, 26);
        setLetterClickListener(son, 27);
        setLetterClickListener(stepbrother, 28);
        setLetterClickListener(stepmother, 29);
        setLetterClickListener(stepsister, 30);
        setLetterClickListener(teacher, 31);
        setLetterClickListener(uncle, 32);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionarycommunity.this,dictionary.class));
                finish();
            }
        });


    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionarycommunity.this, dictionarycommunityvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}