package com.example.ktdeveloperslife.network

class Post(private var id: Int, private var gifURL: String?, private var description: String?) {

    fun getId(): Int {
        return id
    }

    fun getGifURL(): String? {
        return gifURL
    }

    fun getDescription(): String? {
        return description
    }
}