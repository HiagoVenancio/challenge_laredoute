package com.example.challenge.data.api.responses

import com.google.gson.annotations.SerializedName

data class RaceStatusListResponse(
    @SerializedName("beeList") val beeList: List<RaceStatusResponse>
)

data class RaceStatusResponse(
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: String
)