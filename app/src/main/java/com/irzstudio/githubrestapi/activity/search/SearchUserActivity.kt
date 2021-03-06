package com.irzstudio.githubrestapi.activity.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.irzstudio.githubrestapi.`interface`.OnListener
import com.irzstudio.githubrestapi.activity.detailuser.DetailUserActivity
import com.irzstudio.githubrestapi.adapter.SearchUserAdapter
import com.irzstudio.githubrestapi.databinding.ActivitySearchuserBinding
import com.irzstudio.githubrestapi.datauser.DataItemUser
import kotlinx.android.synthetic.main.activity_searchuser.*


class SearchUserActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel

    private lateinit var adapter: SearchUserAdapter
    private lateinit var binding: ActivitySearchuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchuserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        searchView()
        setList()
        observeLiveData()
        observerErrorMessage()
    }

    private fun observeLiveData() {
        viewModel.dataItemUserList.observe(this, { dataItemUserResponse ->
            adapter.setData(dataItemUserResponse)
            sumResult(dataItemUserResponse.size)
        })
    }

    private fun sumResult(total: Int) {
        binding.txtResult.text = "Menampilkan $total hasil"
    }

    private fun observerErrorMessage(){
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this@SearchUserActivity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun searchView() {
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchUser.clearFocus()
                viewModel.requestUserQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
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



