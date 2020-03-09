package com.example.balewater.model;

public class Reserva extends Cliente {
    public String nombreSelecion;
    public String idSeleccion;

    public Reserva(String nombreSelecion, String idSeleccion) {
        this.nombreSelecion = nombreSelecion;
        this.idSeleccion = idSeleccion;
    }

    public Reserva() {
    }

    public Reserva(int idCliente, String nombre, String apellidos, String email, String  tel, String fechaRegistro, String nombreSelecion, String idSeleccion) {
        super(idCliente, nombre, apellidos, email, tel, fechaRegistro);
        this.nombreSelecion = nombreSelecion;
        this.idSeleccion = idSeleccion;
    }

    public String getNombreSelecion() {
        return nombreSelecion;
    }

    public void setNombreSelecion(String nombreSelecion) {
        this.nombreSelecion = nombreSelecion;
    }

    public String getIdSeleccion() {
        return idSeleccion;
    }

    public void setIdSeleccion(String idSeleccion) {
        this.idSeleccion = idSeleccion;
    }
}
