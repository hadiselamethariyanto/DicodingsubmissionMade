package com.bwx.made.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val VOTE_BEST = "Best"
    const val TV_NEW = "New"
    const val TV_OLD = "Old"
    const val MOVIE_NEW = "New_movie"
    const val MOVIE_OLD = "Old_movie"
    const val RANDOM = "Random"
    const val MOVIE_ENTITIES = "movie"
    const val TV_ENTITIES = "tv"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            MOVIE_NEW -> simpleQuery.append("ORDER BY release_date DESC")
            MOVIE_OLD -> simpleQuery.append("ORDER BY release_date ASC")
            TV_NEW -> simpleQuery.append("ORDER BY first_air_date DESC")
            TV_OLD -> simpleQuery.append("ORDER BY first_air_date ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}