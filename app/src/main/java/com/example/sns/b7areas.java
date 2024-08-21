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

public class b7areas extends AppCompatActivity {
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
        setContentView(R.layout.activity_b7areas);
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

        dialog = new Dialog(b7areas.this);
        dialog.setContentView(R.layout.lesson_complete_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Loading = new Dialog(b7areas.this);
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


        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=14nq0msUPffUneR3dYdwjWqJqUpg4x0rR"), //living room
                Uri.parse("https://drive.google.com/uc?export=download&id=1D3ZMT4U2qEEBM4-EgudvBrfDEKnCdbmC"), //kitchen
                Uri.parse("https://drive.google.com/uc?export=download&id=18G75DRf6YDPp69uaEfmtApjVVHfPDJyM"), //pantry
                Uri.parse("https://drive.google.com/uc?export=download&id=1G-K90nKlPl7CwxSR1RzKysLrtKNt-lFN"), //dining room
                Uri.parse("https://drive.google.com/uc?export=download&id=1KZ19no2F2lB8xRohazGC1BPInk15bHJR"), //bed room
                Uri.parse("https://drive.google.com/uc?export=download&id=1_k5yIRKbs4ZQTrcsHUsOSg752d1CP36N"), //bathroom
                Uri.parse("https://drive.google.com/uc?export=download&id=1ZeY-w6RN4Q1YxMgjvEJmXAbn0hgdT7h2"), //chair
                Uri.parse("https://drive.google.com/uc?export=download&id=1XKD3giay94uqd-nTbcOP6FjDynry39AU"), //sofa
                Uri.parse("https://drive.google.com/uc?export=download&id=1PXmPF2ZG8Ji4i9N4fpHedouIq-P7Ax_7"), //table
                Uri.parse("https://drive.google.com/uc?export=download&id=1wkbnAbxSodZUNSeTwcFjMoceePL6-zkc"), //bed
                Uri.parse("https://drive.google.com/uc?export=download&id=19wJWKsebVnZxqr7A40Uoks2ngagxZWxN"), //mirror
                Uri.parse("https://drive.google.com/uc?export=download&id=1O543Nj_atIB_bJO7Uo3X3-9Hv04h7hpD"), //TV
                Uri.parse("https://drive.google.com/uc?export=download&id=1VsdtPdAEoZ99DXudoDfnbT3VL5p2vNza"), //light
                Uri.parse("https://drive.google.com/uc?export=download&id=1OlcmXGKhkX8I-5U9K4FP4hdeKfgwIml1"), //refrigirator
                Uri.parse("https://drive.google.com/uc?export=download&id=1477vdo8XR-Zgj8U7XcVN28WvgA2uFUuN"), //window

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
                startActivity(new Intent(b7areas.this,basiclevel.class));
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
                        usersRef.child("areas").setValue(currentIndex);
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
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("areas");
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
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
        Loading.show();

        // Check and update for "Alphabet"
        usersRef.child("areas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 14) {
                        DatabaseReference lessonaslRef = usersRef.child("lessonasl");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lesson1 == 14 && currentLessonAslValue < 1700) {
                                    lessonaslRef.setValue(1700);
                                    Loading.dismiss();
                                    startActivity(new Intent(b7areas.this,basiclevel.class));
                                    finish();
                                }else {
                                    Loading.dismiss();
                                    startActivity(new Intent(b7areas.this,basiclevel.class));
                                    finish();
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

    public void restart(){
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        usersRef.child("areas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    currentIndex = 0;
                    usersRef.child("areas").setValue(currentIndex);
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
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        usersRef.child("lessonasl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int currentLessonAslValue = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                    if (currentLessonAslValue >= 1700){
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