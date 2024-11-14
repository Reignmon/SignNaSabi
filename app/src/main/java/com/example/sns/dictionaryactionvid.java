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

public class dictionaryactionvid extends AppCompatActivity {
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
        setContentView(R.layout.activity_dictionaryactionvid);
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
                Uri.parse("https://drive.google.com/uc?export=download&id=1bPFyqFVcA_e7-d438kU0KFRVdXwqHnjA"), //act
                Uri.parse("https://drive.google.com/uc?export=download&id=1zrd6guuWJuW7rB-5LXXZh9R13-_QEyOU"), //bow
                Uri.parse("https://drive.google.com/uc?export=download&id=1pDNrWoiu45NHJKBKHNLFCsMGcK69O4UY"), //buy
                Uri.parse("https://drive.google.com/uc?export=download&id=1dmO4Css4U08zYDIidnA-3xt3cgI98JOY"), //clean
                Uri.parse("https://drive.google.com/uc?export=download&id=1pRg0nYOkBx4jUhuIaIILd-Ygff6dF8s1"), //cook
                Uri.parse("https://drive.google.com/uc?export=download&id=11EAX7xr0lzJtD4RHLAKlu__PCoh5uMNK"), //cry
                Uri.parse("https://drive.google.com/uc?export=download&id=1IRM3Q2w094_o-HJ2I2qAUNFhx8D3zI7V"), //cut
                Uri.parse("https://drive.google.com/uc?export=download&id=1fOV8zPrc6sKMkL_-aki5dTv7cZTVfCCv"), //dance
                Uri.parse("https://drive.google.com/uc?export=download&id=1MOPiF41LKcTAt6rtub98InSFb-5c4_E2"), //dig
                Uri.parse("https://drive.google.com/uc?export=download&id=1EjsVLJpkObn_5d41C0eLGLnPzad5naNh"), //drink
                Uri.parse("https://drive.google.com/uc?export=download&id=1Kjo5_vn5h0KHIYjucigpgy5F_jYW7FxE"), //draw
                Uri.parse("https://drive.google.com/uc?export=download&id=1gqMc4Oasiwqfw-2P3zAiMMu2l1t34lna"), //dream
                Uri.parse("https://drive.google.com/uc?export=download&id=1nIvojigIrKHfYhlx9HVUSr5KRyp8upp_"), //eating
                Uri.parse("https://drive.google.com/uc?export=download&id=1ZUDmpTCOF5bLROBzzx0ztBP_OazT4A1u"), //fight
                Uri.parse("https://drive.google.com/uc?export=download&id=1n_tpR_Jea3Gu26me8gIJmbORvXfkLlnU"), //fly
                Uri.parse("https://drive.google.com/uc?export=download&id=11lZzc8isgeEuYbmKGGsoF71cIRbmRtF9"), //laugh
                Uri.parse("https://drive.google.com/uc?export=download&id=165MbMnW51XLWuMVYL5DUvPrgnAeVTb55"), //listen
                Uri.parse("https://drive.google.com/uc?export=download&id=17bOcWGK92InlSDe2PvG0BE9ixDjOUWGQ"), //play
                Uri.parse("https://drive.google.com/uc?export=download&id=1zW_lGfdRlOXGgIsY1040FQ7ada2EVTV_"), //push
                Uri.parse("https://drive.google.com/uc?export=download&id=1QjE_VQHgRYD5fM-GbmayRgmL5xm_WOoK"), //read
                Uri.parse("https://drive.google.com/uc?export=download&id=1EBY0raKxAv-J_UrTIXvuekaVDB9PFO-s"), //ride
                Uri.parse("https://drive.google.com/uc?export=download&id=1GTk1sxPp0bXmx5B4uCp1rs5P35V01kZB"), //to ride in
                Uri.parse("https://drive.google.com/uc?export=download&id=1LiuNzegsAFCt5xW92fbM1aCfCg3AXWEO"), //to ride on
                Uri.parse("https://drive.google.com/uc?export=download&id=1a-kME-0mQBI8XW2SRYRtAxA1xTD1CIeG"), //sing
                Uri.parse("https://drive.google.com/uc?export=download&id=16ruiE6UTVRZxjgfxSFN5PEgyne7OBM9v"), //sit down
                Uri.parse("https://drive.google.com/uc?export=download&id=1o09cR5GJju7zre-6AstAD-910yRAHwtP"), //sleep
                Uri.parse("https://drive.google.com/uc?export=download&id=1j5lbAmTZ-Jeyigpe1L9EuYDgACpCboFJ"), //smile
                Uri.parse("https://drive.google.com/uc?export=download&id=14DpXuay7sfRU29etE5Ga3s_YkuxkpCXe"), //study
                Uri.parse("https://drive.google.com/uc?export=download&id=1UYKFt3-qVU5wNhHYv3Wif7FGDS6c0if1"), //talk
                Uri.parse("https://drive.google.com/uc?export=download&id=1KB2r0B9lMdXxvUH4XLumgpQtyZ2y2kdh"), //think
                Uri.parse("https://drive.google.com/uc?export=download&id=1yysv_2Rc5YShQrEWe2LZM46vJqLDQe5a"), //wait
                Uri.parse("https://drive.google.com/uc?export=download&id=1xVbCu1x3A5pwTrm7TaE7Wn9-MmBqk5Gb"), //wash
                Uri.parse("https://drive.google.com/uc?export=download&id=1rfl8D7f3plokm08ieo9rHUTRIcksm5D7"), //write

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
                startActivity(new Intent(dictionaryactionvid.this, dictionaryactionword.class));
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