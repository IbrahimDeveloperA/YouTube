package com.example.youtube.di

import com.example.youtube.ui.details.viewModel.DetailViewModel
import com.example.youtube.ui.playVideo.viewModel.PlayVideoViewModel
import com.example.youtube.ui.playlists.viewModel.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { DetailViewModel(get()) }
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlayVideoViewModel(get()) }
}