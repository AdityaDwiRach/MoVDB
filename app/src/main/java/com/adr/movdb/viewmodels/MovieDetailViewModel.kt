package com.adr.movdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adr.movdb.helper.constant.GeneralConstant
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.api.ApiResponse
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.MovieDetail
import com.adr.movdb.model.data.Review
import com.adr.movdb.model.data.ReviewListResponse
import com.adr.movdb.model.data.Video
import com.adr.movdb.model.data.VideoListResponse
import com.adr.movdb.model.usecase.DetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailUseCases: DetailUseCases,
    private val session: Session,
) : ViewModel() {

    var movieID = "0"
    var pageNow = 0
    var maxPage = 0

    private val _listReviewResponse = MutableStateFlow<ApiResponse<ReviewListResponse>?>(null)
    val listReviewResponse = _listReviewResponse.asStateFlow()

    private val _totalListReview = MutableStateFlow<List<Review>>(listOf())
    val totalListReview = _totalListReview.asStateFlow()

    private val _movieDetailResponse = MutableStateFlow<ApiResponse<MovieDetail>?>(null)
    val movieDetailResponse = _movieDetailResponse.asStateFlow()

    private val _listVideoResponse = MutableStateFlow<ApiResponse<VideoListResponse>?>(null)
    val listVideoResponse = _listVideoResponse.asStateFlow()

    fun getMovieDetail() = viewModelScope.launch {
        detailUseCases.getDetailMovieUseCase(movieID).collect { _movieDetailResponse.value = it }
    }

    fun getListVideo() = viewModelScope.launch {
        detailUseCases.getListVideoMovieUseCase(movieID).collect { _listVideoResponse.value = it }
    }

    fun getConfigurationImage(): ConfigurationImage = session.getConfigImage()

    fun getYoutubeID(list: List<Video>): String? {
        return list.find { video ->
                video.official &&
                        video.site == GeneralConstant.YOUTUBE.value && video.type == GeneralConstant.TRAILER.value
            }?.key ?: run {
            null
        }
    }

    fun getListReview() = viewModelScope.launch {
        if (isEligibleToRefresh() || (pageNow == 0 && maxPage == 0)) {
            detailUseCases.getListReviewMovieUseCase(movieID, (pageNow + 1)).collect { _listReviewResponse.value = it }
        }
    }

    private fun isEligibleToRefresh() = pageNow < maxPage

    fun setTotalListReview(data: List<Review>) {
        _totalListReview.value += data
    }
}