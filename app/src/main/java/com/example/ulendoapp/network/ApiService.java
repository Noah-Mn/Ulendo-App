package com.example.ulendoapp.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * The interface Api service.
 */
public interface
ApiService {

    /**
     * Send message call.
     *
     * @param headers     the headers
     * @param messageBody the message body
     * @return the call
     */
    @POST("send")
    Call<String> sendMessage(
            @HeaderMap HashMap<String, String> headers,
            @Body String messageBody
            );
}
