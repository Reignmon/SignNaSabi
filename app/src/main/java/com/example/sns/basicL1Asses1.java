package com.example.sns;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class basicL1Asses1 extends AppCompatActivity {

    Dialog correctAnswer,wrongAnswer;
    VideoView videoView;
    LinearLayout aButton, bButton;
    private Uri[] videoUris;
    TextView wrong, correct;
    LottieAnimationView loadingIndicator;
    TextView letterA, letterB;

    private String[] answers;
    private String currentAnswer;
    private int correctScore = 0, wrongScore = 0;
    private Random random = new Random(); // Create a Random instance

    private List<Uri> unplayedVideos; // List to track unplayed videos
    private int videosPlayed = 0; // Counter to track the number of videos played

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_l1_asses1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoView = findViewById(R.id.video);
        aButton = findViewById(R.id.lettera);
        bButton = findViewById(R.id.letterb);
        wrong = findViewById(R.id.wrong);
        correct = findViewById(R.id.correct);
        letterA = findViewById(R.id.a);
        letterB = findViewById(R.id.b);
        loadingIndicator = findViewById(R.id.loading);
        final TextView btnback = findViewById(R.id.btnback);

        correctAnswer = new Dialog(basicL1Asses1.this);
        correctAnswer.setContentView(R.layout.correct_answer);
        correctAnswer.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        correctAnswer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        correctAnswer.setCancelable(false);
        final Button btncorrect = correctAnswer.findViewById(R.id.okaybtn);

        wrongAnswer = new Dialog(basicL1Asses1.this);
        wrongAnswer.setContentView(R.layout.incorrect_dialog);
        wrongAnswer.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrongAnswer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        wrongAnswer.setCancelable(false);
        final Button btnwrong = wrongAnswer.findViewById(R.id.okaybtn);


        // Setup video view
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        // Define video URIs and corresponding answers
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
                // Add more URIs here as needed
        };

        answers = new String[]{"Letter A", "Letter B", "Letter C", "Letter D", "Letter E", "Letter F", "Letter G", "Letter H", "Letter I",
                "Letter J", "Letter K", "Letter L", "Letter M", "Letter N", "Letter O", "Letter P", "Letter Q", "Letter R", "Letter S",
                "Letter T", "Letter U", "Letter V", "Letter W", "Letter X", "Letter Y", "Letter Z"};

        // Initialize unplayed videos
        unplayedVideos = new ArrayList<>();
        Collections.addAll(unplayedVideos, videoUris);
        Collections.shuffle(unplayedVideos); // Shuffle the list initially

        playRandomVideo();

        loadingIndicator.setVisibility(View.VISIBLE);

        videoView.setOnInfoListener((mp, what, extra) -> {
            // Show loading indicator when buffering or preparing
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                loadingIndicator.setVisibility(View.VISIBLE);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                loadingIndicator.setVisibility(View.GONE);
            }
            return false;
        });

        btnback.setOnClickListener(view -> {
            startActivity(new Intent(basicL1Asses1.this, Lesson1.class));
            finish();
        });

        aButton.setOnClickListener(v -> checkAnswer(letterA.getText().toString()));
        bButton.setOnClickListener(v -> checkAnswer(letterB.getText().toString()));

        updateScores();

        btncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer.dismiss();
                loadingIndicator.setVisibility(View.GONE);
                videoView.setBackgroundColor(Color.TRANSPARENT);
                playRandomVideo();
            }
        });

        btnwrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongAnswer.dismiss();
                loadingIndicator.setVisibility(View.GONE);
                videoView.setBackgroundColor(Color.TRANSPARENT);
                playRandomVideo();
            }
        });
    }

    private void playRandomVideo() {
        if (videosPlayed >= 5) {
            if(correctScore >=4){
                startActivity(new Intent(basicL1Asses1.this,dashboard.class));
                finish();
            } else {
                startActivity(new Intent(basicL1Asses1.this,Lesson1.class));
                finish();
            }
            return;
        }

        if (unplayedVideos.isEmpty()) {
            // Reset the list if all videos have been played and shuffle it
            unplayedVideos = new ArrayList<>();
            Collections.addAll(unplayedVideos, videoUris);
            Collections.shuffle(unplayedVideos);
        }

        // Pick a random video from the unplayed list
        int randomIndex = random.nextInt(unplayedVideos.size());
        Uri videoUri = unplayedVideos.get(randomIndex);
        videoView.setVideoURI(videoUri);
        videoView.start();

        // Determine the current answer
        int videoIndex = getIndexFromUri(videoUri);
        currentAnswer = answers[videoIndex]; // Store the correct answer

        setButtonTexts(currentAnswer);

        aButton.setVisibility(View.INVISIBLE);
        bButton.setVisibility(View.INVISIBLE);

        loadingIndicator.setVisibility(View.VISIBLE);
        videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));
        videoView.setOnPreparedListener(mp -> {
            loadingIndicator.setVisibility(View.GONE);
            videoView.setBackgroundColor(Color.TRANSPARENT);
            videoView.start();
        });

        videoView.setOnCompletionListener(mp -> {
            aButton.setVisibility(View.VISIBLE);
            bButton.setVisibility(View.VISIBLE);

            unplayedVideos.remove(videoUri);
            videosPlayed++;
        });
    }

    private int getIndexFromUri(Uri uri) {
        for (int i = 0; i < videoUris.length; i++) {
            if (videoUris[i].equals(uri)) {
                return i;
            }
        }
        return -1; // Should never happen if the URI is valid
    }

    private void setButtonTexts(String correctAnswer) {
        // Create a list of possible answers
        List<String> buttonAnswers = new ArrayList<>();
        buttonAnswers.add(correctAnswer);
        while (buttonAnswers.size() < 2) {
            String incorrectAnswer = getRandomIncorrectAnswer();
            if (!buttonAnswers.contains(incorrectAnswer)) {
                buttonAnswers.add(incorrectAnswer);
            }
        }
        // Shuffle answers and set button texts
        Collections.shuffle(buttonAnswers);
        letterA.setText(buttonAnswers.get(0));
        letterB.setText(buttonAnswers.get(1));
    }

    private String getRandomIncorrectAnswer() {
        String incorrectAnswer;
        do {
            incorrectAnswer = answers[random.nextInt(answers.length)];
        } while (incorrectAnswer.equals(currentAnswer)); // Ensure the answer is not correct
        return incorrectAnswer;
    }

    private void checkAnswer(String answer) {
        if (answer.equals(currentAnswer)) {
            loadingIndicator.setVisibility(View.VISIBLE);
            videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));
            correctAnswer.show();
            correctScore++;
        } else {
            wrongAnswer.show();
            wrongScore++;
        }
        updateScores();
    }

    private void updateScores() {
        correct.setText(String.valueOf(correctScore));
        wrong.setText(String.valueOf(wrongScore));
    }
}
