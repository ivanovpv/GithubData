package co.ivanovpv.githubdata.di

import android.content.Context
import co.ivanovpv.githubdata.api.GithubAPI
import co.ivanovpv.githubdata.app.AppConfiguration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        appConfiguration: AppConfiguration,
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(appConfiguration.getGithubBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            //.addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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
                //addHeader("App-Version", BuildConfig.VERSION_NAME)
                addHeader("Platform", "Android")
            }
            return@Interceptor chain.proceed(builder.build())
        }

    @Singleton
    @Provides
    fun provideHttpClient(appConfiguration: AppConfiguration):HttpClient{
        return HttpClient(Android){
            install(Logging){
                level= LogLevel.ALL
            }
            install(DefaultRequest){
                url(appConfiguration.getGithubBaseUrl())
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                //header("X-Api-Key",BuildConfig.API_KEY)
            }
            install(ContentNegotiation){
                json(Json)
            }
        }
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default

}