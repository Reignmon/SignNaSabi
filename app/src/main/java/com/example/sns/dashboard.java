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
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import android.widget.TextView;
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
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

public class dashboard extends AppCompatActivity {

    Dialog dialog,warning;
    ImageButton imagebtn;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText txttrans;
    private boolean backPressToExit = false;
    ProgressBar basiclevelProgress,advanceProgress,interProgressbar,advanceLevel1progress;

    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "YourPrefs";
    private static final String KEY_TUTORIAL_SHOWN = "tutorial_shown";
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    static String name="";

    int basiclevelprogress = 0, progressLevel = 0,advancelevelprog = 0,intermediateprog = 0, progressLevel1 = 0;
    int showCaseNumber = 0;
    Button btnprof,btnTrans,btnpractice,btnhistory;
    int backgroundColor = Color.argb(191, 0, 255, 255);

    CardView advancelevel,advanceLevel1,intermediateLevel;
    TextView Points,Sign,Complete;
    int pointsTotal = 0,signTotal = 0;
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
        advanceProgress = findViewById(R.id.advanceprogress);
        advancelevel = findViewById(R.id.advancelevel);
        advanceLevel1 = findViewById(R.id.advancelevel1);
        btnTrans = findViewById(R.id.translate);
        btnpractice = findViewById(R.id.practice);
        btnhistory= findViewById(R.id.history);
        intermediateLevel = findViewById(R.id.intermediatelevel);
        interProgressbar = findViewById(R.id.interprogress);
        advanceLevel1progress = findViewById(R.id.advanceprogress1);
        Points = findViewById(R.id.points);
        Sign = findViewById(R.id.sign);
        Complete = findViewById(R.id.complete);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        dialog = new Dialog(dashboard.this);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        warning = new Dialog(dashboard.this);
        warning.setContentView(R.layout.notif_practice);
        warning.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        warning.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        warning.setCancelable(false);

        final TextView txt = dialog.findViewById(R.id.txt);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isTutorialShown = prefs.getBoolean(KEY_TUTORIAL_SHOWN, false);

        if (!isTutorialShown) {
            showTutorial();
            prefs.edit().putBoolean(KEY_TUTORIAL_SHOWN, true).apply();
        }


        retrieveCurrentBasicLevelProgress();
        retrieveadvacelessson();
        retrieveadvacelessson1();
        retrieveintermediate();
        totalPoints();
        totalSign();

        dialog.show();

        intermediateLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this, intermediatelevel.class));
                finish();
            }
        });

        advanceLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,advancelevel1.class));
                finish();
            }
        });
        advancelevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,advancelevel.class));
                finish();
            }
        });
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

            }
        });

        btnpractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this, dictionary.class));
                finish();
            }
        });



    }

    public void showTutorial(){
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(btnTrans, getString(R.string.button_trans_title), getString(R.string.button_trans_des))
                                .cancelable(false)
                                .outerCircleColor(R.color.text_color)
                                .outerCircleAlpha(0.80f)
                                .titleTextSize(25)
                                .descriptionTextSize(16)
                                .textTypeface(Typeface.SANS_SERIF)
                                .targetCircleColor(R.color.policie_color)
                                .titleTextColor(R.color.colorPrimaryDark)
                                .descriptionTextColor(R.color.black)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .targetRadius(70)
                                .tintTarget(true),
                        TapTarget.forView(btnpractice, getString(R.string.button_practice_title), getString(R.string.button_practice_des))
                                .cancelable(false)
                                .outerCircleColor(R.color.text_color)
                                .outerCircleAlpha(0.80f)
                                .titleTextSize(25)
                                .descriptionTextSize(16)
                                .textTypeface(Typeface.SANS_SERIF)
                                .targetCircleColor(R.color.policie_color)
                                .titleTextColor(R.color.colorPrimaryDark)
                                .descriptionTextColor(R.color.black)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .targetRadius(70)
                                .tintTarget(true),
                        TapTarget.forView(btnhistory, getString(R.string.button_dcitionary_title), getString(R.string.button_dictionary_des))
                                .cancelable(false)
                                .outerCircleColor(R.color.text_color)
                                .outerCircleAlpha(0.80f)
                                .titleTextSize(25)
                                .descriptionTextSize(16)
                                .textTypeface(Typeface.SANS_SERIF)
                                .targetCircleColor(R.color.policie_color)
                                .titleTextColor(R.color.colorPrimaryDark)
                                .descriptionTextColor(R.color.black)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .targetRadius(70)
                                .tintTarget(true),
                        TapTarget.forView(btnprof, getString(R.string.button_profile_title), getString(R.string.button_button_profile_title_des))
                                .cancelable(false)
                                .outerCircleColor(R.color.text_color)
                                .outerCircleAlpha(0.80f)
                                .titleTextSize(25)
                                .descriptionTextSize(16)
                                .textTypeface(Typeface.SANS_SERIF)
                                .targetCircleColor(R.color.policie_color)
                                .titleTextColor(R.color.colorPrimaryDark)
                                .descriptionTextColor(R.color.black)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .targetRadius(70)
                                .tintTarget(true)
                )
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        // Handle sequence finish if needed
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        // Handle sequence steps if needed
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Handle cancellation if needed
                    }
                })
                .start();
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
    }
    //end of code for mic

    private void retrieveCurrentBasicLevelProgress() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
        DatabaseReference lessonasl = usersRef.child("lessonasl");
        DatabaseReference sign = usersRef.child("bodypartsscore");
        lessonasl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dialog.dismiss();
                    basiclevelprogress = snapshot.getValue(Integer.class);

                    basiclevelProgress.setProgress(basiclevelprogress);
                    progressLevel = basiclevelProgress.getProgress();

                    sign.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int sign = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                            if (sign >=8){
                                advancelevel.setVisibility(View.VISIBLE);
                                advancelevel.setEnabled(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (basiclevelprogress == 0){
                    basiclevelProgress.setProgress(0);
                    advancelevel.setVisibility(View.INVISIBLE);
                    advancelevel.setEnabled(false);
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });
    }

    private void retrieveadvacelessson() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference Advancelevel = databaseReference.child("advancelevel_tb").child(encodedEmail);
        DatabaseReference advancelesson = Advancelevel.child("advancelesson");
        DatabaseReference idiomScore = Advancelevel.child("idiomscore");
        advancelesson.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    advancelevelprog = snapshot.getValue(Integer.class);
                    advanceProgress.setProgress(advancelevelprog);
                    dialog.dismiss();
                    idiomScore.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                int sign = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                                if (sign >= 8){
                                    advanceLevel1.setVisibility(View.VISIBLE);
                                    advanceLevel1.setEnabled(true);
                                }else{
                                    advanceLevel1.setVisibility(View.INVISIBLE);
                                    advanceLevel1.setEnabled(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    advanceProgress.setProgress(0);
                    advanceLevel1.setVisibility(View.INVISIBLE);
                    advanceLevel1.setEnabled(false);
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveadvacelessson1() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference Advancelevel = databaseReference.child("advancelevel1_tb").child(encodedEmail);
        DatabaseReference advanceLevel = Advancelevel.child("advancelesson1");
        DatabaseReference sign = Advancelevel.child("sign");
        advanceLevel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    advancelevelprog = snapshot.getValue(Integer.class);
                    advanceLevel1progress.setProgress(advancelevelprog);
                    dialog.dismiss();
                    sign.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                int getvalue = snapshot.getValue(Integer.class);

                                if (getvalue == 9){
                                    intermediateLevel.setVisibility(View.VISIBLE);
                                    intermediateLevel.setEnabled(true);
                                }else{
                                    advanceProgress.setProgress(0);
                                    intermediateLevel.setVisibility(View.INVISIBLE);
                                    intermediateLevel.setEnabled(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retrieveintermediate() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference Advancelevel = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child("intermediatelesson");
        Advancelevel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    dialog.dismiss();
                    intermediateprog = snapshot.getValue(Integer.class);
                    interProgressbar.setProgress(intermediateprog);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void totalPoints() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference lessonasl = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("lessonasl");
        DatabaseReference advancelevel = databaseReference.child("advancelevel_tb").child(encodedEmail).child("advancelesson");
        DatabaseReference advancelevel1 = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("advancelesson1");
        DatabaseReference intermediate = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child("intermediatelesson");
        lessonasl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonASLPoints = snapshot.getValue(Integer.class);
                    pointsTotal += lessonASLPoints;
                    dialog.dismiss();

                    advancelevel.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int advancelesson = snapshot.getValue(Integer.class);
                                pointsTotal += advancelesson;
                                Points.setText(String.valueOf(pointsTotal));
                                advancelevel1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            int advancelesson1 = snapshot.getValue(Integer.class);
                                            pointsTotal += advancelesson1;
                                            Points.setText(String.valueOf(pointsTotal));

                                            intermediate.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()){
                                                        int intermetiatepoints = snapshot.getValue(Integer.class);
                                                        pointsTotal += intermetiatepoints;
                                                        Points.setText(String.valueOf(pointsTotal));
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors
                        }
                    });
                }
                Points.setText(String.valueOf(pointsTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
            }
        });
    }

    public void totalSign() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference lessonasl = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("sign");
        DatabaseReference advancelevel = databaseReference.child("advancelevel_tb").child(encodedEmail).child("sign");
        DatabaseReference advancelevel1 = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("sign");
        DatabaseReference intermediate = databaseReference.child("intermediatelevel_tb").child(encodedEmail).child("sign");
        lessonasl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int lessonASLPoints = snapshot.getValue(Integer.class);
                    signTotal += lessonASLPoints;
                    dialog.dismiss();
                    advancelevel.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int advancelesson = snapshot.getValue(Integer.class);
                                signTotal += advancelesson;
                                Sign.setText(String.valueOf(signTotal));
                                Complete.setText(String.valueOf(signTotal));
                                advancelevel1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            int advancelesson1 = snapshot.getValue(Integer.class);
                                            signTotal += advancelesson1;
                                            Sign.setText(String.valueOf(signTotal));
                                            Complete.setText(String.valueOf(signTotal));

                                            intermediate.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    int interme = snapshot.getValue(Integer.class);
                                                    signTotal += interme;
                                                    Sign.setText(String.valueOf(signTotal));
                                                    Complete.setText(String.valueOf(signTotal));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors
                        }
                    });
                }
                Sign.setText(String.valueOf(signTotal));
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