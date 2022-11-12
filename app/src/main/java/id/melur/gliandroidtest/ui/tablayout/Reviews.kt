package id.melur.gliandroidtest.ui.tablayout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import id.melur.gliandroidtest.*
import id.melur.gliandroidtest.adapter.ReviewAdapter
import id.melur.gliandroidtest.databinding.FragmentReviewsBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.MovieReviews
import id.melur.gliandroidtest.model.ReviewItem
import id.melur.gliandroidtest.repository.MovieRepository
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Flow

class Reviews : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var sharedPref: SharedPreferences
    private var id: Int? = 0

    private val pref: DataStore by lazy { DataStore(requireContext()) }
    private val apiService : TMDBApiService by lazy { TMDBClient.instance }
    private val movieRepository: MovieRepository by lazy { MovieRepository(apiService) }
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(movieRepository) }
    private val dataStore: DataStoreViewModel by viewModelsFactory { DataStoreViewModel(pref) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences("id", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireContext().getSharedPreferences("id", Context.MODE_PRIVATE)
//        val movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))

//        observeData(movieId!!)
        observeData(436270)
        getData()
//        getReview(436270)
        val movieId = arguments?.getInt("id")
        val test = sharedPref.getInt("id", 0)
        val EXAMPLE_COUNTER = intPreferencesKey("key_id")

        Toast.makeText(requireContext(), EXAMPLE_COUNTER.toString(), Toast.LENGTH_SHORT).show()


        initRecyclerView()
    }

    private fun getData() {
        id = sharedPref.getInt("id", 0)
    }

    private fun initRecyclerView() {
        reviewAdapter = ReviewAdapter { id: Int, review: ReviewItem ->
            val bundle = Bundle()
            bundle.putInt("id", id)
//            findNavController().navigate(R.id.action_mainScreen_to_infoScreen, bundle)
        }
        binding.rvReview.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeData(movieId: Int) {
        dataStore.getMovieId().observe(viewLifecycleOwner) {
//            viewModel.movieId(it)
        }
        viewModel.getReview(movieId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    reviewAdapter.updateData(it.data)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

//    fun getReview(movieId: Int){
//        apiService.getReview(movieId = movieId, BuildConfig.API_KEY)
//            .enqueue(object : Callback<MovieReviews> {
//                override fun onResponse(
//                    call: Call<MovieReviews>,
//                    response: Response<MovieReviews>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            reviewAdapter.updateData(response.body()!!)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieReviews>, t: Throwable) {
//                    Toast.makeText(requireContext(), "hiks", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }

}