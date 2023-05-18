package ru.mirea.volegovaa.mireaproject;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentMusicPlayerBinding;
import ru.mirea.volegovaa.mireaproject.databinding.FragmentMySensorBinding;


public class MySensorFragment extends Fragment {

    private FragmentMySensorBinding binding;

    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            binding.txt.setText(String.format("%.3f mbar", values[0]));
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMySensorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sensorManager = (SensorManager) root.getContext().getSystemService(SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}