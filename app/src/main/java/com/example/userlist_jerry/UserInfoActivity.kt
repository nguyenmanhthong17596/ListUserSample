package com.example.userlist_jerry

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.userlist_jerry.model.UserDataModel
import com.example.userlist_jerry.model.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlin.random.Random

class UserInfoActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {

    private var imageList = ArrayList<Int>()
    private var userData: UserDataModel? = null
    private val userRepo: UserRepository by lazy {
        UserRepository.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        userData = intent.getSerializableExtra(EXTRA_DATA) as? UserDataModel
        initListImage()
        initializeView()
    }

    private fun initListImage(){
        imageList.add(R.drawable.hinh2)
        imageList.add(R.drawable.hinh3)
        imageList.add(R.drawable.hinh4)
        imageList.add(R.drawable.hinh5)
        imageList.add(R.drawable.hinh6)
        imageList.add(R.drawable.hinh7)
        imageList.add(R.drawable.hinh8)
        imageList.add(R.drawable.hinh9)
        imageList.add(R.drawable.hinh11)
    }

    private fun initializeView(){
        val ranIndex = Random.nextInt(imageList.size)
        val ranImage = imageList[ranIndex]
        Glide.with(this)
            .load(ranImage)
            .placeholder(R.color.black_main)
            .error(R.color.black_main)
            .into(userBackground)

        buttonBack.setOnClickListener { supportFinishAfterTransition() }
        swipeRefreshLayout.setOnRefreshListener(this)

        getUserInfo()
    }

    @SuppressLint("CheckResult")
    private fun getUserInfo(){
        userData ?: return
        userRepo.getUser(userData!!.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                fetchData(it)
            },{error->
                Log.e("THONG","${error.localizedMessage}")
            })
    }

    private fun fetchData(data: UserDataModel?){
        data ?: return
        Glide.with(this)
            .load(data.avatar_url)
            .placeholder(R.color.black_main)
            .error(R.color.black_main)
            .centerCrop()
            .into(userAva)

        username.text = data.name ?: "userName - Null"
        location.text = data.location ?: "location"
        textNumberFollower.text = data.followers.toString()
        numberFollowing.text = data.following.toString()
        textNumberRepos.text = data.public_repos.toString()
        textUserBio.text = data.bio ?: "User bio here"

    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
        fun navigateToUserInfo(activity: Activity, data: UserDataModel?) {
            activity.startActivity(Intent(activity, UserInfoActivity::class.java).apply {
                putExtra(EXTRA_DATA, data)
            })
        }
    }

    override fun onRefresh() {
        initializeView()
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }


}