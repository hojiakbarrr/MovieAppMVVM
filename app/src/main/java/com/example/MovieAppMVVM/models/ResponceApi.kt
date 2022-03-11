package com.example.MovieAppMVVM.models

data class ResponceApi(
    val page: Int,
    val results: List<MovieApi>,
    val total_pages: Int,
    val total_results: Int
)
