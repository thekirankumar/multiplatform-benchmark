package com.thekirankumar.crossplatformbenchmark.android

import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.thekirankumar.crossplatformbenchmark.UseCases


class ReactNativeActivity : ReactActivity() {

    protected override fun getMainComponentName(): String {
        return "HelloWorld"
    }

    protected override fun createReactActivityDelegate(): ReactActivityDelegate {
        return object : ReactActivityDelegate (this, mainComponentName) {
            override fun getLaunchOptions(): Bundle {
                // Retrieve data from the Intent

                val useCaseId = intent?.getStringExtra(UseCases.USE_CASE_INTENT_ID) ?: "default"
                val useCaseTitle = intent?.getStringExtra(UseCases.USE_CASE_INTENT_TITLE) ?: "no title"

                // Pass the data to the React Native app
                val initialProps = Bundle()
                initialProps.putString("useCaseID", useCaseId)
                plainActivity.setTitle("React native: $useCaseTitle")
                return initialProps
            }

            override fun isFabricEnabled(): Boolean {
                return fabricEnabled
            }
        }
    }
}
