package com.bwx.made.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(

    @field:SerializedName("cast")
    val cast: List<CastItem>? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class CastItem(
    @field:SerializedName("cast_id")
    val id: Int,

    @field:SerializedName("character")
    val character: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    )
