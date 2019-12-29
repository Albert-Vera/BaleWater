package com.example.balewater.api;

import android.app.DownloadManager;
import android.util.Log;

import java.io.IOException;


public class BaleWaterApiModule {

    public static BaleWaterApi baleWaterApi = new Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/Albertvedu/BaleWater/master/app/src/main/res/raw/")
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            DownloadManager.Request request = chain.request();

                            long c1 = System.nanoTime();
                            Log.e("INTERCEPTOR", String.format("Sending request %s on %s%n%s",
                                    request.url(), chain.connection(), request.headers()));

                            okhttp3.Response response = chain.proceed(request);

                            long c2 = System.nanoTime();
                            Log.e("INTERCEPTOR---", String.format("Received response for %s in %.1fms%n%s",
                                    response.request().url(), (c2 - c1) / 1e6d, response.headers()));

                            return response;
                        }
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BaleWaterApi.class);
}
