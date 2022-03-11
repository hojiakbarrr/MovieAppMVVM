package com.example.MovieAppMVVM.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.MovieApi
import com.example.MovieAppMVVM.utils.Constants
import retrofit2.HttpException

enum class ResponseType{
    POPULAR,UPCOMING,
}

//class MoviePagingSource(private val apiService: ApiService, private val type: ResponseType) : PagingSource<Int, MovieApi>() {
//    override fun getRefreshKey(state: PagingState<Int, MovieApi>): Int? {
//
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieApi> {
//        return try {
//            val currentPage = params.key?: 1
//
//
//          val response = when(type){
//              ResponseType.POPULAR -> apiService.getPopularMovie(Constants.API_KEY,currentPage)
//              ResponseType.UPCOMING -> apiService.getUpcomingMovies(Constants.API_KEY,currentPage)
//          }
//
//            val data = response.body()?.results?: emptyList()
//            Log.d("testLogs", "OnResponse Success ${data}")
//            val responseData = mutableListOf<MovieApi>()
//            responseData.addAll(data)
//
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (currentPage == 1) null else -1,
//                nextKey = currentPage.plus(1)
//            )
//
//        }catch (e: Exception){
//            LoadResult.Error(e)
//        }    }
//}


class MoviePagingSource(
    private val apiService: ApiService,
    private val responseType: ResponseType,
) : PagingSource<Int, MovieApi>() {
    override fun getRefreshKey(state: PagingState<Int, MovieApi>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieApi> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(20)
        return try {

            val response = when(responseType){
                ResponseType.POPULAR -> apiService.getPopularMovie(Constants.API_KEY,page)
                ResponseType.UPCOMING -> apiService.getUpcomingMovies(Constants.API_KEY,page)
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
