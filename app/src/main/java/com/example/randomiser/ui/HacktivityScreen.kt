package com.example.randomiser.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomiser.model.Animal
import com.example.randomiser.model.HacktivityNavRoute
import com.example.randomiser.model.Round

@Composable
fun Layout(
    contenders: Pair<Animal, Animal>,
    selectWinner: (Animal) -> Unit,
    round: Round,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HacktivityNavRoute.TOURNAMENT.id
    ) {
        composable(HacktivityNavRoute.TOURNAMENT.id) {
            TournamentScreen(
                contenders = contenders,
                selectWinner = selectWinner,
                round = round,
            )
        }

        composable(HacktivityNavRoute.WINNER.id) {
            WinnerScreen()
        }
    }
}
