package com.example.MovieAppMVVM.ui

import androidx.lifecycle.*
import androidx.paging.*
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.MovieApi
import com.example.MovieAppMVVM.paging.MoviePagingSource
import com.example.MovieAppMVVM.paging.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel

class MoviesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _responseType: MutableLiveData<ResponseType> = MutableLiveData()

    val movieFlow: Flow<PagingData<MovieApi>> by lazy {
        _responseType.asFlow()
            .flatMapLatest<ResponseType?, PagingData<MovieApi>> {
                createPagerMovies()
            }.cachedIn(viewModelScope)
    }

    fun createPagerMovies(): Flow<PagingData<MovieApi>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService = apiService,
                    responseType = _responseType.value!!)
            }
        ).flow


    fun getResponse(type: ResponseType){
        _responseType.value = type
    }

    init {
        _responseType.value = ResponseType.POPULAR
    }
}