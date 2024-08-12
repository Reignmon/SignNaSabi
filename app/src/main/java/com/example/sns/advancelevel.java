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

public class advancelevel extends AppCompatActivity {
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4,Lesson5,Lesson6,Lesson7,Lesson8;
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
        btnBack = findViewById(R.id.btnback);

        Lesson1 = findViewById(R.id.Lesson1_layout);
        lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);

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



    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel_tb").child(encodedEmail).child("advancelesson");

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
            schoolTermbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL1schoolterm.class));
                    finish();
                }
            });
            schoolpersonelImg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 200){
            subjectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL1subject.class));
                    finish();
                }
            });
            schoolTermimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 300){
            idustriesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL2industries.class));
                    finish();
                }
            });
            subjectimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 400){
            measureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL3measure.class));
                    finish();
                }
            });
            idustriesimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 500){
            computertermBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL4computer.class));
                    finish();
                }
            });
            measureimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 600){
            sportsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL5sports.class));
                    finish();
                }
            });
            computertermimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 700){
            countriesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL6countries.class));
                    finish();
                }
            });
            sportsimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 800){
            nationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL6nation.class));
                    finish();
                }
            });
            countriesimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 900){
            idiomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(advancelevel.this, advanceL7idiom.class));
                    finish();
                }
            });
            nationimg.setVisibility(View.VISIBLE);
        }
        if (lessonAsl >= 1000){
            idiomimg.setVisibility(View.VISIBLE);
        }
    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }


}