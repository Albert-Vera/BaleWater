package com.example.balewater.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Usuario {
    public  int id;

    public Usuario() {
    }

    public String email;
   // public  String password;
    public String imagePerfil;
 //   public String fechaRegistro;


    public Usuario(String email, String imagePerfil) {
        this.email = email;
        this.imagePerfil = imagePerfil;
    }

//    public String getFechaRegistro() {
//        return fechaRegistro;
//    }
//
//    public void setFechaRegistro(String fechaRegistro) {
//        this.fechaRegistro = fechaRegistro;
//    }

    public String getImagePerfil() {
        return imagePerfil;
    }

    public void setImagePerfil(String imagePerfil) {
        this.imagePerfil = imagePerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    @NonNull
    @Override
    public String toString() {
        return "USER = " + email ;
    }
}
