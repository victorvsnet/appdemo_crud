package com.upc.victorvsnet.examen_yucra.entidades;

public class Plato {
    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private int region;
    private int categoria;
    private int clasificacion;

    public Plato(String nombre, String descripcion, float precio, int region, int categoria, int clasificacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.region = region;
        this.categoria = categoria;
        this.clasificacion = clasificacion;
    }

    public Plato(int id, String nombre, String descripcion, float precio, int region, int categoria, int clasificacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.region = region;
        this.categoria = categoria;
        this.clasificacion = clasificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }
}
