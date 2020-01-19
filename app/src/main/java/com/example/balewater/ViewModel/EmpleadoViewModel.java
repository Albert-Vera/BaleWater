package com.example.balewater.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class EmpleadoViewModel extends AndroidViewModel {


    public EmpleadoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> estoyLoginStaff = new MutableLiveData<>();




    public MutableLiveData<Boolean> getEstoyLoginStaff() {
        return estoyLoginStaff;
    }

    public MutableLiveData<String> nombreEmpleado = new MutableLiveData<>();
}
