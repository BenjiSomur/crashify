package com.level6ninja.crashify;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.level6ninja.crashify.beans.Reporte;
import com.level6ninja.crashify.beans.Respuesta;
import com.level6ninja.crashify.beans.RespuestaReporte;
import com.level6ninja.crashify.beans.RespuestaValidacion;
import com.level6ninja.crashify.ws.HttpUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class AgregarReporteActivity extends AppCompatActivity {

    public static final int REQUEST_CAPTURE = 1;
    public static final int PICK_IMAGE = 100;
    private Uri photoURI;

    private String json = null;
    private Integer imageCount;
    private Integer idUsuario;
    LocationManager locManager;
    private ProgressDialog pd_wait;


    private String idReporteGen;
    private Integer imagenesEnviadas;
    private Double latitud;
    private Double longitud;
    private List<Bitmap> fotosArray = new ArrayList<Bitmap>();

    private EditText txt_descripcion;
    private EditText txt_involucrados;
    private LinearLayout linearLayout1;
    private Button btn_locate;
    private Button btn_evidencia;

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reporte);

        this.txt_descripcion = (EditText) findViewById(R.id.txt_reporteDescripcion);
        this.txt_involucrados = (EditText) findViewById(R.id.txt_reporteInvolucrados);
        this.linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        this.btn_locate = (Button) findViewById(R.id.btn_locate);
        this.btn_evidencia = (Button) findViewById(R.id.btn_evidencia);
        this.btn_locate.setEnabled(false);

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
            Log.v("Cambio de posicion", latLongString);
        } else {
            latLongString = "No location found";
            Log.v("ha pasado algo raro", "pendejo");
        }
    }

    private boolean validar() {
        if (txt_involucrados.getText().toString().isEmpty() && txt_descripcion.getText().toString().isEmpty()) {
            return false;
        }
        Log.v("Mensaje pendejo:", "paso 1");
        if (fotosArray.isEmpty() || this.fotosArray.size() < 4) {
            return false;
        }
        Log.v("Mensaje pendejo:", "paso 2");
        return true;
    }

    public void clickLocation(View view) {
        try {
            this.imagenesEnviadas = 0;
            Log.v("Comienza", "");
            if (validar()) {
                String descripcion = txt_descripcion.getText().toString();
                String involucrados = txt_involucrados.getText().toString();
                Log.v("involucrados:", involucrados);
                WSPOSTReporteTask task = new WSPOSTReporteTask();

                task.execute(descripcion, idUsuario.toString(), Double.toString(latitud), Double.toString(longitud), involucrados);
            } else {
                Toast.makeText(this, getString(R.string.acceso_invalido), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Your Current Position is:\n" + "Lat:" + latitud + "\nLong:" + longitud, Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reporteGuardado() {
        hideProgressDialog();
        if (this.imagenesEnviadas == fotosArray.size()) {
            if (json != null) {
                try {
                    Log.v("Objeto recibido", "" + json);
                    Respuesta res = new Gson().fromJson(json, Respuesta.class);
                    if (res.isError()) {
                        Log.v("Error en WS", "Error: " + res.getMensaje());
                        Toast.makeText(this, res.getMensaje(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Reporte Guardado", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }else{
            imagenesEnviadas++;
            WSPOSTEvidenciasTask task = new WSPOSTEvidenciasTask();
            task.execute(idReporteGen, fotosArray.get(imagenesEnviadas-1));

        }

    }

    private void subirImagenes() {
        String idReporte = null;
        try {
            RespuestaReporte respuestaReporte = new Gson().fromJson(json, RespuestaReporte.class);
            if (respuestaReporte.isError()) {
                Toast.makeText(this, "" + respuestaReporte.getErrorcode().toString() +
                        respuestaReporte.getMensaje(), Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    Reporte reporteAux = new Reporte();
                    JSONObject object = new JSONObject(json);
                    if (object.has("idReporte")) {
                        idReporte = object.get("idReporte").toString();
                        idReporteGen = idReporte;
                    } else {
                        Toast.makeText(this, "Error de la base de datos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (idReporte != null) {
            imagenesEnviadas++;
            Log.v("Reporte: ", idReporte);
            WSPOSTEvidenciasTask task = new WSPOSTEvidenciasTask();
            task.execute(idReporte, fotosArray.get(0));
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
            Log.v("recibido", json);
            subirImagenes();
        }
    }

    class WSPOSTEvidenciasTask extends AsyncTask<Object, String, String> {

        @Override
        protected void onPreExecute() {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground(Object... params) {
            return HttpUtils.subirEvidencia((String) params[0], (Bitmap) params[1]);
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

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        //-------------- Desde camara -------------------------//
        if (requestCode == REQUEST_CAPTURE) {
            if (resultCode == RESULT_OK) {
                ImageView image = new ImageView(AgregarReporteActivity.this);
                image.setImageURI(this.photoURI);
                linearLayout1.addView(image);
                this.fotosArray.add(((BitmapDrawable) image.getDrawable()).getBitmap());
                this.btn_locate.setEnabled(true);
                if (this.fotosArray.size() > 4) {
                    this.btn_locate.setEnabled(true);
                }
                if (fotosArray.size() > 7) {
                    this.btn_evidencia.setEnabled(false);
                }
            }
        }
    }

    public void takePicture(View v) {
        this.btn_locate.setEnabled(false);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File temp = null;
        try {
            temp = this.createTemporalFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (temp != null) {
            this.photoURI = FileProvider.getUriForFile(this,
                    getApplicationContext().getPackageName(), temp);
            i.putExtra(MediaStore.EXTRA_OUTPUT, this.photoURI);
            startActivityForResult(i, REQUEST_CAPTURE);
        }
    }

    private File createTemporalFile() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String name = sdf.format(new Date()) + ".jpg";
        File path = getExternalFilesDir(
                Environment.DIRECTORY_PICTURES + "/tmps"
        );

        return File.createTempFile(name, ".jpg", path);
    }

}
