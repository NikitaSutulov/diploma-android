package com.nikitasutulov.macsro.data.dto

sealed class BaseResponse<out T> {
    data class Success<out T>(
        val data: T?,
        val code: Int
    ) : BaseResponse<T>()
    data class Loading(val nothing: Nothing? = null) : BaseResponse<Nothing>()
    data class Error(val error: ErrorResponse? = null) : BaseResponse<Nothing>()
}