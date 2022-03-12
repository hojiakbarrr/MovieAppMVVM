package com.example.MovieAppMVVM.ui.actor

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
import com.example.MovieAppMVVM.databinding.ActorsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ActorsFragment : Fragment() {

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
        mAdapter = ActorPagedAdapter()
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

}