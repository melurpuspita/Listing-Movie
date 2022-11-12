package id.melur.gliandroidtest.ui

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
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import id.melur.gliandroidtest.*
import id.melur.gliandroidtest.adapter.InfoAdapter
import id.melur.gliandroidtest.databinding.FragmentInfoScreenBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.helper.viewModelsFactory
import id.melur.gliandroidtest.repository.MovieRepository
import id.melur.gliandroidtest.service.TMDBApiService
import id.melur.gliandroidtest.service.TMDBClient
import id.melur.gliandroidtest.ui.tablayout.Overview
import id.melur.gliandroidtest.ui.tablayout.Reviews
import id.melur.gliandroidtest.ui.tablayout.Videos

class InfoScreen : Fragment() {


    private var _binding: FragmentInfoScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPreferences

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
        _binding = FragmentInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        sharedPref = context.getSharedPreferences("id", Context.MODE_PRIVATE)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireContext().getSharedPreferences("id", Context.MODE_PRIVATE)

        val movieId = sharedPref.getInt("id", 0)
//        Toast.makeText(requireContext(), "haduh", Toast.LENGTH_SHORT).show()

//        viewModel.getDetailMovie(movieId)
//        val movieId = arguments?.getInt("id")

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.putInt("id", movieId)
        editor.apply()
        setTabAndViewPager()
//        viewModel.getReview()
        observeData(movieId)
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
//        movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))
//        val movieId = sharedPref.getInt("movieId", requireArguments().getInt("id"))

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Overview"
                }
                1 -> {
                    tab.text = "Reviews"
//                    Toast.makeText(requireContext(), sharedPref.getInt("id", id!!), Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    tab.text = "Videos"
                }
            }
        }.attach()
    }

//    private fun observeData() {
//        viewModel.detailSuccess.observe(viewLifecycleOwner) {
//            binding.apply {
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
//            }
//        }
//    }

    private fun observeData(movieId: Int) {
        viewModel.getDetailMovie(movieId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.apply {
                        Glide.with(requireContext())
                            .load("https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/" + it.data!!.backdropPath)
                            .into(ivBackdrop)
                        Glide.with(requireContext())
                            .load(BuildConfig.BASE_URL_IMAGE + it.data.posterPath)
                            .into(ivPoster)
                        tvRate.text = it.data.voteAverage.toString()
                        tvReleaseDate.text = it.data.releaseDate.toDate()
                        tvTitle.text = it.data.title
                        tvPopularity.text = getString(R.string.popularity, it.data.popularity.toString())
                        tvVoteCount.text = getString(R.string.vote_count, it.data.voteCount.toString())
                        val overview = view?.findViewById<TextView>(R.id.tv_overview)
                        overview?.text = it.data.overview
                        binding.progressBar.isVisible = false
                        it.data.id.let { movieId -> dataStore.saveData(movieId) }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }
    private fun onBackPressed() {
        binding.btnBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

}