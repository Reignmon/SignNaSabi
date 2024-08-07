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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class b2greetings extends AppCompatActivity {

    Dialog dialog;
    private boolean backPressToExit = false;
    VideoView videoView;
    Button nextButton,prevButton;
    private int currentIndex = 0;
    private Uri[] videoUris;
    ProgressBar loadingIndicator;
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
        setContentView(R.layout.activity_b2greetings);
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

        dialog = new Dialog(b2greetings.this);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1qXQ8NMIGSTII5UXYZuJ1Ng_SYCAIqrWs"), //Happy new year
                Uri.parse("https://drive.google.com/uc?export=download&id=1Hiouat9V911ncMK5nnPzCcaqVJuriihg"), //merry christmas
                Uri.parse("https://drive.google.com/uc?export=download&id=1S05z3zijtyxhFnkGiY1BLQzynM5BHOgw"), //happy birthday
                Uri.parse("https://drive.google.com/uc?export=download&id=1lEeyHGOKsRNHe1BTLgEdnQ2RKRD1Y2Ue"), //im sorry
                Uri.parse("https://drive.google.com/uc?export=download&id=1lEeyHGOKsRNHe1BTLgEdnQ2RKRD1Y2Ue"), //where are you going
                Uri.parse("https://drive.google.com/uc?export=download&id=1PbRPLxuS0ngADlhzZA4Jp20X6komfTMO"), //i have to go home
                Uri.parse("https://drive.google.com/uc?export=download&id=1wC9xCqoE3552cU6QW3cgQIVjr-lcjM-z"), //what time is it
                Uri.parse("https://drive.google.com/uc?export=download&id=1LDZNCIIqsxmB0VM0RhIBbLLJ36TaUPx7"), //seet please
                Uri.parse("https://drive.google.com/uc?export=download&id=1k9GZhDpUtNZvwWb4VKH6DkW_0FRpQoZJ"), //whats your phone number
                Uri.parse("https://drive.google.com/uc?export=download&id=1DdOAS7Y3xwEJ5WOSX0z2TLJfAMxXI0oL"), //close door
                Uri.parse("https://drive.google.com/uc?export=download&id=1MzEolbmFjgfUVtvPtQ6USkpFoyY_pzgt"), //open door
                Uri.parse("https://drive.google.com/uc?export=download&id=1T_rVNQwmBs2DcWnlF-zPOrRzAqC5um4L"), //wheres the bathroom
                Uri.parse("https://drive.google.com/uc?export=download&id=1k4J9Fjp_SZ8VS2ejt421UaJGHExCNugR"), //pardon me
                Uri.parse("https://drive.google.com/uc?export=download&id=1XPApmBwWQUN0peEQ9JS196rEwd5_bQYk"), //please
                Uri.parse("https://drive.google.com/uc?export=download&id=19X0esSI0ypw6MGDE2C_-h4XX1Eb327By"), //thankyou
                Uri.parse("https://drive.google.com/uc?export=download&id=1MOUKf6ngwqIqSMiIDjJVw73cF5GdgNSb"), //im fine
                Uri.parse("https://drive.google.com/uc?export=download&id=1AVvDzn1Au4WxQCtTfuyWI6bf1OLiu5Vv"), //goodbye
                Uri.parse("https://drive.google.com/uc?export=download&id=1Lf63wEpE2pFttHBPCsajjc3ojvTXlwYA"), //see you later
                Uri.parse("https://drive.google.com/uc?export=download&id=1vGMgGNhY-zZriu20GvXpldbexw-GI4EK"), //im glad to see you
                Uri.parse("https://drive.google.com/uc?export=download&id=138s1N_MQIv3cjBT-cspD0aUD2aIi2gRS"), //how have you been
                Uri.parse("https://drive.google.com/uc?export=download&id=138s1N_MQIv3cjBT-cspD0aUD2aIi2gRS"), //not done
                Uri.parse("https://drive.google.com/uc?export=download&id=1dJmktBDo8nOo3EOTjNAWLDjJ81WfETrY"), //good night
                Uri.parse("https://drive.google.com/uc?export=download&id=15q2hdABbgYkl-AIHEBUgEAmVKzHQVx56"), //good afternoon
                Uri.parse("https://drive.google.com/uc?export=download&id=1qPi9JyKNsovL65tXlfgAR4ImGSXJmWQc"), //good morning
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
                startActivity(new Intent(b2greetings.this,basiclevel.class));
                finish();
            }
        });

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelessonasl();
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(b2greetings.this,basiclevel.class));
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
                        usersRef.child("greetings").setValue(currentIndex);
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
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail).child("greetings");
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

        // Check and update for "Alphabet"
        usersRef.child("greetings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 23) {
                        DatabaseReference lessonaslRef = usersRef.child("lessonasl");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                                if (lesson1 == 23 && currentLessonAslValue < 300) {
                                    lessonaslRef.setValue(300);
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