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
        Producto info1 = new Producto();
        info1.setProducto("Banquetazo Familiar");
        info1.setPrecio(19.50);
        info1.setDescripcion("6 alitas empanizadas, 9 alitas fritas, 9 camperitos, 1 orden de trocitos");
        info1.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Web58141.png");

        Producto info2 = new Producto();
        info2.setProducto("Combo Luces 10 Piezas");
        info2.setPrecio((double) 21);
        info2.setDescripcion("Combo de 10 piezas de pollo combinadas, 5 acompañamientos, 5 ensaladas de repollo");
        info2.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/58178WAP.png");

        Producto info3 = new Producto();
        info3.setProducto("Combo Fiesta 10 Piezas Bañadas");
        info3.setPrecio(22.50);
        info3.setDescripcion("Combo de 10 piezas de pollo bañadas, 5 acompañamientos, 5 ensaladas de repollo");
        info3.setImagen("https://i.pinimg.com/originals/20/87/bc/2087bc5431a009b4f55120d34ac06743.jpg");

        Producto info4 = new Producto();
        info4.setProducto("Banquete de 20 Finger Foods + Pizza Gigante 1 Ingrediente");
        info4.setPrecio((double) 17);
        info4.setDescripcion("20 Alitas o camperitos, 3 acompañamientos, 2 aderezos y una pizza gigante de un ingrediente");
        info4.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53909.png");

        Producto info5 = new Producto();
        info5.setProducto("Banquete 30 Alitas + Pizza Gigante 1 Ingrediente");
        info5.setPrecio((double) 20);
        info5.setDescripcion("Banquete 30 alitas, con 4 acompañamientos, 3 aderezos y una pizza grande de un ingrediente.");
        info5.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53992A.png");

        Producto info6 = new Producto();
        info6.setProducto("Banquete 30 Alitas + Pizza Gigante De Especialidad");
        info6.setPrecio((double) 20);
        info6.setDescripcion("Banquete 30 alitas, con 4 acompañamientos, 3 aderezos y una pizza gigante de especialidad");
        info6.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb58013A.png");

        Producto info7 = new Producto();
        info7.setProducto("Banquete 30 Camperitos + Pizza Gigante De Especialidad ");
        info7.setPrecio((double) 22);
        info7.setDescripcion("Banquete 30 Camperitos, con 4 acompañamientos, 3 aderezos y una pizza gigante de especialidad");
        info7.setImagen("https://www.saborusa.com/wp-content/uploads/2019/10/Celebremos-juntos-el-Dia-Nacional-del-pollo-frito-Foto-destacada.png");

        Producto info8 = new Producto();
        info8.setProducto("Combo 10 Piezas Cumpleañero");
        info8.setPrecio(25.95);
        info8.setDescripcion("0 Piezas de pollo tradicional, 5 acompañamientos, 5 panes, pastel de tres leches familiar");
        info8.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53489.png");

        Producto info9 = new Producto();
        info9.setProducto("Combo 6 Piezas + Pizza Gigante Cumpleañero");
        info9.setPrecio(24.95);
        info9.setDescripcion("6 Piezas de pollo tradicional, 3 acompañamientos, 3 panes, pastel de tres leches familiar");
        info9.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53490.png");

        Producto info10 = new Producto();
        info10.setProducto("Combo 6 Piezas cumpleañero");
        info10.setPrecio((double) 18);
        info10.setDescripcion("6 Piezas de pollo tradicional, 3 acompañamientos, 3 panes, 2 pastel chocolate ");
        info10.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53491.png");
        lista.add(info1);
        lista.add(info2);
        lista.add(info3);
        lista.add(info4);
        lista.add(info5);
        lista.add(info6);
        lista.add(info7);
        lista.add(info8);
        lista.add(info9);
        lista.add(info10);

    }
}