package com.upc.victorvsnet.examen_yucra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.upc.victorvsnet.examen_yucra.entidades.Plato;
import com.upc.victorvsnet.examen_yucra.modelo.DAOPlato;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.MyViewHolder> {

    private Context context;
    private List<Plato> listPlatos = new ArrayList<>();

    public AdaptadorPersonalizado(Context context, List<Plato> listPlatos) {
        this.context = context;
        this.listPlatos = listPlatos;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.list_element, parent, false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.MyViewHolder holder, int position) {

        holder.fila_nombre.setText(listPlatos.get(position).getNombre().toString());
        holder.fila_descripcion.setText(listPlatos.get(position).getDescripcion().toString());
        holder.fila_precio.setText(listPlatos.get(position).getPrecio() + "");

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MantenimientoActivity.class);
                intent.putExtra("id", listPlatos.get(position).getId() + "");
                intent.putExtra("nombre", listPlatos.get(position).getNombre().toString());
                intent.putExtra("descripcion", listPlatos.get(position).getDescripcion().toString());
                intent.putExtra("precio", listPlatos.get(position).getPrecio() + "");
                intent.putExtra("region", listPlatos.get(position).getRegion() + "");
                intent.putExtra("categoria", listPlatos.get(position).getCategoria() + "");
                intent.putExtra("clasificacion", listPlatos.get(position).getClasificacion() + "");

                Log.d("app_menu_regionAdapter", listPlatos.get(position).getRegion() + "");

                context.startActivity(intent);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("¿Desea Eliminar?");
                ventana.setMessage("Desea eliminar el plato " + listPlatos.get(position).getNombre());
                ventana.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DAOPlato daoPlato = new DAOPlato(context);
                        daoPlato.abrirBD();
                        String mensaje = daoPlato.eliminarPlato(listPlatos.get(position).getId());
                        AlertDialog.Builder v = new AlertDialog.Builder(context);
                        v.setTitle("Mensaje informativo");
                        v.setMessage(mensaje);
                        v.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        });
                        v.create().show();
                    }
                });
                ventana.setNegativeButton("No", null);
                ventana.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPlatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fila_nombre, fila_descripcion, fila_precio;
        ImageButton btnEditar, btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fila_nombre = itemView.findViewById(R.id.txtNombrePlato);
            fila_descripcion = itemView.findViewById(R.id.txtDescripcionPlato);
            fila_precio = itemView.findViewById(R.id.txtPrecioPlato);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
