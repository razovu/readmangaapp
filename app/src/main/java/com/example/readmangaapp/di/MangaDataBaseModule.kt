package com.example.readmangaapp.di

import android.content.Context
import androidx.room.Room
import com.example.readmangaapp.db.MangaDatabase
import com.example.readmangaapp.db.MangaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MangaDataBaseModule {
    @Provides
    fun provideProfileDao(mangaDataBase: MangaDatabase) : MangaDao {
        return mangaDataBase.profileDao()
    }

    @Provides
    @Singleton
    fun provideMangaDatabase(@ApplicationContext appContext: Context) : MangaDatabase {
        return Room.databaseBuilder(
            appContext,
            MangaDatabase::class.java,
            "mangaentity"
        ).build()
    }
}