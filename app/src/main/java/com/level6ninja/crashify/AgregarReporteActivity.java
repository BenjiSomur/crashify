package com.level6ninja.crashify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.level6ninja.crashify.beans.Respuesta;
import com.level6ninja.crashify.beans.RespuestaValidacion;
import com.level6ninja.crashify.ws.HttpUtils;

import java.nio.charset.StandardCharsets;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class AgregarReporteActivity extends AppCompatActivity {

    private String json;
    private Integer idUsuario;
    LocationManager locManager;
    private ProgressDialog pd_wait;

    private Double latitud;
    private Double longitud;

    private EditText txt_descripcion;
    private EditText txt_involucrados;

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider,int status,Bundle extras){}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reporte);

        this.txt_descripcion = (EditText)findViewById(R.id.txt_reporteDescripcion);
        this.txt_involucrados = (EditText)findViewById(R.id.txt_reporteInvolucrados);

        Intent intent = getIntent();
        this.idUsuario = intent.getIntExtra("idUsuario", 0);
        Log.v("usuario: ", idUsuario.toString());

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(findViewById(R.id.agregarLayout), "Location permission is needed because ...",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("retry", new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(View view) {
                            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
                        }
                    })
                    .show();
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, locationListener);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            this.latitud = location.getLatitude();
            this.longitud = location.getLongitude();
        }
    }

    private void updateWithNewLocation(Location location) {
        String latLongString = "";
        if (location != null) {
            Log.v("location:", location.toString());
            this.latitud = location.getLatitude();
            this.longitud = location.getLongitude();
            latLongString = "Lat:" + latitud + "\nLong:" + longitud;
        } else {
            latLongString = "No location found";
            Log.v("ha pasado algo raro","pendejo");
        }
    }

    private boolean validar() {
        return !txt_involucrados.getText().toString().isEmpty() && !txt_descripcion.getText().toString().isEmpty();
    }

    public void clickLocation(View view) {
        try{
            if (validar()) {
                String descripcion = txt_descripcion.getText().toString();
                String involucrados = txt_involucrados.getText().toString();
                Log.v("involucrados:",involucrados);
                WSPOSTReporteTask task = new WSPOSTReporteTask();

                task.execute(descripcion, idUsuario.toString(), Double.toString(latitud), Double.toString(longitud), involucrados);
            } else {
                Toast.makeText(this, getString(R.string.acceso_invalido), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Your Current Position is:\n" + "Lat:" + latitud + "\nLong:" + longitud, Toast.LENGTH_SHORT).show();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void reporteGuardado() {
        System.out.println("Holis: " + json);
        hideProgressDialog();
        if (json != null) {
            try{
                Log.v("Objeto recibido",""+ json);
                Respuesta res = new Gson().fromJson(json, Respuesta.class);
                if (res.isError()) {
                    Log.v("Error en WS", "Error: " + res.getMensaje());
                    Toast.makeText(this, res.getMensaje(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Reporte Guardado", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    }

    class WSPOSTReporteTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            return HttpUtils.agregarReporte(params[0], params[1], params[2], params[3], params[4]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json = result;
            reporteGuardado();
        }
    }

    private void showProgressDialog() {
        pd_wait = new ProgressDialog(this);
        pd_wait.setMessage(getString(R.string.acceso_progress_wait));
        pd_wait.setCancelable(false);
        pd_wait.show();
    }

    private void hideProgressDialog() {
        if (pd_wait.isShowing()) {
            pd_wait.hide();
        }
    }

}
