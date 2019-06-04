package com.ssdi.pricesplash;


import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET("products")
    Call<ResponseObject> getTopRatedMovies(@Query("key") String apiKey, @Query("formatted") String formatted, @Query("barcode") String barcode);

/*
    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
*/


    @GET
    Call<ResponseBody> getListofProducts(@Url String url,@Query("key") String key, @Query("cx") String cx, @Query("q") String query, @Query("startIndex") int startIndex);

}
