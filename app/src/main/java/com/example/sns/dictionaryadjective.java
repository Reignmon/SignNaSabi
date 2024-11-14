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

public class dictionaryadjective extends AppCompatActivity {
    LinearLayout bright, clean, close, dim, dirty, far, heavy, high, light, low, normal, pretty, ugly;
    TextView btnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionaryadjective);
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

        bright = findViewById(R.id.bright);
        clean = findViewById(R.id.clean);
        close = findViewById(R.id.close);
        dim = findViewById(R.id.dim);
        dirty = findViewById(R.id.dirty);
        far = findViewById(R.id.far);
        heavy = findViewById(R.id.heavy);
        high = findViewById(R.id.high);
        light = findViewById(R.id.light);
        low = findViewById(R.id.low);
        normal = findViewById(R.id.normal);
        pretty = findViewById(R.id.prettt);
        ugly = findViewById(R.id.ugly);

        btnack = findViewById(R.id.btnback);

        setLetterClickListener(bright,0);
        setLetterClickListener(clean,1);
        setLetterClickListener(close,2);
        setLetterClickListener(dim,3);
        setLetterClickListener(dirty,4);
        setLetterClickListener(far,5);
        setLetterClickListener(heavy,6);
        setLetterClickListener(high,7);
        setLetterClickListener(light,8);
        setLetterClickListener(low,9);
        setLetterClickListener(normal,10);
        setLetterClickListener(pretty,11);
        setLetterClickListener(ugly,12);

        btnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionaryadjective.this,dictionary.class));
                finish();
            }
        });

    }

    private void setLetterClickListener(LinearLayout letterButton, int alphabetValue) {
        letterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dictionaryadjective.this, dictionaryadjectivevid.class);
                i.putExtra("alphabet", alphabetValue);
                startActivity(i);
                finish();
            }
        });
    }
}