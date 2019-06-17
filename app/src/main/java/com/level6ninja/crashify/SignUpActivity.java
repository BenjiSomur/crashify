package com.level6ninja.crashify;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.level6ninja.crashify.beans.Respuesta;
import com.level6ninja.crashify.beans.RespuestaValidacion;
import com.level6ninja.crashify.ws.HttpUtils;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    DatePickerDialog picker;
    private ProgressDialog pd_wait;
    private String json;

    private EditText txt_username;
    private EditText txt_telefono;
    private EditText txt_password;
    private EditText txt_confirmacion;
    private EditText txt_licencia;
    private EditText txt_fechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.txt_username = (EditText) findViewById(R.id.txt_usernameregistrar);
        this.txt_telefono = (EditText) findViewById(R.id.txt_telefonoregistrar);
        this.txt_password = (EditText) findViewById(R.id.txt_passwordregistrar);
        this.txt_confirmacion = (EditText) findViewById(R.id.txt_passwordconfirmar);
        this.txt_licencia = (EditText) findViewById(R.id.txt_licencia);
        this.txt_fechaNacimiento = (EditText) findViewById(R.id.txt_fechaNacimiento);
        txt_fechaNacimiento.setInputType(InputType.TYPE_NULL);
        txt_fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txt_fechaNacimiento.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private boolean camposInvalidos() {
        return txt_username.getText().toString().isEmpty() || txt_telefono.getText().toString().isEmpty() ||
                txt_password.getText().toString().isEmpty();
    }

    private boolean passwordMatch() {
        return txt_password.getText().toString().equals(txt_confirmacion.getText().toString());
    }

    public void registrarUsuarioOnClick(View view) {
        if (camposInvalidos()){
            Toast.makeText(this, getString(R.string.acceso_invalido), Toast.LENGTH_SHORT).show();
        } else {
            if (!passwordMatch()) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                String username = txt_username.getText().toString();
                String telefono = txt_telefono.getText().toString();
                String password = txt_password.getText().toString();
                String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
                String fechaNacimiento = txt_fechaNacimiento.getText().toString();
                String licencia = txt_licencia.getText().toString();
                WSPOSTRegisterTask task = new WSPOSTRegisterTask();
                task.execute(username, telefono, sha256hex, fechaNacimiento, licencia);
            }
            //String res = HttpUtils.regitrarConductor(txt_username.getText().toString(), txt_telefono.getText().toString(), txt_password.getText().toString());
            //if (res != null) {
            //    Intent i = new Intent(this, ConfirmarActivity.class);
            //    startActivity(i);
            //} else {
                //Toast.makeText(this, getString(R.string.detalle_error), Toast.LENGTH_SHORT).show();
            //}
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

    private void resultadoRegistrar() {
        hideProgressDialog();
        if (json!= null) {
            Respuesta res = new Gson().fromJson(json, Respuesta.class);
            if (res.isError()) {
                Log.v("Error en WS", "Error: " + res.getMensaje());
                Toast.makeText(this, res.getMensaje(), Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(this, ConfirmarActivity.class);
                i.putExtra("telefono", txt_telefono.getText().toString());
                startActivity(i);
                finish();
            }
        }
    }

    class WSPOSTRegisterTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            return HttpUtils.regitrarConductor(params[0], params[1], params[2], params[3], params[4]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json = result;
            resultadoRegistrar();
        }
    }
}
