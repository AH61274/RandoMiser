package com.example.randomiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomiser.ui.Layout
import com.example.randomiser.ui.theme.RandoMiserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Hacktivity : ComponentActivity() {

    val viewModel: HacktivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initViewModel()
        setContent {
            App()
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun App() {
        RandoMiserTheme {
            Layout(viewModel)
        }
    }
}
