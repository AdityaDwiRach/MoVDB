package com.adr.movdb.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adr.movdb.databinding.ItemGenreBinding
import com.adr.movdb.helper.genre.GenreBuilder
import com.adr.movdb.model.data.Genre
import com.bumptech.glide.Glide

class GenreRecyclerViewAdapter : RecyclerView.Adapter<GenreRecyclerViewAdapter.ViewHolder>() {

    private var data: List<Genre> = listOf()
    private var onItemClickListener: ((genreID: Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Genre>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (genreID: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = data[position]
        itemData.id?.let {id ->
            val (_, logoID) = GenreBuilder.getGenreIcon(id)
            if (logoID != 0) Glide.with(holder.binding.root).load(logoID).into(holder.binding.ivGenreLogo)
            holder.binding.tvGenre.text = itemData.name

            holder.binding.ivNext.setOnClickListener {
                holder.binding.ivNext.isPressed = true
                onItemClickListener?.invoke(id)
            }
        } ?: run {
            holder.binding.cvGenre.visibility = GONE
        }
    }

    inner class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)
}