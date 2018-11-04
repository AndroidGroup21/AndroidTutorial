package software.homework4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "MapActivity";
    static double longitude;
    static double latitude;
    LocationManager locationManager;
    String provider;
    Context cur;
    private FusedLocationProviderClient mFusedLocationProviderClient;
//
//    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
//    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
//    private static final float DEFAULT_ZOOM = 15f;

//    LocationManager locationManager;
//    LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String flag = getIntent().getStringExtra("flag");

        String loc_string = getIntent().getStringExtra("location");
        Log.d(TAG, "geoLocate: geolocating");
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(loc_string, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            String format_address = address.getLocality();
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title("Found Location"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(location)      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Toast.makeText(this,format_address,Toast.LENGTH_SHORT).show();
        }

        cur = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ActivityCompat.checkSelfPermission(cur, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        && (ActivityCompat.checkSelfPermission(cur, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String[] permits = new String[2];
                        permits[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
                        permits[1] = Manifest.permission.ACCESS_FINE_LOCATION;
                        requestPermissions(permits, 10);
                    }
                }
                else {
                    mMap.setMyLocationEnabled(true);
                }
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flag = getIntent().getStringExtra("flag");

                String loc_string = getIntent().getStringExtra("location");
                Log.d(TAG, "geoLocate: geolocating");
                Geocoder geocoder = new Geocoder(MapActivity.this);
                List<Address> list = new ArrayList<>();

                try {
                    list = geocoder.getFromLocationName(loc_string, 1);
                } catch (IOException e) {
                    Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
                }

                if (list.size() > 0) {
                    Address address = list.get(0);

                    String format_address = address.getSubAdminArea();
                    Log.d(TAG, "geoLocate: found a location: " + address.toString());
                    LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).title("Found Location"));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(location)      // Sets the center of the map to location user
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    if ((ActivityCompat.checkSelfPermission(cur, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            && (ActivityCompat.checkSelfPermission(cur, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            String[] permits = new String[2];
                            permits[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
                            permits[1] = Manifest.permission.ACCESS_FINE_LOCATION;
                            requestPermissions(permits, 10);
                        }
                    }
                    else {
                        mMap.setMyLocationEnabled(false);
                    }
                }
            }
        });
    }

}
