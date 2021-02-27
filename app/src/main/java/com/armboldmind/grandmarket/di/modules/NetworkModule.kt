package com.armboldmind.grandmarket.di.modules

import android.content.Context
import com.armboldmind.grandmarket.data.network.services.IUserService
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder().header("Content-Type", "application/json").header("Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzOGQ3ZGY4Yi02YmQ1LTQyNjgtOTBiOC1jZTkxZjRkYmE5YzgiLCJ1bmlxdWVfbmFtZSI6IiszNzQ5Mzg3NjM3OCIsImp0aSI6IjFiYTQzZmQyLThhOGEtNDQ4YS1hMGRjLTlmNzY0MDkwYTE1MiIsImlhdCI6MTYxMzEzOTU5MSwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVXNlciIsIm5iZiI6MTYxMzEzOTU5MCwiZXhwIjoxNjIxNzc5NTkwLCJpc3MiOiJ3ZWJBcGkiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjUwMDIvIn0.QZc1Sq45f-24aKG92wuQ7pzrI-pa6HsVzlYqD6NKSUU")
                .build()
            val response = chain.proceed(request)
            response
        }.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl("https://apishop.yerevan-city.am/").addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): IUserService {
        return retrofit.create(IUserService::class.java)
    }


}