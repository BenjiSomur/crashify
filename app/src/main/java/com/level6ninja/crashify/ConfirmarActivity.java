package com.level6ninja.crashify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;

public class ConfirmarActivity extends AppCompatActivity {

    public static final Integer PETICION_PERMISO_MENSAJE = 1;
    private String telefono;
    private EditText txt_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        Intent intent = getIntent();
        this.telefono= intent.getStringExtra("telefono");

        Toast.makeText(this, this.telefono, Toast.LENGTH_SHORT).show();

        txt_token = (EditText)findViewById(R.id.txt_token);
        iniciarBroadcast();
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

    public void onClickValidar(View view) {
        Log.v("Holis: ", "Click en Validar");
    }

}
