package com.jarvis.assistant

import android.app.Application

class JarvisApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: JarvisApplication
            private set
    }
}
