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
                Teammate("Sarah Rehmer", Color(0xFF99cccc), Platform.SDET),
                Teammate("Jude Blinder", Color(0xFFFF3CFF), Platform.Web),
                Teammate("Chao Peng", Color(0xFF04136B), Platform.iOS),
                Teammate("Andrew Hughes", Color(0xFF0DEED4), Platform.Android),
                Teammate("Claire Giovanoni", Color(0xFFFFA0E5), Platform.Design),
                Teammate("Matt Smith", Color(0xFF1D9367), Platform.SDET),
                Teammate("Aditya Jawade", Color(0xFFDEE7F7), Platform.ALayer),
                Teammate("Vamshee Sangani", Color(0xF72BD534), Platform.SDET),
                Teammate("Shawn Han", Color(0xFFCF4201), Platform.iOS),
                Teammate("John Bolger", Color(0xFF888686), Platform.ALayer),
                Teammate("Ethan Berg", Color(0xFF0974E6), Platform.Android),
                Teammate("Yuval Allweil", Color(0xFFF4C2C2), Platform.Web),
            )
    }
}
