package com.adr.movdb.helper.session

import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.Genre

interface Session {

    fun setListGenre(data: List<Genre>)

    fun getListGenre(): List<Genre>

    fun setConfigImage(data: ConfigurationImage)

    fun getConfigImage(): ConfigurationImage
}