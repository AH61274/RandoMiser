package com.example.randomiser.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatData(
    val breeds: List<Any>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
