package com.ilhmdhn.movieapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseResult(
    @field:SerializedName("results")
    val results: List<ResultItems>,
)

data class ResultItems(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("name")
    val name: String
)
