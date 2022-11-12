package id.melur.gliandroidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.melur.gliandroidtest.BuildConfig
import id.melur.gliandroidtest.databinding.ItemMovieBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.model.MoviePopular
import id.melur.gliandroidtest.model.MoviePopularItem

class MovieAdapter(private val onClickListener : (id: Int, movie: MoviePopularItem) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MoviePopularItem>() {
        override fun areItemsTheSame(oldItem: MoviePopularItem, newItem: MoviePopularItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviePopularItem, newItem: MoviePopularItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(movie: MoviePopular?) = listDiffer.submitList(movie?.results)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviePopularItem) {
            binding.apply {
                tvTitle.text = item.title
                tvReleaseDate.text = item.releaseDate.toDate()
                tvRate.text = item.voteAverage.toString()

                Glide.with(itemView.context)
                    .load(BuildConfig.BASE_URL_IMAGE + item.posterPath)
                    .into(ivPoster)
                itemMovie.setOnClickListener {
                    onClickListener.invoke(item.id, item)
                }
            }
        }
    }
}
