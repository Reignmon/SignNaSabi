package com.example.sns;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class basiclevel extends AppCompatActivity {

    private ScrollView scrollView;
    private boolean backPressToExit = false;
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8;
    LinearLayout alphabetbtn,numbersbtn,greetingBtn,qeustionBtn,colorsBtn,daysBtn,monthBtn,sizeBtn,
            familyBtn,peopleBtn,relationshipBtn,actionBtn,adjectiveBtn,emotionBtn,mentalactBtn
            ,directionBtn,componentsBtn,foodsdrinkBtn,clothesbtn,bodypartBtn;
    TextView btnBack;
    ImageView l1_img,imgNum,imgGreet,quesstionImg,colorsImg,daysImg,monthImg,sizeImg,
            familyImg,peopleImg,relationshipImg,actionImg,adjectiveImg,emotionImg,mentalactImg
            ,directionImg,componentsImg,foodsdrinkImg,clothesImg,bodypartImg;
    TextView alphabetScore,numberScore,greetingScore,questionScore,colorsScore,weekScore,monthScore,familyScore,peopleScore
            ,relationScore,actionScore,adjectiveScore,emotionScore,mentalScore,directionScore,componentsScore,fooddrinkScore,
            clothesScore,partsbodyScore;
    CardView LESSON2,LESSON3,LESSON4,LESSON5,LESSON6,LESSON7,LESSON8;
    LinearLayout Lesson2Click,Lesson3Click,Lesson4Click,Lesson5Click,Lesson6Click,Lesson7Click,Lesson8Click;
    int lesson1,alphaScore = 0,numScore =  0;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    static String name="";
    Dialog Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basiclevel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Loading = new Dialog(basiclevel.this);
        Loading.setContentView(R.layout.loading_dialog);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setCancelable(false);

        scrollView = findViewById(R.id.scrollView);

        Lesson1 = findViewById(R.id.Lesson1_layout);
        lesson2 = findViewById(R.id.Lesson2_layout);
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

        alphabetScore = findViewById(R.id.alphabetscore);
        numberScore = findViewById(R.id.numberscore);
        greetingScore = findViewById(R.id.greetingsscore);
        questionScore = findViewById(R.id.questionscore);
        colorsScore = findViewById(R.id.colorscore);
        weekScore = findViewById(R.id.weekscore);
        monthScore = findViewById(R.id.monthscore);
        familyScore = findViewById(R.id.familyscore);
        peopleScore = findViewById(R.id.peoplescore);
        relationScore = findViewById(R.id.relationscore);
        actionScore = findViewById(R.id.actionwordscore);
        adjectiveScore = findViewById(R.id.adjectivescore);
        emotionScore = findViewById(R.id.emtionscore);
        mentalScore = findViewById(R.id.mentalscore);
        directionScore = findViewById(R.id.directionscore);
        componentsScore = findViewById(R.id.componentsscore);
        fooddrinkScore = findViewById(R.id.foodsdrinkscore);
        clothesScore = findViewById(R.id.clothesscore);
        partsbodyScore = findViewById(R.id.partsofbodyscore);


        l1_img = findViewById(R.id.lesson1_image);
        imgNum = findViewById(R.id.imgnum);
        imgGreet = findViewById(R.id.greetingimg);
        quesstionImg = findViewById(R.id.quetionimg);
        colorsImg = findViewById(R.id.colorimg);
        daysImg = findViewById(R.id.daysofwimg);
        monthImg = findViewById(R.id.montsimg);
        sizeImg = findViewById(R.id.sizeimg);
        familyImg = findViewById(R.id.familyimg);
        peopleImg = findViewById(R.id.peopleimg);
        relationshipImg = findViewById(R.id.relationshipimg);
        actionImg = findViewById(R.id.actwordimg);
        adjectiveImg = findViewById(R.id.adjectivesimg);
        emotionImg = findViewById(R.id.emotionimg);
        mentalactImg = findViewById(R.id.mentalactimg);
        directionImg = findViewById(R.id.placeloctbtn);
        componentsImg = findViewById(R.id.partsthingsimg);
        foodsdrinkImg = findViewById(R.id.foodsdrinkimg);
        clothesImg = findViewById(R.id.clothesimg);
        bodypartImg = findViewById(R.id.bodypartimg);

        alphabetbtn = findViewById(R.id.alphabet);
        btnBack = findViewById(R.id.btnback);
        numbersbtn = findViewById(R.id.numbers);
        greetingBtn = findViewById(R.id.greetingbtn);
        qeustionBtn = findViewById(R.id.questionbtn);
        colorsBtn = findViewById(R.id.colorsbtn);
        daysBtn = findViewById(R.id.daysow);
        monthBtn = findViewById(R.id.monthsbtn);
        sizeBtn = findViewById(R.id.sizebtn);
        familyBtn = findViewById(R.id.familybtn);
        peopleBtn = findViewById(R.id.peoplebtn);
        relationshipBtn = findViewById(R.id.relatioshipbtn);
        actionBtn = findViewById(R.id.actwordbtn);
        adjectiveBtn = findViewById(R.id.adjectivebtn);
        emotionBtn = findViewById(R.id.emotionbtn);
        mentalactBtn = findViewById(R.id.mentalactbtn);
        directionBtn = findViewById(R.id.Placeloctbtn);
        componentsBtn = findViewById(R.id.partsthingsbtn);
        foodsdrinkBtn = findViewById(R.id.foodsdrinksbtn);
        clothesbtn = findViewById(R.id.clothesbtn);
        bodypartBtn = findViewById(R.id.bodypartbtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        retrieveLessonASL();



        alphabetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,Lesson1.class));
                finish();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,dashboard.class));
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
    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("sign");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonAsl = snapshot.getValue(Integer.class);
                    handleVisibility(lessonAsl);
                }else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }

    private void handleVisibility(int lessonAsl) {

        // Visibility settings based on lessonAsl value
        if (lessonAsl >= 1){
            l1_img.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "alphabetscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                numbersbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, B1numbers.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name, alphabetScore, "alphabetscore");
                alphabetScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                alphabetScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 2){
            imgNum.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "numberscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                greetingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b2greetings.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name, numberScore, "numberscore");
                numberScore.setVisibility(View.VISIBLE);

                Lesson2Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (lesson2.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(lesson2,new AutoTransition());
                        lesson2.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON2.setAlpha(1.0f);

            } else {
                // Optional: Handle the case where the score is less than 8
                numberScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });

        if (lessonAsl >= 3){
            imgGreet.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "greetingsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                qeustionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b2questionword.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name, greetingScore, "greetingsscore");
                greetingScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                greetingScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 4){
            quesstionImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "quetionwordscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 5) {
                // Set up button click listener if score condition is met
                colorsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b3colors.class));
                        finish();
                    }
                });
                // Display the score
                fetchAndDisplayScore(name, questionScore, "quetionwordscore");
                questionScore.setVisibility(View.VISIBLE);

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

            } else {
                // Optional: Handle the case where the score is less than 8
                questionScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 5){
            colorsImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "colorsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                daysBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b3daysofweek.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name, colorsScore, "colorsscore");
                colorsScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                colorsScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 6){
            daysImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "daysofweekscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 5) {
                // Set up button click listener if score condition is met
                monthBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b3mothoftheyear.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name, weekScore, "daysofweekscore");
                weekScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                weekScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 7){
            monthImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "monthscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                sizeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b3size.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,monthScore , "monthscore");
                monthScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                monthScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 8){
            sizeImg.setVisibility(View.VISIBLE);

            familyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(basiclevel.this, b4family.class));
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
        }
        if (lessonAsl >= 9){
            familyImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "familyscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                peopleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b4people.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,familyScore , "familyscore");
                familyScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                familyScore.setVisibility(View.GONE); // Hide or adjust visibility
            }
        });
        if (lessonAsl >= 10){
            peopleImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "peoplescore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                relationshipBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b4relationship.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,peopleScore , "peoplescore");
                peopleScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                peopleScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 11){
            relationshipImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "relationshipscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                actionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b5actionword.class));
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

                // Display the score
                fetchAndDisplayScore(name,relationScore , "relationshipscore");
                relationScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                relationScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 12){
            actionImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "actionwordscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                adjectiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b4adjective.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,actionScore , "actionwordscore");
                actionScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                actionScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 13){
            adjectiveImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "adjectivescore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                emotionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b6emotion.class));
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

                // Display the score
                fetchAndDisplayScore(name,adjectiveScore , "adjectivescore");
                adjectiveScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                adjectiveScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 14){
            emotionImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "emotionsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                mentalactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b6mentalact.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,emotionScore , "emotionsscore");
                emotionScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                emotionScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 15){
            mentalactImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "mentalactionscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                directionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b7placelocation.class));
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
                fetchAndDisplayScore(name,mentalScore , "mentalactionscore");
                mentalScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                mentalScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 16){
            directionImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "directionscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                componentsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b7areas.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,directionScore , "directionscore");
                directionScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                directionScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 17){
            componentsImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "areasscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                foodsdrinkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b7foodsdrinks.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,componentsScore, "areasscore");
                componentsScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                componentsScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 18){
            foodsdrinkImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "refreshmentscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                clothesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b8clothehygiene.class));
                        finish();
                    }
                });

                Lesson8Click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int v = (Lesson8.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                        TransitionManager.beginDelayedTransition(Lesson8,new AutoTransition());
                        Lesson8.setVisibility(v);

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });

                    }
                });
                LESSON8.setAlpha(1.0f);

                // Display the score
                fetchAndDisplayScore(name,fooddrinkScore , "refreshmentscore");
                fooddrinkScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                fooddrinkScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 19){
            clothesImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "clothesscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                // Set up button click listener if score condition is met
                bodypartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(basiclevel.this, b8bodypart.class));
                        finish();
                    }
                });

                // Display the score
                fetchAndDisplayScore(name,clothesScore, "clothesscore");
                clothesScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                clothesScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 20){
            bodypartImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "bodypartsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                fetchAndDisplayScore(name,partsbodyScore, "bodypartsscore");
                partsbodyScore.setVisibility(View.VISIBLE);

            } else {
                // Optional: Handle the case where the score is less than 8
                partsbodyScore.setVisibility(View.GONE);
            }
        });

    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

    private void fetchAndDisplayScore(String email, TextView scoreTextView, String score) {
        Loading.show();
        String encodedEmail = encodeEmail(email);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child(score);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int alphaScore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                if (alphaScore <= 7){
                    scoreTextView.setText(alphaScore + "/7");
                    Loading.dismiss();
                } else{
                    scoreTextView.setText(alphaScore + "/10");
                    Loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors here
            }
        });
    }

    private void fetchScore(String email, String score, Consumer<Integer> callback) {
        String encodedEmail = encodeEmail(email);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child(score);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int alphaScore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                callback.accept(alphaScore); // Pass the fetched score
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors here
            }
        });
    }

}