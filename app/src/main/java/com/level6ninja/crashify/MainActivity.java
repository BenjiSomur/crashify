package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.level6ninja.crashify.beans.ReporteDictamen;
import com.level6ninja.crashify.beans.ReporteResumido;
import com.level6ninja.crashify.ws.HttpUtils;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String json;
    private List<ReporteResumido> reportes = new ArrayList<>();
    private Integer idUsuario;
    Type reporteType = new TypeToken<ArrayList<ReporteResumido>>(){}.getType();

    private ListView listReportes;
    private ProgressDialog pd_wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listReportes = (ListView) findViewById(R.id.listReportes);
        this.listReportes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDetalles(position);
                return true;
            }
        });
        Intent intent = getIntent();
        this.idUsuario = intent.getIntExtra("idUsuario", 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAgregarReporte(view);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                verReporte(position);
            }
        });

        WSPOSTReportesTask taskVehiculos = new WSPOSTReportesTask ();
        taskVehiculos.execute(this.idUsuario.toString());
    }

    private void verReporte(int position) {
        Intent i = new Intent(this, DetalleActivity.class);
        i.putExtra("idReporte", reportes.get(position).getIdReporte());
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reportes) {
            // Handle the camera action
        } else if (id == R.id.nav_vehiculos) {
            Intent i = new Intent(this, VehiculosActivity.class);
            i.putExtra("idUsuario", this.idUsuario);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickAgregarReporte(View v) {
        Intent i = new Intent(this, AgregarReporteActivity.class);
        i.putExtra("idUsuario", idUsuario);
        startActivity(i);
    }

    public void mostrarReportes() {
        hideProgressDialog();
        System.out.println(json);
        if (json!= null) {

            try{
                JSONArray array = new JSONArray(json);
                for(int x = 0; x < array.length(); x++){
                    ReporteResumido reporteAux = new Gson().fromJson(array.getJSONObject(x).toString(), ReporteResumido.class);
                    reportes.add(reporteAux);
                }

            }catch(Exception ex){
                Log.v("Error", ex.getStackTrace().toString());
                return;
            }
            if (!reportes.isEmpty()) {
                System.out.println(reportes.get(0).getHora());
                List<String> listadeReportes = new ArrayList<>();
                for(ReporteResumido r : reportes){
                    String hora = r.getHora();
                    if (r.getEstado() == 1) {
                        listadeReportes.add(hora + " Estatus: Pendiente");
                    } else {
                        listadeReportes.add(hora + " Estatus: Dictaminado");
                    }

                }
                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadeReportes);
                listReportes.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron reportes aun", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class WSPOSTReportesTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.getReportes(params[0]);
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            json = result;
            mostrarReportes();
        }
    }

    class WSPOSTDetalleReporteTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute () {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
            return HttpUtils.obtenerDetallesReporte(params[0]);
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            json = result;
            detallesReporte();
        }
    }

    private void mostrarDetalles(int posicion){
       String idReporte = this.reportes.get(posicion).getIdReporte().toString();
        WSPOSTDetalleReporteTask task = new WSPOSTDetalleReporteTask();
        task.execute(idReporte);
    }

    private void detallesReporte() {
        hideProgressDialog();
        ReporteDictamen rd = new Gson().fromJson(json, ReporteDictamen.class);
        System.out.println("funcionó"+rd.getDictamen());
        Intent intent = new Intent(this,DetalleActivity.class);
        intent.putExtra("idReporte", rd.getIdReporte());
        startActivity(intent);
    }
}
