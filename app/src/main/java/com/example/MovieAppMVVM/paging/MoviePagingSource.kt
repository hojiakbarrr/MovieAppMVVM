package com.example.MovieAppMVVM.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.films.Movie
import com.example.MovieAppMVVM.repository.MovieRepository
import com.example.MovieAppMVVM.utils.Constants
import com.example.MovieAppMVVM.utils.Constants.API_KEY
import retrofit2.HttpException

enum class ResponseType{
    POPULAR,UPCOMING,TOP_RATED,NOW_PLAYING,SEARCH
}

@Suppress("IMPLICIT_CAST_TO_ANY")
class MoviePagingSource(
    private val apiService: ApiService,
    private val responseType: ResponseType,
    private val query: String,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(20)
        return try {

            val response = when(responseType){
                ResponseType.POPULAR -> apiService.getPopularMovie(Constants.API_KEY,page)
                ResponseType.UPCOMING -> apiService.getUpcomingMovies(Constants.API_KEY,page)
                ResponseType.TOP_RATED -> apiService.getTopRatedMovies(Constants.API_KEY,page)
                ResponseType.NOW_PLAYING -> apiService.getNowPlayingMovies(Constants.API_KEY,page)
                ResponseType.SEARCH -> apiService.getSearchMovie(API_KEY,query)
            }

            if (response.isSuccessful) {
                val movie = checkNotNull(response.body()?.results)
                val nextKey = if (movie.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(movie, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
