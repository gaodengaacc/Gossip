package com.gordon.main.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/13
 * do()
 */

public class APIPhoneRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/html" + "; charset=utf-8");

    private Gson gson;
    private TypeAdapter<T> adapter;


    public APIPhoneRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
    public APIPhoneRequestBodyConverter() {
    }
    @Override
    public RequestBody convert(T value) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE,value.toString());
        return body ;
    }

}
