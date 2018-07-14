package com.example.akash.helprprototype1;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSelect extends Fragment implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Map Is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lat=latLng.latitude;
                lon=latLng.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Set Position Here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
        zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }
    Geocoder geocoder;
    List<Address> addresses;
    private double lat=0,lon=0;
    private static String FINE_LOCATION=android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COARSE_LOCATION=android.Manifest.permission.ACCESS_COARSE_LOCATION;
    public boolean mLocationPermissionGranted=false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFuseLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final float DEFAULT_ZOOM=17f;
    private ImageView zoom_in,zoom_out,locate;
    private Button map_next;
    public LocationSelect() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView=inflater.inflate(R.layout.fragment_location_select, container, false);
        zoom_in=rootView.findViewById(R.id.map_zoomin);
        zoom_out=rootView.findViewById(R.id.mapzoomout);
        locate=rootView.findViewById(R.id.map_locate);
        map_next=rootView.findViewById(R.id.loaction_select_next);
        Bundle b2=getArguments();
        final String animal=b2.getString("animal");
        map_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lat!=0 && lon!=0){
                    geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try{
                        addresses = geocoder.getFromLocation(lat,lon,1);
                    }catch (Exception e){

                    }
                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String Loc=" Address :"+address+" City :"+city+" State:"+state+" Country:"+country+"PostalCode:"+postalCode;
                    //Toast.makeText(getContext(),Loc,Toast.LENGTH_LONG).show();
                    Bundle LocationBundle =new Bundle();
                    LocationBundle.putString("animal",animal);
                    LocationBundle.putDouble("latitude",lat);
                    LocationBundle.putDouble("longitude",lon);
                    LocationBundle.putString("address",address);
                    LocationBundle.putString("city",city);
                    LocationBundle.putString("state",state);
                    LocationBundle.putString("country",country);
                    LocationBundle.putString("postcode",postalCode);
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    PictureSelect three=new PictureSelect();
                    three.setArguments(LocationBundle);
                    fragmentTransaction.replace(R.id.HelpFrame,three);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(getContext(),"Please, Locate Your Self First",Toast.LENGTH_LONG).show();
                }
            }
        });
        getLocationPermission();
        return rootView;
    }
    private void getDeviceLocation(){
        //Toast.makeText(getContext(),"Hello fuck",Toast.LENGTH_SHORT).show();
        mFuseLocationProviderClient=LocationServices.getFusedLocationProviderClient(getContext());

        try {
            if(mLocationPermissionGranted){
                com.google.android.gms.tasks.Task<Location> location=mFuseLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {
                        if(task.isSuccessful()){
                           // Toast.makeText(getContext(),"Location Found",Toast.LENGTH_SHORT).show();
                            Location currentLocation=(Location)task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM);
                            }else{
                            Toast.makeText(getContext(),"Location Not Found",Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        }catch (SecurityException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void moveCamera(LatLng latlng,float zoom){
        lat=latlng.latitude;
        lon=latlng.longitude;
        mMap.clear();
        //Toast.makeText(getContext(),"movecamera",Toast.LENGTH_LONG).show();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoom));
        MarkerOptions options =new MarkerOptions().position(latlng).title("Your Position");
        mMap.addMarker(options);
    }

    private void initMap(){
        SupportMapFragment mapFragment=(SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(getContext(),FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getContext(),COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(getContext(),"Hello i'm here",Toast.LENGTH_SHORT).show();
                mLocationPermissionGranted=true;
                initMap();
            }
        }else {
            requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Toast.makeText(getContext(),"Hello i'm here",Toast.LENGTH_SHORT).show();
        mLocationPermissionGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++){
                        if (grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            return;
                        }

                    }
                    mLocationPermissionGranted=true;
                    initMap();
                    //initialize our map
                }
            }
        }
    }
}
