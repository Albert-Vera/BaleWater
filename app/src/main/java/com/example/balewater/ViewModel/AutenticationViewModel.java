package com.example.balewater.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.balewater.LoginRegister.LoginFormState;
import com.example.balewater.Model.Usuario;
import com.example.balewater.R;
import com.example.balewater.db.AppDao;
import com.example.balewater.db.AppDataBase;

public class AutenticationViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> usuarioActivo= new MutableLiveData<>();
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();


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

    private AppDao appDao;

    public Usuario usuarioLogeado;

    public MutableLiveData<EstadoDeLaAutentication> estadoDeLaAutentication = new MutableLiveData<>(EstadoDeLaAutentication.NO_AUTENTIFICADO);
    public MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public AutenticationViewModel(@NonNull Application application) {
        super(application);
        appDao = AppDataBase.getInstance(application).appDao();

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

    public void iniciarSesion(final String nombre, final String contrasenya) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Usuario usuario = appDao.autenticar(nombre, contrasenya);
                if(usuario != null || usuario.email.equalsIgnoreCase("a")){
                    usuarioLogeado = usuario;
                    estadoDeLaAutentication.postValue(EstadoDeLaAutentication.AUTENTIFICADO);
                    usuarioActivo.postValue(true);

                } else {
                    estadoDeLaAutentication.postValue(EstadoDeLaAutentication.AUTENTIFICACION_INVALIDA);
                }
            }
        });
    }

    public void cerrarSesion() {
        usuarioLogeado = null;
        estadoDeLaAutentication.setValue(EstadoDeLaAutentication.NO_AUTENTIFICADO);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username.equalsIgnoreCase("a")) return true;
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
