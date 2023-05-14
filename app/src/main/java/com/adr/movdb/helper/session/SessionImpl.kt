package com.adr.movdb.helper.session

import android.content.SharedPreferences
import android.util.Log
import com.adr.movdb.model.data.ConfigurationImage
import com.adr.movdb.model.data.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type

class SessionImpl (private val sharedPreferences: SharedPreferences): Session {

    companion object {
        const val LIST_GENRE_KEY = "list_genre_key"
        const val CONFIG_IMAGE = "config_image"
    }

    override fun setListGenre(data: List<Genre>) {
        sharedPreferences.edit().putString(LIST_GENRE_KEY, Gson().toJson(data)).apply()
    }

    override fun getListGenre(): List<Genre> {
        var listToRetrieve: List<Genre> = listOf()
        val serializedObject = sharedPreferences.getString(LIST_GENRE_KEY, null)
        serializedObject?.let {
            val gson = Gson()
            val type: Type = object :TypeToken<List<Genre>>(){}.type
            listToRetrieve = gson.fromJson(serializedObject, type)
        }
        return listToRetrieve
    }

    override fun setConfigImage(data: ConfigurationImage) {
        sharedPreferences.edit().putString(CONFIG_IMAGE, Gson().toJson(data)).apply()
    }

    override fun getConfigImage(): ConfigurationImage {
        var configImage = ConfigurationImage("","", "")
        val config = sharedPreferences.getString(CONFIG_IMAGE, "")
        try {
            configImage = Gson().fromJson(config, ConfigurationImage::class.java)
        } catch (e: Exception) {
            Log.e("Error", e.message ?: "")
        }
        return configImage
    }
}