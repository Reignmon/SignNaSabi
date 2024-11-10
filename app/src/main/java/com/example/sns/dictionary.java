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
    CardView alphabetBtn;

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

        alphabetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleView(alphabetBtn);
                startActivity(new Intent(dictionary.this,dictionaryalphabet.class));
                finish();
            }
        });
    }

    private void scaleView(View view) {
        // Create ObjectAnimators to animate the scaleX and scaleY properties
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f); // Scale up X
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f); // Scale up Y
        scaleUpX.setDuration(100);
        scaleUpY.setDuration(100);


        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f); // Scale down X
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f); // Scale down Y
        scaleDownX.setDuration(100);
        scaleDownY.setDuration(100);

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