package com.example.ktdeveloperslife.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/{type}/{page}?json=true")
    fun getPosts(@Path("type") type: String?, @Path("page") page: Int): Single<Result>
}