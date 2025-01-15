package com.example.challenge.data.api

import com.example.challenge.data.api.responses.RaceDurationResponse
import com.example.challenge.data.api.responses.RaceStatusListResponse
import retrofit2.http.GET

interface DataApi {
    @GET("bees/race/status")
    suspend fun getRaceStatus(): RaceStatusListResponse

    @GET("bees/race/duration")
    suspend fun getRaceDuration(): RaceDurationResponse
}
