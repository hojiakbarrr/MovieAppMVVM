package com.example.MovieAppMVVM.ui.actor_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.example.MovieAppMVVM.R
import com.example.MovieAppMVVM.databinding.ActivityActorDetailBinding
import com.example.MovieAppMVVM.models.actorDetail.Actor_detail
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ActorDetailActivity : AppCompatActivity() {

    private val binding: ActivityActorDetailBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityActorDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: ActorDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("idi", 0)
        viewModel.actordetail(id)


        viewModel.ddetail.observe(this){
            showDetail(it)
        }

    }

    private fun showDetail(it: Actor_detail) {
        binding.apply {
            nameOfActor.text = it.name
            days.text = it.birthday
            place.text = it.place_of_birth
            biography.text = it.biography
            idDb.text = it.imdb_id
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + it.profile_path)
                .into(profile)
        }
    }
}