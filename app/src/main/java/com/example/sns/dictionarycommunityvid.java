package com.example.sns;

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

public class dictionarycommunityvid extends AppCompatActivity {
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
        setContentView(R.layout.activity_dictionarycommunityvid);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1a92kea3kTx4k7yiSaRwCZAsjoT2LNZ9y"), //actor
                Uri.parse("https://drive.google.com/uc?export=download&id=15KEbGfDPQF7SuL26r26yTSruBfQUNldE"), //athlete
                Uri.parse("https://drive.google.com/uc?export=download&id=15QMYp8yxd8tnxH6D28RO9n57A1tlY65x"), //auntie
                Uri.parse("https://drive.google.com/uc?export=download&id=1kenfQgmQgQMbZOCgq-QshxfvipiXkb3Y"), //brother
                Uri.parse("https://drive.google.com/uc?export=download&id=1JH6aNgC2OnvfPBkUut-89IE8UQAZycx2"), //brother-in-law
                Uri.parse("https://drive.google.com/uc?export=download&id=1smuS5bR8WHzNxi_HQgApq1kAy4e2KUJ8"), //chief
                Uri.parse("https://drive.google.com/uc?export=download&id=1WD_cBJ_ZzR-8LGThBviSIpBvJzkZxzhL"), //cousin
                Uri.parse("https://drive.google.com/uc?export=download&id=1GEXTsGSxfx2WLqT5sBJcuFD93bfppUId"), //daughter
                Uri.parse("https://drive.google.com/uc?export=download&id=1O11CbKwEt-DjZ0bD0KWUiAGKQxji06Tt"), //doctor
                Uri.parse("https://drive.google.com/uc?export=download&id=1NA844jL7_iEfSZw5anBfD3WVF56tVtXg"), //engineer
                Uri.parse("https://drive.google.com/uc?export=download&id=1d0ZzKlGNG2OE6M0YZ2_pV8fOz2_SsphT"), //entrepreneur
                Uri.parse("https://drive.google.com/uc?export=download&id=15FzoamvM7TS1fMXfbfnjw35wEkXfZ4VI"), //farmer
                Uri.parse("https://drive.google.com/uc?export=download&id=1dZJwi5uhuJgknGuRrTzTt7Vs7TGB0c1B"), //father
                Uri.parse("https://drive.google.com/uc?export=download&id=1f5V_lcuWgnvw71wq_Wp1OMbfaIPc0xYS"), //grandfather
                Uri.parse("https://drive.google.com/uc?export=download&id=1woXZKLrJzq4Lr1nd4bi7xOzClrpPKapk"), //grandmother
                Uri.parse("https://drive.google.com/uc?export=download&id=11TI3n77ZuweF_Usw2nLorehO0Plv64OC"), //lawyer
                Uri.parse("https://drive.google.com/uc?export=download&id=1jDd7_RbLfAhtdk9kOwb6HLnLhVFY_yiz"), //mother
                Uri.parse("https://drive.google.com/uc?export=download&id=1lJsw2SKj6SwdpimRejg9h-WbrLvjLGNy"), //nephew
                Uri.parse("https://drive.google.com/uc?export=download&id=1fTovHp3SayDDTeGn5cQbAs5pQD7M65fj"), //neice
                Uri.parse("https://drive.google.com/uc?export=download&id=14P9Qf1zmqkNdM9BLLy3JTbsq3A7NtPom"), //nurse
                Uri.parse("https://drive.google.com/uc?export=download&id=1ILWz3WfYVETYbNDt0Me0UJAA1kWmK03j"), //pilot
                Uri.parse("https://drive.google.com/uc?export=download&id=1Ni2T99P8AzAdFme_fhn3YNu9pFSdpvZV"), //police officer
                Uri.parse("https://drive.google.com/uc?export=download&id=1FVqLE1qKasPe1eNFVJvLB2o_8unoJCrQ"), //principal
                Uri.parse("https://drive.google.com/uc?export=download&id=1JYazH2FMppQjG_COCI6Lo52VfRcZkMHm"), //scientist
                Uri.parse("https://drive.google.com/uc?export=download&id=1jvPZMBOYjxAhpr3DdSEqqx7hhvL5dZ6b"), //shoulder
                Uri.parse("https://drive.google.com/uc?export=download&id=1uhG-_Kd8ESyip_7Vx-IJd372ULeLOg6M"), //sister
                Uri.parse("https://drive.google.com/uc?export=download&id=17_esmWBm8-UFWMZsRi21XjtbI9gs7sdZ"), //sister-in-law
                Uri.parse("https://drive.google.com/uc?export=download&id=1j6-Hu5gaY0xHTLo5v3aGHbPRpTv_0YSn"), //son
                Uri.parse("https://drive.google.com/uc?export=download&id=1xYvAFzIrgTgLMyWrno8nBAkpXVCWYE2P"), //step brother
                Uri.parse("https://drive.google.com/uc?export=download&id=15TBKvGiqT0r70wDzMy4rlDCD1Ga731aU"), //step sister
                Uri.parse("https://drive.google.com/uc?export=download&id=1i_8TJCZrbGHLKeoDyP8qDMhSO5B0KWC5"), //step mother
                Uri.parse("https://drive.google.com/uc?export=download&id=1nL8pNRCiPlOygHSU7Kah9sa0y8lYbGXZ"), //teacher
                Uri.parse("https://drive.google.com/uc?export=download&id=1UrMtsFjiuQxT9rEfXgDK8o3rzx16AMQf"), //uncle

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
                startActivity(new Intent(dictionarycommunityvid.this, dictionarycommunity.class));
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