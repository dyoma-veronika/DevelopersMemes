package com.example.ktdeveloperslife.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceProvider {


    companion object {
        private var instance: Api? = null

        @Synchronized
        fun buildService(): Api {
            if (instance == null)
                instance = Retrofit.Builder()
                    .baseUrl("https://developerslife.ru")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(Api::class.java)
            return instance as Api
        }


    }
        /* private val INSTANCE: Api? = null
         private val lock = Any()

         //??!!
         fun buildService(): Api? {
             if (NetworkServiceProvider.INSTANCE == null) {
                 synchronized(NetworkServiceProvider.lock) {
                     if (NetworkServiceProvider.INSTANCE == null) {
                         NetworkServiceProvider.INSTANCE = Retrofit.Builder()
                             .baseUrl("https://developerslife.ru")
                             .addConverterFactory(GsonConverterFactory.create())
                             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                             .build().create(Api::class.java)
                     }
                 }
             }
             return NetworkServiceProvider.INSTANCE
         }*/


}