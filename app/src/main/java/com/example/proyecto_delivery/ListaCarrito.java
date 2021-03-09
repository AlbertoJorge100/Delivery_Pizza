package com.example.proyecto_delivery;

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
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Clases.classProducto;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public AdaptadorCarrito Adaptador=new AdaptadorCarrito();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carrito);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ReciclerView
        manager=new LinearLayoutManager(this);
        this.ListaCarrito=findViewById(R.id.ListaCarritos);
        this.Adaptador.setListaCarrito(this.logger.getListaCarrito());
        this.lista=logger.getListaCarrito();//Traspaso de datos singleton
        this.ListaCarrito.setHasFixedSize(true);
        this.ListaCarrito.setLayoutManager(manager);
        this.ListaCarrito.setAdapter(Adaptador);

        /*Toast.makeText(ListaCarrito.this,this.lista.get(0).getProducto()
                +" "+this.lista.get(0).getPrecioUnitario(),Toast.LENGTH_SHORT).show();*/

        lblItems=findViewById(R.id.CarritolblItems);
        lblProductos=findViewById(R.id.CarritolblProductos);
        lblTotal=findViewById(R.id.Carrito_lblTotal);

        //Cantidad de items
        lblItems.setText(Integer.toString(this.logger.getItems()));

        //Seteo de totales
        lblProductos.setText(Integer.toString(this.logger.getCantidadProductos()));
        lblTotal.setText("$ "+this.logger.getTotalPagar());

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