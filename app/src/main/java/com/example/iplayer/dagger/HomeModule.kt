package com.example.iplayer.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iplayer.repositories.ITunesRepository
import com.example.iplayer.repositories.RoomRepository
import com.example.iplayer.ui.home.HomeActivity
import com.example.iplayer.ui.home.HomeViewModel
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
        fun provideHomeViewModel(
            iTunesRepository: ITunesRepository,
            roomRepository: RoomRepository
        ) : ViewModel =
            HomeViewModel(
                iTunesRepository,
                roomRepository
            )
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