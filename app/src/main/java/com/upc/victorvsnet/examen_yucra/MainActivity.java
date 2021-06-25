package com.upc.victorvsnet.examen_yucra;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upc.victorvsnet.examen_yucra.entidades.Plato;
import com.upc.victorvsnet.examen_yucra.modelo.DAOPlato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnNuevo;

    DAOPlato daoPlato = new DAOPlato(this);
    List<Plato> listaPlatos = new ArrayList<>();
    AdaptadorPersonalizado adaptadorPersonalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();
        daoPlato.abrirBD();
        mostrarPlato();
    }

    private void mostrarPlato() {
        listaPlatos = daoPlato.getPlatos();
        adaptadorPersonalizado = new AdaptadorPersonalizado(MainActivity.this, listaPlatos);
        recyclerView.setAdapter(adaptadorPersonalizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void asignarReferencias() {
        recyclerView = findViewById(R.id.platillosRecyclerView);
        btnNuevo = findViewById(R.id.btnAdd);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_RegistroPlato = new Intent(MainActivity.this, MantenimientoActivity.class);
                startActivity(intent_RegistroPlato);
            }
        });
    }
}