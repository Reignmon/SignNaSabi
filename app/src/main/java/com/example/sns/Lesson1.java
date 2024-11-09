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
import android.util.Log;
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
        btnRestart = findViewById(R.id.btnerestart);

        dialog = new Dialog(Lesson1.this);
        dialog.setContentView(R.layout.completevideo);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        dialog.setCancelable(false);

        Loading = new Dialog(Lesson1.this);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1nk9TCJVY75c4RNaE0K557zuOU7_Xj3jJ"), //A
                Uri.parse("https://drive.google.com/uc?export=download&id=1LYq04gT_0cYYGJKfEy3J8cahbVj_cCFU"), //B
                Uri.parse("https://drive.google.com/uc?export=download&id=18uCaOeTJ_YnI5XmbQv7UtLHvlF25-Ifj"), //C
                Uri.parse("https://drive.google.com/uc?export=download&id=1ETRF9cXJw6TaQXFgdlYRXYQy5h6zFQs7"), //D
                Uri.parse("https://drive.google.com/uc?export=download&id=147b873O2g22GKtTkmyeNPBR1hkd5bXaY"), //E
                Uri.parse("https://drive.google.com/uc?export=download&id=16R8wU5DO7XulRk46HbjXF-sGLYrmBc0_"), //F
                Uri.parse("https://drive.google.com/uc?export=download&id=17tMKP8LhX7GJ8lVJgWIl2qxelro3njAV"), //G
                Uri.parse("https://drive.google.com/uc?export=download&id=191VxcT-dGYarC_PHOus-oo3FcCOUm_NT"), //H
                Uri.parse("https://drive.google.com/uc?export=download&id=1A1wDkaPaJV08ogW_6ZG94N0Mb3PXKTeS"), //I
                Uri.parse("https://drive.google.com/uc?export=download&id=1qnTIzvxxGVfFyp3sMr7KeaQz-yEu8kFq"), //J
                Uri.parse("https://drive.google.com/uc?export=download&id=10k4SfFV_4Iq7H1-E8M8d9Cvi3p-ikAMO"), //K
                Uri.parse("https://drive.google.com/uc?export=download&id=1AEOmGLu2PleAcafWPDxUQP4MpKsFJXeF"), //L
                Uri.parse("https://drive.google.com/uc?export=download&id=1G_LtQQCytHQVdoHG47At-Wq64Gk6pb82"), //M
                Uri.parse("https://drive.google.com/uc?export=download&id=1oFfGAKT4y7_lPnqeYwY4ZdhTKKJB2ZMV"), //N
                Uri.parse("https://drive.google.com/uc?export=download&id=1AEOmGLu2PleAcafWPDxUQP4MpKsFJXeF"), //O
                Uri.parse("https://drive.google.com/uc?export=download&id=1yqE-WmEhWr1NP3bjvqhV0roL31t_hGcY"), //P
                Uri.parse("https://drive.google.com/uc?export=download&id=1Z2q_Dd4cEsmAvGTHpXt43BGMCduAuGbD"), //Q
                Uri.parse("https://drive.google.com/uc?export=download&id=1gFccyNedPiKJbcfNw4dcp5wYMt0fxtPs"), //R
                Uri.parse("https://drive.google.com/uc?export=download&id=1HSJKoUhCgTN1VbB-Yt75dExdBjVh4PwL"), //S
                Uri.parse("https://drive.google.com/uc?export=download&id=1w6jDZv8zoaYZBCnNW-p3KJdz4Idw_K2m"), //T
                Uri.parse("https://drive.google.com/uc?export=download&id=1C6MApuyMl9Q0zIFQRbCrIDrFDxyUfZOj"), //U
                Uri.parse("https://drive.google.com/uc?export=download&id=1_qWWVQO_0h9BBghZngVv0Ao7-pbqp2Pf"), //V
                Uri.parse("https://drive.google.com/uc?export=download&id=16qUL8Nz1ILMfG2aTj76TvQvETXwyzcCH"), //W
                Uri.parse("https://drive.google.com/uc?export=download&id=1i1w-Z5Zk0hRgYliQ_KNoMSifUUBEmlRm"), //X
                Uri.parse("https://drive.google.com/uc?export=download&id=1_GnrFu3E4hVkBTvyDrJSqx_1g-_WbbIM"), //Y
                Uri.parse("https://drive.google.com/uc?export=download&id=1CnYRzXkld3tAoCBBvF2ci6Z2GZj2bJyd")  //Z
                //https://drive.google.com/file/d//view?usp=sharing
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

                    if (currentIndex == 0){
                        prevButton.setVisibility(View.INVISIBLE);
                        prevButton.setEnabled(false);
                    }else {
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


    private void updatelessonasl() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
        Loading.show();

        // Check and update for "Alphabet"
        usersRef.child("Alphabet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get currentIndex from Firebase
                    int lesson1 = snapshot.getValue(Integer.class);
                    if (lesson1 == 25) {
                        DatabaseReference lessonaslRef = usersRef.child("lessonasl");
                        //add sign value in data base
                        DatabaseReference sign = usersRef.child("sign");

                        // Check the current value of lessonasl before updating
                        lessonaslRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Integer currentLessonAslValue = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : null;
                                DatabaseReference getScore = usersRef.child("alphabetscore");
                                if (currentLessonAslValue != null) {
                                    getScore.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Integer currentScore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                                            if (lesson1 == 25 && currentScore < 10) {
                                                Log.e("UpdateLesson1", "Failed to update score" + currentScore);
                                                Loading.dismiss();
                                                startActivity(new Intent(Lesson1.this, basicL1Asses1.class));
                                                finish();
                                            }else{
                                                Log.e("UpdateLesson1", "update score " + currentScore);
                                                Loading.dismiss();
                                                startActivity(new Intent(Lesson1.this, basiclevel.class));
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
                                                startActivity(new Intent(Lesson1.this, basicL1Asses1.class));
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
                        Loading.dismiss();
                    }
                } else {
                    Loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors
                Loading.dismiss();
            }
        });

    }

    public void restart(){
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        usersRef.child("Alphabet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    currentIndex = 0;
                    usersRef.child("Alphabet").setValue(currentIndex);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.dismiss();
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