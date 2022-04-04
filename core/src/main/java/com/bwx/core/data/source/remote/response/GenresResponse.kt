package com.bwx.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @field:SerializedName("genres")
    val genres: List<GenresItem>
)
