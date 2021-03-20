package com.example.ktdeveloperslife.network

class Result
    (private var result: List<Post>, private var totalCount: Int) {

    fun getResult(): List<Post> {
        return result
    }

    fun getTotalCount(): Int {
        return totalCount
    }

}