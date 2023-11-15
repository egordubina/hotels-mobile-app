package ru.egordubina.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.egordubina.data.repositories.HotelRepositoryImpl
import ru.egordubina.domain.repositories.HotelRepository

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun hotelRepositoryProvide(): HotelRepository = HotelRepositoryImpl()
}