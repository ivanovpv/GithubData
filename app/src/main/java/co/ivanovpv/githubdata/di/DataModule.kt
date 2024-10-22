package co.ivanovpv.githubdata.di

import co.ivanovpv.githubdata.api.GithubService
import co.ivanovpv.githubdata.api.GithubServiceImpl
import co.ivanovpv.githubdata.data.repository.GithubRepositoryImpl
import co.ivanovpv.githubdata.domain.repository.GithubRepository
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
    abstract fun bindGithuService(githubServiceImpl: GithubServiceImpl): GithubService

}