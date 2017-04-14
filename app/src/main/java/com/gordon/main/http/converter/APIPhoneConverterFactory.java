package com.gordon.main.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Gordon
 * @since 2017/4/13
 * do()
 */

public class APIPhoneConverterFactory extends Converter.Factory{
    private Gson gson;

    public APIPhoneConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("parameter gson can not be null");
        this.gson = gson;
    }

    public static APINameConverterFactory create() {
        return create(new Gson());
    }

    public static APINameConverterFactory create(Gson gson) {
        return new APINameConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIResponseBodyConverter(gson,adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new APIPhoneRequestBodyConverter(gson,adapter);
    }
}
