package com.thekirankumar.crossplatformbenchmark

import platform.UIKit.UIDevice

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override fun currentTimeMillis(): Long {
        val currentTimeSeconds = NSDate().timeIntervalSince1970
        return (currentTimeSeconds * 1000).toLong()
    }
}

actual fun getPlatform(): Platform = IOSPlatform()