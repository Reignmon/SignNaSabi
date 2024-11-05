package com.example.sns;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class advance1L1biblicalterm extends AppCompatActivity {
    Dialog dialog,Loading;
    private boolean backPressToExit = false;
    VideoView videoView;
    Button nextButton,prevButton;
    private int currentIndex = 0;
    private Uri[] videoUris;
    LottieAnimationView loadingIndicator;
    TextView btnBack,btnRestart;
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    static String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advance1_l1biblicalterm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        videoView = findViewById(R.id.video);
        nextButton = findViewById(R.id.nextbutton);
        prevButton = findViewById(R.id.prevbutton);
        loadingIndicator = findViewById(R.id.loading);
        btnBack = findViewById(R.id.btnback);
        btnRestart = findViewById(R.id.btnerestart);

        dialog = new Dialog(advance1L1biblicalterm.this);
        dialog.setContentView(R.layout.completevideo);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Loading = new Dialog(advance1L1biblicalterm.this);
        Loading.setContentView(R.layout.loading_dialog);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setCancelable(false);

        final Button okayBtn = dialog.findViewById(R.id.okaybtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(videoView.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        //https://drive.google.com/file/d//view?usp=sharing
        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=191w7hQsrWGp9w2-t1INPyDECZ8MMxhSr"), //religion
                Uri.parse("https://drive.google.com/uc?export=download&id=10Xc7YbNeEGgqtujLqYp5WG92S8SUogrS"), //church
                Uri.parse("https://drive.google.com/uc?export=download&id=1z4EGNzlrXoEghC71pXR5PefbK-4lov8G"), //bible
                Uri.parse("https://drive.google.com/uc?export=download&id=1U9PRCvWCfZBAPi7DmxSY4xU4xFRmhAqJ"), //jesus son
                Uri.parse("https://drive.google.com/uc?export=download&id=1o-kulHNJfSLUdG6hLxwCD2dL8hpd43wo"), //holy spirit
                Uri.parse("https://drive.google.com/uc?export=download&id=1BorXM-qYVEMhzaxu-5PJOWL2-wEzsKM2"), //angel
                Uri.parse("https://drive.google.com/uc?export=download&id=1Ue4LzjpLHxuiDR7sc8KC-p3h5SiZfh1F"), //heaven
                Uri.parse("https://drive.google.com/uc?export=download&id=1MAqP7Ao77VHVJi-Fd15cgHcta-MLee8w"), //hell
                Uri.parse("https://drive.google.com/uc?export=download&id=1xsGKn9gQc4cwkB94TErQIeM1L_Cbn-RV"), //redeem
                Uri.parse("https://drive.google.com/uc?export=download&id=1e1VAv7ZLWkqiDiWsIcJUVRzZ1HvYTHwM"), //save
                Uri.parse("https://drive.google.com/uc?export=download&id=1NAsIAs50T9WTizrfLnZRiFrmJK2oz2CZ"), //forgive
                Uri.parse("https://drive.google.com/uc?export=download&id=1O18IIXlVnwKF2pQBolGJwrVTkdNLj8tk"), //grace
                Uri.parse("https://drive.google.com/uc?export=download&id=1eg1c58JoXrjWsSk67kgbmUqP3GrFDJP2"), //love
                Uri.parse("https://drive.google.com/uc?export=download&id=1OELZ9Cvc72mM-zXsy-QKf6ATFFUy71Eu"), //bless
                // Add more URIs as needed

        };

        retrieveCurrentIndexFromFirebase();

        // Set the first video URI
        videoView.setVideoURI(videoUris[currentIndex]);
        // Start playing the video automatically
        videoView.start();
        loadingIndicator.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(mp -> {
            // Hide loading indicator when video is prepared
            loadingIndicator.setVisibility(View.GONE);
            // Start playing the video automatically
            videoView.setBackgroundColor(Color.TRANSPARENT);
            videoView.start();
        });

        videoView.setOnCompletionListener(mp -> {
            // Restart the video or load another video
            videoView.setVideoURI(videoUris[currentIndex]); // Reload the same video
            videoView.start(); // Start playing again
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setEnabled(true);
        });

        videoView.setOnInfoListener((mp, what, extra) -> {
            // Show loading indicator when buffering or preparing
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                loadingIndicator.setVisibility(View.VISIBLE);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                loadingIndicator.setVisibility(View.GONE);
            }
            return false;
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(advance1L1biblicalterm.this,advancelevel1.class));
                finish();
            }
        });
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelessonasl();
            }
        });

        showButton();
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
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

    // code for next button
    public void playNextVideo(View view) {
        // every click it will increment
        currentIndex++;
        if (currentIndex >= videoUris.length) {
            videoView.pause();
            currentIndex = 0; // balik sa unang video
            dialog.show();
        }else {
            // Set the new video URI and start playback
            videoView.setVideoURI(videoUris[currentIndex]);
            videoView.start();
        }

        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);

        if (currentIndex > 0) {
            prevButton.setVisibility(View.VISIBLE);
            prevButton.setEnabled(true);

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                String encodedEmail = encodeEmail(name);
                DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail);
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(encodedEmail)){
                        usersRef.child("biblicalterm").setValue(currentIndex);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            prevButton.setVisibility(View.INVISIBLE);
            prevButton.setEnabled(false);
        }
        loadingIndicator.setVisibility(View.VISIBLE);
        videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));


    }
    // end of code for next button

    // code for prev button
    public void playPreviousVideo(View view) {
        // Decrement index to play the previous video
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = videoUris.length - 1; // Wrap around to the end
        }
        videoView.setVideoURI(videoUris[currentIndex]);
        videoView.start(); // Start playing the previous video

        if (currentIndex > 0) {
            prevButton.setVisibility(View.VISIBLE);
            prevButton.setEnabled(true);
        } else {
            prevButton.setVisibility(View.INVISIBLE);
            prevButton.setEnabled(false);
        }

        loadingIndicator.setVisibility(View.VISIBLE);
        videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));

    }
    // end of code for prev button


    // Method to retrieve currentIndex from Firebase
    private void retrieveCurrentIndexFromFirebase() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("biblicalterm");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    currentIndex = snapshot.getValue(Integer.class);
                    // Set the videoView to play the video at currentIndex

                    if (currentIndex == 0){
                        prevButton.setVisibility(View.INVISIBLE);
                        prevButton.setEnabled(false);
                    }else{
                        prevButton.setVisibility(View.VISIBLE);
                        prevButton.setEnabled(true);
                    }
                    videoView.setVideoURI(videoUris[currentIndex]);
                    videoView.start();
                } else {
                    // Handle case where currentIndex is not found in Firebase
                    currentIndex = 0; // Set default index
                    videoView.setVideoURI(videoUris[currentIndex]);
                    videoView.start();
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

    private void updatelessonasl() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail);
        Loading.show();

        // Check and update for "Alphabet"
        usersRef.child("biblicalterm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 13) {
                        DatabaseReference lessonaslRef = usersRef.child("advancelesson1");
                        //add sign value in data base
                        DatabaseReference sign = usersRef.child("sign");
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Integer currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : null;
                                DatabaseReference getScore = usersRef.child("biblicaltermscore");
                                if (currentLessonAslValue != null) {
                                    getScore.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Integer currentScore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                                            if (lesson1 == 13 && currentScore < 10) {
                                                Log.e("UpdateLesson1", "Failed to update score" + currentScore);
                                                Loading.dismiss();
                                                startActivity(new Intent(advance1L1biblicalterm.this, advance1L1asessterm.class));
                                                finish();
                                            }else{
                                                Log.e("UpdateLesson1", "update score " + currentScore);
                                                Loading.dismiss();
                                                startActivity(new Intent(advance1L1biblicalterm.this, advancelevel1.class));
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Loading.dismiss();
                                        }
                                    });
                                }else{
                                    //add sign value
                                    sign.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists()){
                                                lessonaslRef.setValue(100);
                                                sign.setValue(1);
                                                Loading.dismiss();
                                                startActivity(new Intent(advance1L1biblicalterm.this, advance1L1asessterm.class));
                                                finish();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    //add sign value
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors
                                Loading.dismiss();
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
    }

    public void restart(){
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail);

        usersRef.child("biblicalterm").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    currentIndex = 0;
                    usersRef.child("biblicalterm").setValue(currentIndex);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        prevButton.setVisibility(View.INVISIBLE);
        prevButton.setEnabled(false);
        loadingIndicator.setVisibility(View.VISIBLE);
        videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));
    }

    public void showButton (){
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail);

        usersRef.child("advancelesson1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int currentLessonAslValue = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                    if (currentLessonAslValue >= 100){
                        btnRestart.setVisibility(View.VISIBLE);
                    }else{
                        btnRestart.setVisibility(View.GONE);
                    }
                }
                else{
                    btnRestart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}