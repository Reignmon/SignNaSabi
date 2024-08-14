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

public class advance1L1biblicalcharac extends AppCompatActivity {

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
        setContentView(R.layout.activity_advance1_l1biblicalcharac);
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

        dialog = new Dialog(advance1L1biblicalcharac.this);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1eJu7nP7w8EtpCbAN1w6tQF3P0wL5CSMB"), //adam
                Uri.parse("https://drive.google.com/uc?export=download&id=1cAd50U2Fmfv3g0P0bVy4m-RhUw9KsSXI"), //eve
                Uri.parse("https://drive.google.com/uc?export=download&id=1bI9IXQ5tyJzm2_O70OTeXg9S4rtFJU0X"), //noah
                Uri.parse("https://drive.google.com/uc?export=download&id=1kSOR9oWiUs7Y9A1-MBic6nj84N38L5Mr"), //abraham
                Uri.parse("https://drive.google.com/uc?export=download&id=1XjISPohAgfHLAV32cpaGuhQWSuEn7gFM"), //moses
                Uri.parse("https://drive.google.com/uc?export=download&id=13CY26EWipTTrvgZbu0dX1wbAUruRU3FA"), //david
                Uri.parse("https://drive.google.com/uc?export=download&id=1CAOHZzC6g5d1uc04eWkCtEV9Vs_-_Jv5"), //jesus
                Uri.parse("https://drive.google.com/uc?export=download&id=1bPK3ftBCyc5F_RB6qoCv0bZxDFDVXF7K"), //mary
                Uri.parse("https://drive.google.com/uc?export=download&id=1wxMmMUKGUacrCRonJ4XMmwMl3M6-AP0A"), //paul
                Uri.parse("https://drive.google.com/uc?export=download&id=1N42yWk40KMpMRe9aBwZto1twrt1s2SaJ"), //peter
                Uri.parse("https://drive.google.com/uc?export=download&id=1N42yWk40KMpMRe9aBwZto1twrt1s2SaJ"), //james
                Uri.parse("https://drive.google.com/uc?export=download&id=1Yvl8fEfdPSQ0jSfnvEIuxhBFGf6NOJoD"), //john
                Uri.parse("https://drive.google.com/uc?export=download&id=1gwP5YTpxvWqx0mKtn4UUTIFaNZzrQPmG"), //judas
                Uri.parse("https://drive.google.com/uc?export=download&id=1BMm55NtNJfEv4c9gtTggOnxb9PVfNwdG"), //matthew
                Uri.parse("https://drive.google.com/uc?export=download&id=19a_C2p_5x_nKSBNDK8_BGRDpjNyTe1wP"), //jude
                Uri.parse("https://drive.google.com/uc?export=download&id=1BvlKroKGmi8rMkWnLCyOcpJOUG4xfYXD"), //bartolome
                Uri.parse("https://drive.google.com/uc?export=download&id=1Y74tkraXCIQdhi5D5sUq5JQJKuYR5a0S"), //isaiah
                Uri.parse("https://drive.google.com/uc?export=download&id=1Y74tkraXCIQdhi5D5sUq5JQJKuYR5a0S"), //jeremiah
                Uri.parse("https://drive.google.com/uc?export=download&id=1zvwn4qS1cQmnpU6ALqsTOSz555BiOqbj"), //davis
                Uri.parse("https://drive.google.com/uc?export=download&id=1oTgvq4ooC2Um8c8ORpMMxkT5xZPz0U7z"), //jacob
                Uri.parse("https://drive.google.com/uc?export=download&id=1OR_TtYBoJ9xLw-w8hGSk0bLCs0uZb1ON"), //joshua
                Uri.parse("https://drive.google.com/uc?export=download&id=1rCLCRxAmH0kCzcIkL1jB-zZwNUB4mIJk"), //amos
                Uri.parse("https://drive.google.com/uc?export=download&id=1crbtoaNiBx7YnQ4jXN7e0UlgqY5O83Pq"), //samuel
                Uri.parse("https://drive.google.com/uc?export=download&id=1D21Bst7HM-4dTMmfRjI2ZHrhVk3Vo75a"), //nathan
                Uri.parse("https://drive.google.com/uc?export=download&id=1IEYBVjHXGC4y5y7bUHklXtsVswdD28a2"), //joel
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
                startActivity(new Intent(advance1L1biblicalcharac.this,advancelevel1.class));
                finish();
            }
        });
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelessonasl();
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(advance1L1biblicalcharac.this,advancelevel1.class));
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
                        usersRef.child("biblicalcharacter").setValue(currentIndex);
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
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail).child("biblicalcharacter");
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

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

    private void updatelessonasl() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("advancelevel1_tb").child(encodedEmail);

        // Check and update for "Alphabet"
        usersRef.child("biblicalcharacter").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 24) {
                        DatabaseReference lessonaslRef = usersRef.child("advancelesson1");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lesson1 == 24 && currentLessonAslValue < 200) {
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
    }
}