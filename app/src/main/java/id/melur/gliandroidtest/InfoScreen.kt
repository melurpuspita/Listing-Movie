package id.melur.gliandroidtest

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.melur.gliandroidtest.adapter.InfoAdapter
import id.melur.gliandroidtest.adapter.ReviewAdapter
import id.melur.gliandroidtest.databinding.FragmentInfoScreenBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.model.MovieReviews
import id.melur.gliandroidtest.model.ReviewItem
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoScreen : Fragment() {

    private var _binding: FragmentInfoScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var reviewAdapter: ReviewAdapter

    private lateinit var sharedPref: SharedPreferences
    private var movieId: Int? = 0


    private val apiService : TMDBApiService by lazy { TMDBClient.instance }
    private val viewModel: ViewModel by viewModelsFactory { ViewModel(apiService) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences("movieId", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireContext().getSharedPreferences("movieId", Context.MODE_PRIVATE)

        val movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))
//        val movieId = arguments?.getInt("id")
        viewModel.getDetailMovie(movieId)
//        viewModel.getReview(movieId)
        setTabAndViewPager()
//        getReview(movieId)
//        initRecyclerView()
        observeData()
        onBackPressed()
    }


    private fun setTabAndViewPager() {
        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager

        val listFragment = mutableListOf(
            Overview(),
            Reviews(),
            Videos()
        )

        val infoAdapter = activity?.let { InfoAdapter(listFragment, it.supportFragmentManager, lifecycle) }
        binding.viewPager.adapter = infoAdapter

//        val editor: SharedPreferences.Editor() = sharedPreference.edit()
//        editor.clear()
//        editor.apply()
        movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))
//        val movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Overview"
//                    Toast.makeText(requireContext(), "Reviews = $movieId", Toast.LENGTH_SHORT).show()
//                    tab.setCol
                }
                1 -> {
                    tab.text = "Reviews"
//                    tab.setIcon(R.drawable.ic_fi_diminati)
//                    Toast.makeText(requireContext(), "Review = $movieId", Toast.LENGTH_SHORT).show()

                }
                2 -> {
                    tab.text = "Videos"
//                    Toast.makeText(requireContext(), "Videos = $movieId", Toast.LENGTH_SHORT).show()

//                    tab.setIcon(R.drawable.ic_fi_terjual)
                }
            }
        }.attach()
    }

    private fun observeData() {
        viewModel.detailSuccess.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireContext())
                    .load("https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/" + it.backdropPath)
                    .into(ivBackdrop)
                Glide.with(requireContext())
                    .load(BuildConfig.BASE_URL_IMAGE + it.posterPath)
                    .into(ivPoster)
                tvRate.text = it.voteAverage.toString()
                tvReleaseDate.text = it.releaseDate.toDate()
                tvTitle.text = it.title
                tvPopularity.text = getString(R.string.popularity, it.popularity.toString())
                tvVoteCount.text = getString(R.string.vote_count, it.voteCount.toString())
                val overview = view?.findViewById<TextView>(R.id.tv_overview)
                overview?.text = it.overview
                binding.progressBar.isVisible = false
            }
        }
//        viewModel.reviewSuccess.observe(viewLifecycleOwner) {
//
//            binding.apply {

//                tvRate.text = it.voteAverage.toString()
//                tvReleaseDate.text = it.releaseDate.toDate()
//                tvTitle.text = it.author
//                tvPopularity.text = getString(R.string.popularity, it.popularity.toString())
//                tvVoteCount.text = getString(R.string.vote_count, it.voteCount.toString())
//                tvOverview.text = it.content
//                binding.progressBar.isVisible = false
//            }
//        }
    }


    private fun onBackPressed() {
        binding.btnBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

}