package com.example.bluebits

import android.app.Application
import com.example.bluebits.di.dataSourceModule
import com.example.bluebits.di.repositoryModule
import com.example.bluebits.di.useCaseWrapperModule
import com.example.bluebits.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlueBits: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BlueBits)
            modules(
                dataSourceModule,
                repositoryModule,
                useCaseWrapperModule,
                viewModelModule,
//                workManagerModule
            )
        }
    }
}