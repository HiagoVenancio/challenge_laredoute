package com.example.challenge.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.challenge.R
import com.example.challenge.ui.components.CustomButton
import com.example.challenge.ui.navigation.Screen

@Composable
fun StartScreen(navController: NavHostController) {
    Scaffold { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            CustomButton(onClick = {
                navController.navigate(Screen.MainScreen.route)
            }, text = stringResource(R.string.start_race_label))
        }
    }

}