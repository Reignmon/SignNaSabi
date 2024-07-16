package com.example.sns;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lesson1 extends AppCompatActivity {
    Dialog dialog;
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
        dialog.setContentView(R.layout.sucess_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        dialog.setCancelable(false);
        final Button okayBtn = dialog.findViewById(R.id.okaybtn);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(videoView.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=1lpErjzEVsXdwszyxkzVWqjCarLtkmn4b"), //A
                Uri.parse("https://drive.google.com/uc?export=download&id=1lxSKRgdK7V5HDHnSFPpArNcZasI1aZlo"), //B
                Uri.parse("https://drive.google.com/uc?export=download&id=1gY78ShYpO7d7CrFisgrX1aAyy6DqH_PY"), //C
                Uri.parse("https://drive.google.com/uc?export=download&id=1eTdZLZzn_mXYTnA41GaoFJUHYJy1d8tG"), //D
                Uri.parse("https://drive.google.com/uc?export=download&id=1ZYYqj8LTCDXnJn4Dggp51B1c9U2qkbb0"), //E
                Uri.parse("https://drive.google.com/uc?export=download&id=1MbvPEMJk_xpcSKWeOnoLYraZw2WJ40QP"), //F
                Uri.parse("https://drive.google.com/uc?export=download&id=1TUJyzZkkX5M7ahtR1X2brKdvqdMFDwKU"), //G
                Uri.parse("https://drive.google.com/uc?export=download&id=1ccO8tBjBE0izSyr1GQXpcwv2cF9hPL5d"), //H
                Uri.parse("https://drive.google.com/uc?export=download&id=1Kxiw_-koRZzi4XPA6FrV6-ywtO0AUDFl"), //I
                Uri.parse("https://drive.google.com/uc?export=download&id=1RzCLraIQ4erp6syQMDGT7k-a79-R50vx"), //J
                Uri.parse("https://drive.google.com/uc?export=download&id=1PbtY-FFwkZXfjgWLEKzy5IKrtODBdWQB"), //K
                Uri.parse("https://drive.google.com/uc?export=download&id=1Mtn_hWE3Vz6D9l9BdcfjaKSJNBPFbjVG"), //L
                Uri.parse("https://drive.google.com/uc?export=download&id=1YeZ5G9kzSKNRjtRinAJJBEK4tDLb3rai"), //M
                Uri.parse("https://drive.google.com/uc?export=download&id=1VYCMHx2uuzlaFDZfVkUsl53uK1nVwFJS"), //N
                Uri.parse("https://drive.google.com/uc?export=download&id=1dIUVlHTZaJ3UnUD_6EONSzjKvtALMsk6"), //O
                Uri.parse("https://drive.google.com/uc?export=download&id=1yDnH6o--W0JMNVj974JoLt9Z_N7U2EWf"), //P
                Uri.parse("https://drive.google.com/uc?export=download&id=1f6OhOyyGC97wpHK2VM3B00AF-LUKXQvB"), //Q
                Uri.parse("https://drive.google.com/uc?export=download&id=1wiX7UE9pmxltmWq4b-ZKZBVi0sHFmBB1"), //R
                Uri.parse("https://drive.google.com/uc?export=download&id=1KyrRPlcjBt4jhwGvBREDuGtRnsf6Zt38"), //S
                Uri.parse("https://drive.google.com/uc?export=download&id=1UbgOqn1l13mukX6PSkHbrMq0x8z_9ssN"), //T
                Uri.parse("https://drive.google.com/uc?export=download&id=1TIexTWzYFsszkgdAV7dp6PrTQS1UEB9n"), //U
                Uri.parse("https://drive.google.com/uc?export=download&id=19m7EfK23S2N2q5wFY72wM4SOJdYP24XF"), //V
                Uri.parse("https://drive.google.com/uc?export=download&id=1TOvm3zUGcM1VoAkWDog8BjwZ74fnmHo3"), //W
                Uri.parse("https://drive.google.com/uc?export=download&id=112FbkOuO0NFuz70euB2kh7FrejbuRlS4"), //X
                Uri.parse("https://drive.google.com/uc?export=download&id=1GDy7XT_7C7mCoC1l60gtoambnaAlLFC4"), //Y
                Uri.parse("https://drive.google.com/uc?export=download&id=1_PSutn1PRU3BVAjhNjkLtUft-WVUNLK0")  //Z
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
                startActivity(new Intent(Lesson1.this,basiclevel.class));
                finish();
            }
        });

    }

    // code for next button
    public void playNextVideo(View view) {
        // every click it will increment
        currentIndex++;
        if (currentIndex >= videoUris.length) {
            //currentIndex = 0; // balik sa unang video
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
                DatabaseReference usersRef = databaseReference.child("users").child(encodedEmail);
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(encodedEmail)){
                        usersRef.child("Basic_L1").setValue(currentIndex);
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
        DatabaseReference usersRef = databaseReference.child("users").child(encodedEmail).child("Basic_L1");
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




}