package com.example.proyecto_delivery;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Utilerias.Logger;
import com.squareup.picasso.Picasso;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Logger logger=Logger.getInstance();
        //Toast.makeText(PerfilActivity.this,logger.getPassword(),Toast.LENGTH_SHORT).show();
        String imagen="https://i.pinimg.com/originals/64/3e/fe/643efe51394d635cbf544a25088ee269.png";
        ImageView imgPerfilImagen=findViewById(R.id.imgPerfilImagen);
        TextView lblNombre=findViewById(R.id.lblPerfilNombre);
        TextView lblUsuario=findViewById(R.id.lblPerfilUsuario);
        TextView lblTelefono=findViewById(R.id.lblPerfilTelefono);
        TextView lblCorreo=findViewById(R.id.lblPerfilCorreo);
        TextView lblDireccion=findViewById(R.id.lblPerfilDireccion);

        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgPerfilImagen);
        lblNombre.setText(logger.getNombres());
        lblUsuario.setText(logger.getUsuario());
        lblTelefono.setText(logger.getTelefono());
        lblCorreo.setText(logger.getCorreo());
        lblDireccion.setText(logger.getDireccion());
    }
}