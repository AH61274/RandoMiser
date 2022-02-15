package com.example.randomiser.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomiser.RandoMiserViewModel
import com.example.randomiser.model.Teammate
import com.example.randomiser.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.random.Random

@Composable
fun MemberListItem(teammate: Teammate, modifier: Modifier) {
    with(teammate) {
        Surface(
            color = color,
            modifier = modifier.clip(
                Shapes.small
            )
        ) {
            Text(
                text = name,
                color = accentColor,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LARGE_DP),
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun TeamList(teammates: List<Teammate>, onDismiss: (Teammate) -> Unit) {
    AnimatedVisibility(
        visible = teammates.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(LARGE_DP, MED_DP)
                .animateContentSize()
        ) {
            items(items = teammates, key = {
                it.name
            }) { teammate ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart) || dismissState.isDismissed(
                        DismissDirection.StartToEnd
                    )
                ) {
                    onDismiss(teammate)
                }
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = when (direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Delete
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        MemberListItem(
                            teammate,
                            modifier = Modifier
                                .padding(2.dp)
                                .animateEnterExit()
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun WhosUp(teammate: Teammate, next: () -> Unit) {
    with(teammate) {
        Surface(
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(MAIN_BOX_HEIGHT)
                .clickable { next() }
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
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MainActivityUI(viewModel: RandoMiserViewModel) {
    val next by viewModel.whosUp.observeAsState()
    val teammates by viewModel.teammates.observeAsState(emptyList())
    val isUpdating by viewModel.isUpdating.observeAsState(false)
    BuildUI(
        teammates = teammates.toList(),
        whosUp = next ?: return,
        isUpdating = isUpdating,
        restart = viewModel::restart,
        onClick = viewModel::nextUp,
        onDismiss = viewModel::onDismiss,
    )
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun BuildUI(teammates: List<Teammate>, isUpdating: Boolean, restart: () -> Unit, whosUp: Teammate, onClick: () -> Unit, onDismiss: (Teammate) -> Unit) {
    Column(Modifier.clickable { onClick() }) {
        WhosUp(teammate = whosUp, next = onClick)
        SwipeRefresh(
            state = rememberSwipeRefreshState(isUpdating),
            onRefresh = { restart() },
        ) {
            TeamList(teammates = teammates, onDismiss = onDismiss)
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val teammates = Teammate.makeList()
    RandoMiserTheme {
        BuildUI(
            teammates,
            false,
            {},
            teammates[Random.nextInt(0, teammates.size - 1)],
            {},
            {}
        )
    }
}
