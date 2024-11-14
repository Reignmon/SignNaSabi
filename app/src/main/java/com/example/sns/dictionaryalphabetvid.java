package com.example.sns;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Field;

public class dictionaryalphabetvid extends AppCompatActivity {
    int getvalue;
    TextView btnBack;
    VideoView videoView;
    private Uri[] videoUris;
    ImageView Play,Restart,Slow;
    boolean play = true,slowmotion = false;
    private int playimg = R.drawable.playbutton;
    private int pauseimg = R.drawable.videopausebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionaryalphabetvid);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        videoView = findViewById(R.id.video);
        btnBack = findViewById(R.id.btnback);
        Play = findViewById(R.id.play);
        Restart = findViewById(R.id.restart);
        Slow = findViewById(R.id.slow);

        Intent intent = getIntent();
        getvalue = intent.getIntExtra("alphabet", -1);

        //https://drive.google.com/file/d/1gFccyNedPiKJbcfNw4dcp5wYMt0fxtPs/view?usp=sharing
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1ipvYoGkMjcgoLBrRVL8LuWXiQ5RrX1Qm"), //L
                Uri.parse("https://drive.google.com/uc?export=download&id=1G_LtQQCytHQVdoHG47At-Wq64Gk6pb82"), //M
                Uri.parse("https://drive.google.com/uc?export=download&id=1oFfGAKT4y7_lPnqeYwY4ZdhTKKJB2ZMV"), //N
                Uri.parse("https://drive.google.com/uc?export=download&id=18sjDhRQclb6tv-_pBHL0GDbVGImEdCI8"), //O
                Uri.parse("https://drive.google.com/uc?export=download&id=1g90rSEKfs-DOab9qocW7bV7kiF0lk7Rz"), //P
                Uri.parse("https://drive.google.com/uc?export=download&id=18iKVqpCgD0jeH3L-eX_5UXZM3S71vpq-"), //Q
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

        // Set MediaController for video
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.GONE);
        videoView.setMediaController(mediaController);

        if (getvalue >= 0 && getvalue < videoUris.length) {
            videoView.setVideoURI(videoUris[getvalue]);
            videoView.setBackgroundColor(Color.TRANSPARENT);
            videoView.start();
        }

        final com.airbnb.lottie.LottieAnimationView loadingAnimation = findViewById(R.id.loading);
        loadingAnimation.setVisibility(View.VISIBLE);

        videoView.setOnPreparedListener(mp -> {
            videoView.setBackgroundColor(Color.TRANSPARENT);
            loadingAnimation.setVisibility(View.GONE);
        });

        videoView.setOnCompletionListener(mp -> {
            videoView.setVideoURI(videoUris[getvalue]);
            videoView.start();
        });


        videoView.setOnInfoListener((mp, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                loadingAnimation.setVisibility(View.VISIBLE);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                loadingAnimation.setVisibility(View.GONE);
            }
            return false;
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dictionaryalphabetvid.this, dictionaryalphabet.class));
                finish();
            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (play) {
                    videoView.pause();
                    Play.setImageResource(playimg);
                } else {
                    videoView.start();
                    Play.setImageResource(pauseimg);
                }
                scaleView(Play);
                play = !play;
            }
        });

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(0);
                videoView.start();
                scaleView(Restart);
            }
        });

        Slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Field field = VideoView.class.getDeclaredField("mMediaPlayer");
                    field.setAccessible(true);
                    MediaPlayer mediaPlayer = (MediaPlayer) field.get(videoView);

                    if (mediaPlayer != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        PlaybackParams params = new PlaybackParams();
                        if (slowmotion) {
                            params.setSpeed(1.0f);
                            slowmotion = false;
                        } else {
                            params.setSpeed(0.5f);
                            slowmotion = true;
                        }
                        mediaPlayer.setPlaybackParams(params);
                    } else {
                        Toast.makeText(view.getContext(), "Slow-motion not supported on this Android version", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to access MediaPlayer", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                scaleView(Slow);
            }

        });


    }

    private void scaleView(View view) {
        // Create ObjectAnimators for scaling down (shrink)
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f); // Shrink X-axis to 80%
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f); // Shrink Y-axis to 80%
        scaleDownX.setDuration(100);
        scaleDownY.setDuration(100);

        // Create ObjectAnimators for scaling back up to original size
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f); // Scale back to original size (X)
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f); // Scale back to original size (Y)
        scaleUpX.setDuration(100);
        scaleUpY.setDuration(100);

        // Create an AnimatorSet to play the animations sequentially
        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.play(scaleDownX).with(scaleDownY); // Play scaleDownX and scaleDownY together
        scaleSet.play(scaleUpX).with(scaleUpY).after(scaleDownX); // Play scaleUpX and scaleUpY after scaleDownX/Y
        scaleSet.start();
    }


}