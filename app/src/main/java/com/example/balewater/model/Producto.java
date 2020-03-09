package com.example.balewater.model;

public class Producto {
    private String nombre, descripcion;
    private float precioVenta, precioCoste, iva;
    private boolean disponible;

    public Producto(String nombre, String descripcion, float precioVenta, float precioCoste, float iva, boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.precioCoste = precioCoste;
        this.iva = iva;
        this.disponible = disponible;
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

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getPrecioCoste() {
        return precioCoste;
    }

    public void setPrecioCoste(float precioCoste) {
        this.precioCoste = precioCoste;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
