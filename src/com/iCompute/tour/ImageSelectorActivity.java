package com.iCompute.tour;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ImageSelectorActivity extends Activity implements OnClickListener {

	private final String LOG_TAG="media_activity";
	private final int AUDIO_READY=0;
	private final int AUDIO_REC=-1;
	private final int AUDIO_PLAY=1;
	
	private int mAudioStatus=AUDIO_READY;
	private MediaPlayer   mPlayer = null;
	private MediaRecorder mRecorder = null;

	private String mFileName="stopAudio.3gp";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_selector_layout);
		
		findViewById(R.id.recAudioMediaButton).setOnClickListener(this);
        findViewById(R.id.playAudioMediaButton).setOnClickListener(this);
		
	}
	
	@Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.recAudioMediaButton:
			recordClicked();
			break;
		case R.id.playAudioMediaButton:
			playClicked();
			break;
		}
	}
	
	private void recordClicked()
	{
		if(mAudioStatus==AUDIO_READY)
		{
			mAudioStatus=AUDIO_REC;
			startRecording();
		}
		else if(mAudioStatus==AUDIO_REC)
		{
			mAudioStatus=AUDIO_READY;
			stopRecording();
		}
	}
	private void playClicked()
	{
		if(mAudioStatus==AUDIO_READY)
		{
			mAudioStatus=AUDIO_PLAY;
			startPlaying();
		}
		else if(mAudioStatus==AUDIO_PLAY)
		{
			mAudioStatus=AUDIO_READY;
			stopPlaying();
		}
	}
	
	private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
    	try{
    		
	        mRecorder = new MediaRecorder();
	        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	        mRecorder.setOutputFile(mFileName);
	        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    	}catch(Exception e)
    	{
    		Toast.makeText(this, "Something happened setting up the recorder. Probably mic not availible", Toast.LENGTH_LONG);
    	}
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
