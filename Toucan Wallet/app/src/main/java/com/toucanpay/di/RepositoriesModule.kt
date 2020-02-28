package com.toucanpay.di

import com.toucanpay.data.prefs.UserPreferences
import com.toucanpay.data.remote.repositories.*
import com.toucanpay.data.repositories.*
import org.koin.dsl.module.module

val repositoriesModule = module {

    factory<UserRemoteRepository> { UserRemoteRepositoryImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory<MessagesRemoteRepository> { MessagesRemoteRepositoryImpl(get(), get()) }
    factory<MessagesRepository> { MessagesRepositoryImpl(get()) }
    factory<WalletsRemoteRepository> { WalletsRemoteRepositoryImpl(get(), get()) }
    factory<WalletsRepository> { WalletsRepositoryImpl(get()) }
    factory<RegisterRewardRemoteRepository> { RegisterRewardRemoteRepositoryImpl(get()) }
    factory<RegisterRewardRepository> { RegisterRewardRepositoryImpl(get(), get()) }
    factory<SwapsRemoteRepository> { SwapsRemoteRepositoryImpl(get(), get()) }
    factory<SwapsRepository> { SwapsRepositoryImpl(get()) }

    single { UserPreferences() }
}