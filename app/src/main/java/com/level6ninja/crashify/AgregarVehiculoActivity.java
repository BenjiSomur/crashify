package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.level6ninja.crashify.beans.Aseguradora;
import com.level6ninja.crashify.beans.Marca;
import com.level6ninja.crashify.beans.Respuesta;
import com.level6ninja.crashify.beans.Vehiculo;
import com.level6ninja.crashify.ws.HttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AgregarVehiculoActivity extends AppCompatActivity {

    private String jsonMarcas;
    private String jsonAseguradoras;
    private String jsonRespuesta;
    private Integer idUsuario;
    private ProgressDialog pd_wait;
    private ArrayAdapter adapterMarcas;
    private ArrayAdapter adapterAseg;
    private Spinner spinnerMarcas;
    private List<Marca> marcas;
    private EditText txt_vehiculoPlacas;
    private EditText txt_vehiculoModelo;
    private EditText txt_vehiculoColor;
    private EditText txt_vehiculoYear;
    private EditText txt_vehiculoPoliza;
    private EditText txt_fechaVencimiento;
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
        this.txt_vehiculoColor = (EditText)findViewById(R.id.txt_vehiculoColor);
        this.txt_vehiculoModelo = (EditText)findViewById(R.id.txt_vehiculoModelo);
        this.txt_vehiculoPlacas = (EditText)findViewById(R.id.txt_vehiculoPlacas);
        this.txt_vehiculoYear = (EditText)findViewById(R.id.txt_vehiculoYear);
        this.txt_vehiculoPoliza = (EditText)findViewById(R.id.txt_vehiculoPoliza);
        this.txt_fechaVencimiento = (EditText)findViewById(R.id.txt_fechaVencimiento);

        this.txt_fechaVencimiento.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(txt_fechaVencimiento.getText().toString().trim().length()==4){
                            txt_fechaVencimiento.setText(txt_fechaVencimiento.getText().toString().trim()+"-");
                            txt_fechaVencimiento.setSelection(txt_fechaVencimiento.getText().toString().trim().length());
                        }else if(txt_fechaVencimiento.getText().toString().trim().length()==7){
                            txt_fechaVencimiento.setText(txt_fechaVencimiento.getText().toString().trim()+"-");
                            txt_fechaVencimiento.setSelection(txt_fechaVencimiento.getText().toString().trim().length());
                        }
                    }
                }
        );

        WSPOSTMarcasTask taskMarcas = new WSPOSTMarcasTask();
        taskMarcas.execute();

        hideProgressDialog();
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

    class WSPOSTVehiculoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            jsonRespuesta = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.regitrarVehiculo(params[0],params[1],params[2],params[3],params[4],
                    params[5],params[6],params[7],params[8]);
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            jsonRespuesta = result;
            mostrarRespuesta();
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
        Integer idAseguradoraNumber = adapterAseg.getPosition(this.spinnerAseguradoras.getSelectedItem())+1;
        Integer idMarcaNumber = adapterMarcas.getPosition(this.spinnerMarcas.getSelectedItem())+1;
        String numPlacas = this.txt_vehiculoPlacas.getText().toString().trim();
        String idMarca=idMarcaNumber.toString();
        String modelo = this.txt_vehiculoModelo.getText().toString().trim();
        String color = this.txt_vehiculoColor.getText().toString().trim();
        String year = this.txt_vehiculoYear.getText().toString().trim();
        String numPoliza = this.txt_vehiculoPoliza.getText().toString().trim();
        String idAseguradora = idAseguradoraNumber.toString();
        String fechaVencimiento = this.txt_fechaVencimiento.getText().toString().trim();
        WSPOSTVehiculoTask task = new WSPOSTVehiculoTask();
        task.execute(numPlacas,idMarca,modelo,color,year,numPoliza,idAseguradora,fechaVencimiento,this.idUsuario.toString());

    }

    public void cargarMarcas() {
        if (jsonMarcas!= null) {
            marcas = new Gson().fromJson(jsonMarcas, marcasType);
            if (marcas != null) {
                List<String> listaMarcas = new ArrayList<String>();
                for(Marca m : marcas){
                    listaMarcas.add(m.getNombre());
                }
                adapterMarcas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaMarcas);
                spinnerMarcas.setAdapter(adapterMarcas);
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
                adapterAseg = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaAseguradoras);
                spinnerAseguradoras.setAdapter(adapterAseg);
            } else {
                Toast.makeText(this, "No se encontraron marcas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarRespuesta(){
        if(this.jsonRespuesta!=null){
            try{
                Respuesta res = new Gson().fromJson(this.jsonRespuesta, Respuesta.class);
                if(res.isError()){
                    Toast.makeText(this, ""+res.getMensaje(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Vehiculo guardado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
