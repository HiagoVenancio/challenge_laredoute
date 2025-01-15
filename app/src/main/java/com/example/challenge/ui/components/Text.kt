package com.example.challenge.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextSingleLine(value: String, color: Color = Color.Black, style: TextStyle = MaterialTheme.typography.bodySmall) {
    Text(
        color = color,
        textAlign = TextAlign.Center,
        maxLines = 1,
        style = style,
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    )
}


