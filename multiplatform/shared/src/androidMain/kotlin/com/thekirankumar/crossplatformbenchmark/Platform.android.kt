package com.thekirankumar.crossplatformbenchmark

import android.content.Context

object AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    private lateinit var handleUseCase: (Context, UseCase) -> Unit
    private lateinit var appContext: Context

    fun initialize(context: Context, handleUseCase: (Context, UseCase) -> Unit) {
        appContext = context
        this.handleUseCase = handleUseCase
    }

    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun openUseCase(useCase: UseCase) {
        check(::handleUseCase.isInitialized) { "AndroidPlatform has not been initialized. Call AndroidPlatform.initialize(context, handler) first." }
        handleUseCase(appContext, useCase)
    }

    override fun remoteAPI(): String = "http://10.0.2.2:3000"
    override fun supportedStacks(): List<FrameworkStack> = listOf(FrameworkStacks.ComposeStack, FrameworkStacks.ReactNativeStack)

}

actual fun getPlatform(): Platform = AndroidPlatform
