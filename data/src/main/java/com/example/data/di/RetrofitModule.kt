package com.example.data.di

import android.content.Context
import com.example.core.shared.PreferencesManager
import com.example.data.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun providesPreferencesManager(@ApplicationContext context: Context) = PreferencesManager(context)

    @Provides
    @Named("LoggerInterceptor")
    @Singleton
    fun providesLoggerInterceptor(): Interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Named("ChuckInterceptor")
    @Singleton
    fun providesChuckInterceptor(@ApplicationContext context: Context): Interceptor = ChuckInterceptor(context)

    @Provides
    @Named("AuthInterceptor")
    fun providesAuthInterceptor(sharedPreferences: PreferencesManager, @ApplicationContext context: Context
    ): Interceptor = Interceptor {
        val requestBuilder = it.request().newBuilder().header("Content-Type", "application/json").removeHeader("Pragma")
        val request = requestBuilder.build()
        it.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("AuthInterceptor") authInterceptor: Interceptor,
        @Named("LoggerInterceptor") loggerInterceptor: Interceptor,
        @Named("ChuckInterceptor") chuckInterceptor: Interceptor
    ): OkHttpClient {
        val client =
            OkHttpClient.Builder().addInterceptor(authInterceptor).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(loggerInterceptor)
            client.addInterceptor(chuckInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().client(client).baseUrl(BuildConfig.BASEURL_STAGING).addConverterFactory(GsonConverterFactory.create()).build()


}