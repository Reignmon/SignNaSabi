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

public class dictionaryquestion extends AppCompatActivity {
    LinearLayout how,howhave,pardon,what,name,where,going,bathroom,when,who,whom,why;
    TextView btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionaryquestion);
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

        how = findViewById(R.id.how);
        howhave = findViewById(R.id.howhaveyoubeen);
        pardon = findViewById(R.id.pardon);
        what = findViewById(R.id.what);
        name = findViewById(R.id.whatyourname);
        where = findViewById(R.id.where);
        going = findViewById(R.id.whereareyou);
        bathroom = findViewById(R.id.wheresthe);
        when = findViewById(R.id.when);
        who = findViewById(R.id.who);
        whom = findViewById(R.id.whom);
        why = findViewById(R.id.why);

        btnback = findViewById(R.id.btnback);

        setLetterClickListener(how,0);
        setLetterClickListener(howhave,1);
        setLetterClickListener(pardon,2);
        setLetterClickListener(what,3);
        setLetterClickListener(name,4);
        setLetterClickListener(where,5);
        setLetterClickListener(going,6);
        setLetterClickListener(bathroom,7);
        setLetterClickListener(when,8);
        setLetterClickListener(who,9);
        setLetterClickListener(whom,10);
        setLetterClickListener(whom,11);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionaryquestion.this, dictionary.class));
                finish();
            }
        });

    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionaryquestion.this, dictionaryquestionvid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}