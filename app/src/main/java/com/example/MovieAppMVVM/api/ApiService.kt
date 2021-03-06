package com.example.MovieAppMVVM.api

import androidx.lifecycle.MutableLiveData
import com.example.MovieAppMVVM.models.actorDetail.Actor_detail
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.example.MovieAppMVVM.models.films.ResponseMovie
import com.example.MovieAppMVVM.models.person.ResponseActor
import com.example.MovieAppMVVM.models.trailer.TrailerResponse
import com.example.MovieAppMVVM.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.POPULAR)
    suspend fun getPopularMovie(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): Response<ResponseMovie>

    @GET(Constants.UPCOMING)
    suspend fun getUpcomingMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): Response<ResponseMovie>

    @GET(Constants.TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): Response<ResponseMovie>

    @GET(Constants.NOW_PLAYING)
    suspend fun getNowPlayingMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): Response<ResponseMovie>

    @GET(Constants.MOVIE_DETAILS)
    suspend fun getMoviesDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language:String,
        @Query("api_key") apikey: String,
    ): Response<MovieDetail>

    @GET(Constants.MOVIE_TRAILERS)
    suspend fun getMovieTrailer(
        @Path("movie_id") id: Int,
        @Query("language") language: String,
        @Query("api_key") apikey: String
    ): Response<TrailerResponse>

    @GET(Constants.SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("query") name: String,
    ): Response<ResponseMovie>

    @GET(Constants.POPULAR_ACTORS)
    suspend fun getPerson(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResponseActor>

    @GET(Constants.SEARCH_ACTOR)
    suspend fun getSearchActor(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") name: String,
    ): Response<ResponseActor>

    @GET(Constants.ACTORS_DETAILS)
    suspend fun getActorsDetails(
        @Path("person_id") personId: Int,
        @Query("language")language:String,
        @Query("api_key") api_key: String,
    ): Response<Actor_detail>


}