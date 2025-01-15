package com.example.challenge.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.challenge.R
import com.example.challenge.domain.model.RaceStatusModel
import com.example.challenge.domain.model.RaceStatusResult
import com.example.challenge.ui.components.CaptchaScreen
import com.example.challenge.ui.components.ErrorView
import com.example.challenge.ui.components.StatusItem
import com.example.challenge.ui.components.TextSingleLine
import com.example.challenge.ui.navigation.Screen
import com.example.challenge.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@SuppressLint("NewApi")
@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainViewModel = koinViewModel()

    val isLoading by viewModel.isLoading.collectAsState()
    val remainingTime by viewModel.remainingTime.collectAsState()
    val raceStatus = viewModel.raceStatus.collectAsState()
    val isRaceOver by viewModel.isRaceOver.collectAsState()
    val isCaptchaOpen by viewModel.isCaptchaOpen.collectAsState()
    val winner by viewModel.winner.collectAsState()

    LaunchedEffect(isRaceOver) {
        if (isRaceOver && isCaptchaOpen.not()) {
            delay(500)
            winner?.let {
                navController.navigate(Screen.WinnerScreen.createRoute(it.name, it.color)) {
                    popUpTo(Screen.MainScreen.route) { inclusive = true }
                }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .background(color = Color.Black)
                            .fillMaxWidth()
                            .height(100.dp),
                        verticalArrangement = Arrangement.Center
                    ) {

                        TextSingleLine(
                            value = stringResource(R.string.time_remaining_label),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        TextSingleLine(
                            value = "$remainingTime",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    when (val state = raceStatus.value) {
                        is RaceStatusResult.Success -> RaceScreen(state.data)

                        is RaceStatusResult.CaptchaRequired -> CaptchaScreen(
                            captchaUrl = state.captchaUrl,
                            onCaptchaComplete = {
                                if (isRaceOver) {
                                    winner?.let {
                                        navController.navigate(Screen.WinnerScreen.createRoute(it.name, it.color)) {
                                            popUpTo(Screen.MainScreen.route) { inclusive = true }
                                        }
                                    }
                                } else {
                                    viewModel.handleCaptchaResolved()
                                }
                            }
                        )

                        is RaceStatusResult.Error -> ErrorView(errorMessage = state.message) {
                            viewModel.fetchRaceStatus()
                            viewModel.fetchRaceDuration()
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun RaceScreen(beePositions: List<RaceStatusModel>) {
    LazyColumn(
        modifier = Modifier.padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(beePositions) { index, data ->
            StatusItem(status = data, position = index)
        }
    }
}
