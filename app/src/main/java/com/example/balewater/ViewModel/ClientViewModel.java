package com.example.balewater.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ClientViewModel  extends AndroidViewModel {

    public MutableLiveData<String> nom = new MutableLiveData<>();
    public String cognoms;
    public String email;
    public String phone;
    public String passwordClient;
    public String reservas ;

    public ClientViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<String> getNom() {
        return nom;
    }

}


