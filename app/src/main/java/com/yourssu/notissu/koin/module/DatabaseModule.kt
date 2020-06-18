package com.yourssu.notissu.koin.module

import com.yourssu.notissu.koin.repository.DatabaseRepository
import com.yourssu.notissu.koin.repositoryimpl.DatabaseRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<DatabaseRepository> { DatabaseRepositoryImpl(androidApplication()) }
}