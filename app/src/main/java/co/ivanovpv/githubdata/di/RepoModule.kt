package co.ivanovpv.githubdata.di

import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.repo.contract.IGithubRepo
import co.ivanovpv.githubdata.repo.implementation.GithubRepo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideGithubRepo(
        api: GithubAPI,
        gson: Gson
    ): IGithubRepo = GithubRepo(api, gson)


}