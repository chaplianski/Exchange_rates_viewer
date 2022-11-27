package com.chaplianski.exchangeratesviewer.di

import android.content.Context
import com.chaplianski.exchangeratesviewer.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    fun mainActivityInject(mainActivity: MainActivity): MainActivity

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}