package com.irzstudio.githubrestapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.adapter.PinnedAdapter
import com.irzstudio.githubrestapi.databinding.ActivityDetailuserBinding
import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import kotlinx.android.synthetic.main.item_list_user.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private var login = ""
    private var avatar = ""

    private lateinit var adapterRepoPin: PinnedAdapter
    private lateinit var binding: ActivityDetailuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDataDetailUser()
        requestDetailUserQuery()
        requestDataRepoPin()
        setListRepo()
    }


    private fun requestDetailUserQuery() {
        RetrofitClient.instance.getDetailUser(login).enqueue(object : Callback<DataDetailUser> {
            override fun onResponse(
                call: Call<DataDetailUser>,
                response: Response<DataDetailUser>
            ) {
                response.body()?.let {

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(binding.ivProfil)
                    binding.tvFullname.text = it.login
                    binding.tvUsername.text = it.name
                    binding.tvBio.text = it.bio
                    binding.tvLocation.text = it.location
                    binding.tvBlog.text = it.blog
                    binding.tvFollower.text = it.followers.toString()
                    binding.tvFollowing.text = it.following.toString()
                    binding.tvRepositories.text = it.public_repos.toString()

                }
            }

            override fun onFailure(call: Call<DataDetailUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    private fun requestDataRepoPin(){
        RetrofitClient.instance.getRepoPin(login).enqueue(object : Callback<ArrayList<DataRepoRespone>>{
            override fun onResponse(
                call: Call<ArrayList<DataRepoRespone>>,
                response: Response<ArrayList<DataRepoRespone>>
            ) {
                adapterRepoPin.setRepoPin(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<DataRepoRespone>>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }

        })
    }

    private fun setListRepo(){
        adapterRepoPin = PinnedAdapter()
        binding.rvRepoPin.setHasFixedSize(true)
        binding.rvRepoPin.adapter = adapterRepoPin
    }

    private fun loadDataDetailUser() {
        login = intent.getStringExtra("login") ?: ""
        avatar = intent.getStringExtra("avatar") ?: ""
    }

}