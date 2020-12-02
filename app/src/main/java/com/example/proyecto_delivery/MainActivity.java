package com.example.proyecto_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txbRegistro = findViewById(R.id.txbRegistro);
        final TextView txbUsuario = findViewById(R.id.txbUsuario);
        final TextView txbContrasena = findViewById(R.id.txbContrasena);
        Button btnSesion=findViewById(R.id.btnSesion);
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(MainActivity.this,ListaInformacion.class);
                startActivity(intn);
                /*
                if(ValidarCampos(new TextView[]{txbUsuario,txbContrasena})){
                    classUsuario usuario=new classUsuario(MainActivity.this);
                    if(usuario.GetUsuario(txbUsuario.getText().toString())){
                        if(txbContrasena.getText().toString().equals(usuario.getPassword())){
                            Intent intn=new Intent(MainActivity.this,ListaInformacion.class);
                            startActivity(intn);
                        }else{
                            Toast.makeText(MainActivity.this,usuario.getNombres()+" tu contraseña es incorrecta !",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"El usuario no existe !",Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
        txbRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(MainActivity.this,RegistroActivity.class);
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