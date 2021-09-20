package com.example.userlist_jerry

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.annotation.GlideModule
import com.example.userlist_jerry.model.UserDataModel
import com.example.userlist_jerry.model.UserItem
import com.example.userlist_jerry.model.UserRepository
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_item.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val userRepo: UserRepository by lazy {
        UserRepository.getInstance()
    }

    private var adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()

    }

    private fun initializeView() {
        listUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listUser.adapter = adapter
        getListUser()
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    @SuppressLint("CheckResult")
    private fun getListUser() {
        adapter.clear()
        userRepo.getUserList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    for (i in it) {
                        adapter.add(UserItem(i, onItemClickListener))
                    }
                }, { error ->
                    Log.e("THONG","${error.localizedMessage}")
                })
    }

    private val onItemClickListener = object : UserItem.OnItemClickListener {
        override fun onItemClick(user: UserDataModel) {
            navigateToUserProfile(user)
        }

    }

    private fun navigateToUserProfile(userData: UserDataModel) {
        UserInfoActivity.navigateToUserInfo(this, userData)
    }

    override fun onRefresh() {
        getListUser()
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

}