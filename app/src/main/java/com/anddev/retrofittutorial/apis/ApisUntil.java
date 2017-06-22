package com.anddev.retrofittutorial.apis;

/**
 * Created by setia on 22/06/2017.
 */

public class ApisUntil {
    public final static String BASE_URL = "https://api.stackexchange.com/2.2/";
    public static SOService getSOService(){
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
