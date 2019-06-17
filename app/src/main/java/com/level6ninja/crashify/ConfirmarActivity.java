package com.level6ninja.crashify;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.level6ninja.crashify.beans.RespuestaValidacion;
import com.level6ninja.crashify.ws.HttpUtils;

import java.nio.charset.StandardCharsets;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;

public class ConfirmarActivity extends AppCompatActivity {

    private String json;
    private ProgressDialog pd_wait;

    public static final Integer PETICION_PERMISO_MENSAJE = 1;
    private String telefono;
    private EditText txt_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        Intent intent = getIntent();
        this.telefono= intent.getStringExtra("telefono");

        txt_token = (EditText)findViewById(R.id.txt_token);
        iniciarBroadcast();
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
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(broadcastSMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciarBroadcast() {
        if (tienePermisosMensaje()){
            IntentFilter mIntentFilter = new IntentFilter();
            mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(broadcastSMS, mIntentFilter);
        }
    }

    public boolean tienePermisosMensaje() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{RECEIVE_SMS, READ_SMS}, PETICION_PERMISO_MENSAJE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == PETICION_PERMISO_MENSAJE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarBroadcast();
            }
        }
    }

    private final BroadcastReceiver broadcastSMS = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    //PDU Protocol Data Unit protocolo para dar formato a SMS
                    final Object[]  pdusObj = (Object[]) bundle.get("pdus");
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String texto = sms.getDisplayMessageBody();
                        Log.v("SMS: ", texto);

                        try {
                            // Hola, tu código de MyNOtes es: C0D3
                            int index = texto.indexOf(":");
                            String token = texto.substring(index + 2, index + 6);
                            txt_token.setText(token);
                            // TODO: Invocar el método de confirmar
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                Log.v("SmsReceiver: ", "Exception SmsReceiver " + e);
            }
        }
    };

    private void resultadoEntrar() {
        System.out.println("Holis: " + json);
        hideProgressDialog();
        if (json != null) {
            try{
                Log.v("Objeto recibido",""+ json);
                RespuestaValidacion res = new Gson().fromJson(json, RespuestaValidacion.class);
                if (res.getError().isError()) {
                    Log.v("Error en WS", "Error: " + res.getError().getMensaje());
                    Toast.makeText(this, res.getError().getMensaje(), Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("idUsuario", res.getConductor().getIdConductor());
                    startActivity(i);
                    finish();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickValidar(View view) {
        if (!txt_token.getText().toString().isEmpty()) {
            String token = txt_token.getText().toString();
            WSPOSTConfirmarTask task = new WSPOSTConfirmarTask();
            task.execute(this.telefono, token);
        } else {
            Toast.makeText(this, "Ingrese su token de acceso", Toast.LENGTH_SHORT).show();
        }
    }

    class WSPOSTConfirmarTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            json = null;
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            return HttpUtils.autenticarConductor(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            json = result;
            resultadoEntrar();
        }
    }

}
