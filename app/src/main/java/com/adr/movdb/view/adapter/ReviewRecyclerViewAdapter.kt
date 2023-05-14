package com.adr.movdb.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.movdb.R
import com.adr.movdb.databinding.ItemReviewBinding
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.Review
import com.bumptech.glide.Glide

class ReviewRecyclerViewAdapter:
    RecyclerView.Adapter<ReviewRecyclerViewAdapter.ViewHolder>() {

    private var data: List<Review> = listOf()
    private var onItemClickListener: ((review: Review) -> Unit)? = null
    private var configImage: ConfigurationImage? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Review>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (review: Review) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setConfigImage(config: ConfigurationImage) {
        this.configImage = config
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        itemData.authorDetail?.avatarPath?.let {
            Glide.with(holder.binding.root).asBitmap().load(getAvatarUrl(it)).into(holder.binding.ivReviewLogo)
        }
        holder.binding.tvReviewAuthor.text =
            itemData.authorDetail?.username ?: holder.binding.root.context.getString(R.string.genre_default)
            holder.binding.tvReviewVotes.text = itemData.authorDetail?.rating?.let {
                String.format(
                    holder.itemView.context.getString(R.string.vote_rating_review),
                    it
                )
            } ?: run {
                holder.binding.root.context.getString(R.string.genre_default)
            }
        holder.binding.tvReviewContent.text = itemData.content

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }
    }

    private fun getAvatarUrl(imageUrl: String): String {
        return configImage?.let{
            it.baseURL+it.posterSizes+imageUrl
        } ?: run {
            ""
        }
    }

    class ViewHolder(val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root)

}