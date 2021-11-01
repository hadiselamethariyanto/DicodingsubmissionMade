package com.bwx.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(

    @field:SerializedName("cast")
    val cast: List<CastItem>,

    @field:SerializedName("id")
    val id: Int
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
