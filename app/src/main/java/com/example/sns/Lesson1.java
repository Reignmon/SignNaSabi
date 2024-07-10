package com.example.sns;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lesson1 extends AppCompatActivity {

    VideoView videoView;
    Button nextButton,prevButton;
    private int currentIndex = 0;
    private Uri[] videoUris;
    ProgressBar loadingIndicator;
    TextView btnBack;

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

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(videoView.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=1lpErjzEVsXdwszyxkzVWqjCarLtkmn4b"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1lxSKRgdK7V5HDHnSFPpArNcZasI1aZlo"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1gY78ShYpO7d7CrFisgrX1aAyy6DqH_PY"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1eTdZLZzn_mXYTnA41GaoFJUHYJy1d8tG"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1ZYYqj8LTCDXnJn4Dggp51B1c9U2qkbb0"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1R8lI_WUb5-UtF6Qx3RICScjXZK7afjdi"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1TUJyzZkkX5M7ahtR1X2brKdvqdMFDwKU"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1ccO8tBjBE0izSyr1GQXpcwv2cF9hPL5d"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1Kxiw_-koRZzi4XPA6FrV6-ywtO0AUDFl"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1RzCLraIQ4erp6syQMDGT7k-a79-R50vx"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1PbtY-FFwkZXfjgWLEKzy5IKrtODBdWQB"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1Mtn_hWE3Vz6D9l9BdcfjaKSJNBPFbjVG"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1YeZ5G9kzSKNRjtRinAJJBEK4tDLb3rai"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1VYCMHx2uuzlaFDZfVkUsl53uK1nVwFJS"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1dIUVlHTZaJ3UnUD_6EONSzjKvtALMsk6"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1I5p0vaaAjV0kySn8kLPBW1MeB8NokZ_K"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1f6OhOyyGC97wpHK2VM3B00AF-LUKXQvB"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1wiX7UE9pmxltmWq4b-ZKZBVi0sHFmBB1"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1KyrRPlcjBt4jhwGvBREDuGtRnsf6Zt38"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1UbgOqn1l13mukX6PSkHbrMq0x8z_9ssN"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1TIexTWzYFsszkgdAV7dp6PrTQS1UEB9n"),
                Uri.parse("https://drive.google.com/uc?export=download&id=19m7EfK23S2N2q5wFY72wM4SOJdYP24XF"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1TOvm3zUGcM1VoAkWDog8BjwZ74fnmHo3"),
                Uri.parse("https://drive.google.com/uc?export=download&id=112FbkOuO0NFuz70euB2kh7FrejbuRlS4"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1GDy7XT_7C7mCoC1l60gtoambnaAlLFC4"),
                Uri.parse("https://drive.google.com/uc?export=download&id=1_PSutn1PRU3BVAjhNjkLtUft-WVUNLK0")
                // Add more URIs as needed
        };

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


    }

    // code for next button
    public void playNextVideo(View view) {
        // every click it will increment
        currentIndex++;
        if (currentIndex >= videoUris.length) {
            currentIndex = 0; // balik sa unang video
        }
        videoView.setVideoURI(videoUris[currentIndex]);
        videoView.start();

        nextButton.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);

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

}