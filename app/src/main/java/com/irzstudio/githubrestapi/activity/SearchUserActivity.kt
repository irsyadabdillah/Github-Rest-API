package com.irzstudio.githubrestapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.irzstudio.githubrestapi.R
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.adapter.SearchUserAdapter
import com.irzstudio.githubrestapi.datauser.DataItemUser
import com.irzstudio.githubrestapi.datauser.DataUser
import kotlinx.android.synthetic.main.activity_searchuser.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : AppCompatActivity() {

    private lateinit var adapter: SearchUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchuser)


        requestUserQuery("riz")

        setList()
    }


    private fun initView() {
        etv_search.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun setList() {
        adapter = SearchUserAdapter()
        rv_user_search.setHasFixedSize(true)
        rv_user_search.adapter = adapter
        rv_user_search.layoutManager = LinearLayoutManager(this@SearchUserActivity)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_user.visibility = View.VISIBLE
        } else {
            pb_user.visibility = View.GONE
        }
    }

    private fun requestUserQuery(query:String?) {
        RetrofitClient.instance.getUser(query.orEmpty()).enqueue(object : Callback<DataUser> {
            override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                adapter.setData(response.body()!!.items)
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }

        })
    }

}