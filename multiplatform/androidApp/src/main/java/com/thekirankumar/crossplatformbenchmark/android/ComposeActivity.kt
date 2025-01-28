package com.thekirankumar.crossplatformbenchmark.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thekirankumar.crossplatformbenchmark.CounterApp
import com.thekirankumar.crossplatformbenchmark.ProductGrid
import com.thekirankumar.crossplatformbenchmark.RemoteImageGrid

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val useCaseId = intent.getStringExtra(UseCases.USE_CASE_INTENT_ID) ?: "Unknown Use Case ID"
        val useCaseTitle = intent.getStringExtra(UseCases.USE_CASE_INTENT_TITLE) ?: "Unknown Use Case Title"

        setTitle("Native $useCaseTitle");

        setContent {
            ComposeContent(useCaseId, useCaseTitle)
        }
    }

    @Composable
    fun ComposeContent(useCaseId: String, useCaseTitle: String) {
        when(useCaseId) {
            UseCases.REDUX_COUNTER_ID -> CounterUseCase()
            UseCases.IMAGE_REMOTE_ID -> RemoteImageGrid()
        }
    }
}

@Preview
@Composable
fun CounterUseCase() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        repeat(5 ){ CounterApp()}
    }
}

