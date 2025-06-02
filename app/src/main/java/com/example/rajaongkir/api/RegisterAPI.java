package com.example.rajaongkir.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("api_ongkir.php")
    Call<ResponseBody> cekongkir(@Field("origin") String origin,
                                 @Field("destination") String destination,
                                 @Field("weight") int weight,
                                 @Field("courier") String jne);

    @GET("get_provinsi.php")
    Call<ResponseBody> get_provinsi();

    @GET("get_kota.php")
    Call<ResponseBody> get_kota(@Query("province_id") int province_id);
}
