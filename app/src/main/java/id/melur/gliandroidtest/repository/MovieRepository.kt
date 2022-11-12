package id.melur.gliandroidtest.repository

import id.melur.gliandroidtest.service.TMDBApiService


class MovieRepository(private val apiService: TMDBApiService) {
    suspend fun getMovie(key: String) = apiService.getMoviePopular(key)

    suspend fun getDetailMovie(movieId: Int, key: String) = apiService.getDetailMovie(movieId, key)

    suspend fun getReview(movieId: Int, key: String) = apiService.getReview(movieId, key)

}
