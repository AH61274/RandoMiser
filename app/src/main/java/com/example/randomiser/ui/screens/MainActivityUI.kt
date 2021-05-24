package com.example.randomiser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomiser.RandoMiserViewModel
import com.example.randomiser.model.Platform
import com.example.randomiser.model.Teammate
import com.example.randomiser.ui.theme.RandoMiserTheme
import com.example.randomiser.ui.theme.RandoWhite
import kotlin.random.Random

@Composable
fun MemberListItem(teammate: Teammate) {
    with(teammate) {
        Surface(color = color) {
            Text(
                text = name,
                color = accentColor,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            )
            Divider(
                color = RandoWhite,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun TeamList(teammates: List<Teammate>, onClick: () -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        items(teammates) { teammate ->
            MemberListItem(teammate)
        }
    }
}

@Composable
fun WhosUp(teammate: Teammate) {
    with(teammate) {
        Surface(
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(142.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = name,
                    color = accentColor,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "You're up for $platform",
                    color = accentColor,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Main UI ------------------------------------
@Composable
fun MainActivityUI(viewModel: RandoMiserViewModel) {
    BuildUI(teammates = viewModel.teammates, viewModel.whosUp.value ?: return, viewModel::nextUp)
}

@Composable
private fun BuildUI(teammates: List<Teammate>, whosUp: Teammate, teamListOnClick: () -> Unit) {
    Column(Modifier.clickable { teamListOnClick() }) {
        WhosUp(teammate = whosUp)
        TeamList(teammates = teammates, onClick = { teamListOnClick() })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val teammates = Teammate.makeList()
    RandoMiserTheme {
        BuildUI(
            teammates,
            teammates[Random.nextInt(0, teammates.size - 1)],
            {}
        )
    }
}