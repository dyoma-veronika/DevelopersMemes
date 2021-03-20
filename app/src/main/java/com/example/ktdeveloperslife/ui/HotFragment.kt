package com.example.ktdeveloperslife.ui

import com.example.ktdeveloperslife.network.CacheArrayList
import com.example.ktdeveloperslife.network.Post

class HotFragment : BaseFragment() {
    override fun getCache(): CacheArrayList<Post> {
        return CacheArrayList()
    }

    override fun getFirstPage(): Int {
        return 0
    }

    override fun getPagePath(): String? {
        return "hot"
    }
}