package ru.mirea.volegovaa.mireaproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentMyCameraBinding;
import ru.mirea.volegovaa.mireaproject.databinding.FragmentMySensorBinding;

public class MyCameraFragment extends Fragment {
    private FragmentMyCameraBinding binding;

    private long lastTouchTime = 0;
    private long currentTouchTime = 0;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private Uri imageUri;
    private boolean permision = false;
    boolean isDoubleClicked=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyCameraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    binding.getPhoto.setImageURI(imageUri);
                }
            }
        };
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);


        int cameraPermissionStatus = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(android.os.Build.VERSION.SDK_INT >= 32)
        {
            if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED)
            {
                permision = true;
            } else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
            }
        }
        else
        {
            if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED)
            {
                permision = true;
            } else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }

        binding.getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDoubleClicked){
                    isDoubleClicked=false;
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                }else
                {
                    isDoubleClicked=true;
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (permision) {
                        try {
                            File photoFile = createImageFile();
                            String authorities = root.getContext().getApplicationContext().getPackageName() + ".fileprovider";
                            imageUri = FileProvider.getUriForFile(root.getContext(), authorities, photoFile);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            cameraActivityResultLauncher.launch(cameraIntent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        return root;
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "volegov_IMAGE_" + timeStamp + "_";
        File storageDirectory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
}