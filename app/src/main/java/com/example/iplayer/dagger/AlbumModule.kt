package com.example.iplayer.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iplayer.repositories.ITunesRepository
import com.example.iplayer.ui.album.AlbumActivity
import com.example.iplayer.ui.album.AlbumViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    AlbumModule.ProvideAlbumViewModel::class
])
abstract class AlbumModule {

    @ContributesAndroidInjector(
        modules = [
            InjectViewModel::class
        ]
    )
    abstract fun bind() : AlbumActivity

    @Module
    class ProvideAlbumViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(AlbumViewModel::class)
        fun provideHomeViewModel(iTunesRepository: ITunesRepository) : ViewModel
                = AlbumViewModel(iTunesRepository)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideAlbumViewModel(
            factory: ViewModelProvider.Factory,
            target: AlbumActivity
        ) = ViewModelProvider(target, factory).get(AlbumViewModel::class.java)
    }

}