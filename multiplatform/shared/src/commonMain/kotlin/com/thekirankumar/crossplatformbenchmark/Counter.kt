package com.thekirankumar.crossplatformbenchmark

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.reduxkotlin.Reducer
import org.reduxkotlin.Store
import org.reduxkotlin.createStore
import org.reduxkotlin.threadsafe.createThreadSafeStore

// Actions
sealed class CounterAction {
    data object Increment : CounterAction()
}

// State
data class CounterState(
    val counter: Int = 0,
    val rate: Float = 0f,
    val timestamps: List<Long> = emptyList()
)

// We are keeping a moving window of 5 seconds, All timestamps within our window will be kept in `state.timestamps`
// Windowing for FPS calculation
val WINDOW_DURATION_MS = 5000L

// Reducer
val counterReducer: Reducer<CounterState> = { state, action ->
    when (action) {
        is CounterAction.Increment -> {
            val currentTime = getPlatform().currentTimeMillis()
            val newTimestamps = state.timestamps
                .filter { it > currentTime - WINDOW_DURATION_MS }
                .plus(currentTime)

            val rate = if (newTimestamps.size > 1) {
                newTimestamps.size / (WINDOW_DURATION_MS / 1000f)
            } else {
                0f
            }

            CounterState(
                counter = state.counter + 1,
                rate = rate,
                timestamps = newTimestamps
            )
        }

        else -> state
    }
}

// Store
val store = createStore(counterReducer, CounterState())

// Main Composable
@Composable
fun CounterApp() {
    val counterState by store.subscribeAsState { it }
    val scope = rememberCoroutineScope()

    // Auto-increment effect
    SideEffect {
        scope.launch {
            store.dispatch(CounterAction.Increment)
        }
    }

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Counter: ${counterState.counter}", fontSize = 100.sp)
        Text(text = "FPS: ${counterState.rate}", fontSize = 20.sp)
    }
}

// Subscription
@Composable
fun <T> Store<T>.subscribeAsState(selector: (T) -> T = { it }): State<T> {
    val selectedState = remember { mutableStateOf(selector(state)) }
    DisposableEffect(this) {
        val unsubscribe = subscribe {
            selectedState.value = selector(state)
        }
        onDispose { unsubscribe() }
    }
    return selectedState
}