package com.gavilan.ubicacionmapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.xml.transform.sax.SAXResult;


public class MainActivity extends AppCompatActivity {

    Button btnUbicacion;
    EditText txtLat, txtLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUbicacion = findViewById(R.id.btnUbicacion);
        txtLat = findViewById(R.id.txtLat);
        txtLon = findViewById(R.id.txtLon);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener las coordenadas del gps y mostrarlas
                obtenerCoordenadas();

            }
        });

    }

    public void obtenerCoordenadas() {
        FusedLocationProviderClient localizador =
                LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},1000 );

            return;
        }
        localizador.getLastLocation().
                addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        txtLat.setText(location.getLatitude() + "");
                        txtLon.setText(location.getLongitude() + "");
                        Intent intent = new Intent(MainActivity.this,
                                MapsActivity.class);
                        intent.putExtra("location",location);
                        startActivity(intent);
                    }
                });
    }


}