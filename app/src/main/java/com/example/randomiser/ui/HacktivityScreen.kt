package com.example.randomiser.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomiser.HacktivityViewModel
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData

@Composable
fun Layout(viewModel: HacktivityViewModel) {
    Row {
        Text("Title")
    }
    Animals(viewModel.cat.value, viewModel.dog.value)
    Row {
        Text("Footer")
    }
}

@Composable
fun Animals(cat: CatData?, dog: DogData?) {
    Row {
        if (cat != null && dog != null) {
            Column {
                AsyncImage(
                    model = cat.url,
                    contentDescription = cat.url,
                    modifier = Modifier.width(200.dp).height(200.dp)
                )
            }
            Column {
                AsyncImage(
                    model = dog.url,
                    contentDescription = dog.url,
                    modifier = Modifier.width(200.dp).height(200.dp)
                )
            }
        }
    }
}
