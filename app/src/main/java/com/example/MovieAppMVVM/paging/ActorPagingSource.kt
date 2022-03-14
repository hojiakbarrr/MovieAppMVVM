package com.example.MovieAppMVVM.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.MovieAppMVVM.api.ApiService
import com.example.MovieAppMVVM.models.person.Actor
import com.example.MovieAppMVVM.utils.Constants
import retrofit2.HttpException

enum class ResponseTypeActor{
   POPULAR_ACTORS,SEARCH_ACTOR
}

class ActorPagingSource(
    private val apiService: ApiService,
    private val responseType: ResponseTypeActor,
    private val query: String,
) : PagingSource<Int, Actor>() {
    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(20)
        return try {

            val response = when(responseType){
                ResponseTypeActor.POPULAR_ACTORS -> apiService.getPerson(Constants.API_KEY,Constants.RUSSIA,page)
                ResponseTypeActor.SEARCH_ACTOR -> apiService.getSearchActor(Constants.API_KEY,Constants.RUSSIA,query)

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