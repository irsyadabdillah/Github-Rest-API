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

    private val login: String by lazy {
        intent.getStringExtra("login") ?: ""
    }
    private val adapterRepoPin: PinnedAdapter by lazy {
        PinnedAdapter()
    }

    private lateinit var binding: ActivityDetailuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                response.body()?.let { dataDetailUser ->
                    setUserData(dataDetailUser)
                    loadImage(dataDetailUser.avatar_url)
                }
            }

            override fun onFailure(call: Call<DataDetailUser>, t: Throwable) {
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    private fun setUserData(dataDetailUser: DataDetailUser) {
        binding.tvFullname.text = dataDetailUser.login
        binding.tvUsername.text = dataDetailUser.name
        binding.tvBio.text = dataDetailUser.bio
        binding.tvLocation.text = dataDetailUser.location
        binding.tvBlog.text = dataDetailUser.blog
        binding.tvFollower.text = dataDetailUser.followers.toString()
        binding.tvFollowing.text = dataDetailUser.following.toString()
        binding.tvRepositories.text = dataDetailUser.public_repos.toString()
    }

    private fun loadImage(url: String) {
        Glide.with(this@DetailUserActivity)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.ivProfil)
    }


    private fun requestDataRepoPin() {
        RetrofitClient.instance.getRepoPin(login)
            .enqueue(object : Callback<ArrayList<DataRepoRespone>> {
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

    private fun setListRepo() {
        binding.rvRepoPin.setHasFixedSize(true)
        binding.rvRepoPin.adapter = adapterRepoPin
    }

}