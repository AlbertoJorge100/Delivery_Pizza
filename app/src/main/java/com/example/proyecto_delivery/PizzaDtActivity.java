package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Entidades.Producto;
import com.squareup.picasso.Picasso;

public class PizzaDtActivity extends AppCompatActivity {
     private Double Precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_dt);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final String imagen=getIntent().getStringExtra(ListaProducto.ID_IMAGEN);
        this.Precio=getIntent().getDoubleExtra(ListaProducto.ID_PRECIO,0);
        final String descripcion=getIntent().getStringExtra(ListaProducto.ID_DESCRIPCION);
        final String titulo=getIntent().getStringExtra(ListaProducto.ID_TITULO);

        final ImageView imgProducto=findViewById(R.id.imgProducto);

        TextView lbltitulo=findViewById(R.id.lblTitulo);
        TextView lbldescripcion=findViewById(R.id.lblDescripcion);
        TextView lblPrecio=findViewById(R.id.lblPrecio);
        Button btnAgregar=findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto aux=new Producto();
                aux.setProducto(titulo);
                aux.setDescripcion(descripcion);
                aux.setPrecio(PizzaDtActivity.this.Precio);
                aux.setImagen(imagen);
                ListaInformacion.ListaCarrito.add(aux);
                AlertDialog.Builder builder=new AlertDialog.Builder(PizzaDtActivity.this);
                builder.setTitle("Seleccion");
                builder.setMessage("El producto fue agregado al carrito de compras");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })/*.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })*/;
                AlertDialog dialog=builder.create();
                dialog.show();
                //Toast.makeText(PizzaDtActivity.this,pr.getProducto(),Toast.LENGTH_SHORT).show();
            }
        });
        lbltitulo.setText(titulo);
        lbldescripcion.setText(descripcion);
        lblPrecio.setText("$ "+Double.toString(this.Precio));
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgProducto);

    }
}