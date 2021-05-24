package com.example.randomiser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomiser.RandoMiserViewModel
import com.example.randomiser.model.Teammate
import com.example.randomiser.ui.theme.*
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
                    .padding(LARGE_DP),
            )
            Divider(
                color = RandoWhite,
                thickness = SMALL_DP
            )
        }
    }
}

@Composable
fun TeamList(teammates: List<Teammate>, onClick: () -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(LARGE_DP, MED_DP)
            .clip(Shapes.medium)
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
                .height(MAIN_BOX_HEIGHT)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MED_DP)
            ) {
                Text(
                    text = name,
                    color = accentColor,
                    textAlign = TextAlign.Center,
                    fontSize = XL_FONT,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "You're up for $platform",
                    color = accentColor,
                    textAlign = TextAlign.Center,
                    fontSize = LARGE_FONT,
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
