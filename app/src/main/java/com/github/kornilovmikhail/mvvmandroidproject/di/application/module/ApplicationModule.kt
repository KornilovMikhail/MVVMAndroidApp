package com.github.kornilovmikhail.mvvmandroidproject.di.application.module

import android.app.Application
import android.content.Context
import com.github.kornilovmikhail.mvvmandroidproject.di.application.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {
    @Provides
    @ApplicationScope
    fun provideApp(): Application = app

    @Provides
    @ApplicationScope
    fun provideContext(): Context = app.applicationContext
}
