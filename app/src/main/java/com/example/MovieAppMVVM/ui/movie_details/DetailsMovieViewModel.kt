package com.example.MovieAppMVVM.ui.movie_details

import android.util.Log
import androidx.lifecycle.*
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.example.MovieAppMVVM.repository.MovieRepository
import com.example.MovieAppMVVM.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class DetailsMovieViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _detail: MutableLiveData<MovieDetail> = MutableLiveData()
    val detail: LiveData<MovieDetail> = _detail

    fun moviedetail(id: Int) = viewModelScope.launch {
        var response = repository.MovieDetail(id,Constants.RUSSIA,Constants.API_KEY)

        if (response.isSuccessful) {
            _detail.value = response.body()
//            Log.d("testing", "id $_detail")
        }

    }



}