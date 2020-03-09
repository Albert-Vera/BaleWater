package com.example.balewater.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class AppViewModel extends AndroidViewModel {


    public enum EstadoDeLaAutentificacion{
        AUTENTIFICADO,
        NO_AUTENTIFICADO,
        YA_AUTENTIFICADO

    }

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> idSeleccionado = new MutableLiveData<String>();
    public MutableLiveData<String> castleSeleccionado = new MutableLiveData<String>();

    public MutableLiveData<EstadoDeLaAutentificacion> estadoDeLaAutentificacion = new MutableLiveData<>(EstadoDeLaAutentificacion.NO_AUTENTIFICADO);
//    public MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
//
//    public void loginDataChanged(String useremail, String password) {
//        Log.e("ABCD", "toy aqui  logindatachanged ");
//
//        if (!isUserEmailValid(useremail)) {
//            Log.e("ABCD", "dentro valid useremail ");
//
//            loginFormState.setValue(new LoginFormState(R.string.invalid_useremail, null));
//        } else if (!isPasswordValid(password)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
//    }

    // A placeholder useremail validation check
//    private boolean isUserEmailValid(String useremail) {
//        if (useremail.equalsIgnoreCase("a")) return true;
//        if (useremail == null) {
//            return false;
//        }
//        if (useremail.contains("@")) {
//            return Patterns.EMAIL_ADDRESS.matcher(useremail).matches();
//        } else {
//            return !useremail.trim().isEmpty();
//        }
//    }
//
//    // A placeholder password validation check
//    private boolean isPasswordValid(String password) {
//        return password != null && password.trim().length() > 5;
//    }
}
