package com.example.userlist_jerry.api.repo

import com.example.userlist_jerry.api.ApiServiceExecuter
import com.example.userlist_jerry.api.ApiServiceInterface
import com.example.userlist_jerry.model.UserDataModel
import io.reactivex.Observable

class UserRepo {
    companion object{
        private var INSTANCE: UserRepo? = null
        fun getInstance(): UserRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance().also { INSTANCE = it }
            }
        }
        fun newInstance() = UserRepo()

    }

    private val service: ApiServiceInterface by lazy {
        ApiServiceExecuter.createService(ApiServiceInterface::class.java)
    }

    fun getUserList(): Observable<ArrayList<UserDataModel>>{
        return service.getListUsers()
    }

    fun getUserDetail(id: Int): Observable<UserDataModel>{
        return service.getUserInfo(id)
    }
}