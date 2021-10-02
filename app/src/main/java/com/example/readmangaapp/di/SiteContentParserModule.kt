package com.example.readmangaapp.di

import com.example.readmangaapp.data.SiteContentParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SiteContentParserModule {
        @Provides
        fun provideSiteContentParser() : SiteContentParser {
            return SiteContentParser()
        }
}