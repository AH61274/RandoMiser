package com.example.randomiser.model

enum class Round(val winners: Int) {
    QUALIFIERS(16),
    ROUND_1(8),
    ROUND_2(4),
    SEMI_FINALS(2),
    FINAL(1),
}