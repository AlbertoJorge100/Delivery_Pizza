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
        Button btnSesion=findViewById(R.id.btnSesion);
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn =new Intent(MainActivity.this, ListaInformacion.class);
                startActivity(intn);


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
}