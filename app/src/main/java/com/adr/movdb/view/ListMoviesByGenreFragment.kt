package com.adr.movdb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adr.movdb.R
import com.adr.movdb.databinding.FragmentListMovieByGenreBinding
import com.adr.movdb.helper.constant.GeneralConstant
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.view.adapter.MovieRecyclerViewAdapter
import com.adr.movdb.view.component.PopUpDialogFragment
import com.adr.movdb.viewmodels.ListMoviesByGenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListMoviesByGenreFragment: Fragment() {
    private lateinit var binding: FragmentListMovieByGenreBinding
    private val viewModel: ListMoviesByGenreViewModel by viewModels()
    private lateinit var rvAdapter: MovieRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt(GeneralConstant.GENRE_ID.value)?.let { viewModel.genreID = it.toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListMovieByGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = MovieRecyclerViewAdapter(viewModel.session)
        rvAdapter.setConfigImage(viewModel.getConfigurationImage())
        with(binding) {
            rvListMovieByGenre.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(view.context)
                adapter = rvAdapter
                addOnScrollListener(scrollListener())
            }
        }

        rvAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putInt(GeneralConstant.MOVIE_ID.value, it)
            findNavController().navigate(R.id.action_listMovieByGenreFragment_to_movieDetailFragment, bundle)
        }

        viewModel.getListMovieByGenre()
        subscribeToListMovie()
        subscribeToTotalListMovie()
    }

    private fun subscribeToListMovie() = lifecycleScope.launch {
        viewModel.listMovie.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Loading -> showShimmer()
                    is ApiResponse.Error -> {
                        hideShimmer()
                        val popUp = PopUpDialogFragment.newInstance(true, {
                            viewModel.getListMovieByGenre()
                        }, it.error?.second)
                        popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                    }
                    is ApiResponse.Empty -> {
                        hideShimmer()
                        val popUp = PopUpDialogFragment.newInstance(false, content = it.error?.second)
                        popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                    }
                    is ApiResponse.Completed -> {
                        hideShimmer()
                        it.data?.let { response ->
                            viewModel.pageNow = response.page ?: 0
                            viewModel.maxPage = response.totalPages ?: viewModel.pageNow
                            viewModel.setTotalListMovie(response.results)
                        } ?: run {
                            val popUp = PopUpDialogFragment.newInstance(true, {
                                viewModel.getListMovieByGenre()
                            }, it.error?.second)
                            popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToTotalListMovie() = lifecycleScope.launch {
        viewModel.totalListMovie.collectLatest {
            rvAdapter.setData(it)
            binding.rvListMovieByGenre.visibility = View.VISIBLE
        }
    }

    private fun showShimmer() {
        binding.llListMovie.visibility = View.VISIBLE
    }

    private fun hideShimmer() {
        binding.llListMovie.visibility = View.GONE
    }

    private fun scrollListener() = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleThreshold = 1
                val layoutManager: LinearLayoutManager =
                    binding.rvListMovieByGenre.layoutManager as LinearLayoutManager
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount
                if (currentTotalCount <= lastItem + visibleThreshold) {
                    viewModel.getListMovieByGenre()
                }
            }
        }
    }
}