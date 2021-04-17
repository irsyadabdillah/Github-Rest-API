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

    val message = MutableLiveData<String>()

    fun sayHallo(name: String) {
        // message.postValue("Halo, selamat datang $name")
    }

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
                    t.message?.let { Log.d("Error", it) }
                }
            })
    }

}