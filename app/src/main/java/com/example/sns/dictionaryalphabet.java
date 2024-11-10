package com.example.sns;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class dictionaryalphabet extends AppCompatActivity {
    LinearLayout letterA,letterB,letterC,letterD,letterE,letterF,letterG,letterH,letterI,letterJ,letterK,letterL,letterN,
    letterM,letterO,letterP,letterQ,letterR,letterS,letterT,letterU,letterV,letterW,letterX,letterY,letterZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionaryalphabet);
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

        letterA = findViewById(R.id.lettera);
        letterB = findViewById(R.id.letterb);
        letterC = findViewById(R.id.letterc);
        letterD = findViewById(R.id.letterd);
        letterE = findViewById(R.id.lettere);
        letterF = findViewById(R.id.letterf);
        letterG = findViewById(R.id.letterg);
        letterH = findViewById(R.id.letterh);
        letterI = findViewById(R.id.letteri);
        letterJ = findViewById(R.id.letterj);
        letterK = findViewById(R.id.letterk);
        letterL = findViewById(R.id.letterl);
        letterN = findViewById(R.id.letterm);
        letterM = findViewById(R.id.lettern);
        letterO = findViewById(R.id.lettero);
        letterP = findViewById(R.id.letterp);
        letterQ = findViewById(R.id.letterq);
        letterR = findViewById(R.id.letterr);
        letterS = findViewById(R.id.letters);
        letterT = findViewById(R.id.lettert);
        letterU = findViewById(R.id.letteru);
        letterV = findViewById(R.id.letterv);
        letterW = findViewById(R.id.letterw);
        letterX = findViewById(R.id.letterx);
        letterY = findViewById(R.id.lettery);
        letterZ = findViewById(R.id.letterz);

        setLetterClickListener(letterA, 0);
        setLetterClickListener(letterB, 1);
        setLetterClickListener(letterC, 2);
        setLetterClickListener(letterD, 3);
        setLetterClickListener(letterE, 4);
        setLetterClickListener(letterF, 5);
        setLetterClickListener(letterG, 6);
        setLetterClickListener(letterH, 7);
        setLetterClickListener(letterI, 8);
        setLetterClickListener(letterJ, 9);
        setLetterClickListener(letterK, 10);
        setLetterClickListener(letterL, 11);
        setLetterClickListener(letterM, 12);
        setLetterClickListener(letterN, 13);
        setLetterClickListener(letterO, 14);
        setLetterClickListener(letterP, 15);
        setLetterClickListener(letterQ, 16);
        setLetterClickListener(letterR, 17);
        setLetterClickListener(letterS, 18);
        setLetterClickListener(letterT, 19);
        setLetterClickListener(letterU, 20);
        setLetterClickListener(letterV, 21);
        setLetterClickListener(letterW, 22);
        setLetterClickListener(letterX, 23);
        setLetterClickListener(letterY, 24);
        setLetterClickListener(letterZ, 25);
    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionaryalphabet.this, dictionaryalphabetvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}