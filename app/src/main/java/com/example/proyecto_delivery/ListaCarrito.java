package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorCarrito;
import com.example.proyecto_delivery.BaseDatos.RespuestaProductos;
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Clases.classProducto;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Existencias;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity Lista Carrito
 * Se ha creado una Clase "Carrito" donde se toman los datos mas importantes del producto
 * : Id, Nombre, Precio, Imagen, Descripcion y se añaden los campos: cantidad, total. Y para poder
 * manejar la lista de productos se ha creado los componentes en Singleton "Logger" para un mejor
 * manejo. para calcular los totales se ha creado la clase "CalcularTotales" con metodos estaticos
 * para poder, obtener los datos calculados en cualquier momento, y lugar.
 * Por el momento manejado con datos a travez de SQLite
 */
public class ListaCarrito extends AppCompatActivity{

    //Atributos para usar el recyclerview
    /*El adaptador es estatico porque se necesita modificarlo desde la clase
    * "DetalleActivity" en cualquier momento. */
    public static AdaptadorCarrito Adaptador=new AdaptadorCarrito();
    private List<Carrito> lista=new ArrayList<>();
    private LinearLayoutManager manager;
    private RecyclerView ListaCarrito;
    //Validar si hay existencias modificadas en el servidor
    private Boolean ExstMod=false;
    //Ids para poder enviar datos hacia la activity Detalle
    public static final int ID_PAGO_RESULTADO=1;
    public static final String TAG_MSJV="maSJ_J";
    public static final String ID_CANT_LIMITE="canT";
    public static final String ID_INDICE="Indice";
    public static final String ID_PRODUCTO="IdProducto";
    public static final String ID_OPCION="Opcion";
    public static final String ID_IMAGEN="Imagen";
    public static final String ID_TITULO="Titulo";
    public static final String ID_DESCRIPCION="Descripcion";
    public static final String ID_PRECIO="Precio";
    public static final String ID_TOTAL="Total";
    public static final String ID_CANTIDAD="cANT";

    //Id para enviar monto a pagar a PagoActivity
    public static final String ID_PAGO="pag";
    /*TextViews para setear los totales calculados, los que son estaticos
    * son para poder acceder desde la clase "DetalleActivity" y modificarlos
    * desde ahi */
    private TextView lblItems;
    public static TextView lblProductos;
    public static TextView lblTotal;

    //Variable Singleton para poder manejar la lista de productos
    private Logger logger=Logger.getInstance();

