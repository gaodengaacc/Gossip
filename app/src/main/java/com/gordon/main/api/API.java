package com.gordon.main.api;

import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public class API extends APIBase {
    public DoService doService;

    public API(String baseUrl, Converter.Factory factory, Interceptor interceptor) {
        doService = create(DoService.class, baseUrl,factory, interceptor);
    }
}
