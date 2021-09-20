package com.example.userlist_jerry.api

import com.example.userlist_jerry.model.UserDataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceInterface {
    @GET("/users")
    fun getListUsers(): Observable<ArrayList<UserDataModel>>

    @GET("/users/{id}")
    fun getUserInfo(@Path("id") userId: Int): Observable<UserDataModel>

}