package com.example.proyecto_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HamburguesaActivity extends AppCompatActivity {
    List<String> Lista=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburguesa);
        ListView ListaPrincipal=findViewById(R.id.ListaPrincipal);
        LlenarLista();
        ListaPrincipal.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Lista));
        ListaPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //ItemClicked item = adapterView.getItemAtPosition(i);

                Intent intent = new Intent(HamburguesaActivity.this,PizzaDtActivity.class);
                //based on item add info to intent
                startActivity(intent);
            }
        });
    }
    private void LlenarLista(){
        for(int i=1;i<=10;i++){
            this.Lista.add("Hamburguesa:  "+i+"\nPrecio: "+i);
        }
    }
}