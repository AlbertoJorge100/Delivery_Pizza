package com.example.proyecto_delivery;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorProducto;
import com.example.proyecto_delivery.Entidades.Producto;

import java.util.ArrayList;
import java.util.List;

public class ListaProducto extends AppCompatActivity {
    private AdaptadorProducto adaptador;
    private List<Producto>lista=new ArrayList<Producto>();
    private RecyclerView listaProducto;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        manager= new LinearLayoutManager(this);
        listaProducto=findViewById(R.id.listInformacion);
        CargarInformacion();
        adaptador=new AdaptadorProducto(lista);

        this.listaProducto.setHasFixedSize(true);
        this.listaProducto.setLayoutManager(manager);
        this.listaProducto.setAdapter(adaptador);
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Mensaje: "
                        +lista.get(listaProducto.getChildAdapterPosition(view)).getImagen(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void CargarInformacion() {
        Producto info = new Producto();
        info.setProducto("Pizza familiar");
        info.setPrecio(12.50);
        info.setDescripcion("Pizza familiar de 14 porciones + orden de pan con ajo");
        info.setImagen("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/restaurant-burger-bar-promo-facebook-ad-template-design-3d64d2a306a7e6d78ba351c1d7462c78_screen.jpg?ts=1561441072");
        lista.add(info);

        Producto info2 = new Producto();
        info2.setProducto("Pizza personal");
        info2.setPrecio(3.15);
        info2.setDescripcion("Pizza personal incluye soda y una orden de palitroques pequeños");
        info2.setImagen("https://www.papajohns.com.sv/images/promociones/promo-banq2-19_99-papajohns.jpg");
        lista.add(info2);

        Producto info3 = new Producto();
        info3.setProducto("Pizza mediana");
        info3.setPrecio(5.25);
        info3.setDescripcion("Pizza mediana, incluye soda de 2.5 litros");
        info3.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53489.png");
        lista.add(info3);
        lista.add(info2);
        lista.add(info);
        lista.add(info2);
        lista.add(info3);

    }
}