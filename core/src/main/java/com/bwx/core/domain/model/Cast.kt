package com.bwx.core.domain.model

data class Cast(
    var id: Int,
    var movie_id: Int,
    var character: String,
    var name: String,
    var profile_path: String
)