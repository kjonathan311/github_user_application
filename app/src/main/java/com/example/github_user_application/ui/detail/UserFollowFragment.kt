package com.example.github_user_application.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_application.data.remote.response.ItemsItem
import com.example.github_user_application.databinding.FragmentUserFollowBinding
import com.example.github_user_application.ui.main.UserAdapter

class UserFollowFragment : Fragment() {

    private var _binding:FragmentUserFollowBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentUserFollowBinding.inflate(inflater,container,false)
        val view= binding.root
        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvUserFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUserFollow.addItemDecoration(itemDecoration)

        val index=arguments?.getInt(PAGER_NUMBER,0)
        val username=arguments?.getString("username","")
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        detailViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        if (index==1){
            if (username != null) {
                detailViewModel.getListFollower(username)
            }
            detailViewModel.listFollower.observe(viewLifecycleOwner){
                setData(it)
            }
        }else{
            if (username != null) {
                detailViewModel.getListFollowing(username)
            }
            detailViewModel.listFollowing.observe(viewLifecycleOwner){
                setData(it)
            }
        }
    }

    private fun setData(Users: List<ItemsItem?>?){
        val adapter= UserAdapter()
        adapter.submitList(Users)
        binding.rvUserFollow.adapter=adapter
    }

    private fun showLoading(load:Boolean){
        if (load==true){
            binding.pbFragmentDetail.visibility= View.VISIBLE
        }else{
            binding.pbFragmentDetail.visibility= View.GONE
        }
    }

    companion object{
        const val PAGER_NUMBER = "1"
    }

}