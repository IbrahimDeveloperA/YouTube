package com.example.youtube

import android.app.Application
import com.example.youtube.di.koinModules
import com.example.youtube.repository.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    //Это DI в чистом виде
//    companion object {
//        //Это сингл сылка, один на весь проект
//        val repository: Repository by lazy { Repository() }
//    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //даем ему контекст и говорим что он будет
            // (что бы коин мог проинициализироватся и он будет здесь инициалищироватся )
            androidContext(this@App)
            modules(koinModules)
        }
    }

}