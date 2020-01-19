package com.example.balewater.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.balewater.LoginRegister.LoginFormState;
import com.example.balewater.Model.Usuario;
import com.example.balewater.R;
import com.example.balewater.db.AppDao;
import com.example.balewater.db.AppDataBase;

public class AutenticationViewModel extends AndroidViewModel implements LifecycleOwner {

    public MutableLiveData<Boolean> usuarioActivo= new MutableLiveData<>();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }


    public enum EstadoDeLaAutentication {
        NO_AUTENTIFICADO,
        AUTENTIFICADO,
        AUTENTIFICACION_INVALIDA,
        YA_AUTENTIFICADO
    }

    public enum EstadoDelRegistro {
        INICIO_DEL_REGISTRO,
        NOMBRE_NO_DISPONIBLE,
        REGISTRO_COMPLETADO
    }

    public enum LoginEmpleado {
        LOGIN_EMPLEADO,
        LOGIN_CLIENTE

    }

    private AppDao appDao;

    public Usuario usuarioLogeado;

    public MutableLiveData<EstadoDeLaAutentication> estadoDeLaAutentication = new MutableLiveData<>(EstadoDeLaAutentication.NO_AUTENTIFICADO);
    private MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    private MutableLiveData<LoginEmpleado> loginEmpleadoMutableData = new MutableLiveData<>();
    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public AutenticationViewModel(@NonNull Application application) {
        super(application);
        appDao = AppDataBase.getInstance(application).appDao();

    }

    public MutableLiveData<EstadoDelRegistro> getEstadoDelRegistro() {
        return estadoDelRegistro;
    }

    public MutableLiveData<LoginEmpleado> getLoginEmpleadoMutableData() {
        return loginEmpleadoMutableData;
    }

    public void iniciarRegistro(){
        estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    }

    public void crearCuentaEIniciarSesion(final String email, final String password) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = appDao.comprobarEmailDisponible(email);
                if(usuario == null){
                    Log.e("ABCD", " toy aqui condicional comprobar si existeix usuario.... " );
                    appDao.insertarUsuario(new Usuario(email, password));
                    estadoDelRegistro.postValue(EstadoDelRegistro.REGISTRO_COMPLETADO);
                    iniciarSesion(email, password);
                } else {
                    estadoDelRegistro.postValue(EstadoDelRegistro.NOMBRE_NO_DISPONIBLE);
                }
            }
        });

    }

    public void iniciarSesion(final String correo, final String contrasenya) {



//
//        loginEmpleadoMutableData.observe(getViewLifecycleOwner(), new Observer<LoginEmpleado>() {
//            @Override
//            public void onChanged(LoginEmpleado loginEmpleadoMutableData) {
//                switch (loginEmpleadoMutableData){
//                    case LOGIN_CLIENTE:
//                            Log.e ("ABCD", "toy en observer cliente");
//                        break;
//                    case LOGIN_EMPLEADO:
//                        Log.e ("ABCD", "toy en observer empleado");
//
//                        break;
//                }
//            }
//        });


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = appDao.autenticar(correo, contrasenya);
                if(usuario != null ){
                    usuarioLogeado = usuario;
                    estadoDeLaAutentication.postValue(EstadoDeLaAutentication.AUTENTIFICADO);
                    usuarioActivo.postValue(true);

                } else {
                    estadoDeLaAutentication.postValue(EstadoDeLaAutentication.AUTENTIFICACION_INVALIDA);
                }
                //TODO este condicional es para acceso pruebas (quitarlo al acabar)
                if ( correo.equalsIgnoreCase("a")) {
                    estadoDeLaAutentication.postValue(EstadoDeLaAutentication.AUTENTIFICADO);
                }
            }
        });
    }


    public void cerrarSesion() {
        usuarioLogeado = null;
        estadoDeLaAutentication.setValue(EstadoDeLaAutentication.NO_AUTENTIFICADO);
    }

    public void loginDataChanged(String useremail, String password) {
        if (!isUserEmailValid(useremail)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_useremail, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder useremail validation check
    private boolean isUserEmailValid(String useremail) {
        if (useremail.equalsIgnoreCase("a")) return true;
        if (useremail == null) {
            return false;
        }
        if (useremail.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(useremail).matches();
        } else {
            return !useremail.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
