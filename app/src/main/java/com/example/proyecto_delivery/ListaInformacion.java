package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.proyecto_delivery.Entidades.Factura;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Categorias;
import com.example.proyecto_delivery.Modelos.Facturas;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Toolbar Librerias
//Otras

public class ListaInformacion extends AppCompatActivity {
    //Variable para cargar las facturas
    public static List<Factura> ListaFactura=new ArrayList<>();
    private Toolbar toolbar;//Toolbar
    private List<Categorias> ListaCategorias=new ArrayList<Categorias>();

    //IDS para enviar informacion a la lista de productos
    public static final String ID_TITULO_PRODUCTO="idP";
    public static final String ID_HISTORIAL="Historial de compras";
    public static final String ID_CATEGORIA="idC";
    public static final String ID_LISTA_FACTURAS="lstFaCt";

    //RecyclerView
    private AdaptadorInformacion adaptador=new AdaptadorInformacion();
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;

    //Singleton
    private Logger logger=Logger.getInstance();

    //ProgressDialog
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informacion);

        this.progressDialog=new ProgressDialog(this);

        //Orientacion de pantalla por defecto
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.listaInformacion=findViewById(R.id.listInformacion);
        /*Accediendo a la lista que envia el login
        * Login: Al encontrar el usuario en la base de datos se hace la consula
        * de las categorias para luego acceder directamente con los datos ya cargados
        * */
        Bundle extra = getIntent().getBundleExtra(Login.LISTA_CATEGORIAS);
        this.ListaCategorias = (ArrayList<Categorias>) extra.getSerializable("lista");

        //Historial de compras sera agregado como una categoria mas
        Categorias categoria=new Categorias();
        categoria.setCategoria(ListaInformacion.this.ID_HISTORIAL);
        categoria.setImagen("https://conoce.cobiscorp.com/hubfs/pagos%20online.jpg");

        //Recyclerview
        this.ListaCategorias.add(categoria);
        this.adaptador.setListaInformacion(ListaInformacion.this.ListaCategorias);
        this.manager=new LinearLayoutManager(ListaInformacion.this);
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(ListaInformacion.this.manager);
        this.listaInformacion.setAdapter(ListaInformacion.this.adaptador);

        //Toolbar
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Principal");

        //Evento presionar un item en el recyclerview
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),"Mensaje: "
                        +lista.get(listaInformacion.getChildAdapterPosition(view)).getCategoria(),Toast.LENGTH_SHORT).show();*/
                Categorias categoria=ListaCategorias.get(listaInformacion.getChildAdapterPosition(view));
                String titulo=categoria.getCategoria();
                if(titulo.equals(ID_HISTORIAL)){
                    //Presionar historial de facturas
                    CargarFacturas();
                }else{
                    //Presiono una categoria de algun producto, se envia el id de la categoria
                    Intent intn=new Intent(ListaInformacion.this, ListaProducto.class);
                    intn.putExtra("ID",titulo);
                    intn.putExtra(ListaInformacion.this.ID_CATEGORIA,categoria.getIDCategoria());
                    intn.putExtra(ListaInformacion.this.ID_TITULO_PRODUCTO,titulo);
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

    /**
     * Cargar las facturas que haya gestionado el usuario
     */
    private void CargarFacturas(){
        //progressdialog que se muestra mientras se ejecuta la peticion
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);
        //Direccion base
        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        //Paso del idusuario
        int idusuario=this.logger.getUsuario().getIDUsuario();
        Call<List<Facturas>> call=jsonPlaceHolder.getFacturas(idusuario);
        call.enqueue(new Callback<List<Facturas>>() {
            @Override
            public void onResponse(Call<List<Facturas>> call, Response<List<Facturas>> response) {
                if(response.isSuccessful()){
                    //Peticion exitosa

                    List<Facturas> lista=response.body();
                    if(lista.size()>0){
                        //Existen facturas para este usuario
                        //Envio de la lista de facturas del usuario
                        Bundle extra = new Bundle();
                        extra.putSerializable("lista",(Serializable)lista);
                        Intent intn=new Intent(ListaInformacion.this, ListaFactura.class);
                        intn.putExtra(ListaInformacion.this.ID_LISTA_FACTURAS,extra);
                        startActivity(intn);
                        //Toast.makeText(ListaInformacion.this, lista.get(0).getEstadoEnvio()+" "+lista.get(0).getProductos(), Toast.LENGTH_SHORT).show();
                    }else{
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
                        //Toast.makeText(ListaInformacion.this, "Vacia" + response.message() + " " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //Toast.makeText(Login.this, "Response error: " + response.message() + " " + response.code(), Toast.LENGTH_SHORT).show();
                }
                ListaInformacion.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Facturas>> call, Throwable t) {
                Toast.makeText(ListaInformacion.this, "onFailure error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                ListaInformacion.this.progressDialog.dismiss();
            }
        });
    }
    /**
     * SQLite pruebas
    private void CargarFacturas(){
        this.ListaFactura.clear();
        try{
            int i=0;
            List <classFactura> listaFactura=new ArrayList<>();
            classFactura factura=new classFactura(this);
            factura.setIdCliente(this.logger.getUsuario().getIDUsuario());
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
     */
    /**
     * Envento presionar la tecla de retroceso
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Al presionar salir si se ha dejado una orden inconclusa la eliminaremos
        if(this.logger.getListaCarrito().size()>=1){
            this.logger.getListaCarrito().clear();
            //this.logger.setUsuario(null);
            this.logger.setGestionado(false);
        }
    }


    /*private void getCategorias(){
        //Libreria similar a retrofit
        AsyncHttpClient client=new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        RequestHandle requestHandler=client.get(getResources().getString(R.string.UrlAplicacion_local) + "Categorias" , null
                , new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        Categorias categorias = null;
                        try {
                            String json = new String(responseBody);
                            JSONObject jsonObject = new JSONObject(json);
                            String jsonVisualizer = "";
                            jsonVisualizer = jsonObject.getJSONObject("data").toString();
                            JSONObject jsonUser = jsonObject.getJSONObject("data");
                            Gson gson = new Gson();
                            Type collectionTypes = new TypeToken<List<Categorias>>() {
                            }.getType();
                            ListaInformacion.this.lista = gson.fromJson(jsonVisualizer, collectionTypes);

                            Toast.makeText(ListaInformacion.this,ListaInformacion.this.lista.get(0).getCategoria()+" \n"
                                    +ListaInformacion.this.lista.get(0).getImagen(),Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaInformacion.this,"Excepcion: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(ListaInformacion.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

}