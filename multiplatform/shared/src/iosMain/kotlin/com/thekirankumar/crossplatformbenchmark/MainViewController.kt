package com.thekirankumar.crossplatformbenchmark

import SwipeableTabs
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController(useCaseId: String) = ComposeUIViewController {
    when(useCaseId) {
        UseCases.REDUX_COUNTER_ID -> CounterApp()
        UseCases.IMAGE_REMOTE_ID -> RemoteImageGrid()
        else -> SwipeableTabs()
    }
    //RemoteImageGrid()
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        items(3 ){ }
//    }
}