package com.example.randomiser.model.api

data class NameRequest(
    val animal: String,
    val number: Int = 10
)
