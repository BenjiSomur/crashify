package com.level6ninja.crashify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.level6ninja.crashify.ws.HttpUtils;

public class SignUpActivity extends AppCompatActivity {

    private EditText txt_username;
    private EditText txt_telefono;
    private EditText txt_password;
    private EditText txt_confirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.txt_username = (EditText) findViewById(R.id.txt_usernameregistrar);
        this.txt_telefono = (EditText) findViewById(R.id.txt_telefono);
        this.txt_password= (EditText) findViewById(R.id.txt_passwordregistrar);
        this.txt_confirmacion= (EditText) findViewById(R.id.txt_passwordconfirmar);
    }

    public boolean camposInvalidos() {
        return txt_username.getText().toString().isEmpty() || txt_telefono.getText().toString().isEmpty() ||
                txt_password.getText().toString().isEmpty();
    }

    public void registrarUsuarioOnClick(View view) {
        if (camposInvalidos()){
            Toast.makeText(this, getString(R.string.acceso_invalido), Toast.LENGTH_SHORT).show();
        } else {
         //   String res = HttpUtils.regitrar(txt_username.getText().toString(), txt_telefono.getText().toString(), txt_password.getText().toString());
         //   if (res != null) {
                Intent i = new Intent(this, ConfirmarActivity.class);
                startActivity(i);
        //    } else {
                //Toast.makeText(this, getString(R.string.detalle_error), Toast.LENGTH_SHORT).show();
        //    }
        }
    }
}
