package com.example.MovieAppMVVM.ui.movie

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.MovieAppMVVM.adapter.ItemClickListener
import com.example.MovieAppMVVM.adapter.MoviePagedAdapter
import com.example.MovieAppMVVM.databinding.MoviesFragmentBinding
import com.example.MovieAppMVVM.paging.ResponseType
import com.example.MovieAppMVVM.ui.movie_details.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var  viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding
    private lateinit var mAdapter: MoviePagedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = MoviesFragmentBinding.inflate(layoutInflater, container, false)


        binding.button2.setOnClickListener {
            viewModel.getResponse(ResponseType.UPCOMING)
        }


        return binding.root
    }

    private fun loadingData() {
        lifecycleScope.launch {
            viewModel.movieFlow.collect {pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    private fun setUpRv() {
        mAdapter = MoviePagedAdapter(this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapter
            setHasFixedSize(true)


        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        setUpRv()
        loadingData()
//        Log.d("testLogs", "OnResponse Success ${loadingData()}")
        // TODO: Use the ViewModel
    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}