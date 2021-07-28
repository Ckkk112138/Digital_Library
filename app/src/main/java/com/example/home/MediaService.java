package com.example.home;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MediaService extends Service {
    private MediaPlayer mPlayer;

    public  class MusicController extends Binder
    {
        public void play()
        {
            mPlayer.start();
        }
        public void pause()
        {
            mPlayer.pause();
        }
        public long getMusicDuration()
        {
            return mPlayer.getDuration();
        }

        public long getPosition() {
            return mPlayer.getCurrentPosition();
        }
        public void setPosition(int position)
        {
            mPlayer.seekTo(position);
        }
    }

    // callback when binding the service, allow object to control the service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mPlayer = MediaPlayer.create(this,R.raw.music_os);
    }

    @Override
    public  boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
        super.onDestroy();
    }
}
