package top.aliyunm.example

import android.app.Application
import android.content.Context

class IApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}