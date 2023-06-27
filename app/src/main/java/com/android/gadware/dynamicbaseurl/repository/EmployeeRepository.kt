package com.android.gadware.dynamicbaseurl.repository

import com.android.gadware.dynamicbaseurl.storage.ApiService
import com.android.gadware.dynamicbaseurl.utils.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getEmployeeList(token: String,stationId:String)  = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getEmployeeList(token,stationId)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }


}