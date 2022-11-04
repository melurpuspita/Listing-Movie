package id.melur.gliandroidtest.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.melur.gliandroidtest.BuildConfig
import id.melur.gliandroidtest.R
import id.melur.gliandroidtest.ViewModel
import id.melur.gliandroidtest.adapter.MovieAdapter
import id.melur.gliandroidtest.databinding.FragmentMainScreenBinding
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.MoviePopular
import id.melur.gliandroidtest.model.MoviePopularItem
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var sharedPref: SharedPreferences

    private val apiService : TMDBApiService by lazy { TMDBClient.instance }
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllMovie()
        initRecyclerView()
        onTopofListClicked()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter { id: Int, movie: MoviePopularItem ->
            sharedPref = requireContext().getSharedPreferences("movieId", Context.MODE_PRIVATE)
            val pref = sharedPref.getInt("username", id)

            val bundle = Bundle()
            bundle.putInt("id", pref)
            findNavController().navigate(R.id.action_mainScreen_to_infoScreen, bundle)
        }
        binding.rvData.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun onTopofListClicked(){
        binding.fabToTopList.setOnClickListener {
            binding.rvData.smoothScrollToPosition(0)
        }
    }

    fun getAllMovie() {
        apiService.getMoviePopular(BuildConfig.API_KEY)
            .enqueue(object : Callback<MoviePopular> {
                // kondisi get data berhasil dari http
                override fun onResponse(
                    call: Call<MoviePopular>,
                    response: Response<MoviePopular>
                ) {
                    // response.issuccessful sama dengan 200
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            movieAdapter.updateData(response.body()!!)
                        }
                    }
                    binding.pbMovie.isVisible = false
                }
                // kondisi get data gagal dari server/http, udh bener2 ga bisa di akses
                override fun onFailure(call: Call<MoviePopular>, t: Throwable) {
                    binding.pbMovie.isVisible = false
                }

            })
    }
}

