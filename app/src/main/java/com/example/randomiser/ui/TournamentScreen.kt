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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.randomiser.model.Animal
import com.example.randomiser.model.Round
import com.example.randomiser.ui.theme.RandoMiserTheme

@Composable
fun AnimalCard(
    animal: Animal,
    selectWinner: (Animal) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                selectWinner(animal)
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
                    text = animal.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                AsyncImage(
                    model = animal.url,
                    contentDescription = animal.url,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun TournamentScreen(
    contenders: Pair<Animal, Animal>,
    selectWinner: (Animal) -> Unit,
    round: Round
) {
    Column {
        Text(round.name)
        AnimalCard(
            animal = contenders.first,
            selectWinner = selectWinner,
            modifier = Modifier.weight(1f)
        )
        AnimalCard(
            animal = contenders.second,
            selectWinner = selectWinner,
            modifier = Modifier.weight(1f)
        )
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
        TournamentScreen(
            Pair(Animal(), Animal()),
            selectWinner = {},
            Round.ROUND_1,
        )
    }
}