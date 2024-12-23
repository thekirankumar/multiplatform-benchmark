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
        return DefaultReactActivityDelegate(this, getMainComponentName()!!, fabricEnabled)
    }
}
