package com.example.MovieAppMVVM.ui.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.example.MovieAppMVVM.adapter.AdapterTrailer
import com.example.MovieAppMVVM.databinding.ActivityMovieDetailsBinding
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val trailerAdapter: AdapterTrailer by lazy(LazyThreadSafetyMode.NONE) {
        AdapterTrailer()
    }
    private val binding: ActivityMovieDetailsBinding  by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsMovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        viewModel.moviedetail(id)


        viewModel.detail.observe(this){
            showDetail(it)
        }
    }













    private fun showDetail(movie: MovieDetail) {
        binding.apply {
            moviesDetailsTitle.text = movie.title
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(moviesDetailsImageBanner)

            moviesDetailsDate.text = movie.release_date
            moviesDetailsScore.text = movie.vote_average.toString()
            moviesDetailsBodyOverview.text = movie.overview

        }
    }
}