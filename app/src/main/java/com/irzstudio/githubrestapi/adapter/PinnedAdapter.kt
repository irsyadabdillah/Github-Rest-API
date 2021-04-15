package com.irzstudio.githubrestapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.irzstudio.githubrestapi.R
import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataItemUser
import kotlinx.android.synthetic.main.item_list_pinned.view.*
import kotlinx.android.synthetic.main.item_list_user.view.*

class PinnedAdapter : RecyclerView.Adapter<PinnedAdapter.PinnedViewHolder>() {

    private var list : MutableList<DataRepoRespone> = mutableListOf()

    inner class PinnedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(dataRepoRespone: DataRepoRespone){

            Glide.with(itemView)
                .load(dataRepoRespone.owner.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(itemView.iv_profil_pin)
            itemView.tv_fullname_pin.text = dataRepoRespone.owner.login
            itemView.tv_title.text = dataRepoRespone.name
            itemView.tv_description.text = dataRepoRespone.description
        }
    }

    fun setRepoPin(newData: List<DataRepoRespone>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()//menampilkan data yg baru ke user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinnedViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_pinned, parent, false)
        return PinnedViewHolder(view)
    }

    override fun onBindViewHolder(holder: PinnedViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return if(list.size > 5) 5 else list.size
    }

}