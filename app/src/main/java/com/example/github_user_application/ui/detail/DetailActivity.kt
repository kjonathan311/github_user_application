package com.example.github_user_application.ui.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.github_user_application.R
import com.example.github_user_application.data.local.entity.FavoriteUserEntity
import com.example.github_user_application.data.remote.response.DetailUserResponse
import com.example.github_user_application.databinding.ActivityDetailBinding
import com.example.github_user_application.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2,
        )
        private var detailUserFav:FavoriteUserEntity= FavoriteUserEntity("default","default")
    }

    private var username: String=""
    private var isFav:Boolean=false

    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        val username=intent?.getStringExtra("username")
        val adapter= DetailsTabAdapter(this)
        if (username != null) {
            detailViewModel.getDetailUser(username)
            adapter.username=username
        }

        binding.viewPager2.adapter=adapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position->
            tab.text=resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.detailuser.observe(this) {
            setProfile(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val favoriteviewModel =obtainViewModel(this@DetailActivity)


        if (username != null) {
            favoriteviewModel.isFavorited(username).observe(this) { isFavorited ->
                if(isFavorited){
                    binding.favAdd.setImageResource(R.drawable.favorite_fill)
                    isFav=true
                }else{
                    binding.favAdd.setImageResource(R.drawable.favorite_border)
                    isFav=false
                }
            }
        }

        binding.favAdd.setOnClickListener {
            if (isFav){
                if (username != null) {
                    favoriteviewModel.deleteFav(username)
                }
                binding.favAdd.setImageResource(R.drawable.favorite_border)
            }else{
                favoriteviewModel.saveFav(detailUserFav)
                binding.favAdd.setImageResource(R.drawable.favorite_fill)
            }
        }
    }
    private fun setProfile(detailUser: DetailUserResponse){
        binding.tvDetailName.text=detailUser.name
        binding.tvDetailUsername.text=detailUser.login
        binding.tvDetailFollower.text=getString(R.string.content_Follower,detailUser.followers)
        binding.tvDetailFollowing.text=getString(R.string.content_Following,detailUser.following)
        Glide.with(binding.root)
            .load(detailUser.avatarUrl)
            .into(binding.ivDetailProfile)
        detailUserFav.username= detailUser.login.toString()
        username= detailUser.login.toString()
        detailUserFav.avatarURL= detailUser.avatarUrl.toString()
    }
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}