package com.adopcan.adopcan_voluntarios;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.adopcan.adopcan_voluntarios.DTO.Report;
import com.adopcan.adopcan_voluntarios.DTO.Ubication;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.adopcan.adopcan_voluntarios.R.drawable.dog;

public class ReportMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private double lat = 0.0;
    private double lon = 0.0;
    private com.adopcan.adopcan_voluntarios.Utils.AlertDialog alertDialog;
    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        report = (Report)this.getIntent().getExtras().get("report");
        alertDialog = new com.adopcan.adopcan_voluntarios.Utils.AlertDialog();
        setContentView(R.layout.activity_report_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myUbication();
    }

    private void addMarker(double lat, double lon) {
        LatLng coor = new LatLng(lat, lon);
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(coor,17);
        if (marker != null) {
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions().position(coor).title("Mi Ubicación").icon(BitmapDescriptorFactory.fromResource(dog)).draggable(true).snippet("Si querés cambiar la posición arrastrá el marcador"));
        mMap.animateCamera(ubication);
    }

    private void updateUbication(Location location) {

        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            addMarker(lat, lon);
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateUbication(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void myUbication() {

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updateUbication(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,20000,0,locListener);


    }

    private Report getReportWithUbication(){
        report.setLatitude(lat);
        report.setLongitude(lon);

        return report;
    }

    public void reportLostDogDetails(View view){
        if(lat == 0.0 || lon == 0.0){
            alertDialog.showAlertWithAcept(this, "Alerta", "Tenés que tener marcada una ubicación para continuar");
        }else {
            Intent intent = new Intent(this, ReportLostDogDetailsActivity.class);
            intent.putExtra ("report", getReportWithUbication());
            startActivity(intent);
        }
    }
}
