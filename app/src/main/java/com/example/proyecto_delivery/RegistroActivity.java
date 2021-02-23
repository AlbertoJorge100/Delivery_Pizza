package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.BaseDatos.RespuestaUsuarios;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Usuarios;
import com.example.proyecto_delivery.Utilerias.Hash;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {
    private TextView txbNombre;
    private TextView txbTelefono;
    private TextView txbCorreo;
    private TextView txbDireccion;
    private TextView txbUsuario;
    private TextView txbPassword;
    private TextView txbPassword2;
    private ImageView imgPassword1;
    private TextView imgPassword2;
    private Boolean Pass1=false;
    private Boolean Pass2=false;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressDialog=new ProgressDialog(this);

        this.txbNombre=findViewById(R.id.RegtxbNombre);
        this.txbTelefono=findViewById(R.id.RegtxbTelefono);
        this.txbCorreo=findViewById(R.id.RegtxbCorreo);
        this.txbUsuario=findViewById(R.id.RegtxbUsuario);
        this.txbPassword=findViewById(R.id.RegtxbPassword);
        //this.txbPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        this.txbPassword2=findViewById(R.id.RegtxbPassword2);
        Button btnRegistro=findViewById(R.id.RegbtnRegistro);
//        this.imgPassword1=findViewById(R.id.imgPassword1);
  /*      imgPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Pass1){
                    Pass1=true;
                    txbPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //txbPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                }else{
                    Pass1=false;
                    txbPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    //txbPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
*/
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCampos()){
                    if(txbPassword.getText().toString()
                    .equals(txbPassword2.getText().toString())){
                        Usuarios usuario=new Usuarios();
                        String nombreusuario=txbUsuario.getText().toString();
                        usuario.setNombres(txbNombre.getText().toString());
                        usuario.setCorreo(txbCorreo.getText().toString());
                        usuario.setTelefono(txbTelefono.getText().toString());
                        usuario.setUsuario(nombreusuario);
                        usuario.setPassword(Hash.generarHash(txbPassword.getText().toString(),Hash.SHA256));
                        GuardarUsuario(usuario);
                    }else{
                        txbPassword2.setError("Las contraseñas no coinciden !");
                    }
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
                this.txbUsuario,
                this.txbPassword,
                this.txbPassword2
        };
        for(TextView aux: lista){
           if(aux.getText().toString().equals("")){
               aux.setError("Campo Obligatorio !");
               resultado=false;
           }
        }
        if(resultado){
            if(txbTelefono.getText().toString().length()<8){
                txbTelefono.setError("Numero de telefono invalido !");
                resultado=false;
            }
            if(!ValidarCorreo()){
                txbCorreo.setError("Correo invalido !");
            }
            if(txbUsuario.getText().toString().length()<6){
                txbUsuario.setError("Usuario muy corto !");
                resultado=false;
            }
            if(txbPassword.getText().toString().length()<6){
                txbPassword.setError("Contraseña muy corta !");
                resultado=false;
            }
        }
        return resultado;
    }

    private Boolean ValidarCorreo(){
        String correo=txbCorreo.getText().toString();
        int i=0, cont=0;
        while(i<correo.length()){
            if(correo.charAt(i)=='@'){
                cont++;
            }
            if(correo.charAt(i)=='.'){
                cont++;
            }
            i++;
        }
        return (cont>=2);
    }
    private void GuardarUsuario(Usuarios usuario){
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);
        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        Call<RespuestaUsuarios> call=jsonPlaceHolder.AddUsuarios(usuario);
        call.enqueue(new Callback<RespuestaUsuarios>() {
            @Override
            public void onResponse(Call<RespuestaUsuarios> call, Response<RespuestaUsuarios> response) {
                if(response.isSuccessful()){
                    RespuestaUsuarios respuesta=response.body();
                    if(respuesta.getUsuario()!=null && respuesta.getCodigo().equals("200")){
                        //Registro guardado con exito
                        RegistroActivity.this.finish();
                    }
                    //En caso que lo guarde o no, lo mostrara a traves del mensaje
                    Toast.makeText(RegistroActivity.this,respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this,"Error: onResponse "+response.message(), Toast.LENGTH_SHORT).show();
                }
                RegistroActivity.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RespuestaUsuarios> call, Throwable t) {
                RegistroActivity.this.progressDialog.dismiss();
                Toast.makeText(RegistroActivity.this,"Error onFailure :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


   /*
    GuardarSQLITE
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
    }*/
}