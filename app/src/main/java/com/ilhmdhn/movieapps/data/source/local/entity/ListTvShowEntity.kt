package com.ilhmdhn.movieapps.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listtvshowentities")
data class ListTvShowEntity(
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
    @ColumnInfo(name = "overview")
    var overview: String,
)