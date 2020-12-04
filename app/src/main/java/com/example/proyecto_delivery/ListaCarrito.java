package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorProducto;
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Clases.classProducto;
import com.example.proyecto_delivery.Entidades.Producto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaCarrito extends AppCompatActivity {
    private AdaptadorProducto adaptador;
    private List<Producto> lista=new ArrayList<Producto>();
    private double TotalVenta;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carrito);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final TextView lblCantidad=findViewById(R.id.lblCantidad);
        final TextView lblTotal=findViewById(R.id.lblTotal);
        final TextView txbTarjeta=findViewById(R.id.txbTarjeta);
        final TextView txbCVV=findViewById(R.id.txbCVV);
        final TextView txbAnio=findViewById(R.id.txbAnio);
        final TextView txbMes=findViewById(R.id.txbMes);

        Button btnPagar=findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                ListaInformacion.ListaCarrito.clear();
                            }
                        });
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }else{
                        Toast.makeText(ListaCarrito.this, "Error: El pago no fue aceptado !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        this.listaInformacion=findViewById(R.id.ListaCarrito);
        manager=new LinearLayoutManager(this);
        this.lista=ListaInformacion.ListaCarrito;
        adaptador=new AdaptadorProducto(this.lista);//Accediendo a la lista de productos
        lblCantidad.setText(Integer.toString(this.lista.size()));
        TotalVenta=CalcularTotal();
        lblTotal.setText("$ "+Double.toString(TotalVenta));
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);
    }

    private int ObtenerIdFactura(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String fecha=dateFormat.format(date);
        int respuesta=-1;
        classFactura factura=new classFactura(this);
        factura.setIdCliente(ListaInformacion.IdCliente);
        factura.setTotal(this.TotalVenta);
        factura.setCantidadProductos(lista.size());
        factura.setFecha(fecha);
        if(factura.Insert()){
            respuesta=factura.getIdFactura();
        }
        return respuesta;
    }

    private Boolean ValidarCampos(TextView lista[]){
        Boolean resultado=true;
        for(TextView aux: lista){
            if(aux.getText().toString().equals("")){
                aux.setError("Campo Obligatorio !");
                resultado=false;
            }
        }
        return resultado;
    }
    private Double CalcularTotal(){
        Double total=0.0;
        for(int i=0;i<this.lista.size();i++){
            total+=lista.get(i).getPrecio();
        }
        BigDecimal bd = new BigDecimal(total);//Redondeamos a dos decimales
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    private void GuardarProductos(int i, int idfactura) {
        try{
            classProducto producto = new classProducto(ListaCarrito.this);
            producto.setImagen(lista.get(i).getImagen());
            producto.setProducto(lista.get(i).getProducto());
            producto.setDescripcion(lista.get(i).getDescripcion());
            producto.setPrecio(lista.get(i).getPrecio());
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