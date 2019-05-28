package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.common.hash.Hashing;
import com.level6ninja.crashify.ws.HttpUtils;


import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    private String json;
    private EditText txt_name;
    private EditText txt_password;

    private ProgressDialog pd_wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_name = (EditText)findViewById(R.id.txt_username);
        txt_password = (EditText)findViewById(R.id.txt_password);
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

    public void entrarOnClick (View view) {
    //    if (validar()) {
    //        String username = txt_name.getText().toString();
    //       String password = txt_password.getText().toString();
    //       WSPOSTLoginTask task = new WSPOSTLoginTask();
    //        String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    //       task.execute(username, sha256hex);
    //   } else {
    //       Toast.makeText(this, getString(R.string.acceso_invalido), Toast.LENGTH_SHORT).show();
    //   }
    }

    public void registrarOnClick (View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    public boolean validar () {
        return !txt_name.getText().toString().isEmpty() || !txt_password.getText().toString().isEmpty();
    }

    //private void resultadoEntrar() {
    //    System.out.println("Holis: " + json);
    //    hideProgressDialog();
    //   if (json!= null) {
    //       RespuestaUsuario res = new Gson().fromJson(json, RespuestaUsuario.class);
    //       if (res.getRespuesta().isError()) {
    //           Log.v("Error en WS", "Error: " + res.getRespuesta().getMensaje());
    //           if (res.getRespuesta().getErrorcode() == 9) {
    //               Toast.makeText(this, getString(R.string.acceso_desconocido), Toast.LENGTH_SHORT).show();
    //           }
    //       } else {
    //           Intent i = new Intent(this, NotasActivity.class);
    //           i.putExtra("idUsuario", res.getUsuario().getIdUsuario().toString());
    //            startActivity(i);
    //        }
    //    }
    //}

    class WSPOSTLoginTask extends AsyncTask <String, String, String> {

        @Override
        protected void onPreExecute () {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground (String ... params) {
               return HttpUtils.login(params[0], params[1]);
        }

        @Override
        protected void onPostExecute (String result) {
            super.onPostExecute(result);
            json = result;
            System.out.println("Holis");
        //    resultadoEntrar();
        }
    }
}