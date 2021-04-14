package com.irzstudio.githubrestapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.irzstudio.githubrestapi.R
import com.irzstudio.githubrestapi.`interface`.OnClickDetailUser
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import kotlinx.android.synthetic.main.activity_detailuser.view.*

class DetailUserAdapter : RecyclerView.Adapter<DetailUserAdapter.DetailUserViewHolder>() {

    private val list: MutableList<DataDetailUser> = mutableListOf()
    private var onClickListener: OnClickDetailUser? = null

    inner class DetailUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataDetailUser: DataDetailUser) {

            itemView.setOnClickListener {
                onClickListener?.onClickDetailUser(dataDetailUser)
            }

            Glide.with(itemView)
                .load(dataDetailUser.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(itemView.iv_profil)
            itemView.tv_fullname.text = dataDetailUser.login
            itemView.tv_username.text = dataDetailUser.name
            itemView.tv_bio.text = dataDetailUser.bio
            itemView.tv_location.text = dataDetailUser.location
            itemView.tv_blog.text = dataDetailUser.blog
            itemView.tv_follower.text = dataDetailUser.followers.toString()
            itemView.tv_following.text = dataDetailUser.following.toString()
            itemView.tv_repositories.text = dataDetailUser.repos_url
            itemView.tv_organizations.text = dataDetailUser.organizations_url
            itemView.tv_starred.text = dataDetailUser.starred_url

        }
    }

    fun setDataDetailUser(newData: List<DataDetailUser>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()//menampilkan data yg baru ke user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailUserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return DetailUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailUserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }




}