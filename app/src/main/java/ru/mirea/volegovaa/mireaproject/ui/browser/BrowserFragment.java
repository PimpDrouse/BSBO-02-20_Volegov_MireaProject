package ru.mirea.volegovaa.mireaproject.ui.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentBrowserBinding;

public class BrowserFragment extends Fragment {

    private FragmentBrowserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BrowserViewModel browserViewModel = new ViewModelProvider(this).get(BrowserViewModel.class);

        binding = FragmentBrowserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView webView = binding.webView;
        webView.loadUrl("https://habr.com");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}