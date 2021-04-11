package com.irzstudio.githubrestapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
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

        searchView()
        setList()
    }

    private fun searchView() {
        search_user.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search_user.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                requestUserQuery(newText)
                return false
            }

        })
    }

    private fun requestUserQuery(query: String?) {
        RetrofitClient.instance.getUser(query.orEmpty()).enqueue(object : Callback<DataUser> {
            override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                adapter.setData(response.body()!!.items)
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }

        })
    }

    private fun setList() {
        adapter = SearchUserAdapter()
        rv_user_search.setHasFixedSize(true)
        rv_user_search.adapter = adapter
        rv_user_search.layoutManager = LinearLayoutManager(this@SearchUserActivity)
    }

    private fun navigationToDetailUser(detailUserActivity: DetailUserActivity) {
        val intent = Intent(applicationContext, DetailUserActivity::class.java)

    }

}