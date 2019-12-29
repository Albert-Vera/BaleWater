package com.example.balewater.api;


import com.example.balewater.Model.BaleWaterResponse;
import com.example.balewater.Model.CastleDetail;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BaleWaterApi {

    @GET("bd.json")
    Call<BaleWaterResponse> obtenerCastle();



    @GET("{id}")
    Call<CastleDetail> obtenerCastle(String id);
}
