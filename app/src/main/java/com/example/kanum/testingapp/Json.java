package com.example.kanum.testingapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Json {


    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("image")
    @Expose
    private String image;

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

}