package com.gordon.main.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class APIResponseBodyConverter<T extends Object> implements Converter<ResponseBody, T> {

    private Gson gson;
    private TypeAdapter<T> adapter;

    public APIResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
    public APIResponseBodyConverter() {
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
//        try {
//            return (T) bodyToString(value,"gb2312");
//        } finally {
//            value.close();
//        }
    }
    private String bodyToString(ResponseBody body,String charset) {
        try {
            InputStreamReader r = new InputStreamReader (body.byteStream(),charset);
            StringBuilder b = new StringBuilder();
            int line;
            while ((line = r.read()) != -1) {
                b.append((char)line);
            }
            return b.toString();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
