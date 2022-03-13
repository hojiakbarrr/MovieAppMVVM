package com.example.MovieAppMVVM.ui.actor

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.MovieAppMVVM.R
import com.example.MovieAppMVVM.adapter.ActorPagedAdapter
import com.example.MovieAppMVVM.adapter.ItemlickListener
import com.example.MovieAppMVVM.databinding.ActorsFragmentBinding
import com.example.MovieAppMVVM.ui.actor_detail.ActorDetailActivity
import com.example.MovieAppMVVM.ui.movie_details.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@DelicateCoroutinesApi
@AndroidEntryPoint
class ActorsFragment : Fragment(), ItemlickListener {

    companion object {
        fun newInstance() = ActorsFragment()
    }

    private lateinit var viewModel: ActorsViewModel
    private lateinit var binding: ActorsFragmentBinding
    private lateinit var mAdapter: ActorPagedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = ActorsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun loadingInfo() {
        lifecycleScope.launch {
            viewModel.actorFlow.collect {pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    private fun settingRec() {
        mAdapter = ActorPagedAdapter(this)
        binding.recPersons.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActorsViewModel::class.java)
        // TODO: Use the ViewModel
        settingRec()
        loadingInfo()
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), ActorDetailActivity::class.java)
        intent.putExtra("idi", id)
        startActivity(intent)    }

}