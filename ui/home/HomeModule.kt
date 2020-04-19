package com.example.iplayer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iplayer.dagger.ViewModelKey
import com.example.iplayer.repositories.ITunesRepository
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    HomeModule.ProvideHomeViewModel::class
])
abstract class HomeModule {

    @ContributesAndroidInjector(
        modules = [
            InjectViewModel::class
        ]
    )
    abstract fun bind() : HomeActivity

    @Module
    class ProvideHomeViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        fun provideHomeViewModel(iTunesRepository: ITunesRepository) : ViewModel =
            HomeViewModel(iTunesRepository)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideHomeViewModel(
            factory: ViewModelProvider.Factory,
            target: HomeActivity
        ) = ViewModelProvider(target, factory).get(HomeViewModel::class.java)
    }

}