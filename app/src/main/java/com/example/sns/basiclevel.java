package com.example.sns;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class basiclevel extends AppCompatActivity {
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
    int lesson1;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    static String name="";

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
        Lesson1 = findViewById(R.id.Lesson1_layout);
        lesson2 = findViewById(R.id.Lesson2_layout);
        Lesson3 = findViewById(R.id.Lesson3_layout);
        Lesson4 = findViewById(R.id.Lesson4_layout);
        Lesson5 = findViewById(R.id.Lesson5_layout);
        Lesson6 = findViewById(R.id.Lesson6_layout);
        Lesson7 = findViewById(R.id.Lesson7_layout);
        Lesson8 = findViewById(R.id.Lesson8_layout);

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

        //retrieveCurrentBasicLevelProgress();
        retrieveLessonASL();

        bodypartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b8bodypart.class));
                finish();
            }
        });

        clothesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b8clothehygiene.class));
                finish();
            }
        });

        alphabetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,Lesson1.class));
                finish();
            }
        });

        numbersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this, B1numbers.class));
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

        greetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b2greetings.class));
                finish();
            }
        });
        qeustionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b2questionword.class));
                finish();
            }
        });

        colorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this, b3colors.class));
                finish();
            }
        });

        daysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this, b3daysofweek.class));
                finish();
            }
        });

        monthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this, b3mothoftheyear.class));
                finish();
            }
        });

        sizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this, b3size.class));
                finish();
            }
        });

        familyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b4family.class));
                finish();
            }
        });

        peopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b4people.class));
                finish();
            }
        });

        relationshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b4relationship.class));
                finish();
            }
        });

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b5actionword.class));
                finish();
            }
        });

        adjectiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b4adjective.class));
                finish();
            }
        });

        emotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b6emotion.class));
                finish();
            }
        });

        mentalactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b6mentalact.class));
                finish();
            }
        });

        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b7placelocation.class));
                finish();
            }
        });

        componentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b7areas.class));
                finish();
            }
        });
        foodsdrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(basiclevel.this,b7foodsdrinks.class));
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
    /*private void retrieveCurrentBasicLevelProgress() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        // Check and update for "Alphabet"
        usersRef.child("Alphabet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 25) {
                        l1_img.setVisibility(View.VISIBLE);
                        DatabaseReference lessonaslRef = usersRef.child("lessonasl");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lesson1 == 25 && currentLessonAslValue < 100) {
                                    lessonaslRef.setValue(100);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors
                            }
                        });
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });

        // Check and update for "numbers"
        usersRef.child("numbers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);

                    if (lesson1 == 9) {
                        imgNum.setVisibility(View.VISIBLE);
                        DatabaseReference lessonaslRef = usersRef.child("lessonasl");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lesson1 == 9 && currentLessonAslValue < 200) {
                                    lessonaslRef.setValue(200);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors
                            }
                        });
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });

    }*/

/*

    private void retrieveCurrentBasicLevelProgress() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        // Update lesson progress
        updateLessonProgress(usersRef.child("Alphabet"), 25, 100, l1_img);
        updateLessonProgress(usersRef.child("numbers"), 9, 200, imgNum);
        updateLessonProgress(usersRef.child("greetings"), 23, 300, imgGreet);
        updateLessonProgress(usersRef.child("quetionword"), 7, 400, imgGreet);
    }

    private void updateLessonProgress(DatabaseReference lessonRef, int targetValue, int lessonAslValue, ImageView imageView) {
        lessonRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonValue = snapshot.getValue(Integer.class);
                    if (lessonValue == targetValue) {
                        imageView.setVisibility(View.VISIBLE);
                        DatabaseReference lessonaslRef = lessonRef.getParent().child("lessonasl");

                        // Check current lessonasl value before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lessonAslValue > currentLessonAslValue) {
                                    lessonaslRef.setValue(lessonAslValue);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors
                            }
                        });
                    } else {
                        imageView.setVisibility(View.INVISIBLE); // Ensure image is hidden if not matching target
                    }
                } else {
                    imageView.setVisibility(View.INVISIBLE); // Ensure image is hidden if snapshot doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });
    }
*/
/*
    public void retrieveLessonASL(){
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("lessonasl");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int lessonAsl = snapshot.getValue(Integer.class);
                    switch (lessonAsl){
                        case 100:
                            l1_img.setVisibility(View.VISIBLE);
                            break;
                        case 200:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            break;
                        case 300:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            break;
                        case 400:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            break;
                        case 500:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            colorsImg.setVisibility(View.VISIBLE);
                            break;
                        case 600:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            colorsImg.setVisibility(View.VISIBLE);
                            daysImg.setVisibility(View.VISIBLE);
                            break;
                        case 700:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            colorsImg.setVisibility(View.VISIBLE);
                            daysImg.setVisibility(View.VISIBLE);
                            monthImg.setVisibility(View.VISIBLE);
                            break;
                        case 800:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            colorsImg.setVisibility(View.VISIBLE);
                            daysImg.setVisibility(View.VISIBLE);
                            monthImg.setVisibility(View.VISIBLE);
                            sizeImg.setVisibility(View.VISIBLE);
                            break;
                        case 900:
                            l1_img.setVisibility(View.VISIBLE);
                            imgNum.setVisibility(View.VISIBLE);
                            imgGreet.setVisibility(View.VISIBLE);
                            quesstionImg.setVisibility(View.VISIBLE);
                            colorsImg.setVisibility(View.VISIBLE);
                            daysImg.setVisibility(View.VISIBLE);
                            monthImg.setVisibility(View.VISIBLE);
                            sizeImg.setVisibility(View.VISIBLE);
                            familyImg.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


    public void retrieveLessonASL() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("lessonasl");

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
        // Visibility settings based on lessonAsl value
        if (lessonAsl >= 100) l1_img.setVisibility(View.VISIBLE);
        if (lessonAsl >= 200) imgNum.setVisibility(View.VISIBLE);
        if (lessonAsl >= 300) imgGreet.setVisibility(View.VISIBLE);
        if (lessonAsl >= 400) quesstionImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 500) colorsImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 600) daysImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 700) monthImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 800) sizeImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 900) familyImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1000) peopleImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1100) relationshipImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1200) actionImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1300) adjectiveImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1400) emotionImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1500) mentalactImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1600) directionImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1700) componentsImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1800) foodsdrinkImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 1900) clothesImg.setVisibility(View.VISIBLE);
        if (lessonAsl >= 2000) bodypartImg.setVisibility(View.VISIBLE);
    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}