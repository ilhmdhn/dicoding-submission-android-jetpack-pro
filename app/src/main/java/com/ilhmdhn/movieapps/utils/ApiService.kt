package com.ilhmdhn.movieapps.utils

import com.ilhmdhn.movieapps.data.source.remote.response.ResponseResult
import com.ilhmdhn.movieapps.data.source.remote.response.ShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String
    ): Call<ResponseResult>

    @GET("tv/popular")
    fun getTv(
        @Query("api_key") apiKey: String
    ): Call<ResponseResult>

    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Call<ShowResponse>

    @GET("tv/{id}")
    fun getTvShowDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Call<ShowResponse>
}