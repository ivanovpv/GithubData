package co.ivanovpv.githubdata.di

import co.ivanovpv.githubdata.data.datasource.GithubDataSource
import co.ivanovpv.githubdata.data.datasource.GithubDataSourceImpl
import co.ivanovpv.githubdata.data.repository.GithubRepositoryImpl
import co.ivanovpv.githubdata.data.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository

    @Binds
    @Singleton
    abstract fun bindGithubDataSource(githubDataSourceImpl: GithubDataSourceImpl): GithubDataSource

}