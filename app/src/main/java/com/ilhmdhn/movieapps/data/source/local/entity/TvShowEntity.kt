package com.ilhmdhn.movieapps.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @NonNull
    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String,

    @NonNull
    @ColumnInfo(name = "popularity")
    var popularity: Double,

    @NonNull
    @ColumnInfo(name = "language")
    var language: String,

    @NonNull
    @ColumnInfo(name = "overview")
    var overview: String,

    @NonNull
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)
