package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextSwitcher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AudiobookPlaying extends AppCompatActivity implements View.OnClickListener,
        Runnable, ServiceConnection, SeekBar.OnSeekBarChangeListener {

    private ImageView playBtn,back;
    private boolean isPlaying = false;
    private static final String TAG = AudiobookPlaying.class.getSimpleName();
    private MediaService.MusicController musicController;
    private boolean running;
    private TextSwitcher textSwitcher;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiobook_playing);
        initView();
    }

    void initView()
    {
        back = findViewById(R.id.back_audio_playing);
        back.setOnClickListener(this);
        playBtn = findViewById(R.id.playing_play);
        playBtn.setOnClickListener(this);
        seekBar = findViewById(R.id.music_seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        textSwitcher = findViewById(R.id.text_switcher);
        textSwitcher.setInAnimation(this,android.R.anim.fade_in);
        textSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        Intent intent = new Intent(this, MediaService.class);
        // service for background playing
        startService(intent);
        bindService(intent,this,BIND_AUTO_CREATE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.playing_play:
                if(!isPlaying)
                {
                    playing();
                }
                else
                {
                    musicController.pause();
                    playBtn.setImageResource(R.drawable.start_music);
                    isPlaying = false;
                }
                break;
            case R.id.back_audio_playing:
                Intent intent = new Intent(getApplicationContext(),Audiobook.class);
                startActivity(intent);
                break;

        }

    }

    private void playing()
    {
        playBtn.setImageResource(R.drawable.pause);
        musicController.play();
        isPlaying = true;
    }

    // service start, pause, end
    @Override
    protected void onStart()
    {
        super.onStart();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onStop()
    {
        running = false;
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        unbindService(this);
        super.onDestroy();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
            musicController = ((MediaService.MusicController)service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicController = null;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        musicController.setPosition(seekBar.getProgress());

    }

    // seekbar & time setting
    @Override
    public void run() {
        running = true;
        try {
            while (running)
            {
                if (musicController != null)
                {
                    long musicDuration = musicController.getMusicDuration();
                    final long position = musicController.getPosition();
                    final Date dateTotal = new Date(musicDuration);
                    final SimpleDateFormat sf = new SimpleDateFormat("mm:ss");
                    seekBar.setMax((int)musicDuration);
                    seekBar.setProgress((int)position);
                    textSwitcher.post(new Runnable() {
                        @Override
                        public void run() {
                            Date date = new Date(position);
                            String time = sf.format(date) + "/" + sf.format(dateTotal);
                            textSwitcher.setCurrentText(time);
                        }
                    });
                }
                Thread.sleep(500);
            }
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
