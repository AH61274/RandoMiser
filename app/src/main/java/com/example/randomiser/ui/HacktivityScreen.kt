package com.example.randomiser.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomiser.model.Animal
import com.example.randomiser.model.HacktivityNavRoute

@Composable
fun Layout(
    contenders: Pair<Animal, Animal>,
    selectWinner: (Animal) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HacktivityNavRoute.TOURNAMENT.id
    ) {
        composable(HacktivityNavRoute.TOURNAMENT.id) {
            TournamentScreen(
                contenders = contenders,
                selectWinner = selectWinner
            )
        }

        composable(HacktivityNavRoute.WINNER.id) {
            WinnerScreen()
        }
    }
}
