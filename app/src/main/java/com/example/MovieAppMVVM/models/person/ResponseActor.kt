package com.example.MovieAppMVVM.models.person

data class ResponseActor (
    val page: Int,
    val results: List<Actor>,
    val total_pages: Int,
    val total_results: Int
        )