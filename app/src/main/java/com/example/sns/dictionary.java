package com.example.sns;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dictionary extends AppCompatActivity {
    CardView alphabetBtn,numberBtn,greetingBtn,questionBtn,colorsBtn,daysmonthBtn,sizeBtn,communityBtn,actionBtn,adjectiveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alphabetBtn = findViewById(R.id.alphabet);
        numberBtn = findViewById(R.id.number);
        greetingBtn = findViewById(R.id.greeting);
        questionBtn = findViewById(R.id.question);
        colorsBtn = findViewById(R.id.colorbtn);
        daysmonthBtn = findViewById(R.id.daysmonth);
        sizeBtn = findViewById(R.id.size);
        communityBtn = findViewById(R.id.community);
        actionBtn = findViewById(R.id.action);
        adjectiveBtn = findViewById(R.id.adjective);

        alphabetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(alphabetBtn);
                startActivity(new Intent(dictionary.this,dictionaryalphabet.class));
                finish();
            }
        });

        numberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(numberBtn);
                startActivity(new Intent(dictionary.this,dictionarynumber.class));
                finish();
            }
        });

        greetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(greetingBtn);
                startActivity(new Intent(dictionary.this,dictionarygreeting.class));
                finish();
            }
        });

        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(questionBtn);
                startActivity(new Intent(dictionary.this,dictionaryquestion.class));
                finish();
            }
        });

        colorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(colorsBtn);
                startActivity(new Intent(dictionary.this,dictionarycolors.class));
                finish();
            }
        });

        daysmonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(daysmonthBtn);
                startActivity(new Intent(dictionary.this,dictionarydaysmonth.class));
                finish();
            }
        });
    }

    private void scaleView(View view) {
        // Create ObjectAnimators to animate the scaleX and scaleY properties
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f); // Scale up X
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f); // Scale up Y
        scaleUpX.setDuration(50);
        scaleUpY.setDuration(50);


        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f); // Scale down X
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f); // Scale down Y
        scaleDownX.setDuration(50);
        scaleDownY.setDuration(50);

        scaleUpX.start();
        scaleUpY.start();

        scaleUpX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleDownX.start();
                scaleDownY.start();
            }
        });
    }
}