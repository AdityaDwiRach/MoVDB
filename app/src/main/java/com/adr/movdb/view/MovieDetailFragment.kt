package com.adr.movdb.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adr.movdb.BuildConfig
import com.adr.movdb.R
import com.adr.movdb.databinding.FragmentMovieDetailBinding
import com.adr.movdb.helper.constant.GeneralConstant
import com.adr.movdb.helper.genre.GenreBuilder
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.MovieDetail
import com.adr.movdb.model.data.VideoListResponse
import com.adr.movdb.view.adapter.ReviewRecyclerViewAdapter
import com.adr.movdb.view.component.PopUpDialogFragment
import com.adr.movdb.viewmodels.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var rvAdapter: ReviewRecyclerViewAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(GeneralConstant.MOVIE_ID.value)?.let { viewModel.movieID = it.toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = ReviewRecyclerViewAdapter()
        rvAdapter.setConfigImage(viewModel.getConfigurationImage())

        with(binding) {
            rvListReview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(view.context)
                adapter = rvAdapter
                addOnScrollListener(scrollListener())
            }

            bottomSheetBehavior = BottomSheetBehavior.from(cvListReview)
        }

        rvAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putParcelable(GeneralConstant.REVIEW.value, it)
            findNavController().navigate(R.id.action_movieDetailFragment_to_reviewDetailDialogFragment, bundle)
        }

        viewModel.getMovieDetail()
        viewModel.getListVideo()
        viewModel.getListReview()

        subscribeToMovieDetail()
        subscribeToListVideo()
        subscribeToListReview()
        subscribeToTotalListReview()
    }

    private fun subscribeToMovieDetail() = lifecycleScope.launch {
        viewModel.movieDetailResponse.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Loading -> {
                        showShimmer()
                    }

                    is ApiResponse.Error -> {
                        hideShimmer()
                        val popUp = PopUpDialogFragment.newInstance(true, {
                            viewModel.getMovieDetail()
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
                            setContentDetail(response)
                        } ?: run {
                            val popUp = PopUpDialogFragment.newInstance(true, {
                                viewModel.getMovieDetail()
                            }, it.error?.second)
                            popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToListVideo() = lifecycleScope.launch {
        viewModel.listVideoResponse.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Error -> {
                        binding.ytPlayerMovieError.visibility = VISIBLE
                    }

                    is ApiResponse.Empty -> {
                        binding.ytPlayerMovieError.visibility = VISIBLE
                    }

                    is ApiResponse.Completed -> {
                        it.data?.let { response ->
                            setContentVideo(response)
                        } ?: run {
                            binding.ytPlayerMovieError.visibility = VISIBLE
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun subscribeToListReview() = lifecycleScope.launch {
        viewModel.listReviewResponse.collectLatest {
            it?.let {
                when (it) {
                    is ApiResponse.Loading -> {
                        showBottomSheetShimmer()
                    }

                    is ApiResponse.Error -> {
                        hideBottomSheetShimmer()
                        val popUp = PopUpDialogFragment.newInstance(true, {
                            viewModel.getListReview()
                        }, it.error?.second)
                        popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                    }

                    is ApiResponse.Empty -> {
                        hideBottomSheetShimmer()
                        val popUp = PopUpDialogFragment.newInstance(false, content = it.error?.second)
                        popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                    }

                    is ApiResponse.Completed -> {
                        hideBottomSheetShimmer()
                        it.data?.let { response ->
                            viewModel.pageNow = response.page ?: 0
                            viewModel.maxPage = response.totalPages ?: viewModel.pageNow
                            viewModel.setTotalListReview(response.results)
                        } ?: run {
                            val popUp = PopUpDialogFragment.newInstance(true, {
                                viewModel.getListReview()
                            }, it.error?.second)
                            popUp.show(requireActivity().supportFragmentManager, popUp.tag)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeToTotalListReview() = lifecycleScope.launch {
        viewModel.totalListReview.collectLatest {
            rvAdapter.setData(it)
            binding.rvListReview.visibility = View.VISIBLE
        }
    }

    private fun setContentVideo(response: VideoListResponse) {
        with(binding) {
            viewModel.getYoutubeID(response.results)?.let { key ->
                wvMovieDetail.settings.javaScriptEnabled = true
                wvMovieDetail.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                wvMovieDetail.settings.loadWithOverviewMode = true
                wvMovieDetail.settings.useWideViewPort = true
                wvMovieDetail.loadUrl(BuildConfig.YOUTUBE_EMBED_URL + key)
            } ?: run {
                wvMovieDetail.visibility = GONE
                ytPlayerMovieError.visibility = VISIBLE
            }
        }
    }

    private fun setContentDetail(detail: MovieDetail) {
        with(binding) {
            detail.posterPath?.let {
                Glide.with(root).asBitmap().load(getPosterUrl(it)).addListener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        ivMovieDetailPoster.background = null
                        return false
                    }

                }).into(ivMovieDetailPoster)
            }
            tvMovieDetailTitle.text = detail.title
            tvMovieDetailGenres.text = GenreBuilder.getListGenreName(detail.genres)
            tvMovieDetailReleaseDate.text = detail.releaseDate
            tvMovieDetailVotes.text =
                String.format(getString(R.string.vote_rating), detail.voteAverage, detail.voteCount)
            tvMovieDetailOverviewField.text = detail.overview
            nsvMovieDetail.visibility = VISIBLE
        }
    }

    private fun showShimmer() {
        binding.slMovieDetail.visibility = VISIBLE
    }

    private fun hideShimmer() {
        binding.slMovieDetail.visibility = GONE
    }

    private fun showBottomSheetShimmer() {
        binding.slListReview.visibility = VISIBLE
    }

    private fun hideBottomSheetShimmer() {
        binding.slListReview.visibility = INVISIBLE
    }

    private fun getPosterUrl(imageUrl: String): String {
        val config = viewModel.getConfigurationImage()
        return config.baseURL + config.posterSizes + imageUrl
    }

    private fun scrollListener() = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleThreshold = 1
                val layoutManager: LinearLayoutManager =
                    binding.rvListReview.layoutManager as LinearLayoutManager
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount
                if (currentTotalCount <= lastItem + visibleThreshold) {
                    viewModel.getListReview()
                }
            }
        }
    }
}