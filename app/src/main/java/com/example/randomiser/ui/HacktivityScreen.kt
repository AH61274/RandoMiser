package com.example.randomiser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.example.randomiser.HacktivityViewModel
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData

@Composable
fun Layout(viewModel: HacktivityViewModel) {
    Row {
        Text("Title")
    }

    Animals(viewModel.cat, viewModel.dog)

    Row {
        Text("Footer")
    }
}

@Composable
fun Animals(cat: LiveData<CatData>, dog: LiveData<DogData>) {
    val catState = cat.observeAsState().value
    val dogState = dog.observeAsState().value
    Column {
        if (catState != null && dogState != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = catState.url,
                    contentDescription = catState.url,
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
                    model = dogState.url,
                    contentDescription = dogState.url,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
