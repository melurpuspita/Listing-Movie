package id.melur.gliandroidtest.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.melur.gliandroidtest.BuildConfig
import id.melur.gliandroidtest.databinding.ItemReviewBinding
import id.melur.gliandroidtest.databinding.ItemVideosBinding
import id.melur.gliandroidtest.helper.toDate
import id.melur.gliandroidtest.model.MovieReviews
import id.melur.gliandroidtest.model.ReviewItem
import id.melur.gliandroidtest.model.VideoItem
import id.melur.gliandroidtest.model.Videos

class VideoAdapter(private val onClickListener : (id: Int, video: VideoItem) -> Unit) : RecyclerView.Adapter<VideoAdapter.VideoviewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<VideoItem>() {
        override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)

    fun updateData(video: Videos?) = listDiffer.submitList(video?.results)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoviewHolder {
        val binding = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoviewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoviewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    inner class VideoviewHolder(private val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VideoItem) {
            binding.apply {
                val key = item.key
//                val title = item.name
//                btnVideoHandle.setOnClickListener {

//
//                    val openURL = Intent(Intent.ACTION_VIEW)
//                    openURL.data = Uri.parse("https://www.youtube.com/watch?v=JaV7mmc9HGw")
//                    startActivity(Intent(requireContext(), openURL))
//                }
                tvName.text = item.name
                tvType.text = item.type
                tvPublised.text = item.publishedAt.toDate()
//                tvAuthor.text = item.author
//                tvRate.text = test.rating.toString()
//                tvDate.text = item.updatedAt.toDate()
//                tvContent.text = item.content
//                tvRate.text = test.avatarPath
                Glide.with(itemView.context)
                    .load("https://i.ytimg.com/vi/$key/hqdefault.jpg")
                    .into(ivImage)
            }
        }
    }
}