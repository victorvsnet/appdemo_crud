package com.upc.victorvsnet.examen_yucra.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.upc.victorvsnet.examen_yucra.entidades.Plato;
import com.upc.victorvsnet.examen_yucra.util.Constantes;
import com.upc.victorvsnet.examen_yucra.util.PlatoBD;

import java.util.ArrayList;
import java.util.List;

public class DAOPlato {

    PlatoBD platoBD;
    SQLiteDatabase db;
    private Context context;

    public DAOPlato(Context context) {
        this.context = context;
        this.platoBD = new PlatoBD(context);
    }

    public void abrirBD() {
        db = platoBD.getWritableDatabase();
    }

    public List<Plato> getPlatos() {
        List<Plato> listaPlatos = new ArrayList<>();

        try {
            Cursor c = db.rawQuery("SELECT * FROM " + Constantes.NOMBRE_TABLA, null);
            while (c.moveToNext()) {
                listaPlatos.add(new Plato(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getFloat(3),
                        c.getInt(4),
                        c.getInt(5),
                        c.getInt(6)));
            }
        } catch (Exception e) {
            Log.d("app_menu", e.getMessage());
        }

        return listaPlatos;
    }

    public String registrarPlato(Plato plato) {
        String mensaje = "";
        try {
            ContentValues valores = new ContentValues();
            valores.put("nombre", plato.getNombre());
            valores.put("descripcion", plato.getDescripcion());
            valores.put("precio", plato.getPrecio());
            valores.put("region", plato.getRegion());
            valores.put("categoria", plato.getCategoria());
            valores.put("clasificacion", plato.getClasificacion());
            long result = db.insert(Constantes.NOMBRE_TABLA, null, valores);
            if (result == -1) {
                mensaje = "Error al insertar";
                //Toast.makeText(context,"Error al insertar", Toast.LENGTH_SHORT).show();
            } else {
                mensaje = "Registro Correcto";
                //Toast.makeText(context,"Se registro correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("app_menu", e.getMessage());
        }
        return mensaje;
    }

    public String modificarPlato(Plato plato) {
        String mensaje = "";
        try {
            ContentValues valores = new ContentValues();
            valores.put("nombre", plato.getNombre());
            valores.put("descripcion", plato.getDescripcion());
            valores.put("precio", plato.getPrecio());
            valores.put("region", plato.getRegion());
            valores.put("categoria", plato.getCategoria());
            valores.put("clasificacion", plato.getClasificacion());
            long result = db.update(Constantes.NOMBRE_TABLA, valores, "id=" + plato.getId(), null);
            if (result == -1) {
                mensaje = "Error al actualizar";
                //Toast.makeText(context,"Error al insertar", Toast.LENGTH_SHORT).show();
            } else {
                mensaje = "Modificacion Correcta";
                //Toast.makeText(context,"Se registro correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("app_menu", e.getMessage());
        }
        return mensaje;
    }

    public String eliminarPlato(int id) {
        String mensaje = "";
        try {
            long result = db.delete(Constantes.NOMBRE_TABLA, "id=" + id, null);
            if (result == -1) {
                mensaje = "Error al eliminar";
            } else {
                mensaje = "Se elimino correctamente";
            }
        } catch (Exception e) {
            Log.d("app_menu", e.getMessage());
        }
        return  mensaje;
    }

}
