package com.jairoavila.di

import com.jairoavila.repository.HeroRepository
import com.jairoavila.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> { HeroRepositoryImpl() }
}