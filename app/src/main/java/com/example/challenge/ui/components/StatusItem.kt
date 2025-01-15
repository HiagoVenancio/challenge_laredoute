package com.example.challenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.challenge.R
import com.example.challenge.domain.model.RaceStatusModel
import com.example.challenge.ui.theme.BronzeColor
import com.example.challenge.ui.theme.GoldColor
import com.example.challenge.ui.theme.SilverColor

@Composable
fun StatusItem(status: RaceStatusModel, position: Int) {
    val currentPosition = position.plus(1)
    val isTopThree = currentPosition <= 3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            contentDescription = "",
            painter = painterResource(R.drawable.place_holder_icon),
            modifier = Modifier
                .weight(0.2f)
                .padding(vertical = 10.dp)
                .size(40.dp),
            tint = Color(android.graphics.Color.parseColor(status.color)),
        )

        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 8.dp)
        ) {
            TextSingleLine(value = "Place: $currentPosition")
            Spacer(modifier = Modifier.height(6.dp))
            TextSingleLine(value = status.name)
        }

        if (isTopThree) {
            val tintColor = when (currentPosition) {
                1 -> GoldColor
                2 -> SilverColor
                3 -> BronzeColor
                else -> Color.Transparent
            }
            Icon(
                painter = painterResource(id = R.drawable.trophy_icon),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.3f)
                    .size(24.dp),
                tint = tintColor
            )
        } else {
            Spacer(
                modifier = Modifier.weight(0.3f)
            )
        }
    }
}

