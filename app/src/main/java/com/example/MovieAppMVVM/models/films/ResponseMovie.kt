package com.example.MovieAppMVVM.models.films

import com.example.MovieAppMVVM.models.films.Movie

data class ResponseMovie(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
