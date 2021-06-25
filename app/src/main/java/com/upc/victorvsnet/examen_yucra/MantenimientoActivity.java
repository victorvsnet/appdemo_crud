package com.upc.victorvsnet.examen_yucra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import com.upc.victorvsnet.examen_yucra.entidades.Plato;
import com.upc.victorvsnet.examen_yucra.modelo.DAOPlato;

import java.util.Arrays;

public class MantenimientoActivity extends AppCompatActivity {

    TextView txtTitulo;
    EditText txtNombre, txtDescripcion, txtPrecio;
    Button btnGuardar;

    AutoCompleteTextView autoCompleteRegion;
    AutoCompleteTextView autoCompleteCategoria;
    AutoCompleteTextView autoCompleteClasificacion;


    String nombre, descripcion;
    float precio;
    int id, region, categoria, clasificacion;
    boolean registra = true;
    DAOPlato daoPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);

        asignarReferencias();
        cargarCombos();
        recibirDatos();

    }

    private void recibirDatos() {

        if (getIntent().hasExtra("id")) {
            registra = false;
            id = Integer.parseInt(getIntent().getStringExtra("id"));
            nombre = getIntent().getStringExtra("nombre");
            descripcion = getIntent().getStringExtra("descripcion");
            precio = Float.parseFloat(getIntent().getStringExtra("precio"));
            region = Integer.parseInt(getIntent().getStringExtra("region"));
            categoria = Integer.parseInt(getIntent().getStringExtra("categoria"));
            clasificacion = Integer.parseInt(getIntent().getStringExtra("clasificacion"));

            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);
            txtPrecio.setText(precio + "");

            //autoCompleteRegion.setSelection(region);
            autoCompleteRegion.setListSelection(region);
            autoCompleteCategoria.setListSelection(categoria);
            autoCompleteClasificacion.setListSelection(clasificacion);
            //Log.d("app_menu_region", getIntent().getStringExtra("region") + "");
            Log.d("app_menu_region", region + "");
            Log.d("app_menu_categoria", categoria + "");
            Log.d("app_menu_clasificacion", clasificacion + "");
        }

        btnGuardar = findViewById(R.id.btnGuardar);
        txtTitulo = findViewById(R.id.txtTitulo);

        btnGuardar.setText((registra) ? "Guardar Plato" : "Actualizar Plato");
        txtTitulo.setText((registra) ? "Registro de Plato" : "Modificar Plato");

    }

    private void cargarCombos() {

        // autoCompleteRegion = findViewById(R.id.autoCompleteRegion);
        // autoCompleteCategoria = findViewById(R.id.autoCompleteCategoria);
        // autoCompleteClasificacion = findViewById(R.id.autoCompleteClasificacion);

        String[] regiones = getResources().getStringArray(R.array.regiones);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, regiones);
        autoCompleteRegion.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteRegion.setAdapter(arrayAdapter);
        autoCompleteRegion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                region = Arrays.asList(regiones).indexOf(selected);
            }
        });

        //otro tipo de carga si se lee de un servicio lo cargariamos de la siguiente manera
        String[] categorias = {"-SELECCIONE-", "Plato de fondo", "Entrada", "Postre"};
        ArrayAdapter adapterCategoria = new ArrayAdapter(this, R.layout.dropdown_item, categorias);
        autoCompleteCategoria.setText(adapterCategoria.getItem(0).toString(), false);
        autoCompleteCategoria.setAdapter(adapterCategoria);
        autoCompleteCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                categoria = Arrays.asList(categorias).indexOf(selected);
            }
        });

        String[] clasificaciones = {"-SELECCIONE-", "NACIONAL", "EXTRANJERO"};
        ArrayAdapter adapterClasificacion = new ArrayAdapter(this, R.layout.dropdown_item, clasificaciones);
        autoCompleteClasificacion.setText(adapterClasificacion.getItem(0).toString(), false);
        autoCompleteClasificacion.setAdapter(adapterClasificacion);
        autoCompleteClasificacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                clasificacion = Arrays.asList(clasificaciones).indexOf(selected);
            }
        });

    }

    private void asignarReferencias() {

        txtTitulo = findViewById(R.id.txtTitulo);
        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);

        autoCompleteRegion = findViewById(R.id.autoCompleteRegion);
        autoCompleteCategoria = findViewById(R.id.autoCompleteCategoria);
        autoCompleteClasificacion = findViewById(R.id.autoCompleteClasificacion);

        daoPlato = new DAOPlato(this);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validar()) {
                    String mensaje;
                    Plato plato;
                    daoPlato.abrirBD();

                    if (registra) {
                        plato = new Plato(nombre, descripcion, precio, region, categoria, clasificacion);
                        mensaje = daoPlato.registrarPlato(plato);
                    } else {
                        plato = new Plato(id, nombre, descripcion, precio, region, categoria, clasificacion);
                        mensaje = daoPlato.modificarPlato(plato);
                    }

                    AlertDialog.Builder ventanaConfirmacion = new AlertDialog.Builder(MantenimientoActivity.this);
                    ventanaConfirmacion.setTitle("Mensaje informativo");
                    ventanaConfirmacion.setMessage(mensaje);
                    ventanaConfirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MantenimientoActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    ventanaConfirmacion.create().show();
                }
            }
        });
    }

    private boolean validar() {

        boolean resultado = true;
        nombre = txtNombre.getText().toString();
        descripcion = txtDescripcion.getText().toString();
        precio = txtPrecio.getText().toString().equals("") ? 0 : Float.parseFloat(txtPrecio.getText().toString());

        if (nombre.equals("")) {
            txtNombre.setError("El nombre es Obligatorio");
            resultado = false;
        }
        if (descripcion.equals("")) {
            txtDescripcion.setError("Debe ingresar alguna descripción");
            resultado = false;
        }
        if (precio == 0) {
            txtPrecio.setError("El precio es obligatorio");
            resultado = false;
        }
        if (region == 0) {
            autoCompleteRegion.setError("Debe seleccionar una Región");
            resultado = false;
        }
        if (categoria == 0) {
            autoCompleteCategoria.setError("Debe seleccionar una Categoria");
            resultado = false;
        }
        if (clasificacion == 0) {
            autoCompleteClasificacion.setError("Debe seleccionar una Clasificación");
            resultado = false;
        }

        Log.d("app_menu_region", region + "");
        Log.d("app_menu_categoria", categoria + "");
        Log.d("app_menu_clasificacion", clasificacion + "");
        Log.d("app_menu_precio", precio + "");

        return resultado;

    }
}