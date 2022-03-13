package com.example.MovieAppMVVM.repository

import androidx.lifecycle.MutableLiveData
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.actorDetail.Actor_detail
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: ApiService){

    suspend fun MovieDetail(id: Int, ru: String, key: String): Response<MovieDetail> = api.getMoviesDetails(id,ru,key)


    suspend fun ActorDetail(id: Int, ru: String, key: String): Response<Actor_detail> = api.getActorsDetails(id,ru,key)


}