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

public class advancelevel extends AppCompatActivity {
    private ScrollView scrollView;
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7;
    LinearLayout schoolperBtn,schoolTermbtn,subjectBtn,idustriesBtn,measureBtn,computertermBtn,sportsBtn,countriesBtn,nationBtn,idiomBtn;
    ImageView schoolpersonelImg,schoolTermimg,subjectimg,idustriesimg,measureimg,computertermimg,sportsimg,countriesimg,nationimg,idiomimg;
    private boolean backPressToExit = false;
    TextView btnBack;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");

    static String name="";
    CardView LESSON2,LESSON3,LESSON4,LESSON5,LESSON6,LESSON7;
    LinearLayout Lesson2Click,Lesson3Click,Lesson4Click,Lesson5Click,Lesson6Click,Lesson7Click;
    TextView schoolpersonelScore,schoolTermScore,subjectScore,idustriesScore,measureScore,computertermScore,sportsScore
            ,countriesScore,nationScore,idiomScore;
    Dialog Loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advancelevel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Loading = new Dialog(advancelevel.this);
        Loading.setContentView(R.layout.loading_dialog);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setCancelable(false);

        scrollView = findViewById(R.id.scrollView);


        btnBack = findViewById(R.id.btnback);

        Lesson1 = findViewById(R.id.Lesson1_layout);
        lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);

        LESSON2 = findViewById(R.id.lesson2);
        LESSON3 = findViewById(R.id.lesson3);
        LESSON4 = findViewById(R.id.lesson4);
        LESSON5 = findViewById(R.id.lesson5);
        LESSON6 = findViewById(R.id.lesson6);
        LESSON7 = findViewById(R.id.lesson7);

        Lesson2Click = findViewById(R.id.lesson2Click);
        Lesson3Click = findViewById(R.id.lesson3Click);
        Lesson4Click = findViewById(R.id.lesson4Click);
        Lesson5Click = findViewById(R.id.lesson5Click);
        Lesson6Click = findViewById(R.id.lesson6Click);
        Lesson7Click = findViewById(R.id.lesson7Click);

        schoolperBtn = findViewById(R.id.schoolpersonelbtn);
        schoolTermbtn = findViewById(R.id.schooltermbtn);
        subjectBtn = findViewById(R.id.subjectbtn);
        idustriesBtn = findViewById(R.id.tradeindusbtn);
        measureBtn = findViewById(R.id.measurebtn);
        computertermBtn = findViewById(R.id.compurtertermbtn);
        sportsBtn = findViewById(R.id.sportsbtn);
        countriesBtn = findViewById(R.id.countriesbtn);
        nationBtn = findViewById(R.id.nationalitiesbtn);
        idiomBtn = findViewById(R.id.idiomsbtn);

        schoolpersonelScore = findViewById(R.id.schoolpersonelscore);
        schoolTermScore = findViewById(R.id.schooltermscore);
        subjectScore = findViewById(R.id.subjectscore);
        idustriesScore = findViewById(R.id.tradeindusscore);
        measureScore = findViewById(R.id.measurescore);
        computertermScore = findViewById(R.id.compurtertermscore);
        sportsScore = findViewById(R.id.sportsscore);
        countriesScore = findViewById(R.id.countriesscore);
        nationScore = findViewById(R.id.nationalitiesscore);
        idiomScore = findViewById(R.id.idiomsscore);

        idiomimg = findViewById(R.id.idiomsimg);
        nationimg = findViewById(R.id.nationalitiesimg);
        countriesimg = findViewById(R.id.countriesimg);
        computertermimg = findViewById(R.id.compurtertermimg);
        schoolTermimg = findViewById(R.id.schooltermimg);
        schoolpersonelImg = findViewById(R.id.schoolpersonelimg);
        subjectimg = findViewById(R.id.subjectimg);
        idustriesimg = findViewById(R.id.tradeindusimg);
        measureimg = findViewById(R.id.measureimg);
        sportsimg = findViewById(R.id.sportsimg);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        retrieveLessonASL();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(advancelevel.this, dashboard.class));
                finish();
            }
        });
        schoolperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(advancelevel.this, advanceL1personel.class));
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



    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel_tb").child(encodedEmail).child("sign");
        Loading.show();
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonAsl = snapshot.getValue(Integer.class);
                    handleVisibility(lessonAsl);
                    Loading.dismiss();
                }else{
                    Loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });
    }

    private void handleVisibility(int lessonAsl) {
        Loading.show();
        if (lessonAsl >= 1){
            schoolpersonelImg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "personelscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                schoolTermbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL1schoolterm.class));
                        finish();
                    }
                });

                schoolpersonelScore.setVisibility(View.VISIBLE);
                schoolpersonelScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                schoolpersonelScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 2){
            schoolTermimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "schooltermscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 7) {
                subjectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL1subject.class));
                        finish();
                    }
                });

                // Display the score
                schoolTermScore.setVisibility(View.VISIBLE);
                schoolTermScore.setText(alphaScore + "/9");
            } else {
                // Optional: Handle the case where the score is less than 8
                schoolTermScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 3){
            subjectimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "subjectscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 5) {
                idustriesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL2industries.class));
                        finish();
                    }
                });

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

                // Display the score
                subjectScore.setVisibility(View.VISIBLE);
                subjectScore.setText(alphaScore + "/7");
            } else {
                // Optional: Handle the case where the score is less than 8
                subjectScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 4){
            idustriesimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "industriesscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 5) {
                measureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL3measure.class));
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
                idustriesScore.setVisibility(View.VISIBLE);
                idustriesScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                idustriesScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 5){
            measureimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "measurescore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                computertermBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL4computer.class));
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
                measureScore.setVisibility(View.VISIBLE);
                measureScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                measureScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 6){
            computertermimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "computerscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                sportsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL5sports.class));
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
                computertermScore.setVisibility(View.VISIBLE);
                computertermScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                computertermScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 7){
            sportsimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "sportsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                countriesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL6countries.class));
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
                sportsScore.setVisibility(View.VISIBLE);
                sportsScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                sportsScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 8){
            countriesimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "countriesscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                nationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL6nation.class));
                        finish();
                    }
                });
                // Display the score
                countriesScore.setVisibility(View.VISIBLE);
                countriesScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                countriesScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 9){
            nationimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "nationscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                idiomBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel.this, advanceL7idiom.class));
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
                nationScore.setVisibility(View.VISIBLE);
                nationScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                nationScore.setVisibility(View.GONE);
            }
        });
        if (lessonAsl >= 10){
            idiomimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "idiomscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                idiomScore.setVisibility(View.VISIBLE);
                idiomScore.setText(alphaScore + "/10");
            } else {
                // Optional: Handle the case where the score is less than 8
                idiomScore.setVisibility(View.GONE);
            }
        });
    }

    private void fetchScore(String email, String score, Consumer<Integer> callback) {
        String encodedEmail = encodeEmail(email);
        DatabaseReference usersRef = databaseReference.child("advancelevel_tb").child(encodedEmail).child(score);
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