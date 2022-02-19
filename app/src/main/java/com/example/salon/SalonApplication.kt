package com.example.salon

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SalonApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}