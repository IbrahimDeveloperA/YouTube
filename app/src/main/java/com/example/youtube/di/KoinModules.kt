package com.example.youtube.di

import com.example.youtube.core.network.remoteModule
import com.example.youtube.data.remote.remoteDataSource
import org.koin.core.module.Module

val koinModules = listOf<Module>(
    repoModules, viewModules,remoteModule,remoteDataSource
)