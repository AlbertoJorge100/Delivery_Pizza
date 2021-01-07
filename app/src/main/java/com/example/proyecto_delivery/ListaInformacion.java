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
import com.example.proyecto_delivery.Utilerias.Logger;

import java.util.ArrayList;
import java.util.List;

//Toolbar Librerias
//Otras

public class ListaInformacion extends AppCompatActivity {
    //Variable para cargar las facturas
    public static List<Factura> ListaFactura=new ArrayList<>();
    private Toolbar toolbar;//Toolbar
    private List<Informacion> lista=new ArrayList<Informacion>();

    //IDS para enviar informacion a la lista de productos
    public static final String ID_POLLO="Pollo";
    public static final String ID_HAMBURGUESA="Hamburguesas";
    public static final String ID_PIZZA="Pizzas";
    public static final String ID_PROMOCION="Promociones";
    public static final String ID_HISTORIAL="Historial de compras";

    //RecyclerView
    private AdaptadorInformacion adaptador;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;

    //Singleton
    private Logger logger=Logger.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacion);
        //Orientacion de pantalla por defecto
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.listaInformacion=findViewById(R.id.listInformacion);

        //Recyclerview
        manager=new LinearLayoutManager(this);
        adaptador=new AdaptadorInformacion(lista);
        CargarInformacion();//Cargando la informacion quemada
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);

        //Toolbar
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Principal");

        //Evento presionar un item en el recyclerview
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),"Mensaje: "
                        +lista.get(listaInformacion.getChildAdapterPosition(view)).getTitulo(),Toast.LENGTH_SHORT).show();*/
                String titulo=lista.get(listaInformacion.getChildAdapterPosition(view)).getTitulo();
                if(titulo.equals(ID_HISTORIAL)){
                    CargarFacturas();
                    //Presionar historial de facturas
                    if(ListaFactura.size()>0){
                        //Si existen facturas
                        Intent intn=new Intent(ListaInformacion.this, ListaFactura.class);
                        startActivity(intn);
                    }else{
                        //No existen en el servidor
                        AlertDialog.Builder builder=new AlertDialog.Builder(ListaInformacion.this);
                        builder.setTitle("Mensaje");
                        builder.setMessage("Todavia no ha realizado compras");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Presiono aceptar
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }
                }else{
                    /*Toast.makeText(getApplicationContext(),"Mensaje: "
                            +lista.get(listaInformacion.getChildAdapterPosition(view)).getIdMenu()+"",Toast.LENGTH_SHORT).show();*/
                    //Se selecciona una categoria de la lista
                    Intent intn=new Intent(ListaInformacion.this, ListaProducto.class);
                    intn.putExtra("ID",titulo);
                    startActivity(intn);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Menu del toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        //item.add
        switch(item.getItemId()){
            //Si se presiona el carrito de compras
            case R.id.carrito:
                if(this.logger.getListaCarrito().size()>0){
                    //Si existen productos en el carrito
                    Intent intn=new Intent(ListaInformacion.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    //No existen
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
                //Presionar perfil de usuario
            case R.id.persona:
                Intent intn=new Intent(this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
    }

    //Datos quemados de prueba
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
        info.setIdMenu(0);
        info.setTitulo(ID_PROMOCION);
        info.setImagen("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/restaurant-burger-bar-promo-facebook-ad-template-design-3d64d2a306a7e6d78ba351c1d7462c78_screen.jpg?ts=1561441072");
        lista.add(info);

        Informacion info1=new Informacion();
        info1.setIdMenu(1);
        info1.setTitulo(ID_HAMBURGUESA);
        info1.setImagen("https://sevilla.abc.es/gurme/wp-content/uploads/sites/24/2014/10/hamburguesas-960x540.jpg");
        lista.add(info1);

        Informacion info2=new Informacion();
        info2.setIdMenu(2);
        info2.setTitulo(ID_PIZZA);
        info2.setImagen("https://www.marketingdirecto.com/wp-content/uploads/2018/06/telepizza-1.jpg");
        lista.add(info2);

        Informacion info3=new Informacion();
        info3.setIdMenu(3);
        info3.setTitulo(ID_POLLO);
        info3.setImagen("https://s3.amazonaws.com/ldm-src/Campero/test/Combo8Mixtos.jpg");
        lista.add(info3);

        Informacion info4=new Informacion();
        info4.setIdMenu(4);
        info4.setTitulo(ID_HISTORIAL);
        info4.setImagen("https://blog.cobiscorp.com/hubfs/pagos%20online.jpg");
        lista.add(info4);

    }

    //Cargamos las facturas desde SQLite o del web services
    private void CargarFacturas(){
        this.ListaFactura.clear();
        try{
            int i=0;
            List <classFactura> listaFactura=new ArrayList<>();
            classFactura factura=new classFactura(this);
            factura.setIdCliente(this.logger.getIdUsuario());
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Al presionar salir si se ha dejado una orden inconclusa la eliminaremos
        if(this.logger.getListaCarrito().size()>=1){
            this.logger.getListaCarrito().clear();
        }
    }
}