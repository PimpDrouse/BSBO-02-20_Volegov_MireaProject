package ru.mirea.volegovaa.mireaproject.ui.music;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentMusicPlayerBinding;

public class MusicPlayer extends Fragment {

    private FragmentMusicPlayerBinding binding;
    private int play_status = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextCompat.startForegroundService(root.getContext(), new Intent(root.getContext(), MusicService.class));
            }
        });

        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.getContext().stopService(new Intent(root.getContext(), MusicService.class));
            }
        });

        return root;
    }
}