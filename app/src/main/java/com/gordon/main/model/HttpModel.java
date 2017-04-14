package com.gordon.main.model;

import com.gordon.main.api.API;
import com.gordon.main.api.DoService;
import com.gordon.main.http.ResultInterceptor;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import retrofit2.Converter;

/**
 * @author Gordon
 * @since 2017/4/5
 * do()
 */

public class HttpModel {

    public String baseUrl;
    public Interceptor interceptor;
    private DoService service;

    public HttpModel(String baseUrl, Converter.Factory factory, Interceptor interceptor) {
        this.baseUrl = baseUrl;
        this.interceptor = interceptor;
        service = new API(baseUrl, factory ,interceptor).doService;

    }

    public Observable result(String name, ResultInterceptor.Sign sign) {
//        RequestBean requestBean = new RequestBean();
//        String s1 = java.net.URLEncoder.encode(name, "gb2312");//%B8%DF%B5%C7
//        String s2 = java.net.URLEncoder.encode(name, "UTF-8");
//        String s3 = java.net.URLEncoder.encode(name, "ISO-8859-1");
//        requestBean.setCeming(java.net.URLEncoder.encode(name, "gb2312"));
        Observable observable = null;
        switch (sign) {
            case NameOne:
                observable = service.nameOne("ceming=" + name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case NameTwo:
                observable = service.nameTwo("hanzi=" + name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case NameThree:
                observable = service.nameThree("text=" + name + "&" + "do=yess")
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case NameFour:
                observable = service.nameFour("text=" + name + "&" + "do=yess")
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case NameFive:
                observable = service.nameFive("xm=" + name + "&" + "dxfx=1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case PhoneOne:
                observable = service.phoneOne(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            case PhoneTwo:
                break;
            case PhoneThree:
                break;
            default:
                observable = service.nameOne("ceming=" + name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;

        }
        return observable;
    }
}
