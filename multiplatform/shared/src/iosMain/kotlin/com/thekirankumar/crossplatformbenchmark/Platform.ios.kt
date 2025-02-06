package com.thekirankumar.crossplatformbenchmark

import platform.UIKit.UIDevice

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

object IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    private lateinit var handleUseCase: (UseCase) -> Unit

    fun initialize(handleUseCase: (UseCase) -> Unit) {
        this.handleUseCase = handleUseCase
    }
    override fun currentTimeMillis(): Long {
        val currentTimeSeconds = NSDate().timeIntervalSince1970
        return (currentTimeSeconds * 1000).toLong()
    }

    override fun openUseCase(useCase: UseCase) {
        check(::handleUseCase.isInitialized) { "IOSPlatform has not been initialized. Call IOSPlatform.initialize(handler) first." }
        handleUseCase(useCase)
    }

    override fun remoteAPI(): String = "https://dummyjson.com"
    override fun supportedStacks(): List<FrameworkStack> = listOf(FrameworkStacks.SwiftUIStack, FrameworkStacks.ComposeStack, FrameworkStacks.ReactNativeStack)

}

actual fun getPlatform(): Platform = IOSPlatform