package com.level6ninja.crashify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {

    private Integer idReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Intent intent = getIntent();
        this.idReporte = intent.getIntExtra("idReporte", 0);

        Toast.makeText(this, idReporte.toString(), Toast.LENGTH_SHORT).show();
    }
}
