package com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juan.carlos.flores.bastida.movies.pruebamacropay.data.remote.dto.movies.Movie
import javax.inject.Inject

class MoviePaging @Inject constructor(
    private val movieAPI: MovieAPI
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = movieAPI.getMovies(page = nextPageNumber)
            val movies = response.results
            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1
            val nextKey = if (movies.isEmpty()) null else nextPageNumber + 1
            return LoadResult.Page(data = movies, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}