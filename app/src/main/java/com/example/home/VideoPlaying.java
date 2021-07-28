package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoPlaying extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;
    private RelativeLayout mVideoLayout;
    private ViewGroup.LayoutParams mVideoViewLayoutParams;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playing);
        videoView = findViewById(R.id.video_view);
        mVideoLayout = findViewById(R.id.video_layout);
        back = findViewById(R.id.back_video_playing);
        back.setOnClickListener(this);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.three_princesses));
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rot = getWindowManager().getDefaultDisplay().getRotation();
        //CNTrace.d("onConfigurationChanged : " + newConfig + ", rot : " + rot);
        if(rot == Surface.ROTATION_90 || rot == Surface.ROTATION_270){
            mVideoViewLayoutParams = mVideoLayout.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mVideoLayout.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }else if(rot == Surface.ROTATION_0){
//            RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(320,240);
//            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoLayout.setLayoutParams(mVideoViewLayoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_video_playing:
                Intent intent = new Intent(getApplicationContext(),Category.class);
                startActivity(intent);
        }
    }
}
