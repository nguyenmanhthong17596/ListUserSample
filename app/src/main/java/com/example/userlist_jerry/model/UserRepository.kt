package com.example.userlist_jerry.model

import com.example.userlist_jerry.api.ApiServiceExecuter
import com.example.userlist_jerry.api.ApiServiceInterface
import io.reactivex.Observable

class UserRepository {
    companion object {
        private var INSTANCE: UserRepository? = null
        fun getInstance(): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: newInstance().also { INSTANCE = it }
            }
        }
        private fun newInstance() = UserRepository()
    }

    private val service: ApiServiceInterface by lazy {
        ApiServiceExecuter.createService(ApiServiceInterface::class.java)
    }

    fun getUserList(): Observable<ArrayList<UserDataModel>> {
        return service.getListUsers()
    }

    fun getUser(id: Int): Observable<UserDataModel>{
        return service.getUserInfo(id)
    }
}