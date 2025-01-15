package com.example.challenge.data.api.responses

import com.google.gson.annotations.SerializedName

data class RaceDurationResponse(
    @SerializedName("timeInSeconds") val timeInSeconds: Int,
)
