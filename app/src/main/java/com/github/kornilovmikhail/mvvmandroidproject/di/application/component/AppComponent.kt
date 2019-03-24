package com.github.kornilovmikhail.mvvmandroidproject.di.application.component

import android.app.Application
import com.github.kornilovmikhail.mvvmandroidproject.di.application.module.ApplicationModule
import com.github.kornilovmikhail.mvvmandroidproject.di.application.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun provideApp(): Application
}