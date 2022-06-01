package com.example.randomiser.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DogData(
    val fileSizeBytes: Long,
    val url: String
)
