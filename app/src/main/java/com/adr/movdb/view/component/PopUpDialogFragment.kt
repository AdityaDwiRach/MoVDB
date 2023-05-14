package com.adr.movdb.view.component


import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.adr.movdb.R
import com.adr.movdb.databinding.FragmentPopUpDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopUpDialogFragment : DialogFragment() {

    private var isError = false
    private var onClickListener: OnClickListener? = null
    private var content: String? = null
    private lateinit var binding: FragmentPopUpDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isError = it.getBoolean(IS_ERROR)
            content = it.getString(CONTENT)
        }

        lifecycleScope.launchWhenResumed {
            setWidthPercentage()
            dialog?.apply {
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPopUpDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvPopUp.text = content ?: getString(if (isError) R.string.error_admin else R.string.error_comm)
            btnPopUp.text = getString(if (isError) R.string.retry else R.string.dismiss)
            btnPopUp.setOnClickListener {
                onClickListener?.onClick(it)
                dismiss()
            }
        }
    }

    private fun setWidthPercentage() {
        val percent = 80f / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    companion object {

        private const val IS_ERROR = "is_error"
        private const val CONTENT = "content"

        @JvmStatic
        fun newInstance(isError: Boolean, listener: OnClickListener? = null, content: String? = null) =
            PopUpDialogFragment().apply {
                this.onClickListener = listener
                arguments = bundleOf(
                    IS_ERROR to isError,
                    CONTENT to content
                )
            }
    }
}