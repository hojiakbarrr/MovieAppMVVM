package com.example.MovieAppMVVM.ui.actor_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MovieAppMVVM.models.actorDetail.Actor_detail
import com.example.MovieAppMVVM.repository.MovieRepository
import com.example.MovieAppMVVM.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActorDetailsViewModel @Inject constructor(private val repositor: MovieRepository) :
    ViewModel() {
        private val _ddetail: MutableLiveData<Actor_detail> = MutableLiveData()
    val ddetail: LiveData<Actor_detail> = _ddetail

    fun actordetail(id: Int) = viewModelScope.launch {
        var response = repositor.ActorDetail(id,Constants.RUSSIA,Constants.API_KEY)

        if (response.isSuccessful) {
            _ddetail.value = response.body()
            Log.d("testing1", "id $response")
        }

    }

}