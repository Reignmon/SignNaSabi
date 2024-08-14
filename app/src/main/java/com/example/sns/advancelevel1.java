package com.example.sns;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class advancelevel1 extends AppCompatActivity {
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8,Lesson9;
    LinearLayout biblicaltermBtn,biblicalcharacBtn,evangelistBtn,bibleplaceBtn,celebrationBtn,sacramentsBtn,perosonelBtn,sectorBtn,artsBtn;
    ImageView biblicaltermimg,biblicalcharacimg,evangelistimg,bibleplaceimg,celebrationimg,sacramentsimg,perosonelimg,sectorimg,artsimg;
    private boolean backPressToExit = false;
    TextView btnBack;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");

    static String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advancelevel1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Lesson1 = findViewById(R.id.Lesson1_layout);
        lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);
        Lesson8 = findViewById(R.id.Lesson8_layout);
        Lesson9 = findViewById(R.id.Lesson9_layout);

        biblicaltermBtn = findViewById(R.id.biblicaltermbtn);
        biblicalcharacBtn = findViewById(R.id.biblicalcharacterbtn);
        evangelistBtn = findViewById(R.id.evangelistbtn);
        bibleplaceBtn = findViewById(R.id.bibleplacebtn);
        celebrationBtn = findViewById(R.id.religiouscelebbtn);
        sacramentsBtn = findViewById(R.id.sacramentsbtn);
        perosonelBtn = findViewById(R.id.churchpersonelbtn);
        sectorBtn = findViewById(R.id.sectorbtn);
        artsBtn = findViewById(R.id.performingartsbtn);

        biblicaltermimg = findViewById(R.id.biblicaltermimg);
        biblicalcharacimg = findViewById(R.id.biblicalcharacterimg);
        evangelistimg = findViewById(R.id.evangelistimg);
        bibleplaceimg = findViewById(R.id.bibleplaceimg);
        celebrationimg = findViewById(R.id.religiousceleimg);
        sacramentsimg = findViewById(R.id.sacramentsimg);
        perosonelimg = findViewById(R.id.churchpersonelimg);
        sectorimg = findViewById(R.id.sectorimg);
        artsimg = findViewById(R.id.performingartsimg);

        btnBack = findViewById(R.id.btnback);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);
        retrieveLessonASL();

        biblicaltermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(advancelevel1.this,advance1L1biblicalterm.class));
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(advancelevel1.this,dashboard.class));
                finish();
            }
        });
    }

    //code for backpress
    @Override
    public void onBackPressed() {
        if (backPressToExit) {
            super.onBackPressed();
            return;
        }

        // Show confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backPressToExit = true;
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        dialog.show();

        // Set a timer to automatically dismiss the dialog after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
//end of code for backpress
    public void lesson1_view(View view){
        int v = (Lesson1.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson1,new AutoTransition());
        Lesson1.setVisibility(v);
    }
    public void lesson2_view(View view){
        int v = (lesson2.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(lesson2,new AutoTransition());
        lesson2.setVisibility(v);
    }
    public void lesson3_view(View view){
        int v = (Lesson3.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson3,new AutoTransition());
        Lesson3.setVisibility(v);
    }
    public void lesson4_view(View view){
        int v = (Lesson4.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson4,new AutoTransition());
        Lesson4.setVisibility(v);
    }

    public void lesson5_view(View view){
        int v = (Lesson5.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson5,new AutoTransition());
        Lesson5.setVisibility(v);
    }
    public void lesson6_view(View view){
        int v = (Lesson6.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson6,new AutoTransition());
        Lesson6.setVisibility(v);
    }
    public void lesson7_view(View view){
        int v = (Lesson7.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson7,new AutoTransition());
        Lesson7.setVisibility(v);
    }
    public void lesson8_view(View view){
        int v = (Lesson8.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson8,new AutoTransition());
        Lesson8.setVisibility(v);
    }
    public void lesson9_view(View view){
        int v = (Lesson9.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson9,new AutoTransition());
        Lesson9.setVisibility(v);
    }

    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("advancelesson1");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonAsl = snapshot.getValue(Integer.class);
                    handleVisibility(lessonAsl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }
    private void handleVisibility(int lessonAsl) {
        if (lessonAsl >= 100){
            biblicaltermimg.setVisibility(View.VISIBLE);
            biblicalcharacBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L1biblicalcharac.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 200){
            biblicalcharacimg.setVisibility(View.VISIBLE);
            evangelistBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, adavance1L3evangelist.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 300){
            evangelistimg.setVisibility(View.VISIBLE);
            bibleplaceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L4bibleplace.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 400){
            bibleplaceimg.setVisibility(View.VISIBLE);
            celebrationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L5celebration.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 500){
            celebrationimg.setVisibility(View.VISIBLE);
            sacramentsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L6sacraments.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 600){
            sacramentsimg.setVisibility(View.VISIBLE);
            perosonelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L7personel.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 700){
            perosonelimg.setVisibility(View.VISIBLE);
            sectorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L8sector.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 800){
            sectorimg.setVisibility(View.VISIBLE);
            artsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L9arts.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 900){
            artsimg.setVisibility(View.VISIBLE);
        }
    }

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}