package com.example.randomiser.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.example.randomiser.ui.theme.RandoBlack
import com.example.randomiser.ui.theme.RandoWhite

data class Teammate(
    val name: String,
    val color: Color,
    val platform: Platform,
) {
    val isLight = color.luminance() > 0.5
    val accentColor = if (isLight) RandoBlack else RandoWhite

    companion object {
        fun makeList() =
            mutableListOf(
                Teammate("Dave Pack", Color(0xFFEC0808), Platform.Web),
                Teammate("Sarah Rehmer", Color(0xFF99cccc), Platform.Android),
                Teammate("Aditya Jawade", Color(0xFFDEE7F7), Platform.ALayer),
                Teammate("Ethan Berg", Color(0xFF0974E6), Platform.Android),
                Teammate("Chao Peng", Color(0xFF04136B), Platform.iOS),
                Teammate("Andrew Hughes", Color(0xFF2FEE0D), Platform.Android),
                Teammate("Matt Smith", Color(0xFF1D9367), Platform.iOS),
                Teammate("John Bolger", Color(0xFF888686), Platform.ALayer),
                Teammate("Ginny Adamson", Color(0xFF581855), Platform.Design),
                Teammate("Doug Schuster", Color(0xFF6D24FF), Platform.Web),
                Teammate("Yuval Allweil", Color(0xFFF4C2C2), Platform.Web),
            )
    }
}
