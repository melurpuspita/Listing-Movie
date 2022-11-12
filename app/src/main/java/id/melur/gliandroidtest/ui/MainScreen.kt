package id.melur.gliandroidtest.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.melur.gliandroidtest.*
import id.melur.gliandroidtest.adapter.MovieAdapter
import id.melur.gliandroidtest.databinding.FragmentMainScreenBinding
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.MoviePopularItem
import id.melur.gliandroidtest.repository.MovieRepository
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient

class MainScreen : Fragment() {

    var haduh = "buset"
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var sharedPref: SharedPreferences

    private val apiService : TMDBApiService by lazy { TMDBClient.instance }

    private val movieRepository: MovieRepository by lazy { MovieRepository(apiService) }
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(movieRepository) }

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
        observeData()
        viewModel.getAllMovie()
        initRecyclerView()
        observeData()
        onTopofListClicked()
        observeData()
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

    private fun observeData() {
        viewModel.getAllMovie().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    movieAdapter.updateData(it.data)
                    binding.pbMovie.isVisible = false
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
//                initRecyclerView()
//                tvTitle.text = item.title
//                tvReleaseDate.text = item.releaseDate.toDate()
//                tvRate.text = item.voteAverage.toString()
//
//                Glide.with(itemView.context)
//                    .load(BuildConfig.BASE_URL_IMAGE + item.posterPath)
//                    .into(ivPoster)
//                itemMovie.setOnClickListener {
//                    onClickListener.invoke(item.id, item)
//                }
//                Glide.with(requireContext())
//                    .load("https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/" + it.backdropPath)
//                    .into(ivBackdrop)
//                Glide.with(requireContext())
//                    .load(BuildConfig.BASE_URL_IMAGE + it.posterPath)
//                    .into(ivPoster)
//                tvRate.text = it.voteAverage.toString()
//                tvReleaseDate.text = it.releaseDate.toDate()
//                tvTitle.text = it.title
//                tvPopularity.text = getString(R.string.popularity, it.popularity.toString())
//                tvVoteCount.text = getString(R.string.vote_count, it.voteCount.toString())
//                val overview = view?.findViewById<TextView>(R.id.tv_overview)
//                overview?.text = it.overview
//                binding.progressBar.isVisible = false


//    fun getAllMovie() {
//        apiService.getMoviePopular(BuildConfig.API_KEY)
//            .enqueue(object : Callback<MoviePopular> {
//                // kondisi get data berhasil dari http
//                override fun onResponse(
//                    call: Call<MoviePopular>,
//                    response: Response<MoviePopular>
//                ) {
//                    // response.issuccessful sama dengan 200
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            movieAdapter.updateData(response.body()!!)
//                        }
//                    }
//                    binding.pbMovie.isVisible = false
//                }
//                // kondisi get data gagal dari server/http, udh bener2 ga bisa di akses
//                override fun onFailure(call: Call<MoviePopular>, t: Throwable) {
//                    binding.pbMovie.isVisible = false
//                }
//
//            })
//    }
}

