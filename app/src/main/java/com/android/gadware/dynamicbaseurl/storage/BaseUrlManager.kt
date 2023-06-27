package com.android.gadware.dynamicbaseurl.storage

object BaseUrlManager {
    private var baseUrl: String = "http://103.86.109.78:7001/"

    fun setBaseUrl(newBaseUrl: String) {
        baseUrl = newBaseUrl
    }

    fun getBaseUrl(): String {
        return baseUrl
    }
}
