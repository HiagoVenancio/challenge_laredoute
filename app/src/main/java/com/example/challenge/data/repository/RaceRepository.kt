package com.example.challenge.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.challenge.data.api.DataApi
import com.example.challenge.data.mapper.toDomainModel
import com.example.challenge.data.mapper.toDomainModelList
import com.example.challenge.domain.model.RaceDurationModel
import com.example.challenge.domain.model.RaceStatusResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException

interface IRaceRepository {
    suspend fun getRaceDuration(): RaceDurationModel
    suspend fun getRaceStatus(): Flow<RaceStatusResult>
}

@SuppressLint("NewApi")
class RaceRepository(private val api: DataApi) :
    IRaceRepository {

    override suspend fun getRaceDuration(): RaceDurationModel {
        return api.getRaceDuration().toDomainModel()
    }

    override suspend fun getRaceStatus(): Flow<RaceStatusResult> = flow {
        var shouldCallApi = true
        while (shouldCallApi) {
            try {
                val response = api.getRaceStatus()
                emit(RaceStatusResult.Success(response.beeList.toDomainModelList()))
            } catch (e: HttpException) {
                when (e.code()) {
                    403 -> {
                        val captchaUrl = parseCaptchaUrl(e.response()?.errorBody()?.string())
                        Log.e("CaptchaScreen", "403: $captchaUrl")
                        shouldCallApi = false
                        emit(RaceStatusResult.CaptchaRequired(captchaUrl))
                    }

                    else -> {
                        shouldCallApi = false
                        emit(RaceStatusResult.Error(e.message()))
                    }
                }
            } catch (e: Exception) {
                shouldCallApi = false
                emit(RaceStatusResult.Error("${e.message}"))
            }
            delay(2000L)
        }
    }

    private fun parseCaptchaUrl(errorBody: String?): String {
        return JSONObject(errorBody ?: "{}").optString("captchaUrl", "")
    }
}