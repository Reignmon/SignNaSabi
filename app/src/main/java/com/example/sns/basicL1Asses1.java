package com.example.sns;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class basicL1Asses1 extends AppCompatActivity {

    Dialog correctAnswer,wrongAnswer,congratsDialog,betterLuck,Loading;
    VideoView videoView;
    LinearLayout aButton, bButton;
    private Uri[] videoUris;
    LottieAnimationView loadingIndicator;
    TextView letterA, letterB;

    private String[] answers;
    private String currentAnswer;
    private int correctScore = 0, wrongScore = 0;
    private Random random = new Random(); // Create a Random instance

    private List<Uri> unplayedVideos; // List to track unplayed videos
    private int videosPlayed = 0; // Counter to track the number of videos played
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    static String name="";
    TextView txt;

    ImageView question1, question2, question3,question4,question5,question6,
            question7,question8,question9,question10;
    private ImageView[] questionImages;
    private int checkImage = R.drawable.check; // Reference to check image
    private int wrongImage = R.drawable.wrong; // Reference to wrong image


    private int currentQuestionIndex = 0; // Track the current question
    static int previousScore = 0,gettotalScore = 0;

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
        letterA = findViewById(R.id.a);
        letterB = findViewById(R.id.b);
        loadingIndicator = findViewById(R.id.loading);
        final TextView btnback = findViewById(R.id.btnback);

        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        question5 = findViewById(R.id.question5);
        question6 = findViewById(R.id.question6);
        question7 = findViewById(R.id.question7);
        question8 = findViewById(R.id.question8);
        question9 = findViewById(R.id.question9);
        question10 = findViewById(R.id.question10);

        questionImages = new ImageView[]{question1, question2, question3,question4,question5,
                question6,question7,question8,question9,question10};

        correctAnswer = new Dialog(basicL1Asses1.this);
        correctAnswer.setContentView(R.layout.correct_answer);
        correctAnswer.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        correctAnswer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        correctAnswer.setCancelable(false);
        final Button btncorrect = correctAnswer.findViewById(R.id.okaybtn);

        betterLuck = new Dialog(basicL1Asses1.this);
        betterLuck.setContentView(R.layout.better_luck_nexttime);
        betterLuck.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        betterLuck.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        betterLuck.setCancelable(false);
        final Button btnbetter = betterLuck.findViewById(R.id.okaybtn);
        txt = betterLuck.findViewById(R.id.txt);

        wrongAnswer = new Dialog(basicL1Asses1.this);
        wrongAnswer.setContentView(R.layout.incorrect_dialog);
        wrongAnswer.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrongAnswer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        wrongAnswer.setCancelable(false);
        final Button btnwrong = wrongAnswer.findViewById(R.id.okaybtn);

        congratsDialog = new Dialog(basicL1Asses1.this);
        congratsDialog.setContentView(R.layout.lesson_complete_dialog);
        congratsDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        congratsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        congratsDialog.setCancelable(false);
        final Button btncongrats = congratsDialog.findViewById(R.id.okaybtn);

        Loading = new Dialog(basicL1Asses1.this);
        Loading.setContentView(R.layout.loading_dialog);
        Loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setCancelable(false);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_EMAIL,null);


        // Setup video view
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        // Define video URIs and corresponding answers
        videoUris = new Uri[]{
                Uri.parse("https://drive.google.com/uc?export=download&id=1XVAx83xHbVcC4ywsDouTxSP11e3_Ctet"), //A
                Uri.parse("https://drive.google.com/uc?export=download&id=1iRirkuZLK7yA6TBq84JbfRJQsYkMnmAn"), //B
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1568TwkQEDQ0tbvb5ivmb8YeZLPfzpqMd"), //S
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


        btncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer.dismiss();
                loadingIndicator.setVisibility(View.GONE);
                videoView.setBackgroundColor(Color.TRANSPARENT);
                updateQuestionImage(checkImage);
                playRandomVideo();
            }
        });

        btnwrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongAnswer.dismiss();
                loadingIndicator.setVisibility(View.GONE);
                videoView.setBackgroundColor(Color.TRANSPARENT);
                updateQuestionImage(wrongImage);
                playRandomVideo();
            }
        });

        btncongrats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the alphabet score
                addScore(() -> {updatelesson();});
            }
        });

        btnbetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loading.show();
                String encodedEmail = encodeEmail(name);
                DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
                usersRef.child("Alphabet").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference lessonaslRef = usersRef.child("Alphabet");
                        DatabaseReference checkalphabetScore = usersRef.child("alphabetscore");
                        checkalphabetScore.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    betterLuck.dismiss();
                                    Loading.dismiss();
                                    startActivity(new Intent(basicL1Asses1.this, basiclevel.class));
                                    finish();
                                }else{
                                    lessonaslRef.setValue(0);
                                    betterLuck.dismiss();
                                    Loading.dismiss();
                                    startActivity(new Intent(basicL1Asses1.this, Lesson1.class));
                                    finish();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Loading.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Loading.dismiss();
                    }
                });
            }
        });
    }

    private void playRandomVideo() {
        if (videosPlayed >= 10) {
            if(correctScore >=8){
                new Handler().postDelayed(() -> {
                    correctAnswer.dismiss();
                    congratsDialog.show();
                }, 500);
            } else {
                new Handler().postDelayed(() -> {
                    wrongAnswer.dismiss();
                    betterLuck.show();
                }, 500);
                new Handler().postDelayed(() -> {
                    txt.setVisibility(View.VISIBLE);
                }, 1500);
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
            loadingIndicator.setVisibility(View.VISIBLE);
            videoView.setBackgroundColor(this.getResources().getColor(R.color.backgroundColor));
            wrongAnswer.show();
            wrongScore++;

        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questionImages.length) {
            playRandomVideo(); // Load the next video
        }
    }


    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

    private void updateQuestionImage(int imageResource) {
        if (currentQuestionIndex < questionImages.length) {
            questionImages[currentQuestionIndex - 1].setImageResource(imageResource);
        }
    }

    private void addScore(Runnable onComplete) {
        Loading.show();

        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);

        // Add a listener to read the previous score
        usersRef.child("alphabetscore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    previousScore = snapshot.getValue(Integer.class);
                    Log.d("UpdateLesson1", "Previous score: " + previousScore);
                }else{
                    usersRef.child("alphabetscore").setValue(correctScore).addOnCompleteListener(task -> {
                        Loading.dismiss();
                        if (task.isSuccessful()) {
                            if (onComplete != null) {
                                onComplete.run(); // Invoke the callback
                            }
                        } else {
                            Log.e("UpdateLesson1", "Failed to update score", task.getException());
                        }
                    });
                }

                if(correctScore > previousScore){
                    // Update the score with the current score
                    usersRef.child("alphabetscore").setValue(correctScore).addOnCompleteListener(task -> {
                        Loading.dismiss();
                        if (task.isSuccessful()) {
                            if (onComplete != null) {
                                onComplete.run(); // Invoke the callback
                            }
                        } else {
                            Log.e("UpdateLesson1", "Failed to update score", task.getException());
                        }
                    });
                }else{
                    Loading.dismiss();
                    startActivity(new Intent(basicL1Asses1.this, basiclevel.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.dismiss();
            }
        });
    }

    private void updatelesson() {
        String encodedEmail = encodeEmail(name);
        DatabaseReference usersRef = databaseReference.child("BasicLevel_tb").child(encodedEmail);
        Loading.show();

        usersRef.child("lessonasl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer currentLessonAslValue = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                int prevscore = previousScore * 2;
                int getTotal = currentLessonAslValue - prevscore;
                int total = getTotal + (correctScore * 2);

                usersRef.child("lessonasl").setValue(total).addOnCompleteListener(task -> {
                    Loading.dismiss();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(basicL1Asses1.this, basiclevel.class));
                        finish();
                    } else {
                        Log.e("UpdateLesson2", "Failed to update lesson", task.getException());
                    }
                });

                Log.d("UpdateLesson1", "Previous score: " + previousScore);
                Log.d("UpdateLesson1", "Current lesson score: " + currentLessonAslValue);
                Log.d("UpdateLesson2", "New lesson score: " + total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Loading.dismiss();
            }
        });
    }
}
