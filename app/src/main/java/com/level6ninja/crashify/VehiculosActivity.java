package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.level6ninja.crashify.beans.Vehiculo;
import com.level6ninja.crashify.ws.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VehiculosActivity extends AppCompatActivity {

    private String json;
    private List<Vehiculo> vehiculos;
    private Integer idUsuario;
    Type vehiculosType = new TypeToken<ArrayList<Vehiculo>>(){}.getType();

    private ListView listVehiculos;
    private ProgressDialog pd_wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.listVehiculos = (ListView)findViewById(R.id.listVehiculos);

        Intent intent = getIntent();
        this.idUsuario = intent.getIntExtra("idUsuario", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        WSPOSTVehiculosTask taskVehiculos = new WSPOSTVehiculosTask();
        taskVehiculos.execute(this.idUsuario.toString());
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

    private void cargarListaVehiculos(){
        hideProgressDialog();
        System.out.println(json);
        if (json!= null) {
            vehiculos = new Gson().fromJson(json, vehiculosType);
            if (vehiculos != null) {
                List<String> listaVehiculos = new ArrayList<>();
                for(Vehiculo v : vehiculos){
                    System.out.println("Vehículo:" + v.getNumPlacas());
                    listaVehiculos.add(v.getModelo() + " " + v.getColor());
                }
                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, listaVehiculos);
                listVehiculos.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron vehículos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class WSPOSTVehiculosTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.getVehiculos(params[0]);
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            json = result;
            cargarListaVehiculos();
        }
    }

}
