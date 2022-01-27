package com.example.sapperjava.data;

import com.example.sapperjava.domain.RequestDto;
import com.example.sapperjava.domain.ResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("splash.php")
    Call<ResponseDto> sendLocale(@Body RequestDto request);

}
