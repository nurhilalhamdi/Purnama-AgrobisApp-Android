package org.d3ifcool.testing.RestAPI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

//    private static final String baseURL = "http://192.168.1.13/testingAPI/";
//    private static final String baseURL = "http://192.168.1.13/ci4Project/public/";
//    private static final String baseURL = "http://192.168.1.15/Ayamqu/api/";
//    private static final String baseURL = "https://ayamqu-id.000webhostapp.com/api/";
    private static final String baseURL = "https://www.ayamqu.web.id/api/";
//    private static final String baseURL = "https://agrobis-id.000webhostapp.com/api/    ";
    private static Retrofit retrofit;

    public static Retrofit konekRetrofit(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
