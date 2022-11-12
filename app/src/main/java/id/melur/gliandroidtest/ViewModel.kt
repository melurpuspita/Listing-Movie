package id.melur.gliandroidtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.melur.gliandroidtest.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(private val repository: MovieRepository) : ViewModel() {

    val movieId = MutableLiveData<Int?>()

    fun getAllMovie() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getMovie(BuildConfig.API_KEY)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getDetailMovie(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getDetailMovie(
                        movieId,
                        BuildConfig.API_KEY
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun getReview(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    repository.getReview(
                        movieId,
                        BuildConfig.API_KEY
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

//    fun getMovieId(id: Int) {
//        viewModelScope.launch {
//            movieId.value = repository.getMovie(id)
//        }
//    }
}
