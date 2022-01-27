package com.example.sapperjava.domain;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class RequestDto {
    @SerializedName("locale")
    String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
