package com.example.proyecto_delivery;


import android.app.ProgressDialog;
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
import com.example.proyecto_delivery.Utilerias.Logger;

public class Login extends AppCompatActivity {
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView txbRegistro = findViewById(R.id.txbRegistro);
        final TextView txbUsuario = findViewById(R.id.txbUsuario);
        final TextView txbContrasena = findViewById(R.id.txbContrasena);
        txbUsuario.setText("admin");
        txbContrasena.setText("admin");
        Button btnSesion=findViewById(R.id.btnSesion);

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCampos(new TextView[]{txbUsuario,txbContrasena})){
                    classUsuario usuario=new classUsuario(Login.this);
                    if(usuario.GetUsuario(txbUsuario.getText().toString())){
                        //Encriptacion de contraseña ingresada x el usuario
                        String pass=txbContrasena.getText().toString();
                        String password= Hash.generarHash(pass,Hash.SHA256);
                        if(password.equals(usuario.getPassword())){
                            Intent intsn=new Intent(Login.this,ListaInformacion.class);
                            startActivity(intsn);
                            //Singleton fill
                            Logger logger=Logger.getInstance();
                            logger.setIdUsuario(usuario.getIdUsuario());
                            logger.setNombres(usuario.getNombres());
                            logger.setUsuario(usuario.getUsuario());
                            logger.setPassword(password);
                            logger.setPassword2(pass);
                            logger.setCorreo(usuario.getCorreo());
                            logger.setDireccion(usuario.getDireccion());
                            logger.setTelefono(usuario.getTelefono());

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
    public void download(View view){
        progress=new ProgressDialog(this);
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
}