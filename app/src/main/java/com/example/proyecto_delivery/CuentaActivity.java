package com.example.proyecto_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        TextView txbPerfil=findViewById(R.id.txbPerfil);
        TextView txbTarjeta=findViewById(R.id.txbTarjeta);
        TextView txbSalir=findViewById(R.id.txbSalir);
        txbPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(CuentaActivity.this,PerfilActivity.class);
                startActivity(intn);
            }
        });
        txbTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(CuentaActivity.this, TarjetaActivity.class);
                startActivity(intn);
            }
        });
        txbSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(CuentaActivity.this,PrincipalActivity.class);
                startActivityForResult(intn,3);
            }
        });
    }
}