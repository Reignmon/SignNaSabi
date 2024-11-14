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

public class dictionarygreeting extends AppCompatActivity {
    LinearLayout goodbye,goodafter,goodmor,goodnig,happnew,happybirt,imglad,imsorry,merrychris,seeyou,fine;
    TextView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionarygreeting2);
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

        goodbye = findViewById(R.id.goodbye);
        goodafter = findViewById(R.id.goodafternoon);
        goodmor = findViewById(R.id.goodmorning);
        goodnig = findViewById(R.id.goodnight);
        happnew = findViewById(R.id.happynewyear);
        happybirt = findViewById(R.id.happybirth);
        imglad = findViewById(R.id.imglad);
        imsorry = findViewById(R.id.sorry);
        merrychris = findViewById(R.id.merry);
        seeyou = findViewById(R.id.seeyah);
        fine = findViewById(R.id.imfine);

        btnback = findViewById(R.id.btnback);

        setLetterClickListener(goodbye,0);
        setLetterClickListener(goodafter,1);
        setLetterClickListener(goodmor,2);
        setLetterClickListener(goodnig,3);
        setLetterClickListener(happnew,4);
        setLetterClickListener(happybirt,5);
        setLetterClickListener(imglad,6);
        setLetterClickListener(fine,7);
        setLetterClickListener(imsorry,8);
        setLetterClickListener(merrychris,9);
        setLetterClickListener(seeyou,10);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionarygreeting.this,dictionary.class));
                finish();
            }
        });


    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionarygreeting.this, dictionarygreetingsvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}