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

public class dictionarydaysmonth extends AppCompatActivity {
    LinearLayout april,august, december ,febuary ,friday ,january ,jully ,june ,may ,march ,monday ,november ,october ,saturday
            ,september ,sunday ,thursday ,tuesday ,wednesday;
    TextView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionarydaysmonth);
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

        april = findViewById(R.id.april);
        august = findViewById(R.id.august);
        december = findViewById(R.id.december);
        febuary = findViewById(R.id.febuary);
        friday = findViewById(R.id.friday) ;
        january = findViewById(R.id.january);
        jully = findViewById(R.id.jully);
        june = findViewById(R.id.june);
        may = findViewById(R.id.main);
        march = findViewById(R.id.march);
        monday = findViewById(R.id.monday);
        november = findViewById(R.id.november);
        october = findViewById(R.id.october);
        saturday = findViewById(R.id.saturday);
        september = findViewById(R.id.september);
        sunday = findViewById(R.id.sunday);
        thursday = findViewById(R.id.thursday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);

        btnback = findViewById(R.id.btnback);

        setLetterClickListener(april,0);
        setLetterClickListener(august,1);
        setLetterClickListener(december,2);
        setLetterClickListener(febuary,3);
        setLetterClickListener(friday,4);
        setLetterClickListener(january,5);
        setLetterClickListener(jully,6);
        setLetterClickListener(june,7);
        setLetterClickListener(may,8);
        setLetterClickListener(march,9);
        setLetterClickListener(monday,10);
        setLetterClickListener(november,11);
        setLetterClickListener(october,12);
        setLetterClickListener(saturday,13);
        setLetterClickListener(september,14);
        setLetterClickListener(sunday,15);
        setLetterClickListener(thursday,16);
        setLetterClickListener(tuesday,17);
        setLetterClickListener(wednesday,18);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionarydaysmonth.this,dictionary.class));
                finish();
            }
        });

    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionarydaysmonth.this, dictionarymonthdayvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}