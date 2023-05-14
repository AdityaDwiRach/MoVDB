package com.adr.movdb.helper.genre

import com.adr.movdb.R
import com.adr.movdb.model.data.Genre

object GenreBuilder {
    private val action: Pair<Int, Int> = Pair(28, R.drawable.ic_action_genre)
    private val adventure: Pair<Int, Int> = Pair(12, R.drawable.ic_adventure_genre)
    private val animation: Pair<Int, Int> = Pair(16, R.drawable.ic_animation_genre)
    private val comedy: Pair<Int, Int> = Pair(35, R.drawable.ic_comedy_genre)
    private val crime: Pair<Int, Int> = Pair(80, R.drawable.ic_crime_genre)
    private val documentary: Pair<Int, Int> = Pair(99, R.drawable.ic_documentary_genre)
    private val drama: Pair<Int, Int> = Pair(18, R.drawable.ic_drama_genre)
    private val family: Pair<Int, Int> = Pair(10751, R.drawable.ic_family_genre)
    private val fantasy: Pair<Int, Int> = Pair(14, R.drawable.ic_fantasy_genre)
    private val history: Pair<Int, Int> = Pair(36, R.drawable.ic_history_genre)
    private val horror: Pair<Int, Int> = Pair(27, R.drawable.ic_history_genre)
    private val music: Pair<Int, Int> = Pair(10402, R.drawable.ic_music_genre)
    private val mystery: Pair<Int, Int> = Pair(9648, R.drawable.ic_mystery_genre)
    private val romance: Pair<Int, Int> = Pair(10749, R.drawable.ic_romance_genre)
    private val scienceFiction: Pair<Int, Int> = Pair(878, R.drawable.ic_science_fiction_genre)
    private val tvMovie: Pair<Int, Int> = Pair(10770, R.drawable.ic_tv_movie_genre)
    private val thriller: Pair<Int, Int> = Pair(53, R.drawable.ic_thriller_genre)
    private val war: Pair<Int, Int> = Pair(10752, R.drawable.ic_war_genre)
    private val western: Pair<Int, Int> = Pair(37, R.drawable.ic_western_genre)

    private fun getListGenre() = listOf(
        action,
        adventure,
        animation,
        comedy,
        crime,
        documentary,
        drama,
        family,
        fantasy,
        history,
        horror,
        music,
        mystery,
        romance,
        scienceFiction,
        tvMovie,
        thriller,
        war,
        western
    )

    fun getGenreIcon(genreID: Int): Pair<Int, Int> {
        return getListGenre().find { (id, _) -> id == genreID } ?: run { Pair(genreID, 0) }
    }

    fun getListGenreName(listCodes: List<Int>, listGenre: List<Genre>): String {
        val listGenreName: ArrayList<String> = arrayListOf()
        listCodes.forEach { code ->
            listGenre.find { genre -> genre.id == code }?.let {
                listGenreName.add(it.name ?: "")
            }
        }
        return listGenreName.joinToString()
    }

    fun getListGenreName(listGenre: List<Genre>): String {
        val listGenreName: ArrayList<String> = arrayListOf()
        listGenre.forEach { genre ->
            genre.name?.let {
                listGenreName.add(it)
            }
        }
        return listGenreName.joinToString()
    }
}