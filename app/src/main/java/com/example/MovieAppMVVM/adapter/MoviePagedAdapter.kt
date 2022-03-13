package com.example.MovieAppMVVM.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.MovieAppMVVM.adapter.MoviePagedAdapter.MyViewHolder
import com.example.MovieAppMVVM.databinding.MovieItemBinding
import com.example.MovieAppMVVM.models.films.Movie
import com.example.MovieAppMVVM.ui.movie_details.MovieDetailsActivity

class MoviePagedAdapter (val mItemclickListener: ItemClickListener): PagingDataAdapter<Movie, MyViewHolder>(diffCallback = diffCallback) {


    inner class MyViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {
            val imageLink = currentItem?.poster_path

            imageview.load("https://image.tmdb.org/t/p/w500"+imageLink) {
                crossfade(true)
                crossfade(1000)
            }
        }
        holder.itemView.setOnClickListener{
            currentItem?.let { it1 -> mItemclickListener.onItemClick(it1.id) }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    fun alert(context: Context, text: String) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("text", text)
        context.startActivity(intent)
    }
}

interface ItemClickListener {
    fun onItemClick(id: Int)
}
