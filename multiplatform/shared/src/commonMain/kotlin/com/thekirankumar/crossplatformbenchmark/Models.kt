package com.thekirankumar.crossplatformbenchmark


// Data Class for representing a Technology/Framework stack
data class FrameworkStack(val title: String, val id: String)
// Data Class for Use Cases
data class UseCase(val stack: String, val id: String, val title: String, val description: String? = null)
// Data Class for Nested Use Cases
data class NestedUseCase(val root: UseCase, val children: List<UseCase>)

object FrameworkStacks {
    val ReactNativeStack = FrameworkStack(Stacks.STACK_REACT_NATIVE_TITLE, Stacks.STACK_REACT_NATIVE_ID)
    val ComposeStack = FrameworkStack(Stacks.STACK_COMPOSE_TITLE, Stacks.STACK_COMPOSE_ID)
    val SwiftUIStack = FrameworkStack(Stacks.STACK_SWIFTUI_TITLE, Stacks.STACK_SWIFTUI_ID)
}

object UseCases {
    // Intent ID key
    const val USE_CASE_INTENT_ID = "usecase_id"
    const val USE_CASE_INTENT_TITLE = "usecase_title"

    // Redux Use Cases
    const val REDUX_ID = "redux_id"
    const val REDUX_TITLE = "Redux"
    const val REDUX_COUNTER_ID = "redux_counter_id"
    const val REDUX_COUNTER_TITLE = "Redux Counter"
    
    // Image Use cases
    const val IMAGE_ID = "image_id"
    const val IMAGE_TITLE = "Images"
    const val IMAGE_REMOTE_ID = "image_remote_id"
    const val IMAGE_REMOTE_TITLE = "Remote images"
}

object Stacks {
    // Stacks
    const val STACK_SWIFTUI_ID = "stack_swiftui_id"
    const val STACK_SWIFTUI_TITLE = "SwiftUI"
    const val STACK_REACT_NATIVE_ID = "stack_react_native_id"
    const val STACK_REACT_NATIVE_TITLE = "React Native"
    const val STACK_COMPOSE_ID = "stack_compose_id"
    const val STACK_COMPOSE_TITLE = "Compose"
}