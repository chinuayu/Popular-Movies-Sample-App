package me.ayushig.viewmodel.base

import android.app.Application
import me.ayushig.viewmodel.di.component.ApplicationComponent
import me.ayushig.viewmodel.di.component.DaggerApplicationComponent

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
    }

    companion object {
        lateinit var component: ApplicationComponent
        fun getApplicationComponent(): ApplicationComponent {
            return component
        }
    }
}
