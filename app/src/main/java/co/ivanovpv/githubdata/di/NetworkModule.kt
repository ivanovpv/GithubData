package co.ivanovpv.githubdata.di

import android.content.Context
import co.ivanovpv.githubdata.BuildConfig
import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.app.AppConfiguration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGithubAPI(retrofit: Retrofit): GithubAPI {
        return retrofit.create(GithubAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        appConfiguration: AppConfiguration,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(appConfiguration.getGithubBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        headersInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(headersInterceptor)

            if (AppConfiguration.IS_NETWORK_LOGGING_ENABLED) {
                val logging = HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                addInterceptor(logging)

            }

            cache(
                Cache(
                    directory = File(context.cacheDir, AppConfiguration.CACHE_DIR),
                    maxSize = AppConfiguration.CACHE_SIZE
                )
            )
            connectTimeout(AppConfiguration.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(AppConfiguration.WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(AppConfiguration.READ_TIMEOUT, TimeUnit.SECONDS)
            followRedirects(false)
        }.build()

    @Provides
    @Singleton
    fun provideHeadersInterceptor(): Interceptor =
        Interceptor { chain: Interceptor.Chain ->
            val builder = chain.request().newBuilder().apply {
                addHeader("Content-Type", "application/vnd.api+json")
                addHeader("Accept", "application/json")
                addHeader("App-Version", BuildConfig.VERSION_NAME)
                addHeader("Platform", "Android")
            }
            return@Interceptor chain.proceed(builder.build())
        }

}