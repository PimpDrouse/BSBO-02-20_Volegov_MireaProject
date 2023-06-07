package ru.mirea.volegovaa.mireaproject;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.volegovaa.mireaproject.databinding.FragmentPlacesBinding;

public class PlacesFragment extends Fragment {

    private FragmentPlacesBinding binding;
    private View root;
    private MapView mapView = null;
    private MyLocationNewOverlay locationNewOverlay;
    private static final int REQUEST_CODE_PERMISSION = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPlacesBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        Configuration.getInstance().load(root.getContext().getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(root.getContext().getApplicationContext()));
        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(root.getContext().getApplicationContext(), new InternalCompassOrientationProvider(root.getContext().getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        int cLOCATION = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fLOCATION = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (cLOCATION == PackageManager.PERMISSION_GRANTED || fLOCATION == PackageManager.PERMISSION_GRANTED) {
            setMyLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
        }

        Marker m1 = new Marker(mapView);
        m1.setPosition(new GeoPoint(55.731241, 37.717757));
        m1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Пятёрочка\n" + "\nЯНижегородская ул., 84", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(m1);
        m1.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        m1.setTitle("Пятёрочка");

        Marker m2 = new Marker(mapView);
        m2.setPosition(new GeoPoint(55.671929, 37.557090));
        m2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Лимончино" + "\nПрофсоюзная ул., 33, стр. 1", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(m2);
        m2.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        m2.setTitle("Лимончино");

        Marker m3 = new Marker(mapView);
        m3.setPosition(new GeoPoint(55.655979, 37.744197));
        m3.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"MiMi by Генацвале" + "\nул. Перерва, 52", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(m3);
        m3.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        m3.setTitle("MiMi");

        Marker m4 = new Marker(mapView);
        m4.setPosition(new GeoPoint(55.655979, 37.744197));
        m4.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Якитория" + "\nул. Перерва, 52", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(m4);
        m4.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        m4.setTitle("Якитория");

        Marker m5 = new Marker(mapView);
        m5.setPosition(new GeoPoint(55.848422, 37.423698));
        m5.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Кафе-бар Улыбка" + "\nТуристская ул., 6, стр. 1", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(m5);
        m5.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        m5.setTitle("Кафе-бар Улыбка");

        return root;
    }
    private void setMyLocation()
    {
        locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(root.getContext().getApplicationContext()), mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);
        locationNewOverlay.runOnFirstFix(new Runnable() {
            public void run() {

                try {
                    double latitude = locationNewOverlay.getMyLocation().getLatitude();
                    double longitude = locationNewOverlay.getMyLocation().getLongitude();
                    Log.d("coord", String.valueOf(latitude));
                    Log.d("coord", String.valueOf(longitude));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IMapController mapController = mapView.getController();
                            mapController.setZoom(15.0);
                            GeoPoint startPoint = new GeoPoint(latitude, longitude);
                            mapController.setCenter(startPoint);
                        }
                    });
                }
                catch (Exception e) {}
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            boolean isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(isWork)
            {
                setMyLocation();
            }
        }
    }
}