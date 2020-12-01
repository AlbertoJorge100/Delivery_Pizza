package com.example.proyecto_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PromocionesActivity extends AppCompatActivity {
    List<String>Lista=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
        ListView ListaPrincipal=findViewById(R.id.ListaPrincipal);
        //this.Lista=(ArrayList<String>)getIntent().getSerializableExtra(MainActivity.TAG_MSJ);
        LlenarLista();
        ListaPrincipal.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Lista));

    }
    private void LlenarLista(){
        for(int i=1;i<=10;i++){
            this.Lista.add("promocion: "+i);
        }
    }
}