package com.gordon.main.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */

public class APINameRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded" + "; charset=gb2312");
    private static final Charset UTF_8 = Charset.forName("gb2312");

    private Gson gson;
    private TypeAdapter<T> adapter;


    public APINameRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
    public APINameRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
//        Buffer buffer = new Buffer();
//        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
//        JsonWriter jsonWriter = gson.newJsonWriter(writer);
//        adapter.write(jsonWriter, value);
//        jsonWriter.close();
        RequestBody body = RequestBody.create(MEDIA_TYPE,value.toString());
        return body ;
    }

}
