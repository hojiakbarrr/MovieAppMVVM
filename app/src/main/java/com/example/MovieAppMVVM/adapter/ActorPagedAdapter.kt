package com.example.MovieAppMVVM.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.MovieAppMVVM.adapter.ActorPagedAdapter.ActorViewHold
import com.example.MovieAppMVVM.databinding.ActorItemBinding
import com.example.MovieAppMVVM.databinding.MovieItemBinding
import com.example.MovieAppMVVM.models.films.Movie
import com.example.MovieAppMVVM.models.person.Actor

class ActorPagedAdapter : PagingDataAdapter<Actor, ActorViewHold>(ActorCallback) {

    inner class ActorViewHold(val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val ActorCallback = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: ActorViewHold, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            val imageLink = currentItem?.profile_path

            fotoActor.load("https://image.tmdb.org/t/p/w500" + imageLink)
            nameActor.text = currentItem!!.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHold {
        return ActorViewHold(ActorItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


}