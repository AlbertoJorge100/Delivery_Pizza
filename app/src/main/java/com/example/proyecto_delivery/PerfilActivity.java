package com.example.proyecto_delivery;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Utilerias.Logger;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class PerfilActivity extends AppCompatActivity {
    private Logger logger;
    private Switch SwitchNombres;
    private Switch SwitchUsuario;
    private Switch SwitchTelefono;
    private Switch SwitchCorreo;
    private Switch SwitchPassword;
    private TextView txbNombres;
    private TextView txbUsuario;
    private TextView txbTelefono;
    private TextView txbCorreo;
    private TextView txbPass1;
    private TextView txbPass2;
    private TextView txbPass3;

    private Button btnAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.logger=Logger.getInstance();

        //Toast.makeText(PerfilActivity.this,logger.getPassword(),Toast.LENGTH_SHORT).show();
        String imagen="https://i.pinimg.com/originals/64/3e/fe/643efe51394d635cbf544a25088ee269.png";
        ImageView imgPerfilImagen=findViewById(R.id.imgPerfilImagen);
        final TextView lblNombre=findViewById(R.id.lblPerfilNombre);
        final TextView lblUsuario=findViewById(R.id.lblPerfilUsuario);
        final TextView lblTelefono=findViewById(R.id.lblPerfilTelefono);
        final TextView lblCorreo=findViewById(R.id.lblPerfilCorreo);
        final TextView lblPassword=findViewById(R.id.lblPerfilPassword);
        final TextView lblTituloPassword=findViewById(R.id.lblTituloPassword);
        lblPassword.setText(this.logger.getPassword2());
        this.btnAceptar=findViewById(R.id.PerfilbtnAceptar);


        txbNombres=findViewById(R.id.PerfiltxbNombres);
        txbUsuario=findViewById(R.id.PerfiltxbUsuario);
        txbTelefono=findViewById(R.id.PerfiltxbTelefono);
        txbCorreo=findViewById(R.id.PerfiltxbCorreo);
        txbPass1=findViewById(R.id.txbPass1);
        txbPass2=findViewById(R.id.txbPass2);
        txbPass3=findViewById(R.id.txbPass3);
        final TextInputLayout txbPassword=findViewById(R.id.PerfiltxbPassword);
        final TextInputLayout txbPasswordN=findViewById(R.id.PerfiltxbPasswordN);
        final TextInputLayout txbPasswordR=findViewById(R.id.PerfiltxbPasswordR);


        this.SwitchNombres=findViewById(R.id.SwitchNombres);
        this.SwitchUsuario=findViewById(R.id.SwitchUsuario);
        this.SwitchTelefono=findViewById(R.id.SwitchTelefono);
        this.SwitchCorreo=findViewById(R.id.SwitchCorreo);
        this.SwitchPassword=findViewById(R.id.SwitchPassword);
        Boolean a=SwitchNombres.isChecked();
        SwitchNombres.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido
                    txbNombres.setVisibility(View.VISIBLE);
                    txbNombres.setText(PerfilActivity.this.logger.getNombres());
                    lblNombre.setVisibility(View.GONE);
                }else{
                    txbNombres.setVisibility(View.GONE);
                    lblNombre.setVisibility(View.VISIBLE);
                    //Apagado
                }
                EstadoBoton(b);
            }
        });

        SwitchUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txbUsuario.setVisibility(View.VISIBLE);
                    txbUsuario.setText(PerfilActivity.this.logger.getUsuario());
                    lblUsuario.setVisibility(View.GONE);
                }else{
                    txbUsuario.setVisibility(View.GONE);
                    lblUsuario.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });
        SwitchTelefono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txbTelefono.setVisibility(View.VISIBLE);
                    txbTelefono.setText(PerfilActivity.this.logger.getTelefono());
                    lblTelefono.setVisibility(View.GONE);
                }else{
                    txbTelefono.setVisibility(View.GONE);
                    lblTelefono.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });
        SwitchCorreo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txbCorreo.setVisibility(View.VISIBLE);
                    txbCorreo.setText(PerfilActivity.this.logger.getCorreo());
                    lblCorreo.setVisibility(View.GONE);
                }else{
                    txbCorreo.setVisibility(View.GONE);
                    lblCorreo.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });

        SwitchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txbPassword.setVisibility(View.VISIBLE);
                    txbPasswordN.setVisibility(View.VISIBLE);
                    txbPasswordR.setVisibility(View.VISIBLE);
                    lblPassword.setVisibility(View.GONE);
                    lblTituloPassword.setVisibility(View.INVISIBLE);//quien eres?
                }else{
                    txbPassword.setVisibility(View.GONE);
                    txbPasswordN.setVisibility(View.GONE);
                    txbPasswordR.setVisibility(View.GONE);
                    lblPassword.setVisibility(View.VISIBLE);
                    lblTituloPassword.setVisibility(View.VISIBLE);
                    txbPass1.setText("");
                    txbPass2.setText("");
                    txbPass3.setText("");
                }
                EstadoBoton(b);
            }
        });

        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgPerfilImagen);
        lblNombre.setText(logger.getNombres());
        lblUsuario.setText(logger.getUsuario());
        lblTelefono.setText(logger.getTelefono());
        lblCorreo.setText(logger.getCorreo());
    }

    //Ocultar, mostrar boton
    private void EstadoBoton(Boolean estado){
        Switch switches[]={SwitchNombres,SwitchUsuario,SwitchPassword,SwitchTelefono,SwitchCorreo};
        int cont=0;
        for(int i=0;i<switches.length;i++){
            if(switches[i].isChecked()){
                cont++;
            }
        }
        if(estado){
            //Mostrar boton
            if(cont==1){
                this.btnAceptar.setVisibility(View.VISIBLE);
            }
        }else{
            //Ocultar boton
            if(cont==0){
                this.btnAceptar.setVisibility(View.GONE);
            }
        }
    }

    private void ValidarCampos(){
        if(this.SwitchNombres.isChecked()){
            if(this.txbNombres.getText().toString().equals("")){
                this.txbNombres.setError("Campo obligatorio !");
            }
        }
        if(this.SwitchUsuario.isChecked()){
            if(this.txbUsuario.getText().toString().equals("")){
                this.txbUsuario.setError("Campo obligatorio !");
            }
        }
        if(this.SwitchPassword.isChecked()){
            if(this.txbPass1.getText().toString().equals("")){
                this.txbPass1.setError("Campo obligatorio !");
            }
            if(this.txbPass2.getText().toString().equals("")){
                this.txbPass2.setError("Campo obligatorio !");
            }
            if(this.txbPass3.getText().toString().equals("")){
                this.txbPass3.setError("Campo obligatorio !");
            }
        }
    }
}