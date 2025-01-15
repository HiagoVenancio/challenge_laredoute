package com.example.challenge.data.mapper

import com.example.challenge.data.api.responses.RaceStatusResponse
import com.example.challenge.domain.model.RaceStatusModel

fun RaceStatusResponse.toDomainModel(): RaceStatusModel {
    return RaceStatusModel(
        name = this.name,
        color = this.color
    )
}

fun List<RaceStatusResponse>.toDomainModelList(): List<RaceStatusModel> {
    return this.map { it.toDomainModel() }
}