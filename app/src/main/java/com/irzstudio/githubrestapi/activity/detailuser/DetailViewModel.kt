package com.irzstudio.githubrestapi.activity.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _dataDetailUserList = MutableLiveData<DataDetailUser>()
    val dataDetailUserList : LiveData<DataDetailUser> = _dataDetailUserList

    private val _dataRepoList = MutableLiveData<ArrayList<DataRepoRespone>>()
    val dataResponse : LiveData<ArrayList<DataRepoRespone>> = _dataRepoList


    fun requestDetailUserQuery(query: String?) {
        RetrofitClient.instance.getDetailUser(query.orEmpty()).enqueue(object : Callback<DataDetailUser> {
            override fun onResponse(
                call: Call<DataDetailUser>,
                response: Response<DataDetailUser>
            ) {
                response.body()?.let { dataDetailUser ->
                    _dataDetailUserList.postValue(dataDetailUser)
                }
            }

            override fun onFailure(call: Call<DataDetailUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    fun requestDataRepoPin(query: String?) {
        RetrofitClient.instance.getRepoPin(query.orEmpty())
            .enqueue(object : Callback<ArrayList<DataRepoRespone>> {
                override fun onResponse(
                    call: Call<ArrayList<DataRepoRespone>>,
                    response: Response<ArrayList<DataRepoRespone>>
                ) {
                    response.body()?.let { dataRepoRespone ->
                        _dataRepoList.postValue(dataRepoRespone)
                    }
                }

                override fun onFailure(call: Call<ArrayList<DataRepoRespone>>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }

}
