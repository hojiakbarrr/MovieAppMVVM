package com.example.MovieAppMVVM.ui.actor

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.example.MovieAppMVVM.models.person.Actor
import com.example.MovieAppMVVM.models.trailer.TrailerResponse
import com.example.MovieAppMVVM.paging.ActorPagingSource
import com.example.MovieAppMVVM.paging.ResponseType
import com.example.MovieAppMVVM.paging.ResponseTypeActor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class ActorsViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val responsetype: MutableLiveData<ResponseTypeActor> = MutableLiveData()
    private val _text: MutableLiveData<String> = MutableLiveData()



    val actorFlow: Flow<PagingData<Actor>> by lazy {
        responsetype.asFlow()
            .flatMapLatest<ResponseTypeActor?, PagingData<Actor>> {
                createPagerActors()
            }.cachedIn(viewModelScope)
    }

    private fun createPagerActors(): Flow<PagingData<Actor>> =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ActorPagingSource(
                    apiService = apiService,
                    responseType = responsetype.value!!, query = _text.value!!
                    )
            }
        ).flow

    fun getSearch(text: String){
        if (text == ""){
            responsetype.value = ResponseTypeActor.POPULAR_ACTORS
        }else{
            _text.value = text
        }
    }

    fun getResponse(type: ResponseTypeActor){
        responsetype.value = type
    }


    init {
        responsetype.value = ResponseTypeActor.POPULAR_ACTORS
    }

    init {
        _text.value = ""
    }


}