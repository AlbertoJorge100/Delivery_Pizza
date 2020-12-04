package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorInformacion;
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Entidades.Factura;
import com.example.proyecto_delivery.Entidades.Informacion;
import com.example.proyecto_delivery.Entidades.Producto;

import java.util.ArrayList;
import java.util.List;

//Toolbar Librerias
//Otras

public class ListaInformacion extends AppCompatActivity {
    //Variables de sesion.... de manera temporal xq ya no tengo tiempo
    public static List<Producto> ListaCarrito=new ArrayList<Producto>();
    public static List<Factura> ListaFactura=new ArrayList<>();
    public static String Nombres;
    public static int IdCliente;
    public static String Telefono;
    public static String Correo;
    public static String Direccion;
    public static String Usuario;
    Toolbar toolbar;
    private List<Informacion> lista=new ArrayList<Informacion>();
    public static final String ID_POLLO="Pollo";
    public static final String ID_HAMBURGUESA="Hamburguesas";
    public static final String ID_PIZZA="Pizzas";
    public static final String ID_PROMOCION="Promociones";
    public static final String ID_HISTORIAL="Historial de compras";
    private AdaptadorInformacion adaptador;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacion);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.listaInformacion=findViewById(R.id.listInformacion);
        manager=new LinearLayoutManager(this);
        adaptador=new AdaptadorInformacion(lista);
        CargarInformacion();
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);

        //Toolbar
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Principal");


        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),"Mensaje: "
                        +lista.get(listaInformacion.getChildAdapterPosition(view)).getTitulo(),Toast.LENGTH_SHORT).show();*/
                String titulo=lista.get(listaInformacion.getChildAdapterPosition(view)).getTitulo();
                if(titulo.equals(ID_HISTORIAL)){
                    CargarFacturas();
                    if(ListaFactura.size()>0){
                        Intent intn=new Intent(ListaInformacion.this, ListaFactura.class);
                        startActivity(intn);
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(ListaInformacion.this);
                        builder.setTitle("Mensaje");
                        builder.setMessage("Todavia no ha realizado compras");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }
                }else{
                    Intent intn=new Intent(ListaInformacion.this, ListaProducto.class);
                    intn.putExtra("ID",titulo);
                    startActivity(intn);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.carrito:
                if(ListaInformacion.ListaCarrito.size()>0){
                    Intent intn=new Intent(ListaInformacion.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(ListaInformacion.this);
                    builder.setTitle("Mensaje");
                    builder.setMessage("No hay productos agregados al carrito");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
            case R.id.persona:
                Intent intn=new Intent(ListaInformacion.this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
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
        info.setTitulo(ID_PROMOCION);
        info.setSubTitulo("Disfruta de las mejores ofertas que tenemos para ti!");
        info.setDescripcion("Promociones por tiempo limitado, limitada a cobertura geografica");
        info.setImagen("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/restaurant-burger-bar-promo-facebook-ad-template-design-3d64d2a306a7e6d78ba351c1d7462c78_screen.jpg?ts=1561441072");
        lista.add(info);

        Informacion info1=new Informacion();
        info1.setTitulo(ID_HAMBURGUESA);
        info1.setSubTitulo("Disfruta hamburguesas de todos precios y todos los gustos !");
        info1.setDescripcion("Promociones por tiempo limitado, limitada a cobertura geografica");
        info1.setImagen("https://sevilla.abc.es/gurme/wp-content/uploads/sites/24/2014/10/hamburguesas-960x540.jpg");
        lista.add(info1);

        Informacion info2=new Informacion();
        info2.setTitulo(ID_PIZZA);
        info2.setSubTitulo("Disfruta tus pizzas de todos tamaños !");
        info2.setDescripcion("Aprovecha tus pizzas de todos los tamaños, a precios accesibles");
        info2.setImagen("https://www.papajohns.com.sv/images/promociones/promo-banq2-19_99-papajohns.jpg");
        lista.add(info2);

        Informacion info3=new Informacion();
        info3.setTitulo(ID_POLLO);
        info3.setSubTitulo("Disfruta de pollo frito, muchos combos para ti !");
        info3.setDescripcion("Aprovecha tus combos de pollo frito, a precios accesibles");
        info3.setImagen("https://s3.amazonaws.com/ldm-src/Campero/test/Combo8Mixtos.jpg");
        lista.add(info3);

        Informacion info4=new Informacion();
        info4.setTitulo(ID_HISTORIAL);
        info4.setSubTitulo("Historial de todas tus compras realizadas!");
        info4.setDescripcion("Puedes verificar tus pedidos, realizados anteriormente");
        info4.setImagen("https://blog.cobiscorp.com/hubfs/pagos%20online.jpg");
        lista.add(info4);

    }
    private void CargarFacturas(){
        this.ListaFactura.clear();
        try{
            int i=0;
            List <classFactura> listaFactura=new ArrayList<>();
            classFactura factura=new classFactura(this);
            factura.setIdCliente(ListaInformacion.this.IdCliente);
            listaFactura = factura.GetAllFacturas();
            for(classFactura fct : listaFactura ){
                Factura fct2=new Factura();
                fct2.setIdFactura(fct.getIdFactura());
                fct2.setFecha(fct.getFecha());
                fct2.setCantidad(fct.getCantidadProductos());
                fct2.setTotal(fct.getTotal());
                this.ListaFactura.add(fct2);
                //Toast.makeText(this, pro.getProducto()+" "+(++i), Toast.LENGTH_SHORT).show();
            }
        }catch (SQLiteException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}