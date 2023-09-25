package com.example.github_user_application.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.databinding.ActivityFavoriteBinding
import com.example.github_user_application.ui.ViewModelFactory
import com.example.github_user_application.ui.detail.FavoriteViewModel
import com.example.github_user_application.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFavoriteBinding

    private lateinit var adapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= UserAdapter()
        binding.rvFavorite.layoutManager= LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)

        val favoriteViewModel=obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getFavorite().observe(this){favoriteList->
            if (favoriteList!=null){
                binding.pgFavorite.visibility= View.GONE
                val items= arrayListOf<ItemsItem>()
                favoriteList.map {
                    val item=ItemsItem(login=it.username, avatarUrl = it.avatarURL)
                    items.add(item)
                }
                adapter.submitList(items)
            }else{
                binding.pgFavorite.visibility= View.VISIBLE
            }
        }

        binding.rvFavorite.adapter=adapter

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}