package com.example.paginationdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
    @GET("top.json")
    Call<String> STRING_CALL(


    );

}
