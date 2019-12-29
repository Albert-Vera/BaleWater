package com.example.balewater.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.balewater.Model.Usuario;

@Dao
public abstract class AppDao {

    @Insert
    public abstract void insertarUsuario(Usuario usuario);

    @Query(value = "SELECT * FROM Usuario WHERE email = :email")
    public abstract Usuario comprobarEmailDisponible(String email);

    @Query("SELECT * FROM Usuario WHERE email = :email AND password = :password")
    public abstract Usuario autenticar(String email, String password);



}
