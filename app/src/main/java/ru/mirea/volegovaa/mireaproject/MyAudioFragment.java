package ru.mirea.volegovaa.mireaproject;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentMyAudioBinding;
import ru.mirea.volegovaa.mireaproject.databinding.FragmentMySensorBinding;


public class MyAudioFragment extends Fragment {

    private FragmentMyAudioBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = MainActivity.class.getSimpleName();
    private boolean permission = false;
    private String fileName = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean isRecording = true;
    boolean isPlaying = true;
    private String recordFilePath = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyAudioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.play.setEnabled(false);
        recordFilePath = (new File(root.getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audiorecordtest.3gp")).getAbsolutePath();


        int storagePermissionStatus = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.RECORD_AUDIO);
        if(android.os.Build.VERSION.SDK_INT > 32) {
            if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED) {
                permission = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
            }
        }
        else
        {
            if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
                permission = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }

        binding.reccord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    binding.reccord.setText("Stop recording");
                    binding.play.setEnabled(false);
                    startRecording();
                } else {
                    binding.reccord.setText("Start recording");
                    binding.play.setEnabled(true);
                    stopRecording();
                }
                isRecording = !isRecording;
            }
        });
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    binding.play.setText("Stop playing");
                    binding.reccord.setEnabled(false);
                    startPlaying();
                } else {
                    binding.play.setText("Start playing");
                    binding.reccord.setEnabled(true);
                    stopPlaying();
                }
                isPlaying = !isPlaying;
            }
        });

        return root;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        recorder.start();
    }
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }



    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
    private void stopPlaying() {
        player.release();
        player = null;
    }
}