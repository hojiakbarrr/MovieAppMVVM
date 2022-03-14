package com.example.MovieAppMVVM.ui.movie

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.MovieAppMVVM.adapter.ItemClickListener
import com.example.MovieAppMVVM.adapter.MoviePagedAdapter
import com.example.MovieAppMVVM.databinding.MoviesFragmentBinding
import com.example.MovieAppMVVM.paging.ResponseType
import com.example.MovieAppMVVM.ui.movie_details.MovieDetailsActivity
import com.google.android.material.R
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
        invisible()
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
        invisible()
        setUpRv()
        loadingData()
        spinnerLoad()
        search()
//        Log.d("testLogs", "OnResponse Success ${loadingData()}")
    }

    private fun search() {

        binding.apply {
            find.setOnClickListener {
                val text = editTextTextPersonName.text
                viewModel.getSearch(text.toString())
                viewModel.getResponse(ResponseType.SEARCH)
            }
        }
    }

    private fun invisible(){
        binding.apply {
            progress.visibility = VISIBLE
            editTextTextPersonName.visibility = INVISIBLE
            find.visibility = INVISIBLE
            recyclerView.visibility = INVISIBLE
            spinner.visibility = INVISIBLE

        }
    }

    private fun visible(){
        binding.apply {
            progress.visibility = INVISIBLE
            editTextTextPersonName.visibility = VISIBLE
            find.visibility = VISIBLE
            recyclerView.visibility = VISIBLE
            spinner.visibility = VISIBLE

        }
    }

    private fun spinnerLoad() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
           override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
               if (adapter!!.getItemAtPosition(position) == "Рейтинговые") {
                   viewModel.getResponse(ResponseType.TOP_RATED)
                   visible()
               }else if (adapter!!.getItemAtPosition(position) == "Сейчас смотрят"){
                   viewModel.getResponse(ResponseType.NOW_PLAYING)
                   visible()
               }else if (adapter!!.getItemAtPosition(position) == "Предстоящий"){
                   viewModel.getResponse(ResponseType.UPCOMING)
                   visible()
               }else if (adapter!!.getItemAtPosition(position) == "Самые популярные"){
                   viewModel.getResponse(ResponseType.POPULAR)
                   visible()
               }
               else if (adapter!!.getItemAtPosition(position) == "Жанры"){
                   viewModel.getResponse(ResponseType.POPULAR)
                   visible()
               }
           }
           override fun onNothingSelected(p0: AdapterView<*>?) {
               viewModel.getResponse(ResponseType.POPULAR)
               visible()
           }

       }

    }

    override fun onItemClick(id: Int) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}