package com.example.github_user_application.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_application.R
import com.example.github_user_application.data.datastore.SettingPreference
import com.example.github_user_application.data.datastore.dataStore
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.databinding.ActivityMainBinding
import com.example.github_user_application.ui.favorite.FavoriteActivity
import com.example.github_user_application.ui.settings.SettingViewModel
import com.example.github_user_application.ui.settings.SettingViewModelFactory
import com.example.github_user_application.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var search:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        search="a"
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.getUsers(search)
        mainViewModel.listUser.observe(this){
            setData(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }
        binding.searchBar.inflateMenu(R.menu.option_menu_main)
        binding.searchBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId){
                R.id.mt_option_favorite->{
                    val intent= Intent(binding.root.context, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.mt_option_settings->{
                    val intent= Intent(binding.root.context, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }else->false
            }
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, i, keyEvent ->
                    searchBar.text=searchView.text
                    search=searchBar.text.toString()
                    searchView.hide()
                    mainViewModel.getUsers(search)
                    false
                }
        }
        checktheme()
    }

    private fun setData(users: List<ItemsItem?>?){
        val adapter= UserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter=adapter
    }
    private fun showLoading(load:Boolean){
        if (load){
            binding.progressBar.visibility= View.VISIBLE
        }else{
            binding.progressBar.visibility= View.GONE
        }
    }
    private fun checktheme(){
        val pref = SettingPreference.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}