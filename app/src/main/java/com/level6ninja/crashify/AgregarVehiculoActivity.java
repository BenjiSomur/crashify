package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.level6ninja.crashify.beans.Aseguradora;
import com.level6ninja.crashify.beans.Marca;
import com.level6ninja.crashify.beans.Vehiculo;
import com.level6ninja.crashify.ws.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AgregarVehiculoActivity extends AppCompatActivity {

    private String jsonMarcas;
    private String jsonAseguradoras;
    private Integer idUsuario;
    private ProgressDialog pd_wait;

    private Spinner spinnerMarcas;
    private List<Marca> marcas;
    Type marcasType = new TypeToken<ArrayList<Marca>>(){}.getType();

    private Spinner spinnerAseguradoras;
    private List<Aseguradora> aseguradoras;
    Type aseguradorasType = new TypeToken<ArrayList<Aseguradora>>(){}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vehiculo);

        Intent intent = getIntent();
        this.idUsuario = intent.getIntExtra("idUsuario", 0);

        spinnerMarcas = (Spinner)findViewById(R.id.spinnerMarcas);
        spinnerAseguradoras = (Spinner)findViewById(R.id.spinnerAseguradoras);

        WSPOSTMarcasTask taskMarcas = new WSPOSTMarcasTask();
        taskMarcas.execute();

        WSPOSTAseguradorasTask taskAseguradoras = new WSPOSTAseguradorasTask();
        taskAseguradoras.execute();
    }

    class WSPOSTMarcasTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            jsonMarcas = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.getMarcas();
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            jsonMarcas = result;
            cargarMarcas();
        }
    }

    class WSPOSTAseguradorasTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            jsonAseguradoras = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.getAseguradoras();
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            jsonAseguradoras = result;
            cargarAseguradoras();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pd_wait!=null && pd_wait.isShowing() ){
            pd_wait.cancel();
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

    public void onClickAgregarVehiculo(View views) {

    }

    public void cargarMarcas() {
        hideProgressDialog();
        if (jsonMarcas!= null) {
            marcas = new Gson().fromJson(jsonMarcas, marcasType);
            if (marcas != null) {
                List<String> listaMarcas = new ArrayList<String>();
                for(Marca m : marcas){
                    listaMarcas.add(m.getNombre());
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaMarcas);
                spinnerMarcas.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron marcas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarAseguradoras() {
        hideProgressDialog();
        if (jsonAseguradoras!= null) {
            aseguradoras = new Gson().fromJson(jsonAseguradoras, aseguradorasType);
            if (aseguradoras != null) {
                List<String> listaAseguradoras = new ArrayList<String>();
                for(Aseguradora m : aseguradoras){
                    listaAseguradoras.add(m.getNombre());
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaAseguradoras);
                spinnerAseguradoras.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron marcas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }
}
