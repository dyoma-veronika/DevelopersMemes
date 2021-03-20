package com.example.ktdeveloperslife.network

import java.util.*
import kotlin.collections.ArrayList

class CacheArrayList<T>
    (private var localCache: MutableList<T> = ArrayList(), private var position: Int = -1) {

    operator fun hasNext(): Boolean {
        return position + 1 < localCache.size
    }

    fun hasPrevious(): Boolean {
        return position - 1 >= 0
    }

    operator fun next(): T {
        position++
        return localCache[position]
    }

    fun previous(): T {
        position--
        return localCache[position]
    }

    fun addAll(collection: Collection<T>?) {
        localCache.addAll(collection!!)
    }

}