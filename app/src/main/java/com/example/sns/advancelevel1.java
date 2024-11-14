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

import org.w3c.dom.Text;

import java.util.function.Consumer;

public class advancelevel1 extends AppCompatActivity {
    private ScrollView scrollView;
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8,Lesson9;
    LinearLayout biblicaltermBtn,biblicalcharacBtn,evangelistBtn,bibleplaceBtn,celebrationBtn,sacramentsBtn,perosonelBtn,sectorBtn,artsBtn;
    ImageView biblicaltermimg,biblicalcharacimg,evangelistimg,bibleplaceimg,celebrationimg,sacramentsimg,perosonelimg,sectorimg,artsimg;
    private boolean backPressToExit = false;
    TextView btnBack;
    LinearLayout Lesson2Click,Lesson3Click,Lesson4Click,Lesson5Click,Lesson6Click,Lesson7Click,Lesson8Click,Lesson9Click;
    CardView LESSON2,LESSON3,LESSON4,LESSON5,LESSON6,LESSON7,LESSON8,LESSON9;
    TextView biblicaltermScore,biblicalcharacScore,evangelistScore,bibleplaceScore,celebrationScore,sacramentsScore,perosonelScore,sectorScore,artsScore;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    Dialog Loading;
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

        Loading = new Dialog(advancelevel1.this);
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
        Lesson9 = findViewById(R.id.Lesson9_layout);

        LESSON2 = findViewById(R.id.lesson2);
        LESSON3 = findViewById(R.id.lesson3);
        LESSON4 = findViewById(R.id.lesson4);
        LESSON5 = findViewById(R.id.lesson5);
        LESSON6 = findViewById(R.id.lesson6);
        LESSON7 = findViewById(R.id.lesson7);
        LESSON8 = findViewById(R.id.lesson8);
        LESSON9 = findViewById(R.id.lesson9);

        Lesson2Click = findViewById(R.id.lesson2Click);
        Lesson3Click = findViewById(R.id.lesson3Click);
        Lesson4Click = findViewById(R.id.lesson4Click);
        Lesson5Click = findViewById(R.id.lesson5Click);
        Lesson6Click = findViewById(R.id.lesson6Click);
        Lesson7Click = findViewById(R.id.lesson7Click);
        Lesson8Click = findViewById(R.id.lesson8Click);
        Lesson9Click = findViewById(R.id.lesson9Click);

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

        biblicaltermScore = findViewById(R.id.biblicaltermscore);
        biblicalcharacScore = findViewById(R.id.biblicalcharacterscore);
        evangelistScore = findViewById(R.id.evangelistscore);
        bibleplaceScore = findViewById(R.id.bibleplacescore);
        celebrationScore = findViewById(R.id.religiouscelescore);
        sacramentsScore = findViewById(R.id.sacramentsscore);
        perosonelScore = findViewById(R.id.churchpersonelscore);
        sectorScore = findViewById(R.id.sectorscore);
        artsScore = findViewById(R.id.performingartsscore);

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
    public void lesson9_view(View view){
    }

    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("sign");
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
            biblicaltermimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "biblicaltermscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                biblicalcharacBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, advance1L1biblicalcharac.class));
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


                biblicaltermScore.setVisibility(View.VISIBLE);
                biblicaltermScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                biblicaltermScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 2){
            biblicalcharacimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "biblicalcharacterscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                evangelistBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, adavance1L3evangelist.class));
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


                biblicalcharacScore.setVisibility(View.VISIBLE);
                biblicalcharacScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                biblicalcharacScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 3){
            evangelistimg.setVisibility(View.VISIBLE);

            bibleplaceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L4bibleplace.class));
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

        if (lessonAsl >= 4){
            bibleplaceimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "bibleplacescore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                celebrationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, advance1L5celebration.class));
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


                bibleplaceScore.setVisibility(View.VISIBLE);
                bibleplaceScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                bibleplaceScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 5){
            celebrationimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "religiouscelebscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                sacramentsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, advance1L6sacraments.class));
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


                celebrationScore.setVisibility(View.VISIBLE);
                celebrationScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                celebrationScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 6){
            sacramentsimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "sacramentsscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 5) {
                perosonelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, advance1L7personel.class));
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


                sacramentsScore.setVisibility(View.VISIBLE);
                sacramentsScore.setText(alphaScore + "/7");

            } else {
                // Optional: Handle the case where the score is less than 8
                sacramentsScore.setVisibility(View.GONE);
            }
        });


        if (lessonAsl >= 7){
            perosonelimg.setVisibility(View.VISIBLE);
        }
        fetchScore(name, "churchpersonelscore", alphaScore -> {
            // Check the score
            if (alphaScore >= 8) {
                sectorBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(advancelevel1.this, advance1L8sector.class));
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


                perosonelScore.setVisibility(View.VISIBLE);
                perosonelScore.setText(alphaScore + "/10");

            } else {
                // Optional: Handle the case where the score is less than 8
                perosonelScore.setVisibility(View.GONE);
            }
        });

        if (lessonAsl >= 8){
            sectorimg.setVisibility(View.VISIBLE);

            artsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel1.this, advance1L9arts.class));
                    finish();
                }
            });

            Lesson9Click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int v = (Lesson9.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
                    TransitionManager.beginDelayedTransition(Lesson9,new AutoTransition());
                    Lesson9.setVisibility(v);

                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });

                }
            });
            LESSON9.setAlpha(1.0f);
        }

        if (lessonAsl >= 9) {
            artsimg.setVisibility(View.VISIBLE);
        }
    }

    private void fetchScore(String email, String score, Consumer<Integer> callback) {
        Loading.show();
        String encodedEmail = encodeEmail(email);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail).child(score);
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