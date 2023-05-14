package com.adr.movdb.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.movdb.R
import com.adr.movdb.databinding.ItemMovieBinding
import com.adr.movdb.helper.genre.GenreBuilder
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.Movie
import com.bumptech.glide.Glide

class MovieRecyclerViewAdapter(private val session: Session):
    RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    private var data: List<Movie> = listOf()
    private var onItemClickListener: ((movieID: Int) -> Unit)? = null
    private var configImage: ConfigurationImage? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (movieID: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setConfigImage(config: ConfigurationImage) {
        this.configImage = config
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        itemData.posterPath?.let {
            Glide.with(holder.binding.root).asBitmap().load(getPosterUrl(it)).into(holder.binding.ivMoviePoster)
        }
        holder.binding.tvMovieTitle.text = itemData.title
        holder.binding.tvMovieGenres.text = GenreBuilder.getListGenreName(itemData.genreIDs, session.getListGenre())
        holder.binding.tvMovieReleaseDate.text = itemData.releaseDate
        holder.binding.tvMovieVotes.text =
            String.format(
                holder.itemView.context.getString(R.string.vote_rating),
                itemData.voteAverage,
                itemData.voteCount
            )
        holder.itemView.setOnClickListener {
            itemData.id?.let { id ->
                onItemClickListener?.invoke(id)
            }
        }
    }

    private fun getPosterUrl(imageUrl: String): String {
        return configImage?.let{
            it.baseURL+it.posterSizes+imageUrl
        } ?: run {
            ""
        }
    }

    private fun getBackdropUrl(imageUrl: String): String {
        return configImage?.let{
            it.baseURL+it.backdropSizes+imageUrl
        } ?: run {
            ""
        }
    }

    inner class ViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)
}