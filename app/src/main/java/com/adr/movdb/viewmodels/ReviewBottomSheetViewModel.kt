package com.adr.movdb.viewmodels

import androidx.lifecycle.ViewModel
import com.adr.movdb.helper.session.Session
import com.adr.movdb.model.data.ConfigurationImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewBottomSheetViewModel @Inject constructor(
    val session: Session
) : ViewModel() {

    fun getConfigurationImage(): ConfigurationImage = session.getConfigImage()
}