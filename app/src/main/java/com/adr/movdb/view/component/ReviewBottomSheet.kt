package com.adr.movdb.view.component

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adr.movdb.R
import com.adr.movdb.databinding.FragmentReviewDetailBottomSheetBinding
import com.adr.movdb.helper.constant.GeneralConstant
import com.adr.movdb.model.data.Review
import com.adr.movdb.viewmodels.ReviewBottomSheetViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewBottomSheet : BottomSheetDialogFragment() {

    private lateinit var dialog: BottomSheetDialog
    private lateinit var binding: FragmentReviewDetailBottomSheetBinding
    private var data: Review? = null
    private val viewModel: ReviewBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dialog = getDialog() as BottomSheetDialog

        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(GeneralConstant.REVIEW.value, Review::class.java)
        } else {
            arguments?.getParcelable("DATA")
        }

        binding = FragmentReviewDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            data?.let {
                it.authorDetail?.avatarPath?.let { path ->
                    Glide.with(root).asBitmap().load(getAvatarUrl(path)).into(ivReviewDetailLogo)
                }
                tvReviewDetailAuthor.text =
                    it.authorDetail?.username ?: root.context.getString(R.string.genre_default)
                tvReviewDetailVotes.text = it.authorDetail?.rating?.let { rating ->
                    String.format(
                        requireContext().getString(R.string.vote_rating_review),
                        rating
                    )
                } ?: run {
                    requireContext().getString(R.string.genre_default)
                }
                tvReviewDetailContent.text = it.content
            }
        }
    }

    private fun getAvatarUrl(imageUrl: String): String {
        return viewModel.getConfigurationImage().let {
            it.baseURL + it.posterSizes + imageUrl
        }
    }
}