package com.android.gadware.dynamicbaseurl.models
import androidx.annotation.Keep

@Keep
data class EmployeeResponseModel(
    val stationId: Long,
    val createdAt: String,
    val updatedAt: String,
    val current_flag: Long,
    val employeeId: Long,
    val name: String?,
    val pin_no: String?,
    val new_pin: String?,
    var email: String?,
    var mobile_no: String?,
    val img_url: String?,
    val join_date: String?,
    val about: String?,
    val designation: String?
)