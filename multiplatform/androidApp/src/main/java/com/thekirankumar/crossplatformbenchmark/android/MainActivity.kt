package com.thekirankumar.crossplatformbenchmark.android

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
            MainScreen(
                onComposeClick = {
                    val intent = Intent(this, ComposeActivity::class.java)
                    startActivity(intent)
                },
                onReactNativeClick = {
                    val intent = Intent(this, ReactNativeActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun MainScreen(onComposeClick: () -> Unit, onReactNativeClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onComposeClick) {
                Text(text = "Compose", fontSize = 20.sp)
            }
            Button(onClick = onReactNativeClick) {
                Text(text = "React Native", fontSize = 20.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onComposeClick = {}, onReactNativeClick = {})
}