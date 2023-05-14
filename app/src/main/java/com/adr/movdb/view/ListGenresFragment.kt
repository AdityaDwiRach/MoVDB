package com.adr.movdb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adr.movdb.R
import com.adr.movdb.databinding.FragmentListGenresBinding
import com.adr.movdb.helper.constant.GeneralConstant
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.view.adapter.GenreRecyclerViewAdapter
import com.adr.movdb.view.component.PopUpDialogFragment
import com.adr.movdb.viewmodels.ListGenresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListGenresFragment : Fragment() {
    private lateinit var binding: FragmentListGenresBinding
    private val viewModel: ListGenresViewModel by viewModels()
    private val rvAdapter = GenreRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvListGenre.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(view.context)
                adapter = rvAdapter
            }
        }

        rvAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putInt(GeneralConstant.GENRE_ID.value, it)
            findNavController().navigate(R.id.action_listGenreFragment_to_listMovieByGenreFragment, bundle)
        }

        viewModel.getListGenre()
        subscribeToListGenre()
    }

    private fun subscribeToListGenre() = lifecycleScope.launch {
        viewModel.listGenre.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Loading -> {
                        showShimmer()
                    }
                    is ApiResponse.Error -> {
                        hideShimmer()
                        val popUp = PopUpDialogFragment.newInstance(true, {
                            viewModel.getListGenre()
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
                            viewModel.saveListGenre(response.genres)
                            rvAdapter.setData(response.genres)
                            binding.rvListGenre.visibility = VISIBLE
                        } ?: run {
                            val popUp = PopUpDialogFragment.newInstance(true, {
                                viewModel.getListGenre()
                            }, it.error?.second)
                            popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                        }
                    }
                }
            }
        }
    }

    private fun showShimmer() {
        binding.llListGenre.visibility = VISIBLE
    }

    private fun hideShimmer() {
        binding.llListGenre.visibility = GONE
    }
}