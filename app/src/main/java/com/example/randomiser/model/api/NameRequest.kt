package com.example.randomiser.model.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameRequest(
    val animal: String,
    val number: Int = 10
)
