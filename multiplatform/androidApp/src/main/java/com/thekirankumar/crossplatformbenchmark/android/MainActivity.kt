package com.thekirankumar.crossplatformbenchmark.android

import SwipeableTabs
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.thekirankumar.crossplatformbenchmark.android.ComposeActivity
import com.thekirankumar.crossplatformbenchmark.android.ReactNativeActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MainScreen(
//                onComposeClick = {
//                    val intent = Intent(this, ComposeActivity::class.java)
//                    startActivity(intent)
//                },
//                onReactNativeClick = {
//                    val intent = Intent(this, ReactNativeActivity::class.java)
//                    startActivity(intent)
//                }
//            )
            SwipeableTabs()
        }
    }
}

@Composable
fun MainScreen(onComposeClick: () -> Unit, onReactNativeClick: () -> Unit) {

}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onComposeClick = {}, onReactNativeClick = {})
}