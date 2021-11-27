package com.ilhmdhn.movieapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ShowResponse(

    @field:SerializedName("original_language")
    val originalLanguage: String,

    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("title")
    var title: String,

    @field:SerializedName("popularity")
    val popularity: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("release_date")
    var releaseDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,
)
