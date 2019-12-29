package com.example.balewater.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.balewater.Model.BaleWaterResponse;
import com.example.balewater.Model.Castle;
import com.example.balewater.Model.CastleDetail;
import com.example.balewater.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DataViewModel extends AndroidViewModel {

    Application application;

    public MutableLiveData<Castle> castle = new MutableLiveData<>();

    public DataViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

    }

    public LiveData<BaleWaterResponse> obtenerCastle(){
        final MutableLiveData<BaleWaterResponse> apiResponse = new MutableLiveData<>();

        try {
            Gson gson = new Gson();
            InputStream raw =  application.getResources().openRawResource(R.raw.bd);
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            BaleWaterResponse data = gson.fromJson(rd, BaleWaterResponse.class);

            apiResponse.setValue(data);

        }catch (Exception e){
            Log.e("ABCD", "Exception" + e.getMessage());
        }

        return apiResponse;
    }


    public LiveData<CastleDetail> obtenerCastleDetail(String castleId){
        final MutableLiveData<CastleDetail> apiResponse = new MutableLiveData<>();

        try {
            Gson gson = new Gson();

            int resourceId = application.getResources().getIdentifier(castleId, "raw", "com.example.balewater");

            InputStream raw =  application.getResources().openRawResource(resourceId);
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            CastleDetail data = gson.fromJson(rd, CastleDetail.class);

            apiResponse.setValue(data);

        }catch (Exception e){
            Log.e("ABCD", "Exception" + e.getMessage());
        }

        return apiResponse;
    }

//    public LiveData<BarceloninaResponse> obtenerTours(){
//        final MutableLiveData<BarceloninaResponse> apiResponse = new MutableLiveData<>();
//
//        BarceloninaApiModule.barceloninaApi.obtenerTours().enqueue(new Callback<BarceloninaResponse>() {
//            @Override
//            public void onResponse(Call<BarceloninaResponse> call, Response<BarceloninaResponse> response) {
//                apiResponse.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<BarceloninaResponse> call, Throwable t) {
//            }
//        });
//
//        return apiResponse;
//    }
//
//    public LiveData<TourDetail> obtenerTour(String id){
//        final MutableLiveData<TourDetail> tourDetailMutableLiveData = new MutableLiveData<>();
//
//        BarceloninaApiModule.barceloninaApi.obtenerTour(id).enqueue(new Callback<TourDetail>() {
//            @Override
//            public void onResponse(Call<TourDetail> call, Response<TourDetail> response) {
//                tourDetailMutableLiveData.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<TourDetail> call, Throwable t) {
//            }
//        });
//
//        return tourDetailMutableLiveData;
//    }
}
