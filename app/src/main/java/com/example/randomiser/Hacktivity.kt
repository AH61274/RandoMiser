package com.example.randomiser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import com.example.randomiser.ui.Layout
import com.example.randomiser.ui.theme.RandoMiserTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Hacktivity : ComponentActivity() {

    val viewModel: HacktivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewModelScope.launch {
            viewModel.initViewModel()
        }
        setContent {
            App()
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun App() {
        val cat by viewModel.cat.collectAsState()
        val dog by viewModel.dog.collectAsState()

        RandoMiserTheme {
            Layout(
                cat = cat,
                dog = dog,
                selectWinner = viewModel::selectWinner
            )
        }
    }
}
