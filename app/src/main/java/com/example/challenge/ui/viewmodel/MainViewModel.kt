package com.example.challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge.data.repository.IRaceRepository
import com.example.challenge.domain.model.RaceStatusModel
import com.example.challenge.domain.model.RaceStatusResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: IRaceRepository
) : ViewModel() {

    private val _raceStatus =
        MutableStateFlow<RaceStatusResult>(RaceStatusResult.Success(emptyList()))
    val raceStatus: StateFlow<RaceStatusResult> = _raceStatus

    private val _remainingTime = MutableStateFlow(0)
    val remainingTime: StateFlow<Int> = _remainingTime

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _winner = MutableStateFlow<RaceStatusModel?>(null)
    val winner: StateFlow<RaceStatusModel?> = _winner

    private val _isRaceOver = MutableStateFlow(false)
    val isRaceOver: StateFlow<Boolean> = _isRaceOver

    private val _isCaptchaOpen = MutableStateFlow(false)
    val isCaptchaOpen: StateFlow<Boolean> = _isCaptchaOpen

    private var raceStatusJob: Job? = null

    private var timeExtra: Int = 20

    init {
        fetchRaceStatus()
        fetchRaceDuration()
    }

    fun fetchRaceDuration() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getRaceDuration()
                val timeInSeconds = result.timeInSeconds.plus(timeExtra)
                startCountdown(timeInSeconds)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchRaceStatus() {
        raceStatusJob = viewModelScope.launch {
            repository.getRaceStatus().collectLatest { raceStatus ->
                when (raceStatus) {
                    is RaceStatusResult.Success -> {
                        _winner.value = raceStatus.data.first()
                    }

                    is RaceStatusResult.CaptchaRequired -> {
                        _isCaptchaOpen.value = true
                    }

                    else -> {}
                }
                _raceStatus.value = raceStatus
            }
        }
    }

    fun handleCaptchaResolved() {
        _isCaptchaOpen.value = false
        fetchRaceStatus()
    }

    private fun startCountdown(duration: Int) {
        viewModelScope.launch {
            _remainingTime.value = duration
            for (time in duration downTo 0) {
                _remainingTime.value = time
                delay(1000)
                if (time == 0) {
                    _isRaceOver.value = true
                    raceStatusJob?.cancel()
                }
            }
        }
    }

    private fun resetWinner() {
        _winner.value = null
        _isRaceOver.value = false
    }

    override fun onCleared() {
        super.onCleared()
        resetWinner()
        raceStatusJob?.cancel()
    }
}