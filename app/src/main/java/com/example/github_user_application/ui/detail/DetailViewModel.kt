package com.example.github_user_application.ui.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_application.BuildConfig
import com.example.github_user_application.data.remote.response.DetailUserResponse
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailuser: LiveData<DetailUserResponse> = _detailUser

    private var _listFollower = MutableLiveData<List<ItemsItem?>>()
    val listFollower: MutableLiveData<List<ItemsItem?>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem?>>()
    val listFollowing: LiveData<List<ItemsItem?>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getDetailUser(username:String){
        _isLoading.value = true
        val client= ApiConfig.getApiService().getDetailUser(username,BuildConfig.Token)
        client.enqueue(object :Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()?.copy()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
    fun getListFollower(username: String){
        _isLoading.value = true
        val client= ApiConfig.getApiService().getUserFollowers(username,BuildConfig.Token)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value=response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getListFollowing(username: String){
        _isLoading.value = true
        val client= ApiConfig.getApiService().getUserFollowing(username,BuildConfig.Token)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value=response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
