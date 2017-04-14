package com.gordon.main.api.response;

/**
 * @author Gordon
 * @since 2017/4/13
 * do()
 */

public class PhoneOneResponse extends BaseResponse {
    private String phoneValue = "";
    private String isGoodOrBad = "";

    public String getPhoneValue() {
        return phoneValue;
    }

    public void setPhoneValue(String phoneValue) {
        this.phoneValue = phoneValue;
    }

    public String getGoodOrBad() {
        return isGoodOrBad;
    }

    public void setGoodOrBad(String isGoodOrBad) {
        isGoodOrBad = isGoodOrBad;
    }
}
