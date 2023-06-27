package com.android.gadware.dynamicbaseurl.utils

import android.app.Application
import android.content.SharedPreferences
import androidx.annotation.Keep
import dagger.hilt.android.HiltAndroidApp
@Keep
@HiltAndroidApp
class AppInstance :Application(){
    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences(Constants.sharedPerfName, MODE_PRIVATE)
        editor = sharedPref.edit()
    }
    companion object {
        lateinit var sharedPref: SharedPreferences
            private set
        lateinit var editor: SharedPreferences.Editor
            private set
    }
}