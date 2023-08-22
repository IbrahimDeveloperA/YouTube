package com.example.youtube.di

import com.example.youtube.repository.Repository
import org.koin.dsl.module

val repoModules = module {
    single { Repository(get(),get()) }
}
//Repositury это фактори (потомучто приходят разные дыннные)