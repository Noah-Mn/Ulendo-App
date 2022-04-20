package com.example.ulendoapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * The type Api client.
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    /**
     * Get client retrofit.
     *
     * @return the retrofit
     */
    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/fcm/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

