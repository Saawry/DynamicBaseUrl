package com.android.gadware.dynamicbaseurl.storage


import com.android.gadware.dynamicbaseurl.models.EmployeeResponseModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {


    companion object {

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(NetworkModule.provideBaseUrl())
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }






    @GET("api/Employee/GetAllByStation")
    suspend fun getEmployeeList(@Header("Authorization") token: String,@Query ("stationId") stationId:String)  :List<EmployeeResponseModel>


}