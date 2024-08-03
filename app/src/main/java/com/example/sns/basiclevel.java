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
    LinearLayout Lesson1,lesson2,Lesson3,Lesson4;
    LinearLayout alphabetbtn,numbersbtn,greetingBtn,qeustionBtn,colorsBtn,daysBtn,monthBtn,sizeBtn,
            familyBtn,peopleBtn,relationshipBtn;
    TextView btnBack;
    ImageView l1_img,imgNum,imgGreet;
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

        l1_img = findViewById(R.id.lesson1_image);
        imgNum = findViewById(R.id.imgnum);
        imgGreet = findViewById(R.id.greetingimg);

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

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        //retrieveCurrentBasicLevelProgress();



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



    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}