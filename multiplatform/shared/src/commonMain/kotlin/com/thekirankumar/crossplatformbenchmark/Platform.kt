package com.thekirankumar.crossplatformbenchmark

interface Platform {
    val name: String
    fun currentTimeMillis(): Long
}

expect fun getPlatform(): Platform