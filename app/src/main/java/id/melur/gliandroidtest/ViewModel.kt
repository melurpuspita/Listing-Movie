package id.melur.gliandroidtest

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.melur.gliandroidtest.model.MoviePopular
import id.melur.gliandroidtest.model.MoviePopularItem
import id.melur.gliandroidtest.model.ReviewItem
import id.melur.gliandroidtest.model.ReviewResponse
import id.melur.gliandroidtest.service.TMDBApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModel(private val apiService: TMDBApiService) : ViewModel() {


    private val _reviewSuccess = MutableLiveData<ReviewItem>()
    val reviewSuccess: LiveData<ReviewItem> get() = _reviewSuccess

    private val _dataSuccess = MutableLiveData<MoviePopular>()
    val dataSuccess: LiveData<MoviePopular> get() = _dataSuccess

    private val _detailSuccess = MutableLiveData<MoviePopularItem>()
    val detailSuccess: LiveData<MoviePopularItem> get() = _detailSuccess

    private val _dataError = MutableLiveData<String>()
    val dataError: LiveData<String> get() = _dataError


    fun getDetailMovie(movieId: Int) {
        apiService.getDetailMovie(movieId = movieId, BuildConfig.API_KEY)
            .enqueue(object : Callback<MoviePopularItem> {
                override fun onResponse(
                    call: Call<MoviePopularItem>,
                    response: Response<MoviePopularItem>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            _detailSuccess.postValue(response.body())
                        } else {
                            _dataError.postValue("Datanya kosong")
                        }
                    } else {
                        _dataError.postValue("Pengambilan data gagal}")
                    }
                }

                override fun onFailure(call: Call<MoviePopularItem>, t: Throwable) {
                    _dataError.postValue("Server bermasalah")
                }
            })
    }

//    fun getAllMovie() {
//        apiService.getMoviePopular(BuildConfig.API_KEY)
//            .enqueue(object : Callback<MoviePopular> {
//                // kondisi get data berhasil dari http
//                override fun onResponse(
//                    call: Call<MoviePopular>,
//                    response: Response<MoviePopular>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            _dataSuccess.postValue(response.body())
//                            Log.d("SUKSES","$response")
//
//                        } else {
//                            _dataError.postValue("Datanya kosong")
//                        }
//                    } else {
//                        _dataError.postValue("Pengambilan data gagal}")
//                    }
//                }
//
//                override fun onFailure(call: Call<MoviePopular>, t: Throwable) {
//                    _dataError.postValue("Server bermasalah")
//                }
//            })
//    }

//    fun getReview(movieId: Int){
//        apiService.getReview(movieId = movieId, BuildConfig.API_KEY)
//            .enqueue(object : Callback<ReviewItem> {
//                override fun onResponse(
//                    call: Call<ReviewItem>,
//                    response: Response<ReviewItem>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            _reviewSuccess.postValue(response.body())
//                        } else {
//                            _dataError.postValue("Datanya kosong")
//                        }
//                    } else {
//                        _dataError.postValue("Pengambilan data gagal}")
//                    }
//                }
//
//                override fun onFailure(call: Call<ReviewItem>, t: Throwable) {
//                    _dataError.postValue("Server bermasalah")
//                }
//            })
//    }

}