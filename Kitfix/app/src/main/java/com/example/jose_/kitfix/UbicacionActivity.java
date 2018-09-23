package com.example.jose_.kitfix;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
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
        refaccionarias(googleMap);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
    public void refaccionarias (GoogleMap googleMap){
        mMap = googleMap;
        // Coordenadas del tec. de villahermosa:   final LatLng ITVilla =new LatLng(18.0212266,-92.9087055);
        final LatLng ref1 =new LatLng(18.0184702,-92.9279221);
        final LatLng ref2 =new LatLng(18.0184145,-92.9289482);
        final LatLng ref3 =new LatLng(17.9959497,-92.9297907);
        final LatLng ref4 =new LatLng(17.9817789,-92.9273843);
        final LatLng ref5 =new LatLng(17.9853983,-92.9435049);
        mMap.addMarker(new MarkerOptions().position(ref1).title("Refaccionaria RODVAL"));
        mMap.addMarker(new MarkerOptions().position(ref2).title("Refaccionaria Zaragoza TIERRA"));
        mMap.addMarker(new MarkerOptions().position(ref3).title("Refaccionaria Aries"));
        mMap.addMarker(new MarkerOptions().position(ref4).title("Auto Refaccionaria de Villahermosa"));
        mMap.addMarker(new MarkerOptions().position(ref5).title("Refaccionaria Zaragoza"));

        //En caso de necesitar acercarce a un punto especifico desde el inicio
        //solo colocar esto con el numbre del punto
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nombre del LatLng,14f));
    }
}
