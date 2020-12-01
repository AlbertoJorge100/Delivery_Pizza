package com.example.proyecto_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        TextView txbCuenta=findViewById(R.id.txbCuenta);
        TextView txbPromociones=findViewById(R.id.txbPromociones);
        TextView txbPizzas=findViewById(R.id.txbPizzas);
        TextView txbPollos=findViewById(R.id.txbPollos);
        TextView txbHamburguesas=findViewById(R.id.txbHamburguesas);
        txbCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(PrincipalActivity.this, CuentaActivity.class);
                startActivity(intn);
            }
        });
        txbPromociones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(PrincipalActivity.this,PromocionesActivity.class);
                startActivity(intn);
            }
        });
        txbPizzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(PrincipalActivity.this,PizzaActivity.class);
                startActivity(intn);
            }
        });
        txbPollos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(PrincipalActivity.this,PolloActivity.class);
                startActivity(intn);
            }
        });
        txbHamburguesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(PrincipalActivity.this,HamburguesaActivity.class);
                startActivity(intn);
            }
        });
    }
}