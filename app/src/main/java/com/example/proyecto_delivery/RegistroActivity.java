package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Clases.classUsuario;
import com.example.proyecto_delivery.Utilerias.Hash;

public class RegistroActivity extends AppCompatActivity {
    private TextView txbNombre;
    private TextView txbTelefono;
    private TextView txbCorreo;
    private TextView txbDireccion;
    private TextView txbUsuario;
    private TextView txbPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.txbNombre=findViewById(R.id.txbNombre);
        this.txbTelefono=findViewById(R.id.txbTelefono);
        this.txbCorreo=findViewById(R.id.txbCorreo);
        this.txbDireccion=findViewById(R.id.txbDireccion);
        this.txbUsuario=findViewById(R.id.txbUsuario);
        this.txbPassword=findViewById(R.id.txbPassword);
        Button btnRegistro=findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCampos()){
                    GuardarLibro();
                }
            }
        });
    }

    private Boolean ValidarCampos(){
        Boolean resultado=true;
        TextView lista[]=new TextView[]{
                this.txbNombre,
                this.txbTelefono,
                this.txbCorreo,
                this.txbDireccion,
                this.txbUsuario,
                this.txbPassword
        };
        for(TextView aux: lista){
           if(aux.getText().toString().equals("")){
               aux.setError("Campo Obligatorio !");
               resultado=false;
           }
        }
        return resultado;
    }
    private void GuardarLibro() {//Perdon Cliente jeje
        classUsuario registro=new classUsuario(this);
        try {
            //Validamos que los campos esten completados
                //Creamos una instancia del libro
                registro.setNombres(this.txbNombre.getText().toString());
                registro.setTelefono(this.txbTelefono.getText().toString());
                registro.setDireccion(this.txbDireccion.getText().toString());
                registro.setCorreo(this.txbCorreo.getText().toString());
                registro.setUsuario(this.txbUsuario.getText().toString());
                //Encriptacion de contraseña con SHA_256
                registro.setPassword(Hash.generarHash(this.txbPassword.getText().toString(),Hash.SHA256));
                //Agregamos el libro a la base de datos
                if(registro.Insert()){
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegistroActivity.this);
                    builder.setTitle("Mensaje");
                    builder.setMessage("El usuario se ha registrado exitosamente !");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else{
                    Toast.makeText(this, "No fue posible Agregar los datos!", Toast.LENGTH_SHORT).show();
                }
        }catch (SQLiteException ex){
            //Colocamos mensajes para depuración
            Log.d("ERROR_LIBROS", ex.getMessage());
            Toast.makeText(this, "No fue posible Agregar los datos!", Toast.LENGTH_SHORT).show();
        }
    }
}