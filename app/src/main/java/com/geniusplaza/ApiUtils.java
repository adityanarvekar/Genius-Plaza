package com.geniusplaza;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://reqres.in/api/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
