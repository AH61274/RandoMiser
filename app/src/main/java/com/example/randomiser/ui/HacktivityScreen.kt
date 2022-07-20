package com.example.randomiser.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.randomiser.model.Animal
import com.example.randomiser.ui.theme.RandoMiserTheme

@Composable
fun Layout(
    cat: Animal,
    dog: Animal,
    selectWinner: (Animal) -> Unit
) {

    Animals(
        cat = cat,
        dog = dog,
        selectWinner = selectWinner
    )
}

@Composable
fun Animals(
    cat: Animal,
    dog: Animal,
    selectWinner: (Animal) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .clickable {
                    selectWinner(cat)
                }
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Cyan,
                        shape = RectangleShape,
                    )
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = cat.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    AsyncImage(
                        model = cat.url,
                        contentDescription = cat.url,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .clickable {
                    selectWinner(dog)
                }
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Cyan,
                        shape = RectangleShape,
                    )
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dog.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    AsyncImage(
                        model = dog.url,
                        contentDescription = dog.url,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview(
    name = "HacktivityScreen Preview - Light",
    showBackground = true,
    backgroundColor = 0xffeff0f1
)
@Composable
fun HacktivityScreenPreview() {
    RandoMiserTheme {
        Layout(
            cat = Animal(),
            dog = Animal(),
            selectWinner = {}
        )
    }
}
