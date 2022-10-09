package com.zeroplusx.mobile.ui.di

import com.zeroplusx.mobile.data.repository.ApiArticlesRepository
import com.zeroplusx.mobile.data.repository.ApiSourcesRepository
import com.zeroplusx.mobile.domain.repository.ArticlesRepository
import com.zeroplusx.mobile.domain.repository.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface BindingModule {
    @Binds
    fun bindsArticlesRepository(repository: ApiArticlesRepository): ArticlesRepository

    @Binds
    fun bindsSourcesRepository(repository: ApiSourcesRepository): SourcesRepository
}
