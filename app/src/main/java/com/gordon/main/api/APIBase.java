package com.gordon.main.api;


import com.gordon.main.http.APIClient;

import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public class APIBase {
    /**
     * 根据传入的Service接口，创建接口实例
     *
     * @param service
     * @param <T>
     * @return
     */
    protected  <T> T create(Class<? extends T> service, String baseUrl, Converter.Factory factory,Interceptor interceptor) {
        return  new APIClient(baseUrl,factory,interceptor)
                .retrofit()
                .create(service);
    }

}