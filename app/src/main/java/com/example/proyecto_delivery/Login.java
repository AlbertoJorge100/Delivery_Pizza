package com.example.proyecto_delivery;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Clases.classUsuario;
import com.example.proyecto_delivery.Utilerias.Hash;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView txbRegistro = findViewById(R.id.txbRegistro);
        final TextView txbUsuario = findViewById(R.id.txbUsuario);
        final TextView txbContrasena = findViewById(R.id.txbContrasena);
        Button btnSesion=findViewById(R.id.btnSesion);

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCampos(new TextView[]{txbUsuario,txbContrasena})){
                    classUsuario usuario=new classUsuario(Login.this);
                    if(usuario.GetUsuario(txbUsuario.getText().toString())){
                        //Encriptacion de contraseña ingresada x el usuario
                        String contraseña= Hash.generarHash(txbContrasena.getText().toString(),Hash.SHA256);
                        if(contraseña.equals(usuario.getPassword())){
                            Intent intsn=new Intent(Login.this,ListaInformacion.class);
                            startActivity(intsn);
                            ListaInformacion.Nombres=usuario.getNombres();
                            ListaInformacion.Correo=usuario.getCorreo();
                            ListaInformacion.Direccion=usuario.getDireccion();
                            ListaInformacion.Telefono=usuario.getTelefono();
                            ListaInformacion.Usuario=usuario.getUsuario();
                            ListaInformacion.IdCliente=usuario.getIdUsuario();
                        }else{
                            Toast.makeText(Login.this,usuario.getNombres()+" tu contraseña es incorrecta !",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this,"El usuario no existe !",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        txbRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(Login.this,RegistroActivity.class);
                startActivity(intn);

            }
        });
    }
    private Boolean ValidarCampos(TextView []datos){
        Boolean resultado=true;
        for(TextView aux:datos){
            if(aux.getText().toString().equals("")){
                resultado=false;
                aux.setError("Campo obligatorio !");
            }
        }
        return resultado;
    }
}