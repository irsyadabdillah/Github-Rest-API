package com.irzstudio.githubrestapi.activity.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.`interface`.OnListener
import com.irzstudio.githubrestapi.activity.DetailUserActivity
import com.irzstudio.githubrestapi.adapter.PinnedAdapter
import com.irzstudio.githubrestapi.adapter.SearchUserAdapter
import com.irzstudio.githubrestapi.databinding.ActivitySearchuserBinding
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import com.irzstudio.githubrestapi.datauser.DataItemUser
import com.irzstudio.githubrestapi.datauser.DataUserResponse
import kotlinx.android.synthetic.main.activity_searchuser.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchUserActivity : AppCompatActivity() {

    private lateinit var adapter: SearchUserAdapter
    private lateinit var binding: ActivitySearchuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView()
        setList()
    }

    private fun sumResult(dataUser: DataUserResponse) {
        binding.txtResult.text = "Menampilkan ${dataUser.items.size.toString()} hasil"
    }

    private fun searchView() {
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchUser.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                requestUserQuery(newText)
                return false
            }
        })
    }

    private fun requestUserQuery(query: String?) {
        RetrofitClient.instance.getUser(query.orEmpty()).enqueue(object : Callback<DataUserResponse> {
            override fun onResponse(call: Call<DataUserResponse>, response: Response<DataUserResponse>) {
                adapter.setData(response.body()!!.items)
                sumResult(dataUser = response.body()!!)

            }

            override fun onFailure(call: Call<DataUserResponse>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }
        })
    }


    private fun setList() {
        adapter = SearchUserAdapter()
        binding.rvUserSearch.setHasFixedSize(true)
        binding.rvUserSearch.adapter = adapter
        binding.rvUserSearch.layoutManager = LinearLayoutManager(this@SearchUserActivity)
        adapter.onClickListener = object : OnListener {
            override fun onClick(dataItemUser: DataItemUser) {
                navigationToDetailUser(dataItemUser)
            }
        }
    }

    private fun navigationToDetailUser(dataItemUser: DataItemUser) {
        val intent = Intent(applicationContext, DetailUserActivity::class.java)
        intent.putExtra("login", dataItemUser.login)
        startActivity(intent)
    }
}



