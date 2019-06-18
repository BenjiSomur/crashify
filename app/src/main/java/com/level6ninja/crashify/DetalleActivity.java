package com.level6ninja.crashify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.level6ninja.crashify.beans.ReporteDictamen;

public class DetalleActivity extends AppCompatActivity {

    private String json;
    private TextView txtDescripcion;
    private TextView txtPlacas;
    private TextView txtTimeStamp;
    private TextView txtLongitud;
    private TextView txtLatitud;
    private TextView txtDictamen;
    private TextView txtPerito;
    private ReporteDictamen reporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        this.txtDescripcion = findViewById(R.id.txtDescripcion);
        this.txtDictamen = findViewById(R.id.txtDictamen);
        this.txtLatitud = findViewById(R.id.txtLatitud);
        this.txtLongitud = findViewById(R.id.txtLongitud);
        this.txtPerito = findViewById(R.id.txtPerito);
        this.txtPlacas = findViewById(R.id.txtPlacas);
        this.txtTimeStamp = findViewById(R.id.txtTimeStamp);

        Intent intent = getIntent();
        this.json = intent.getStringExtra("Reporte");

        reporte = new Gson().fromJson(json, ReporteDictamen.class);
        if(reporte!=null){
            this.txtDescripcion.setText(reporte.getDescripcion());
            this.txtDictamen.setText(reporte.getDictamen());
            this.txtLatitud.setText(reporte.getLatitud().toString());
            this.txtLongitud.setText(reporte.getLongitud().toString());
            this.txtPerito.setText(reporte.getNombrePerito());
            this.txtPlacas.setText(reporte.getNumPlacas());
            this.txtTimeStamp.setText(reporte.getHora());
        }
    }
}
