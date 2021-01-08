package com.example.proyecto_delivery;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proyecto_delivery.Utilerias.FragmentDialog;
import com.squareup.picasso.Picasso;

//import android.support.v7.widget.Toolbar;

public class PagoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public static String resultado="hola";
    public static Button btnMunicipio;
    //private Button btnMunicipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        ImageView img=findViewById(R.id.imgPago);
        String imagen="https://www.tiendaidc.es/img/Logos%20de%20Pago.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) img);
        Button btnAceptar=findViewById(R.id.btnPagar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PagoActivity.this,resultado,Toast.LENGTH_SHORT).show();
            }
        });

        btnMunicipio=findViewById(R.id.idMunicipio);
        btnMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 new FragmentDialog().show(getSupportFragmentManager(),"FragmentDialog");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         switch(item.getItemId()){
             case R.id.carrito:
                 Toast.makeText(this,this.resultado,Toast.LENGTH_SHORT);
                 break;
         }
         return true;
    }
}