package com.example.sns;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.erkutaras.showcaseview.ShowcaseManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class dashboard extends AppCompatActivity {

    Dialog dialog;
    ImageButton imagebtn;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText txttrans;
    private boolean backPressToExit = false;
    ProgressBar basiclevelProgress;

    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    static String name="";

    int basiclevelprogress = 0, progressLevel = 0;
    int showCaseNumber = 0;
    Button btnprof,btnTrans,btnpractice,btnhistory;
    int backgroundColor = Color.argb(191, 0, 255, 255);

    CardView intermediateCard,advancelevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        imagebtn = findViewById(R.id.micbtn);
        txttrans = findViewById(R.id.transtxt);
        btnprof = findViewById(R.id.profile);
        final CardView MbasciLevel = findViewById(R.id.basiclevel);
        basiclevelProgress = findViewById(R.id.basiclevel_progress);
        advancelevel = findViewById(R.id.advancelevel);
        btnTrans = findViewById(R.id.translate);
        btnpractice = findViewById(R.id.practice);
        btnhistory= findViewById(R.id.history);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        dialog = new Dialog(dashboard.this);
        dialog.setContentView(R.layout.policie_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        dialog.setCancelable(false);

        retrieveCurrentBasicLevelProgress();

       /* //code for showcase
        showCaseNumber = 1;
        new Handler().postDelayed(() -> {
            ShowcaseManager.Builder builder = new ShowcaseManager.Builder();
            builder.context(this)
                    .key("Key")
                    .developerMode(true)
                    .view(btnTrans)
                    .descriptionTitle("Translator Camera")
                    .descriptionText("This button is use for ASL translor. Just simply point your camera at ASL signs, and instantly see them translated into spoken language.")
                    .buttonText("Done")
                    .buttonVisibility(true)
                    .cancelButtonVisibility(false)
                    .add()
                    .build()
                    .show();
        }, 1000); // 1-second delay
        //end of code for showcase

*/
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        btnprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,profile.class));
                finish();
            }
        });

        MbasciLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,basiclevel.class));
                finish();
            }
        });

        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
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

/*

    private void openShowCase1(){
        showCaseNumber = 2;
        new Handler().postDelayed(() -> {
            ShowcaseManager.Builder builder = new ShowcaseManager.Builder();
            builder.context(this)
                    .key("Key")
                    .developerMode(true)
                    .view(btnpractice)
                    .descriptionTitle("Practice Button")
                    .descriptionText("This button is use for ASL practice. Practice ASL by " +
                            "capturing gestures with your camera, and receive instant translations into " +
                            "spoken language for learning and improvement.")
                    .buttonText("Done")
                    .buttonVisibility(true)
                    .cancelButtonVisibility(false)
                    .add()
                    .build()
                    .show();
        }, 1000); // 1-second delay
    }

    private void openShowCase2(){
        showCaseNumber = 3;
        new Handler().postDelayed(() -> {
            ShowcaseManager.Builder builder = new ShowcaseManager.Builder();
            builder.context(this)
                    .key("Key")
                    .developerMode(true)
                    .view(btnhistory)
                    .descriptionTitle("History Button")
                    .descriptionText("This button use to see the last progress that you take in e-learning by this you can view your progress over time.")
                    .buttonText("Done")
                    .buttonVisibility(true)
                    .cancelButtonVisibility(false)
                    .add()
                    .build()
                    .show();
        }, 1000); // 1-second delay
    }

    private void openShowCase3(){
        showCaseNumber = 4;
        new Handler().postDelayed(() -> {
            ShowcaseManager.Builder builder = new ShowcaseManager.Builder();
            builder.context(this)
                    .key("Key")
                    .developerMode(true)
                    .view(btnprof)
                    .descriptionTitle("Profile Button")
                    .descriptionText("This button show you and manage your personal profile information, including user details and preferences")
                    .buttonText("Done")
                    .buttonVisibility(true)
                    .cancelButtonVisibility(false)
                    .add()
                    .build()
                    .show();
        }, 1000); // 1-second delay
    }
*/


    //code for mic
    private void speak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // getting data data from the voice audio
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txttrans.setText(result.get(0));
                }
                break;
            }
        }
        // end of code here

       /* // check if the showcase was done and proceed to another showcase
        if (requestCode == ShowcaseManager.REQUEST_CODE_SHOWCASE && resultCode == Activity.RESULT_OK && showCaseNumber == 1) {
            showCaseNumber = 2;
            openShowCase1();
        } else if (requestCode == ShowcaseManager.REQUEST_CODE_SHOWCASE && resultCode == Activity.RESULT_OK && showCaseNumber == 2) {
            showCaseNumber = 3;
            openShowCase2();
        }else if (requestCode == ShowcaseManager.REQUEST_CODE_SHOWCASE && resultCode == Activity.RESULT_OK && showCaseNumber == 3) {
            showCaseNumber = 4;
            openShowCase3();
        }
        // end of code here*/
    }
    //end of code for mic

    private void retrieveCurrentBasicLevelProgress() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("Elearning_tb").child(encodedEmail).child("Basic_L1");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    basiclevelprogress = snapshot.getValue(Integer.class);
                    basiclevelProgress.setProgress(basiclevelprogress);
                    progressLevel = basiclevelProgress.getProgress();

                    if(progressLevel == 25){
                        advancelevel.setVisibility(View.VISIBLE);
                        advancelevel.setEnabled(true);
                    }
                } else {
                    basiclevelProgress.setProgress(0);
                    advancelevel.setVisibility(View.INVISIBLE);
                    advancelevel.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });
    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }


}