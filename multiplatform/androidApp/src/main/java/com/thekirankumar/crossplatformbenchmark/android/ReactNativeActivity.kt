package com.thekirankumar.crossplatformbenchmark.android

import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate


class ReactNativeActivity : ReactActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("React Native")

    }


    protected override fun getMainComponentName(): String? {
        return "HelloWorld"
    }

    protected override fun createReactActivityDelegate(): ReactActivityDelegate {
        return object : ReactActivityDelegate (this, mainComponentName) {
            override fun getLaunchOptions(): Bundle? {
                // Retrieve data from the Intent
                val useCaseId = intent?.getStringExtra(UseCases.USE_CASE_INTENT_ID) ?: "default"

                // Pass the data to the React Native app
                val initialProps = Bundle()
                initialProps.putString("useCaseID", useCaseId)
                return initialProps
            }

            override fun isFabricEnabled(): Boolean {
                return fabricEnabled
            }
        }
    }
}
