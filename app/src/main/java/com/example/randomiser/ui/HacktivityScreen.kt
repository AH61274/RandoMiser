package com.example.randomiser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomiser.HacktivityViewModel
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData

@Composable
fun Layout(viewModel: HacktivityViewModel) {

    val cat by viewModel.cat.collectAsState()
    val dog by viewModel.dog.collectAsState()

    Row {
        Text("Title")
    }

    Animals(cat, dog)

    Row {
        Text("Footer")
    }
}

@Composable
fun Animals(cat: CatData, dog: DogData) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = cat.url,
                contentDescription = cat.url,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = dog.url,
                contentDescription = dog.url,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
