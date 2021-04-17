package com.irzstudio.githubrestapi.activity.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.irzstudio.githubrestapi.RetrofitClient
import com.irzstudio.githubrestapi.activity.search.SearchViewModel
import com.irzstudio.githubrestapi.adapter.PinnedAdapter
import com.irzstudio.githubrestapi.databinding.ActivityDetailuserBinding
import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import kotlinx.android.synthetic.main.item_list_user.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var viewModel : DetailViewModel
    private lateinit var binding: ActivityDetailuserBinding

    private val login: String by lazy {
        intent.getStringExtra("login") ?: ""
    }
    private val adapterRepoPin: PinnedAdapter by lazy {
        PinnedAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        viewModel.requestDetailUserQuery(login)
        viewModel.requestDataRepoPin(login)
        setListRepo()
        observeLiveData()
        observeLiveDataRepo()
    }

    private fun observeLiveData(){
        viewModel.dataDetailUserList.observe(this, {dataDetailUserRespone ->
            setUserData(dataDetailUserRespone)
            loadImage(dataDetailUserRespone.avatar_url)
        })
    }

    private fun observeLiveDataRepo(){
        viewModel.dataResponse.observe(this,{dataRepoResponse ->
            adapterRepoPin.setRepoPin(dataRepoResponse)

        })
    }


    fun setUserData(dataDetailUser: DataDetailUser) {
        binding.tvFullname.text = dataDetailUser.login
        binding.tvUsername.text = dataDetailUser.name
        binding.tvBio.text = dataDetailUser.bio
        binding.tvLocation.text = dataDetailUser.location
        binding.tvBlog.text = dataDetailUser.blog
        binding.tvFollower.text = dataDetailUser.followers.toString()
        binding.tvFollowing.text = dataDetailUser.following.toString()
        binding.tvRepositories.text = dataDetailUser.public_repos.toString()
    }

    fun loadImage(url: String) {
        Glide.with(this@DetailUserActivity)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.ivProfil)
    }

    private fun setListRepo() {
        binding.rvRepoPin.setHasFixedSize(true)
        binding.rvRepoPin.adapter = adapterRepoPin
    }

}