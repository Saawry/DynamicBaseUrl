package com.android.gadware.dynamicbaseurl.models
import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val user_id: String,
    val user_role: String,
    val user_role_id: String,
    val station_id: Long,
    val employee_id: Long,
    val api_token: String,
    val permissions: List<String>
)