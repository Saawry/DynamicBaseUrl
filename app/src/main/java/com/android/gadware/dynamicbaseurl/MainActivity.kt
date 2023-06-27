package com.android.gadware.dynamicbaseurl

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.gadware.dynamicbaseurl.databinding.ActivityMainBinding
import com.android.gadware.dynamicbaseurl.models.LoginResponse
import com.android.gadware.dynamicbaseurl.storage.BaseUrlManager
import com.android.gadware.dynamicbaseurl.storage.NetworkModule.getUnsafeOkHttpClient
import com.android.gadware.dynamicbaseurl.utils.Constants
import com.android.gadware.dynamicbaseurl.utils.Constants.Companion.STATION_ID_KEY
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("Dynamic_Base_Url_SP", MODE_PRIVATE)
        editor = sharedPref.edit()


        binding.actionBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                okhtttpLogin("kawser@perkyrabbit.com","123Abc*")
            }

        }

    }

    fun okhtttpLogin(stringEmailorMobile: String, stringPassword: String){
        val client = getUnsafeOkHttpClient()?.build()
        val request = Request.Builder()
            .url("https://103.86.109.78:7002/Admin/AppAuthenAuthor/GetAppAuth?email=$stringEmailorMobile&password=$stringPassword")
            .build()
        if (client!=null){
            try {
                val response = client.newCall(request).execute()
                if (response.code==200){
                    val result=response.body?.string()
                    Log.d("LoginAttempt", "okhtttpLogin body: "+result)

                    if (result.toString().contains("Invalid login attempt.")){

                    }else if (result.toString().contains("user_role")){
                        val it = Gson().fromJson(result, LoginResponse::class.java)

                        editor.putString(Constants.TOKEN_KEY,"Bearer "+it.api_token).apply()

                        editor.putLong(STATION_ID_KEY,it.station_id).apply()
                        val url=binding.tvUrl.text.toString()
                        if (url.isNotEmpty()){
                            updateBaseUrl(url)
                        }
//                        val intent=Intent(this@LoginActivity,HomeActivity::class.java)
//                        startActivity(intent)
//                        finish()
                    }
                }

            }catch (e:Exception){
                Log.d("LoginAttempt", "okhtttpLogin: "+e.message)
            }

        }else{
            Log.d("LoginAttempt", "null")
        }

    }


    private fun updateBaseUrl(newBaseUrl: String) {
        BaseUrlManager.setBaseUrl(newBaseUrl)
        startActivity(Intent(this,EmployeeActivity::class.java))
    }
}