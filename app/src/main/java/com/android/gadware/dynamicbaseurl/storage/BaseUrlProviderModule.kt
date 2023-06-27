package com.android.gadware.dynamicbaseurl.storage

import com.android.gadware.dynamicbaseurl.storage.BaseUrlManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlProviderModule {

    @Provides
    @Singleton
    fun provideBaseUrlManager(): BaseUrlManager {
        return BaseUrlManager
    }
}
