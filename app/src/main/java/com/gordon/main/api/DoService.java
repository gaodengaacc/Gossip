package com.gordon.main.api;

import com.gordon.main.api.response.BaseResponse;
import com.gordon.main.api.response.NameFiveResponse;
import com.gordon.main.api.response.PhoneOneResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Gordon
 * @since 2017/4/5
 * do()
 */

public interface DoService {
    @POST("gongsi_ceming.php")
    Observable<BaseResponse> nameOne(@Body String ceming);
    @POST("qiaolian.asp")
    Observable<BaseResponse> nameTwo(@Body String hanzi);
    @POST("name.php")
    Observable<BaseResponse> nameThree(@Body String text);
    @POST("shentui.php")
    Observable<BaseResponse> nameFour(@Body String text);
    @POST("xmdf.php")
    Observable<NameFiveResponse> nameFive(@Body String text);
    @POST("/")
    Observable<PhoneOneResponse> phoneOne(@Query("q") String text);
}
