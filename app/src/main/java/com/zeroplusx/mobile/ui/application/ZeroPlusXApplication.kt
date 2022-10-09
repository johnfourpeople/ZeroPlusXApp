package com.zeroplusx.mobile.ui.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeroPlusXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ZeroPlusXApplication.applicationContext = this
    }

    companion object {

        lateinit var applicationContext: ZeroPlusXApplication
            private set
    }
}
