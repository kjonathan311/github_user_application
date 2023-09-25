package com.example.github_user_application.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.databinding.ItemLayoutBinding
import com.example.github_user_application.ui.detail.DetailActivity

class UserAdapter :ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.ivItemProfile)
            binding.tvItemUsername.text=user.login.toString()
            binding.root.setOnClickListener {
                val intent=Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("username",user.login)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}