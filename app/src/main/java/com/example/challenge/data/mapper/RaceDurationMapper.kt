package com.example.challenge.data.mapper

import com.example.challenge.data.api.responses.RaceDurationResponse
import com.example.challenge.domain.model.RaceDurationModel


fun RaceDurationResponse.toDomainModel(): RaceDurationModel {
    return RaceDurationModel(
        timeInSeconds = this.timeInSeconds,
    )
}



