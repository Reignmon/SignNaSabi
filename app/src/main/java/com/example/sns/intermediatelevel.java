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

public class intermediatelevel extends AppCompatActivity {
    LinearLayout Lesson1,Lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8;

    private boolean backPressToExit = false;
    LinearLayout vocubularyBtn,attitudesBtn,characteristicsBtn,qualityBtn,quantityBtn,actworBtn,
            courtshipBtn,loveBtn,marriageBtn,sesnsesBtn,foodBtn,drinksBtn,placeBtn,animalsBtn,insectBtn,natureBtn,transpoBtm;
    ImageView vocubularyimg,attitudesimg,characteristicsimg,qualityimg,quantityimg,actworimg,
            courtshipimg,loveimg,marriageimg,sesnsesimg,foodimg,drinksimg,placeimg,animalsimg,insectimg,natureimg,transpoimg;
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
        setContentView(R.layout.activity_intermediatelevel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnback);

        Lesson1 = findViewById(R.id.Lesson1_layout);
        Lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);
        Lesson8 = findViewById(R.id.Lesson8_layout);

        vocubularyBtn = findViewById(R.id.vocubularybtn);
        attitudesBtn = findViewById(R.id.attitudesbtn);
        characteristicsBtn = findViewById(R.id.Characteristicsbtn);
        qualityBtn = findViewById(R.id.Qualitybtn);
        quantityBtn = findViewById(R.id.Quantitybtn);
        actworBtn = findViewById(R.id.actwordbtn);
        courtshipBtn = findViewById(R.id.Courtshipbtn);
        loveBtn = findViewById(R.id.Lovebtn);
        marriageBtn = findViewById(R.id.Marriagebtn);
        sesnsesBtn = findViewById(R.id.sensesbtn);
        foodBtn = findViewById(R.id.Foodsbtn);
        drinksBtn = findViewById(R.id.Drinksbtn);
        placeBtn = findViewById(R.id.Placebtn);
        animalsBtn = findViewById(R.id.Animalsbtn);
        insectBtn = findViewById(R.id.Insectsbtn);
        natureBtn = findViewById(R.id.Naturebtn);
        transpoBtm = findViewById(R.id.Transportationbtn);

        vocubularyimg = findViewById(R.id.vocubularyimg);
        attitudesimg = findViewById(R.id.attitudesimg);
        characteristicsimg = findViewById(R.id.Characteristicsimg);
        qualityimg = findViewById(R.id.Qualityimg);
        quantityimg = findViewById(R.id.Quantityimg);
        actworimg = findViewById(R.id.actwordimg);
        courtshipimg = findViewById(R.id.Courtshipimg);
        loveimg = findViewById(R.id.Loveimg);
        marriageimg = findViewById(R.id.Marriageimg);
        sesnsesimg = findViewById(R.id.sensesimg);
        foodimg = findViewById(R.id.Foodsimg);
        drinksimg = findViewById(R.id.Drinksimg);
        placeimg = findViewById(R.id.Placeimg);
        animalsimg = findViewById(R.id.Animalsimg);
        insectimg = findViewById(R.id.Insectsimg);
        natureimg = findViewById(R.id.Natureimg);
        transpoimg = findViewById(R.id.Transportationimg);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        retrieveLessonASL();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(intermediatelevel.this,dashboard.class));
                finish();
            }
        });

        vocubularyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(intermediatelevel.this, interL1vocubulary.class));
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
        int v = (Lesson2.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(Lesson2,new AutoTransition());
        Lesson2.setVisibility(v);
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
    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child("intermediatelesson");

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
            vocubularyimg.setVisibility(View.VISIBLE);
            attitudesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL2attitudes.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 200){
            attitudesimg.setVisibility(View.VISIBLE);
            characteristicsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL2charac.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 300){
            characteristicsimg.setVisibility(View.VISIBLE);
            qualityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL2quality.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 400){
            qualityimg.setVisibility(View.VISIBLE);
            quantityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, itnerL2quantity.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 500){
            quantityimg.setVisibility(View.VISIBLE);
            actworBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL3actionword.class));
                    finish();
                }
            });

        }

        if (lessonAsl >= 600){
            actworimg.setVisibility(View.VISIBLE);
            courtshipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL4courtship.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 700){
            courtshipimg.setVisibility(View.VISIBLE);
            loveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL4love.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 800){
            loveimg.setVisibility(View.VISIBLE);
            marriageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL4marriage.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 900){
            marriageimg.setVisibility(View.VISIBLE);
            sesnsesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL5senses.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1000){
            sesnsesimg.setVisibility(View.VISIBLE);
            foodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL6foods.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1100){
            foodimg.setVisibility(View.VISIBLE);
            drinksBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL6drinks.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1200){
            drinksimg.setVisibility(View.VISIBLE);
            placeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL7place.class));
                    finish();
                }
            });
        }

        if (lessonAsl >= 1300){
            placeimg.setVisibility(View.VISIBLE);
            animalsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL8animals.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1400){
            animalsimg.setVisibility(View.VISIBLE);
            insectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL8insects.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1500){
            insectimg.setVisibility(View.VISIBLE);
            natureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL8nature.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1600){
            natureimg.setVisibility(View.VISIBLE);
            transpoBtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL8transpo.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 1700){
            transpoimg.setVisibility(View.VISIBLE);
        }

    }

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}