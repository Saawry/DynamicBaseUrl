package com.android.gadware.dynamicbaseurl

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.android.gadware.dynamicbaseurl.databinding.ActivityEmployee2Binding
import com.android.gadware.dynamicbaseurl.databinding.ActivityEmployeeBinding
import com.android.gadware.dynamicbaseurl.utils.Constants
import com.android.gadware.dynamicbaseurl.utils.NetworkResult
import com.android.gadware.dynamicbaseurl.viewmodels.EmployeeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityEmployee2Binding
    private val employeeViewModel: EmployeeViewModel by viewModels()
    var TOKEN=""
    var STATION_ID:Long=-1

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEmployee2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("Dynamic_Base_Url_SP", MODE_PRIVATE)
        editor = sharedPref.edit()

        TOKEN=sharedPref.getString(Constants.TOKEN_KEY,"").toString()
        STATION_ID=sharedPref.getLong(Constants.STATION_ID_KEY,-1)
        getEmployeeList()
    }
    private fun getEmployeeList() {
        employeeViewModel.getEmployeeList(TOKEN,STATION_ID.toString())
        employeeViewModel.employeeResponse.observe(this) {
            when(it) {
                is NetworkResult.Loading -> {
                    //binding.progressbar.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Log.d("SecondEmployeeList", "error: "+it.errorMessage)
                    //Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
//                    binding.progressbar.isVisible = false
                }

                is  NetworkResult.Success -> {
                    Log.d("SecondEmployeeList", "list: "+it.data)
                    //employeeAdapter.updateModelList(it.data)
                    //binding.progressbar.isVisible = false
                }
                else -> {}
            }
        }
    }
}