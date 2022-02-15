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
        fun makeList(): MutableList<Teammate> =
            mutableListOf(
                Teammate("Sarah Rehmer", Color(0xFF99cccc), Platform.SDET),
                Teammate("Jude Blinder", Color(0xFFFF3CFF), Platform.Web),
                Teammate("Chao Peng", Color(0xFF04136B), Platform.iOS),
                Teammate("Andrew Hughes", Color(0xFF0DEED4), Platform.Android),
                Teammate("Stephen Kinney", Color(0xFFFF8D00), Platform.Design),
                Teammate("Matt Smith", Color(0xFF1D9367), Platform.SDET),
                Teammate("Aditya Jawade", Color(0xFFDEE7F7), Platform.ALayer),
                Teammate("Vamshee Sangani", Color(0xF72BD534), Platform.SDET),
                Teammate("Shawn Han", Color(0xFFCF4201), Platform.iOS),
                Teammate("Moses Robinson", Color(0xFF009688), Platform.SDET),
                Teammate("Thomas Harlan", Color(0xFF0974E6), Platform.Android),
                Teammate("Bert Harvey", Color(0xFF4AC380), Platform.SDET),
                Teammate("Yuval Allweil", Color(0xFFF4C2C2), Platform.Web),
            )
    }
}
