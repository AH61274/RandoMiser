package com.example.randomiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.randomiser.ui.screens.MainActivityUI
import com.example.randomiser.ui.theme.RandoMiserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: RandoMiserViewModel by viewModels()

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildUi()
    }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    private fun buildUi() {
        setContent {
            RandoMiserTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainActivityUI(viewModel)
                }
            }
        }
    }
}
