package id.melur.gliandroidtest.ui.tablayout

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.melur.gliandroidtest.BuildConfig
import id.melur.gliandroidtest.ViewModel
import id.melur.gliandroidtest.adapter.VideoAdapter
import id.melur.gliandroidtest.databinding.FragmentVideosBinding
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.VideoItem
import id.melur.gliandroidtest.model.Videos
import id.melur.gliandroidtest.repository.MovieRepository
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Videos : Fragment() {


    private var _binding: FragmentVideosBinding? = null
    private val binding get() = _binding!!

    private lateinit var videoAdapter: VideoAdapter
    private lateinit var sharedPref: SharedPreferences
    private var movieId: Int? = 0

    private val apiService : TMDBApiService by lazy { TMDBClient.instance }

    private val movieRepository: MovieRepository by lazy { MovieRepository(apiService) }
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(movieRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        getVideos(436270)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        videoAdapter = VideoAdapter { id: Int, video: VideoItem ->
            val key = video.key
//            binding.rvVideo.setOnClickListener{
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://www.youtube.com/watch?v=$key")
                startActivity(openURL)
//            }
        }
        binding.rvVideo.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

//    fun getVideos(movieId: Int){
//        apiService.getVideos(movieId = movieId, BuildConfig.API_KEY)
//            .enqueue(object : Callback<Videos> {
//                override fun onResponse(
//                    call: Call<Videos>,
//                    response: Response<Videos>
//                ) {
//                    if (response.isSuccessful) {
//                        if (response.body() != null) {
//                            videoAdapter.updateData(response.body()!!)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<Videos>, t: Throwable) {
//                    Toast.makeText(requireContext(), "hiks", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }

}