package com.example.github_user_application.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_application.data.remote.response.GitHubResponse
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<ItemsItem?>>()
    val listUser: LiveData<List<ItemsItem?>?> = _listUser

    companion object{
        private var _search="a"
    }

    fun getUsers(search:String){
        _isLoading.value=true
        if (search != "") {
            _search =search
        }
        val client= ApiConfig.getApiService().getUsers(_search)
        client.enqueue(object :Callback<GitHubResponse>{
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _listUser.value= response.body()?.items!!
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }

        })
    }
}