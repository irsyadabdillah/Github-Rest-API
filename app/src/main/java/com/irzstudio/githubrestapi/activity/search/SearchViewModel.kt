package com.irzstudio.githubrestapi.activity.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.datauser.DataItemUser
import com.irzstudio.githubrestapi.datauser.DataUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _dataItemUserList = MutableLiveData<ArrayList<DataItemUser>>()
    val dataItemUserList: LiveData<ArrayList<DataItemUser>> = _dataItemUserList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun requestUserQuery(query: String?) {
        RetrofitClient.instance.getUser(query.orEmpty())
            .enqueue(object : Callback<DataUserResponse> {
                override fun onResponse(
                    call: Call<DataUserResponse>,
                    response: Response<DataUserResponse>
                ) {
                    val dataUserResponse: DataUserResponse = response.body()!!
                    _dataItemUserList.postValue(dataUserResponse.items)

                }

                override fun onFailure(call: Call<DataUserResponse>, t: Throwable) {
                    t.printStackTrace()
                    _errorMessage.postValue("Error")
                }
            })
    }

}