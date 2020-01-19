package com.example.balewater.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.balewater.Model.Usuario;


@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDataBase extends androidx.room.RoomDatabase{

    public abstract AppDao appDao();

    static AppDataBase appDataBase;

    public static AppDataBase getInstance(Context context){

        if(appDataBase == null){
            // synchronized (AppDatabase.class) {
            appDataBase = Room.databaseBuilder(context, AppDataBase.class, "app.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            crearUsuario();
                        }

                    }) // anadir por defecto datos
                    .build();
            //}
        }
        return appDataBase;
    }

    private static void crearUsuario(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDataBase.appDao().insertarUsuario(new Usuario("aaaaaa@aaaaaa.com","aaaaaa"));
                appDataBase.appDao().insertarUsuario(new Usuario("EMPLEADO.001","EMPLEADO"));
                appDataBase.appDao().insertarUsuario(new Usuario("EMPLEADO.002","EMPLEADO"));
                appDataBase.appDao().insertarUsuario(new Usuario("EMPLEADO.003","EMPLEADO"));
                appDataBase.appDao().insertarUsuario(new Usuario("EMPLEADO.004","EMPLEADO"));


            }
        });
    }

}
