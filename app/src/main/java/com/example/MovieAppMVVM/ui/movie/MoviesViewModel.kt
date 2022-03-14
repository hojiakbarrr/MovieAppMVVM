package com.example.MovieAppMVVM.ui.movie

import android.content.Context
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.paging.*
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.example.MovieAppMVVM.models.films.Movie
import com.example.MovieAppMVVM.models.trailer.TrailerResponse
import com.example.MovieAppMVVM.paging.MoviePagingSource
import com.example.MovieAppMVVM.paging.ResponseType
import com.example.MovieAppMVVM.repository.MovieRepository
import com.example.MovieAppMVVM.utils.Constants
import com.google.android.material.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {



    private val _responseType: MutableLiveData<ResponseType> = MutableLiveData()
    private val _text: MutableLiveData<String> = MutableLiveData()


    val movieFlow  :  Flow<PagingData<Movie>> by lazy {
        _responseType.asFlow()
            .flatMapLatest<ResponseType?, PagingData<Movie>> {
                createPagerMovies()
            }.cachedIn(viewModelScope)
    }

    fun createPagerMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService = apiService,
                    responseType = _responseType.value!!, query = _text.value!!)
            }
        ).flow


    fun getResponse(type: ResponseType){
        _responseType.value = type
    }

    fun getSearch(text: String){
        _text.value = text
    }

//    init {             заменил в спиннере где ничего не выбирается поэтому закоменнтировал
//        _responseType.value = ResponseType.POPULAR
//    }

    init {
        _text.value = ""
    }




}