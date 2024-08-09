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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
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

public class Lesson1 extends AppCompatActivity {
    Dialog dialog;
    private boolean backPressToExit = false;
    VideoView videoView;
    Button nextButton,prevButton;
    private int currentIndex = 0;
    private Uri[] videoUris;
    LottieAnimationView loadingIndicator;
    TextView btnBack;
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
        setContentView(R.layout.activity_lesson1);
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

        dialog = new Dialog(Lesson1.this);
        dialog.setContentView(R.layout.lesson_complete_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        final Button okayBtn = dialog.findViewById(R.id.okaybtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(videoView.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);



        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=1lpErjzEVsXdwszyxkzVWqjCarLtkmn4b"), //A not done
                Uri.parse("https://drive.google.com/uc?export=download&id=1lxSKRgdK7V5HDHnSFPpArNcZasI1aZlo"), //B not done
                Uri.parse("https://drive.google.com/uc?export=download&id=1Imy6u1xhNjqqWYt7V1DfFqJzH5jUpXPc"), //C
                Uri.parse("https://drive.google.com/uc?export=download&id=1dMPORTiGKIFWU-H3pv-vP7vqPFKYjsWR"), //D
                Uri.parse("https://drive.google.com/uc?export=download&id=16aHajT_bSKN750vO6CZJuepKtTdb9mrZ"), //E
                Uri.parse("https://drive.google.com/uc?export=download&id=189HD7cliG97oAUjSAuAJfpir24FD5xN8"), //F
                Uri.parse("https://drive.google.com/uc?export=download&id=1TUJyzZkkX5M7ahtR1X2brKdvqdMFDwKU"), //G
                Uri.parse("https://drive.google.com/uc?export=download&id=1JPsU1491GnGti1NWE4qd7CN2Y5YVB6Ys"), //H
                Uri.parse("https://drive.google.com/uc?export=download&id=1fvDnoPpSjRobIq6-2r-co1s5dGhw90zM"), //I
                Uri.parse("https://drive.google.com/uc?export=download&id=19KhnHEQkypQh-0Jh8c0RC6wXWkGkBnv1"), //J
                Uri.parse("https://drive.google.com/uc?export=download&id=1o_vkWW5DgcoedrrGSCVnU6RNrucllieE"), //K
                Uri.parse("https://drive.google.com/uc?export=download&id=1OuGxOWOK5TIfxrKP31MzRqh5m6JJElsi"), //L
                Uri.parse("https://drive.google.com/uc?export=download&id=1nQKgH1Et3bytnfqw6vV6D0VQfqZvxzTC"), //M
                Uri.parse("https://drive.google.com/uc?export=download&id=1LpmEWUpEymaH_Lo-kLcLGw0G4mhIZz6E"), //N
                Uri.parse("https://drive.google.com/uc?export=download&id=1CrdfWdFAEJQdKCcLiqyMhUS6ckKeUcFn"), //O
                Uri.parse("https://drive.google.com/uc?export=download&id=1NNzzy2BlQnOll8R5HP795r_z8vrD7THF"), //P
                Uri.parse("https://drive.google.com/uc?export=download&id=1V6ZcR1h52YXW6bsS3I3XPDH1Uo374M8q"), //Q
                Uri.parse("https://drive.google.com/uc?export=download&id=16ZoJMkizbyJ_k9NObdxEQXIqOhuQHEZ-"), //R
                Uri.parse("https://drive.google.com/uc?export=download&id=1KyrRPlcjBt4jhwGvBREDuGtRnsf6Zt38"), //S not done
                Uri.parse("https://drive.google.com/uc?export=download&id=1ZelE2EaZJs-eq1lNyZcgPgnwsOx2pImz"), //T
                Uri.parse("https://drive.google.com/uc?export=download&id=1WEQZxltdPab7zAQIVG4-5WI83qVfelvH"), //U
                Uri.parse("https://drive.google.com/uc?export=download&id=1WvCSQIJ-dLsNHcE43EJAS3OiHocO-kJL"), //V
                Uri.parse("https://drive.google.com/uc?export=download&id=1AaYGcz0hUE_HhuQCew-ieIwErl_icnxa"), //W
                Uri.parse("https://drive.google.com/uc?export=download&id=1QLnOg6L9AET_p8mA09QZSrBE3qYDvM8R"), //X
                Uri.parse("https://drive.google.com/uc?export=download&id=1DXC-Qggl8kXGWfB02eaesAR2gYk-Ps-x"), //Y
                Uri.parse("https://drive.google.com/uc?export=download&id=1XyclVlC-KVncPpZlvd6n1P-yO7sG7ijr")  //Z
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
                startActivity(new Intent(Lesson1.this,basiclevel.class));
                finish();
            }
        });

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelessonasl();
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(Lesson1.this,basiclevel.class));
                    finish();
                }, 500); // 1-second delay

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
            currentIndex = 0; // balik sa unang video
            dialog.show();
        }
        videoView.setVideoURI(videoUris[currentIndex]);
        videoView.start();

        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);

        if (currentIndex > 0) {
            prevButton.setVisibility(View.VISIBLE);
            prevButton.setEnabled(true);

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                String encodedEmail = encodeEmail(name);
                DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(encodedEmail)){
                        usersRef.child("Alphabet").setValue(currentIndex);
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

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

    // Method to retrieve currentIndex from Firebase
    private void retrieveCurrentIndexFromFirebase() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("Alphabet");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    currentIndex = snapshot.getValue(Integer.class);
                    // Set the videoView to play the video at currentIndex

                    prevButton.setVisibility(View.VISIBLE);
                    prevButton.setEnabled(true);

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


    private void updatelessonasl() {
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

    }

}