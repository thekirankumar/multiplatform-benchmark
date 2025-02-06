package com.thekirankumar.crossplatformbenchmark

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
    fun currentTimeMillis(): Long
    fun openUseCase(useCase: UseCase)
    fun remoteAPI(): String
    fun supportedStacks() : List<FrameworkStack>
}

expect fun getPlatform(): Platform