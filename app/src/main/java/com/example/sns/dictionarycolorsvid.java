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

public class dictionarycolorsvid extends AppCompatActivity {
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
        setContentView(R.layout.activity_dictionarycolorsvid);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1druvr0fVw5-jw-3qt7jVVUh4lLuFkxLP"), //black
                Uri.parse("https://drive.google.com/uc?export=download&id=1Vs-mG0UPpYbc9vJS3JdDhvm_EHyGehpT"), //brown
                Uri.parse("https://drive.google.com/uc?export=download&id=1Vs-mG0UPpYbc9vJS3JdDhvm_EHyGehpT"), //blue
                Uri.parse("https://drive.google.com/uc?export=download&id=1t9V-vpFlakcytwk6Jb6f8-cUHnZxmAlA"), //gold
                Uri.parse("https://drive.google.com/uc?export=download&id=1EaztsA3nvdzsdKXQgsOoeD222_dxVs8t"), //gray
                Uri.parse("https://drive.google.com/uc?export=download&id=1EkcAC20t7UqQTHe22n0tw6Mkienx994e"), //green
                Uri.parse("https://drive.google.com/uc?export=download&id=1LWUnFnSKS7gm_pwHoZDgzUHmx4_LTQsA"), //orange
                Uri.parse("https://drive.google.com/uc?export=download&id=1sRWgl3t365JRT5bTql3CAPBDzeITVg8l"), //pink
                Uri.parse("https://drive.google.com/uc?export=download&id=1GBIpKKD05UbF3ryfvvFJme4hy0IetGXj"), //purple
                Uri.parse("https://drive.google.com/uc?export=download&id=1KVos9Tfx7GdH6sxu_cpqoE6RJUN0PwWZ"), //red
                Uri.parse("https://drive.google.com/uc?export=download&id=1BceO-AvCTuAOYCkE-CBrqiSsgM2P__Qn"), //white
                Uri.parse("https://drive.google.com/uc?export=download&id=1-pflDP3zBxWBGELh1PfblfDKVqVK1U9t"), //yellow

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
                startActivity(new Intent(dictionarycolorsvid.this, dictionarycolors.class));
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