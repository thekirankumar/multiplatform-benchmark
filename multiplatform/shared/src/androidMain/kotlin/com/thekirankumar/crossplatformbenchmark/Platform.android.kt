package com.thekirankumar.crossplatformbenchmark

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}

actual fun getPlatform(): Platform = AndroidPlatform()