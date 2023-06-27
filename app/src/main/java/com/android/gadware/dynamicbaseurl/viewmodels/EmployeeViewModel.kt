package com.android.gadware.dynamicbaseurl.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.gadware.dynamicbaseurl.models.EmployeeResponseModel
import com.android.gadware.dynamicbaseurl.repository.EmployeeRepository
import com.android.gadware.dynamicbaseurl.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private var _employeeResponse = MutableLiveData<NetworkResult<List<EmployeeResponseModel>>>()
    val employeeResponse: LiveData<NetworkResult<List<EmployeeResponseModel>>> = _employeeResponse





      fun getEmployeeList(token: String,stationId:String) {
        viewModelScope.launch {
            employeeRepository.getEmployeeList(token,stationId).collect {
                _employeeResponse.postValue(it)
            }
        }
    }

}