package com.irzstudio.githubrestapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.adapter.DetailUserAdapter
import com.irzstudio.githubrestapi.databinding.ActivityDetailuserBinding
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {


    private var login = ""
    private var avatar = ""

    private lateinit var adapter: DetailUserAdapter
    private lateinit var binding: ActivityDetailuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDataDetailUser()
    }


    private fun requestDetailUserQuery() {
        RetrofitClient.instance.getDetailUser(login).enqueue(object : Callback<DataDetailUser> {
            override fun onResponse(
                call: Call<DataDetailUser>,
                response: Response<DataDetailUser>
            ) {
                response.body()?.let {
                    binding.tvFullname.
                }
            }

            override fun onFailure(call: Call<DataDetailUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    private fun loadDataDetailUser() {
        login = intent.getStringExtra("login") ?: ""
        avatar = intent.getStringExtra("avatar") ?: ""
    }

}