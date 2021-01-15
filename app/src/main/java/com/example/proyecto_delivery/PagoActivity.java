package com.example.proyecto_delivery;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proyecto_delivery.Utilerias.FragmentDialog;
import com.squareup.picasso.Picasso;

//import android.support.v7.widget.Toolbar;

public class PagoActivity extends AppCompatActivity {
    public static boolean ValidarMunicipio=false;
    private Toolbar toolbar;
    public static String ResultadoMunicipio="";
    public static Button btnMunicipio;
    private TextWatcher KeyPress;
    //private Button btnMunicipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final TextView txbTelefono=findViewById(R.id.PagotxbTelefono);
        TextView lblTotalPago=findViewById(R.id.lblTotalTarjeta);
        lblTotalPago.setText("Total a pagar: "+getIntent().getStringExtra(ListaCarrito.ID_PAGO));

        String mensaje="Posterior al pago de tu orden, podras monitorear el estado de esta, a travez de la opcion: " +
                "Historial de compras; Donde podras ver el estado de tu orden en tiempo real. en caso de que suceda algun " +
                "inconveniente con tu orden, te sera notificado a travez del numero telefonico, que nos proporcionastes." +
                " Si tienes dudas o necesitas asistencia con tu orden llama al 2453-0091, proporcionando el numero de la orden " +
                "que se te ha sido asignado, ten en cuenta que el envio de tu orden puede tardar en aproximadamente 15 minutos en preparacion " +
                "y el tiempo de llegada depende de tu ubicacion, siempre que se encuentre dentro de una zona de cobertura";
        final TextView lblMensaje=findViewById(R.id.lblMensaje);
        lblMensaje.setText(mensaje);
        final TextView txbDireccion=findViewById(R.id.PagotxbDireccion);
        final TextView txbPropietario=findViewById(R.id.PagotxbPropietario);
        final TextView txbTarjeta=findViewById(R.id.PagotxbTarjeta);
        final TextView txbCVV=findViewById(R.id.PagotxbCVV);
        final TextView txbAnio=findViewById(R.id.PagotxbAnio);
        final TextView txbMes=findViewById(R.id.PagotxbMes);

        txbDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if(txbDireccion.length()>=100){
                    txbDireccion.setError("Limite de escritura alcanzado");
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        //Switch on/off
        Switch switchTelefono=findViewById(R.id.switchTelefono);
        //Boolean estadoSwitch=switchTelefono.isChecked();  Verificar si esta encendido o apagado
        txbTelefono.setText("7003-2797");
        txbTelefono.setEnabled(false);
        //Evento On/Off Switch
        switchTelefono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txbTelefono.setEnabled(true);
                }else{
                    txbTelefono.setEnabled(false);
                }
            }
        });


        ImageView img=findViewById(R.id.imgPago);
        String imagen="https://www.tiendaidc.es/img/Logos%20de%20Pago.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) img);
        Button btnAceptar=findViewById(R.id.btnPagar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PagoActivity.this.ValidarMunicipio && PagoActivity.this.ResultadoMunicipio.length()>0){
                    if(ValidarCampos(new TextView[]{txbDireccion,txbTelefono,
                            txbPropietario,txbTarjeta,txbCVV,txbAnio,txbMes})){
                        if(txbCVV.length()==3){
                            if(txbAnio.length()==2){
                                if(txbMes.length()==2){
                                    //Aceptacion de pago
                                    Toast.makeText(PagoActivity.this,"Aceptado",Toast.LENGTH_SHORT).show();
                                }else{
                                    txbMes.setError("Longitud invalida! ej: 06, 06: mes");
                                }

                            }else{
                                txbAnio.setError("Longitud invalida! ej: 34, 34: año");
                            }
                        }else{
                            txbCVV.setError("Longitud invalida !");
                        }
                    }
                }else{
                    Toast.makeText(PagoActivity.this,"Debe seleccionar un municipio !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMunicipio=findViewById(R.id.PagoidMunicipio);
        btnMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 new FragmentDialog().show(getSupportFragmentManager(),"FragmentDialog");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         switch(item.getItemId()){
             case R.id.carrito:
                 //Toast.makeText(this,this.Reesultado,Toast.LENGTH_SHORT);
                 break;
         }
         return true;
    }

    private boolean ValidarCampos(TextView [] lista){
        boolean resultado=true;
        for(TextView aux:lista){
            if(aux.getText().toString().equals("")){
                aux.setError("Campo obligatorio !");
                resultado=false;
            }
        }
        return resultado;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.ValidarMunicipio=false;
        this.ResultadoMunicipio="";
    }
}