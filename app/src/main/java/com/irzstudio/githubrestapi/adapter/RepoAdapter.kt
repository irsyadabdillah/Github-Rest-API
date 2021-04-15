package com.irzstudio.githubrestapi.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataItemUser

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var list: MutableList<DataRepoRespone> = mutableListOf()

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(dataRepoRespone: DataRepoRespone){

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RepoAdapter.RepoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}