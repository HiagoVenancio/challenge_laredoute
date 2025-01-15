package com.example.challenge.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.challenge.R
import com.example.challenge.ui.components.CustomButton
import com.example.challenge.ui.components.TextSingleLine
import com.example.challenge.ui.navigation.Screen
import com.example.challenge.ui.theme.GoldColor

@Composable
fun WinnerScreen(navController: NavHostController, name: String, color: String) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Icon(
                painter = painterResource(id = R.drawable.trophy_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp),
                tint = GoldColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.winner_label),
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            val tintColor =
                if (color.isNotEmpty()) Color(android.graphics.Color.parseColor(color)) else Color.Black

            Icon(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(60.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.place_holder_icon),
                contentDescription = "",
                tint = tintColor
            )

            Spacer(modifier = Modifier.height(5.dp))
            TextSingleLine(
                value = name,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(30.dp))

            CustomButton(onClick = {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.WinnerScreen.route) { inclusive = true }
                }
            }, text = stringResource(R.string.re_start_bee_race))
        }
    }
}