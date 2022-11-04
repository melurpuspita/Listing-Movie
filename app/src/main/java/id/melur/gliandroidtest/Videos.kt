package id.melur.gliandroidtest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.google.android.material.card.MaterialCardView
import id.melur.gliandroidtest.adapter.ReviewAdapter
import id.melur.gliandroidtest.adapter.VideoAdapter
import id.melur.gliandroidtest.databinding.FragmentInfoScreenBinding
import id.melur.gliandroidtest.databinding.FragmentReviewsBinding
import id.melur.gliandroidtest.databinding.FragmentVideosBinding
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.MovieReviews
import id.melur.gliandroidtest.model.ReviewItem
import id.melur.gliandroidtest.model.VideoItem
import id.melur.gliandroidtest.model.Videos
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
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(apiService) }

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

//        sharedPref = requireContext().getSharedPreferences("movieId", Context.MODE_PRIVATE)
//        val movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))
//        val movieId = arguments?.getInt("movieId")
//        Toast.makeText(requireContext(), "Reviews = $movieId", Toast.LENGTH_SHORT).show()

        getVideos(436270)
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

    fun getVideos(movieId: Int){
        apiService.getVideos(movieId = movieId, BuildConfig.API_KEY)
            .enqueue(object : Callback<Videos> {
                override fun onResponse(
                    call: Call<Videos>,
                    response: Response<Videos>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            videoAdapter.updateData(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Videos>, t: Throwable) {
                    Toast.makeText(requireContext(), "hiks", Toast.LENGTH_SHORT).show()
                }
            })
    }

}