package id.melur.gliandroidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.melur.gliandroidtest.databinding.ItemReviewBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.model.MovieReviews
import id.melur.gliandroidtest.model.ReviewItem


class ReviewAdapter(private val onClickListener : (id: Int, review: ReviewItem) -> Unit) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ReviewItem>() {
        override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(review: MovieReviews?) = listDiffer.submitList(review?.results)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReviewItem) {
            binding.apply {
                val test = item.authorDetails
                tvAuthor.text = item.author
                tvRate.text = test.rating.toString()
                tvDate.text = item.updatedAt.toDate()
                tvContent.text = item.content
            }
        }
    }
}
