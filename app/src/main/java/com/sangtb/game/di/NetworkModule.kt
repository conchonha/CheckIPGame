package com.sangtb.game.di

import com.sangtb.game.data.ApiIpList
import com.sangtb.game.data.repository.IpRepositoryImpl
import com.sangtb.game.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    @RetrofitAddress
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @RetrofitVietNam
    fun provideRetrofitVietNam(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL_VIETNAM)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ApiIPAddress
    fun provideApiServices(@RetrofitAddress retrofit: Retrofit): ApiIpList {
        return retrofit.create(ApiIpList::class.java)
    }

    @Provides
    @Singleton
    @ApiIPVietNam
    fun provideApiServicesVietNam(@RetrofitVietNam retrofit: Retrofit): ApiIpList {
        return retrofit.create(ApiIpList::class.java)
    }

    @Singleton
    @Provides
    fun provideIpListRepository(@ApiIPAddress apiIpList: ApiIpList,@ApiIPVietNam apiIPVietNam1: ApiIpList): IpRepositoryImpl {
        return IpRepositoryImpl(apiIpList,apiIPVietNam1)
    }
}