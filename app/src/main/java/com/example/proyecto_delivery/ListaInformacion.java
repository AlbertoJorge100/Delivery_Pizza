package com.example.proyecto_delivery;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorInformacion;
import com.example.proyecto_delivery.Entidades.Informacion;

import java.util.ArrayList;
import java.util.List;

public class ListaInformacion extends AppCompatActivity {
    private List<Informacion> lista=new ArrayList<Informacion>();
    private AdaptadorInformacion adaptador;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacion);
        this.listaInformacion=findViewById(R.id.listInformacion);
        manager=new LinearLayoutManager(this);
        adaptador=new AdaptadorInformacion(lista);
        CargarInformacion();
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Mensaje: "
                        +lista.get(listaInformacion.getChildAdapterPosition(view)).getTitulo(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CargarInformacion(){
        /*for(int i=0;i<15;i++){
            Informacion info=new Informacion();
            info.setTitulo("Titulo"+Integer.toString(i));
            info.setSubTitulo("SubTitulo"+Integer.toString(i));
            info.setDescripcion("Descripcion"+Integer.toString(i));
            info.setImagen("https://developer.android.com/studio/images/write/tools-attribute-context_2x.png?hl=es");
            lista.add(info);
        }*/
        Informacion info=new Informacion();
        info.setTitulo("Promociones");
        info.setSubTitulo("Aprovecha solo hoy !");
        info.setDescripcion("Promociones por tiempo limitado, limitada a cobertura geografica");
        info.setImagen("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/restaurant-burger-bar-promo-facebook-ad-template-design-3d64d2a306a7e6d78ba351c1d7462c78_screen.jpg?ts=1561441072");
        lista.add(info);

        Informacion info2=new Informacion();
        info2.setTitulo("Pizza");
        info2.setSubTitulo("Disfruta tus pizzas de todos tamaños !");
        info2.setDescripcion("Aprovecha tus pizzas de todos los tamaños, a precios accesibles");
        info2.setImagen("https://www.papajohns.com.sv/images/promociones/promo-banq2-19_99-papajohns.jpg");
        lista.add(info2);

        Informacion info3=new Informacion();
        info3.setTitulo("Pollo");
        info3.setSubTitulo("Disfruta de pollo frito, muchos combos para ti !");
        info3.setDescripcion("Aprovecha tus combos de pollo frito, a precios accesibles");
        info3.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53489.png");
        lista.add(info3);

    }

}