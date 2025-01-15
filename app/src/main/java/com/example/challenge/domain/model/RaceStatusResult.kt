package com.example.challenge.domain.model

sealed class RaceStatusResult {
    data class Success(val data: List<RaceStatusModel>) : RaceStatusResult()
    data class CaptchaRequired(val captchaUrl: String) : RaceStatusResult()
    data class Error(val message: String) : RaceStatusResult()
}