package ru.mirea.volegovaa.mireaproject.ui.hobbys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentHobbysBinding;

public class HobbyFragment extends Fragment {

    private FragmentHobbysBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HobbyViewModel homeViewModel =
                new ViewModelProvider(this).get(HobbyViewModel.class);

        binding = FragmentHobbysBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}