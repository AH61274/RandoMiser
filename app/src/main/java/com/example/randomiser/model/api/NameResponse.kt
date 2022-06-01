package com.example.randomiser.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameResponse(
    val list: List<String>
)
