package com.level6ninja.crashify;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AgregarReporteActivity extends AppCompatActivity {

    private Integer idUsuario;
    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reporte);

        Intent intent = getIntent();
        this.idUsuario = intent.getIntExtra("idUsuario", 0);
    }


}
