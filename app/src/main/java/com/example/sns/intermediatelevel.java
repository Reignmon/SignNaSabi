package com.example.sns;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class intermediatelevel extends AppCompatActivity {

    private ScrollView scrollView;
    LinearLayout Lesson1,Lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8;

    private boolean backPressToExit = false;
    LinearLayout vocubularyBtn,attitudesBtn,characteristicsBtn,qualityBtn,quantityBtn,actworBtn,
            courtshipBtn,loveBtn,marriageBtn,sesnsesBtn,foodBtn,drinksBtn,placeBtn,animalsBtn,insectBtn,natureBtn,transpoBtm;
    ImageView vocubularyimg,attitudesimg,characteristicsimg,qualityimg,quantityimg,actworimg,
            courtshipimg,loveimg,marriageimg,sesnsesimg,foodimg,drinksimg,placeimg,animalsimg,insectimg,natureimg,transpoimg;
    TextView vocubularyScore,attitudesScore,characteristicsScore,qualityScore,quantityScore,actworScore,
            courtshipScore,loveScore,marriageScore,sesnsesScore,foodScore,drinksScore,placeScore,animalsScore,insectScore,natureScore,transpoScore;
    TextView btnBack;

    CardView LESSON2,LESSON3,LESSON4,LESSON5,LESSON6,LESSON7,LESSON8;
    LinearLayout Lesson2Click,Lesson3Click,Lesson4Click,Lesson5Click,Lesson6Click,Lesson7Click,Lesson8Click;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");

    static String name="";

    Dialog Loading;

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

        Loading = new Dialog(intermediatelevel.this);
        Loading.setContentView(R.layout.loading_dialog);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setCancelable(false);

        scrollView = findViewById(R.id.scrollView);

        btnBack = findViewById(R.id.btnback);

        Lesson1 = findViewById(R.id.Lesson1_layout);
        Lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);
        Lesson8 = findViewById(R.id.Lesson8_layout);

        LESSON2 = findViewById(R.id.lesson2);
        LESSON3 = findViewById(R.id.lesson3);
        LESSON4 = findViewById(R.id.lesson4);
        LESSON5 = findViewById(R.id.lesson5);
        LESSON6 = findViewById(R.id.lesson6);
        LESSON7 = findViewById(R.id.lesson7);
        LESSON8 = findViewById(R.id.lesson8);

        Lesson2Click = findViewById(R.id.lesson2Click);
        Lesson3Click = findViewById(R.id.lesson3Click);
        Lesson4Click = findViewById(R.id.lesson4Click);
        Lesson5Click = findViewById(R.id.lesson5Click);
        Lesson6Click = findViewById(R.id.lesson6Click);
        Lesson7Click = findViewById(R.id.lesson7Click);
        Lesson8Click = findViewById(R.id.lesson8Click);

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

        vocubularyScore = findViewById(R.id.vocubularyscore);
        attitudesScore = findViewById(R.id.attitudesscore);
        characteristicsScore = findViewById(R.id.Characteristicsscore);
        qualityScore = findViewById(R.id.Qualityscore);
        quantityScore = findViewById(R.id.Quantityscore);
        actworScore = findViewById(R.id.actwordscore);
        courtshipScore = findViewById(R.id.Courtshipscore);
        loveScore = findViewById(R.id.Lovescore);
        marriageScore = findViewById(R.id.Marriagescore);
        sesnsesScore = findViewById(R.id.sensesscore);
        foodScore = findViewById(R.id.Foodsscore);
        drinksScore = findViewById(R.id.Drinksscore);
        placeScore = findViewById(R.id.Placescore);
        animalsScore = findViewById(R.id.Animalsscore);
        insectScore = findViewById(R.id.Insectsscore);
        natureScore = findViewById(R.id.Naturescore);
        transpoScore = findViewById(R.id.Transportationscore);

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
    }
    public void lesson3_view(View view){
    }
    public void lesson4_view(View view){

    }
    public void lesson5_view(View view){
    }
    public void lesson6_view(View view){
    }
    public void lesson7_view(View view){
    }
    public void lesson8_view(View view){
    }
    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child("sign");

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
        if (lessonAsl >= 1){
            vocubularyimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "vocubularyscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                attitudesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL2attitudes.class));
                        finish();
                    }
                });
                Lesson2Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (Lesson2.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(Lesson2,new AutoTransition());
                        Lesson2.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON2.setAlpha(1.0f);

                // Display the score
                vocubularyScore.setVisibility(View.VISIBLE);
                vocubularyScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                vocubularyScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 2){
            attitudesimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "attitudesscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                characteristicsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL2charac.class));
                        finish();
                    }
                });
                // Display the score
                attitudesScore.setVisibility(View.VISIBLE);
                attitudesScore.setText(alphaScore + "/9");
            } else {
                // Optional: Handle the case where the score is less than 8
                attitudesScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 3){
            characteristicsimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "characteristicsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                qualityBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL2quality.class));
                        finish();
                    }
                });
                // Display the score
                characteristicsScore.setVisibility(View.VISIBLE);
                characteristicsScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                characteristicsScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 4){
            qualityimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "qualityscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                quantityBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, itnerL2quantity.class));
                        finish();
                    }
                });
                // Display the score
                qualityScore.setVisibility(View.VISIBLE);
                qualityScore.setText(alphaScore + "/9");
            } else {
                // Optional: Handle the case where the score is less than 8
                qualityScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 5){
            quantityimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "quantityscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                actworBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL3actionword.class));
                        finish();
                    }
                });

                Lesson3Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (Lesson3.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(Lesson3,new AutoTransition());
                        Lesson3.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON3.setAlpha(1.0f);

                // Display the score
                quantityScore.setVisibility(View.VISIBLE);
                quantityScore.setText(alphaScore + "/8");
            } else {
                // Optional: Handle the case where the score is less than 8
                quantityScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 6){
            actworimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "actwordscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                courtshipBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL4courtship.class));
                        finish();
                    }
                });

                Lesson4Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (Lesson4.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(Lesson4,new AutoTransition());
                        Lesson4.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON4.setAlpha(1.0f);

                // Display the score
                actworScore.setVisibility(View.VISIBLE);
                actworScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                actworScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 7){
            courtshipimg.setVisibility(View.VISIBLE);
            loveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL4love.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 8){
            loveimg.setVisibility(View.VISIBLE);
            marriageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL4marriage.class));
                    finish();
                }
            });
        }
        if (lessonAsl >= 9){
            marriageimg.setVisibility(View.VISIBLE);
            sesnsesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL5senses.class));
                    finish();
                }
            });

            Lesson5Click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int v = (Lesson5.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                    TransitionManager.beginDelayedTransition(Lesson5,new AutoTransition());
                    Lesson5.setVisibility(v);

                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                }
            });
            LESSON5.setAlpha(1.0f);
        }

        if (lessonAsl >= 10){
            sesnsesimg.setVisibility(View.VISIBLE);
            foodBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(intermediatelevel.this, interL6foods.class));
                    finish();
                }
            });

            Lesson6Click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int v = (Lesson6.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                    TransitionManager.beginDelayedTransition(Lesson6,new AutoTransition());
                    Lesson6.setVisibility(v);

                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                }
            });
            LESSON6.setAlpha(1.0f);
        }

        if (lessonAsl >= 11){
            foodimg.setVisibility(View.VISIBLE);
        }

        fetchScore(name, "foodsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                drinksBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL6drinks.class));
                        finish();
                    }
                });


                // Display the score
                foodScore.setVisibility(View.VISIBLE);
                foodScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                foodScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 12){
            drinksimg.setVisibility(View.VISIBLE);
        }

        fetchScore(name, "drinksscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                placeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(intermediatelevel.this, interL7place.class));
                        finish();
                    }
                });

                Lesson7Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (Lesson7.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(Lesson7,new AutoTransition());
                        Lesson7.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON7.setAlpha(1.0f);


                // Display the score
                drinksScore.setVisibility(View.VISIBLE);
                drinksScore.setText(alphaScore + "/9");
            } else {
                drinksScore.setVisibility(View.GONE);
            }
        });



    }

    private void fetchScore(String email, String score, Consumer<Integer> callback) {
        String encodedEmail = encodeEmail(email);
        DatabaseReference usersRef = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child(score);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int alphaScore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                callback.accept(alphaScore); // Pass the fetched score
                Loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors here
            }
        });

    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}