    private Button BtnPagar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carrito);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.progressDialog=new ProgressDialog(this);
        this.ListaCarrito=findViewById(R.id.ListaCarritos);

        /*Toast.makeText(ListaCarrito.this,this.lista.get(0).getProducto()
                +" "+this.lista.get(0).getPrecioUnitario(),Toast.LENGTH_SHORT).show();*/

        //El seteo de totales se hace en ActualizarExistencias
        this.lblItems=findViewById(R.id.CarritolblItems);
        this.lblProductos=findViewById(R.id.CarritolblProductos);
        this.lblTotal=findViewById(R.id.Carrito_lblTotal);

        /*Consultar al servidor, si existen modificaciones en las existencias que hemos seleccionado
         * Conexion al recyclerview desde ahi...
         * */
        this.ActualizarExistencias();

        this.BtnPagar=findViewById(R.id.btnPagar);
        //Evento boton pagar
        this.BtnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(ListaCarrito.this,PagoActivity.class);
                //Envio de id
                intn.putExtra(ListaCarrito.this.ID_PAGO,lblTotal.getText().toString());
                //Esperamos que si se concretó el pago, se cierre esta activity
                startActivityForResult(intn,ID_PAGO_RESULTADO);
                /*
                SQLite
                if(ValidarCampos(new TextView[]{txbTarjeta,txbAnio,txbMes,txbCVV})){
                    int idf=ObtenerIdFactura();
                    if(idf>0){
                        for(int i=0;i<lista.size();i++){
                            GuardarProductos(i,idf);
                        }
                        AlertDialog.Builder builder=new AlertDialog.Builder(ListaCarrito.this);
                        builder.setTitle("Informacion de pago");
                        builder.setMessage("El pago fue aceptado con exito !");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                logger.getListaCarrito().clear();
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }else{
                        Toast.makeText(ListaCarrito.this, "Error: El pago no fue aceptado !", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

        //Evento mantener presionado un item de la lista: Eliminar unproducto de la lista
        this.Adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                //Mostrando un AlertDialog para confirmacion
                AlertDialog.Builder builder=new AlertDialog.Builder(ListaCarrito.this);
                builder.setTitle("Confirmacion");
                builder.setMessage("Desea eliminar el item de la lista?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Toast.makeText(ListaCarrito.this, "Presiono aceptar!", Toast.LENGTH_SHORT).show();*/
                        //Validacion si es el ultimo item de la lista para poder cerrar la activity
                        if(ListaCarrito.this.logger.getListaCarrito().size()==1){
                            ListaCarrito.this.finish();
                            Toast.makeText(ListaCarrito.this, "Lista eliminada", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ListaCarrito.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                        }
                        //Eliminando el item accediendo a Singleton: "ListaCarrito.getChildAdapterPosition(view)" esto retorna el indice de la lista
                        ListaCarrito.this.logger.getListaCarrito().remove(ListaCarrito.getChildAdapterPosition(view));
                        /*Modificando el adaptador para mostrar los cambios*/
                        ListaCarrito.this.Adaptador.notifyDataSetChanged();

                        //Recalculando los totales con la clase "CalcularTotales"
                        ListaCarrito.this.lblProductos.setText(Integer.toString(ListaCarrito.this.logger.getCantidadProductos()));
                        ListaCarrito.this.lblTotal.setText("$ "+ListaCarrito.this.logger.getTotalPagar());//Esta funcion retorna un String
                        ListaCarrito.this.lblItems.setText(Integer.toString(ListaCarrito.this.logger.getItems()));
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Presiono cancelar
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;
            }
        });

        //Evento click de un item: "Visualizar detalles"
        this.Adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Modificando la variable de sesion
                /*Toast.makeText(ListaCarrito.this, ListaCarrito.this.lista.get(ListaCarrito.getChildAdapterPosition(view)).getIdProducto(),
                        Toast.LENGTH_SHORT).show();*/
                //Modificando la opcion de acceso a "DetalleActivity" a travez de Singleton
                ListaCarrito.this.logger.setOpcion(Logger._Opcion.MODIFICAR);
                Intent intn=new Intent(ListaCarrito.this, DetalleActivity.class);
                //Envio de datos a travez de los IDS
                int indice=ListaCarrito.getChildAdapterPosition(view);
                Carrito carrito=lista.get(indice);
                intn.putExtra(ID_INDICE,indice);
                intn.putExtra(ID_PRODUCTO,carrito.getIdProducto());
                intn.putExtra(ID_TITULO,carrito.getProducto());
                intn.putExtra(ID_PRECIO,carrito.getPrecioUnitario());
                intn.putExtra(ID_TOTAL,carrito.getTotal());
                intn.putExtra(ID_CANTIDAD,carrito.getCantidad());
                intn.putExtra(ID_IMAGEN,carrito.getImagen());
                intn.putExtra(ID_CANT_LIMITE,carrito.getCant_Limite());
                intn.putExtra(ID_DESCRIPCION,carrito.getDescripcion());
                startActivity(intn);
            }
        });
    }

    /**
     * Al ingresar a esta activity, la app consultara a la api rest, para poder actualizar
     * las existencias y sincronizar las existencias en nuestra app y si las existencias seleccionadas
     * en nuestro carrito de compras se ven afectadas este metodo modificara el stock de nuestro carrito
     * y mostrara el mensaje.
     */
    private void ActualizarExistencias(){
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);

        /*Pasando las existencias de los productos a listaExistencias,
        * para mandarlas a la api rest y validarlas...
        * */
        List<Existencias> listaExistencias=new ArrayList<>();
        for(Carrito carrito: this.logger.getListaCarrito()){
            Existencias existencias=new Existencias();
            existencias.setId(carrito.getIdProducto());
            existencias.setExistencias(carrito.getCantidad());
            listaExistencias.add(existencias);
        }
        try{
            String url=getResources().getString(R.string.UrlAplicacion_local);
            Retrofit retrofit=new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
            Call<RespuestaProductos> call=jsonPlaceHolder.ValidarExistencias(listaExistencias);
            call.enqueue(new Callback<RespuestaProductos>() {
                @Override
                public void onResponse(Call<RespuestaProductos> call, Response<RespuestaProductos> response) {
                    //Modificar las existencias que trae el api rest
                    RespuestaProductos respuesta=response.body();
                    Boolean resp=false;
                    if(response.isSuccessful()){
                        if(respuesta.getCodigo().equals("300") && respuesta.getData().size()>0){
                            resp=true;
                        }
                        //Finalizando el progressdialog
                        progressDialog.dismiss();
                        ModificarCarrito(resp,respuesta.getData());
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(ListaCarrito.this, respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(ListaCarrito.this, response.message(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<RespuestaProductos> call, Throwable t) {
                    Toast.makeText(ListaCarrito.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    ListaCarrito.this.progressDialog.dismiss();
                }
            });
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            ListaCarrito.this.progressDialog.dismiss();
        }
        //Si se elimino todo de la lista de carrito, hay que cerrar la activity
        if(this.logger.getListaCarrito().size()==0){
            this.finish();
        }
    }


    private void ModificarCarrito(Boolean modificar, List<Existencias> existencias){
        if(modificar){
            int idproducto=0, cont=0;
            Carrito carrito=new Carrito();
            String mensaje="";
            for(int i=0;i<existencias.size();i++){
                for(int j=0;j<this.logger.getListaCarrito().size();j++){
                    int id=this.logger.getListaCarrito().get(j).getIdProducto();
                    if(existencias.get(i).getId()==id){
                        carrito=this.logger.getListaCarrito().get(j);
                        mensaje+=(++cont)+" - "+carrito.getProducto()+". Stock: "+existencias.get(i).getExistencias()+"\n";
                        int exst=existencias.get(i).getExistencias();
                        if(exst==0){
                            this.logger.getListaCarrito().remove(j);
                        }else{
                            Carrito car=this.logger.getListaCarrito().get(j);
                            car.setCantidad(exst);
                            car.setCant_Limite(exst);
                            car.setTotal(car.getPrecioUnitario()*exst);
                        }
                        break;
                    }
                }
            }
            //Mostrando la lista de productos
            AlertDialog.Builder builder=new AlertDialog.Builder(ListaCarrito.this);
            builder.setTitle("Aviso");
            builder.setIcon(R.drawable.warning);
            builder.setMessage("Los siguientes productos fueron modificados en el servidor:\n"+mensaje);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }

        //Seteo de totales...
        this.lblItems.setText(Integer.toString(this.logger.getItems()));
        this.lblProductos.setText(Integer.toString(this.logger.getCantidadProductos()));
        this.lblTotal.setText("$ "+this.logger.getTotalPagar());

        //ReciclerView
        manager=new LinearLayoutManager(ListaCarrito.this);
        ListaCarrito.this.Adaptador.setListaCarrito(logger.getListaCarrito());
        ListaCarrito.this.lista=logger.getListaCarrito();//Traspaso de datos singleton
        ListaCarrito.setHasFixedSize(true);
        ListaCarrito.setLayoutManager(manager);
        ListaCarrito.setAdapter(Adaptador);
        //return mensaje;
    }


    /**
     * Obteniendo Id factura de SQLite Pendiente de modificar
     * @return int
     */
    private int ObtenerIdFactura(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String fecha=dateFormat.format(date);
        int respuesta=-1;
        classFactura factura=new classFactura(this);
        Logger logger=Logger.getInstance();
        factura.setIdCliente(logger.getUsuario().getIDUsuario());
        factura.setTotal(Double.parseDouble(this.logger.getTotalPagar()));
        factura.setCantidadProductos(lista.size());
        factura.setFecha(fecha);
        //El metodo Insert de classFactura, retornara el id de la factura "int"
        if(factura.Insert()){
            respuesta=factura.getIdFactura();
        }
        return respuesta;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ID_PAGO_RESULTADO:{//Siempre usar llaves.... :'c
                if(data!=null){
                    Boolean cerrarAct=data.getBooleanExtra(PagoActivity.TAG_MSJ,false);
                    if(cerrarAct){
                        //Cerrar esta activity
                        /*Intent intn=new Intent();
                        intn.putExtra(TAG_MSJV,true);
                        setResult(ListaProducto.ID_CERRAR,intn);*/
                        this.finish();
                    }else{
                        //Hubieron modificaciones... en las cantidades
                        lblTotal.setText("$ "+this.logger.getTotalPagar());
                        //Actualizando los cambios en el carrito de compras
                        this.Adaptador.notifyDataSetChanged();
                    }
                }
                break;
            }
        }
    }

    /*private Boolean ValidarCampos(TextView lista[]){
        Boolean resultado=true;
        for(TextView aux: lista){
            if(aux.getText().toString().equals("")){
                aux.setError("Campo Obligatorio !");
                resultado=false;
            }
        }
        return resultado;
    }*/

    /**
     * Metodo pendiente de modificar
     * @param i
     * @param idfactura
     */
    private void GuardarProductos(int i, int idfactura) {
        //Pendiente de modificar porque han habido muchos cambios
        try{
            classProducto producto = new classProducto(ListaCarrito.this);
            producto.setImagen(lista.get(i).getImagen());
            producto.setProducto(lista.get(i).getProducto());
            producto.setDescripcion(lista.get(i).getDescripcion());
            producto.setPrecio(lista.get(i).getPrecioUnitario());
            producto.setIdFactura(idfactura);
            if (producto.Insert()) {
                //Toast.makeText(this, "Datos Agregados exitosamente !"+(i+1), Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(this, "No fue posible Agregar los datos!", Toast.LENGTH_SHORT).show();
            }
        }catch(SQLiteException ex){
            Toast.makeText(this, "No fue posible Agregar los datos!"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}