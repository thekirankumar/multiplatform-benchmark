package com.thekirankumar.crossplatformbenchmark.android

import SwipeableTabs
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thekirankumar.crossplatformbenchmark.AndroidPlatform
import com.thekirankumar.crossplatformbenchmark.Stacks
import com.thekirankumar.crossplatformbenchmark.UseCase
import com.thekirankumar.crossplatformbenchmark.UseCases

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidPlatform.initialize(this, MainActivity::openUseCase)
        setContent {
            SwipeableTabs()
        }
    }

    companion object {
        fun openUseCase(context: Context, usecase: UseCase) {
            val intent = if (Stacks.STACK_COMPOSE_ID == usecase.stack) {
                Intent(context, ComposeActivity::class.java)
            } else {
                Intent(context, ReactNativeActivity::class.java)
            }
            intent.putExtra(UseCases.USE_CASE_INTENT_ID, usecase.id)
            intent.putExtra(UseCases.USE_CASE_INTENT_TITLE, usecase.title)
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }

    }

}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SwipeableTabs()
}