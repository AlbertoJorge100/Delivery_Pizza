package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.proyecto_delivery.Adaptadores.AdaptadorProducto;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Productos;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Toolbar Librerias

public class ListaProducto extends AppCompatActivity {
    //Constante para el requestcode de la lsita de carritos
    public static final int ID_CERRAR=1;
    //Ids para enviar los datos a "DetalleActivity"
    public static final String ID_PRODUCTO="idproducto";
    public static final String ID_IMAGEN="imagen";
    public static final String ID_TITULO="titulo";
    public static final String ID_DESCRIPCION="descripcion";
    public static final String ID_PRECIO="precio";
    public static final String ID_CANTIDAD="cantss";
    //private List<Producto>lista=new ArrayList<>();
    //Progrres dialog para mostrar el progreso del web services
    ProgressDialog progressDialog;

    //Para el pullrefresh
    SwipeRefreshLayout swipeRefreshLayout;
    //Adaptador
    private AdaptadorProducto Adaptador=new AdaptadorProducto();
    private List<Productos>ListaProductos=new ArrayList<Productos>();
    private RecyclerView ReciclerV;
    LinearLayoutManager Manager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressDialog=new ProgressDialog(this);

        ReciclerV=findViewById(R.id.listInformacion);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(ListaInformacion.ID_TITULO_PRODUCTO));

        //Id de la categoria previamente cargado
        int idcategoria=getIntent().getIntExtra(ListaInformacion.ID_CATEGORIA,0);

        //Cargando los productos
        CargarProductos(idcategoria,false);

        //Evento on click del adaptador, (Presionando un producto)
        this.Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Cargando los datos para ver el detalle del producto en la siguiente activity
                Productos producto=ListaProducto.this.ListaProductos.get(ReciclerV.getChildAdapterPosition(view));
                Intent intn=new Intent(ListaProducto.this, DetalleActivity.class);
                //Seteo de datos hacia "DetallActivity"
                intn.putExtra(ID_PRODUCTO,producto.getIDProducto());
                intn.putExtra(ID_IMAGEN,producto.getImagen());
                intn.putExtra(ID_TITULO,producto.getProducto());
                intn.putExtra(ID_DESCRIPCION,producto.getDescripcion());
                intn.putExtra(ID_PRECIO,producto.getPrecio());
                intn.putExtra(ID_CANTIDAD,producto.getExistencias());
                startActivityForResult(intn,ID_CERRAR);
            }
        });

        //Pull to refresh
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CargarProductos(getIntent().getIntExtra(ListaInformacion.ID_CATEGORIA,0),true);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ID_CERRAR:
                if(data!=null){
                    Toast.makeText(ListaProducto.this,"Cerrar!",Toast.LENGTH_SHORT).show();
                    Boolean resultado=data.getBooleanExtra(ListaCarrito.TAG_MSJV,false);
                    if(resultado){
                        Toast.makeText(ListaProducto.this,"Cerrar!",Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                }
                break;

        }
    }

    /**
     * Menu del Toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.carrito:
                //Presiono carrito de compras
                Logger logger=Logger.getInstance();
                if(logger.getListaCarrito().size()>0){
                    Intent intn=new Intent(ListaProducto.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(ListaProducto.this);
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
                //Ver el perfil de la persona
                Intent intn=new Intent(ListaProducto.this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
    }
    /*public void ModificarLista(List<Productos> lista){
        for(Productos prod1:ListaProductos){
            for(Productos prod2:lista){
                if(prod1.getIDProducto()==prod2.getIDProducto() && prod2.getExistencias()<prod1.getExistencias()){
                    prod1.setExistencias(prod2.getExistencias());
                }
            }
        }
    }*/

    /**
     * Caragndo los productos del web services
     * @param idcategoria
     * @param refresh if true: Presiono pull to refresh
     */
    private void CargarProductos(int idcategoria, final Boolean refresh){
        try{
            //Mostrando progreso
            progressDialog.show();
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            String url=getResources().getString(R.string.UrlAplicacion_local);
            Retrofit retrofit=new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
            Call<List<Productos>> call=jsonPlaceHolder.getProductos(idcategoria);
            call.enqueue(new Callback<List<Productos>>() {
                @Override
                public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                    if(response.isSuccessful()){
                        //Peticion exitosa
                        ListaProducto.this.ListaProductos=response.body();
                        if(!refresh) {
                            ListaProducto.this.Adaptador.setListaInformacion(ListaProducto.this.ListaProductos);
                            ListaProducto.this.Manager= new LinearLayoutManager(ListaProducto.this);
                            ListaProducto.this.ReciclerV.setHasFixedSize(true);
                            ListaProducto.this.ReciclerV.setLayoutManager(ListaProducto.this.Manager);
                            ListaProducto.this.ReciclerV.setAdapter(ListaProducto.this.Adaptador);
                            //ListaProducto.this.Adaptador.notifyDataSetChanged();
                        }else{
                            //Presiono pull to refresh
                            //ModificarLista(response.body());
                            ListaProducto.this.Adaptador.notifyDataSetChanged();
                            ListaProducto.this.swipeRefreshLayout.setRefreshing(false);
                        }
                    }else{
                        Toast.makeText(ListaProducto.this,"Error onResponse: "+response.message(),Toast.LENGTH_SHORT).show();
                    }
                    ListaProducto.this.progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<List<Productos>> call, Throwable t) {
                    Toast.makeText(ListaProducto.this, "Error onFailure: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    ListaProducto.this.progressDialog.dismiss();
                }
            });
        }catch(Exception e){
            Log.d("Exc",e.getMessage());
            this.progressDialog.dismiss();
        }
    }

    /**
     * Despues de refrescar el reciclerview puede que en el servidor hayan disminuido las existencias de los productos
     * y si alguno de nuestros productos en el carrito de compras, tienen existencias mayores a las que el servidor nos
     * devuelve, procederemos a modificar esas existencias.
     */
    private void RefreshExistencias(){
        Logger logger=Logger.getInstance();
        List<Carrito> lista=logger.getListaCarrito();
        int cont=0;
        if(lista.size()>0){
            for(Carrito carrito:lista){
                for(Productos producto:this.ListaProductos){
                    if(carrito.getIdProducto()==producto.getIDProducto()){
                        int cant=producto.getExistencias();
                        if(carrito.getCantidad()>cant){
                            carrito.setCantidad(cant);
                            cont++;
                        }
                    }
                }
            }
            if(cont>0){
                Toast.makeText(ListaProducto.this,
                        cont+" Productos fueron modificados en tu carrito de compras ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Insertar productos al web services
     * Usado nada mas para pruebas....
     * @param prod
     * @param precio
     * @param existencias
     * @param imagen
     * @param descripcion
     * @param idcategoria
     */
    private void InsertProductos(String prod, double precio, int existencias
    , String imagen, String descripcion, int idcategoria){
        Productos producto=new Productos();
        producto.setProducto(prod);
        producto.setPrecio(precio);
        producto.setExistencias(existencias);
        producto.setImagen(imagen);
        producto.setDescripcion(descripcion);
        producto.setIDCategoria(idcategoria);
        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        Call<Productos>call=jsonPlaceHolder.AddProductos(producto);
        call.enqueue(new Callback<Productos>() {
            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                //Toast.makeText(ListaProducto.this,"Agregado",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Productos> call, Throwable t) {

            }
        });
    }



}