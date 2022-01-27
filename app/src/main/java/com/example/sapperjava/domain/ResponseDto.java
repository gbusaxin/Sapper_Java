package com.example.sapperjava.domain;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class ResponseDto {
    @SerializedName("url")
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
