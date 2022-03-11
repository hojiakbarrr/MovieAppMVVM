package com.example.MovieAppMVVM.api

import com.example.MovieAppMVVM.models.ResponceApi
import com.example.MovieAppMVVM.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getPopularMovie(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): Response<ResponceApi>


    @GET(Constants.END_POINT1)
    suspend fun  getUpcomingMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): Response<ResponceApi>
}