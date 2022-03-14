package com.example.MovieAppMVVM.ui.movie_details

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import com.example.MovieAppMVVM.R
import com.example.MovieAppMVVM.adapter.AdapterTrailer
import com.example.MovieAppMVVM.databinding.ActivityMovieDetailsBinding
import com.example.MovieAppMVVM.models.filmDetail.MovieDetail
import com.example.MovieAppMVVM.models.trailer.TrailerResponse
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
        invisible()


        val id = intent.getIntExtra("id", 0)
        viewModel.moviedetail(id)
        viewModel.getTrailer(id)


        viewModel.detail.observe(this){
            showDetail(it)
            binding.progress.visibility = INVISIBLE

        }
        viewModel.detailTrailer.observe(this){
            showTrailer(it)
        }


    }



//    private fun progressbar() {
//        val builder = AlertDialog.Builder(this)
//        val dialogview = layoutInflater.inflate(R.layout.progress_dialog,null)
//        builder.setView(dialogview)
//        builder.setCancelable(false)
//        val dialog = builder.create()
//        dialog.window?.setBackgroundDrawableResource(com.firebase.ui.auth.R.color.fui_transparent)
//        dialog.show()
//    }

    private fun invisible(){
        binding.apply {
            binding.progress.visibility = VISIBLE
            rcTrailer.visibility = INVISIBLE
            moviesDetailsTitle.visibility = INVISIBLE
            moviesDetailsImageBanner.visibility = INVISIBLE
            moviesDetailsDate.visibility = INVISIBLE
            moviesDetailsScore.visibility = INVISIBLE
            moviesDetailsBodyOverview.visibility = INVISIBLE
            moviesDetailsBodyOverviewLabel.visibility = INVISIBLE
            moviesDetailsScores.visibility = INVISIBLE
            moviesDetailsDateLabel.visibility = INVISIBLE
        }
    }

    private fun visible(){
        binding.apply {
            binding.progress.visibility = INVISIBLE
            rcTrailer.visibility = VISIBLE
            moviesDetailsTitle.visibility = VISIBLE
            moviesDetailsImageBanner.visibility = VISIBLE
            moviesDetailsDate.visibility = VISIBLE
            moviesDetailsScore.visibility = VISIBLE
            moviesDetailsBodyOverview.visibility = VISIBLE
            moviesDetailsBodyOverviewLabel.visibility = VISIBLE
            moviesDetailsScores.visibility = VISIBLE
            moviesDetailsDateLabel.visibility = VISIBLE
        }
    }

    private fun showTrailer(it: TrailerResponse) {
        binding.apply {
            rcTrailer.adapter = trailerAdapter
            trailerAdapter.trailerList = it.trailerList

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
            visible()

        }
    }
}