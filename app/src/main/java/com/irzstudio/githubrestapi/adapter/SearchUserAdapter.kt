package com.irzstudio.githubrestapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.irzstudio.githubrestapi.R
import com.irzstudio.githubrestapi.`interface`.OnListener
import com.irzstudio.githubrestapi.datauser.DataItemUser
import kotlinx.android.synthetic.main.item_list_user.view.*

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() {

    private val list: MutableList<DataItemUser> = mutableListOf()

    var onClickListener: OnListener? = null

    inner class SearchUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataItemlUser: DataItemUser) {

            itemView.setOnClickListener {
                onClickListener?.onClick(dataItemlUser)
            }

            Glide.with(itemView)
                .load(dataItemlUser.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(itemView.iv_profil_search)
            itemView.tv_fullname_search.text = dataItemlUser.login

        }
    }


    fun setData(newData: List<DataItemUser>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()//menampilkan data yg baru ke user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return SearchUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}